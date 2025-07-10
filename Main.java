import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String rollNumber;
    private String grade;
    private String course;

    public Student(String name, String rollNumber, String grade, String course) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.course = course;
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    public String getCourse() { return course; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setCourse(String course) { this.course = course; }

    @Override
    public String toString() {
        return String.format("Name: %s | Roll No: %s | Grade: %s | Course: %s",
                name, rollNumber, grade, course);
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    public void addStudent(Student s) {
        students.add(s);
        System.out.println("Student added successfully.");
        saveToFile();
    }

    public void removeStudent(String rollNumber) {
        Student s = findStudentByRoll(rollNumber);
        if (s != null) {
            students.remove(s);
            System.out.println("Student removed.");
            saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void editStudent(String rollNumber, Scanner sc) {
        Student s = findStudentByRoll(rollNumber);
        if (s != null) {
            sc.nextLine(); // consume newline
            System.out.print("Enter new name: ");
            String name = sc.nextLine();
            System.out.print("Enter new grade: ");
            String grade = sc.nextLine();
            System.out.print("Enter new course: ");
            String course = sc.nextLine();

            if (!name.isEmpty()) s.setName(name);
            if (!grade.isEmpty()) s.setGrade(grade);
            if (!course.isEmpty()) s.setCourse(course);

            System.out.println("Student updated.");
            saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    public void searchStudent(String rollNumber) {
        Student s = findStudentByRoll(rollNumber);
        if (s != null) {
            System.out.println("Student Found: " + s);
        } else {
            System.out.println("Student not found.");
        }
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("All Students:");
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    private Student findStudentByRoll(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            students = (List<Student>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error loading from file.");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== Student Management System ====");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Edit Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Invalid input. Enter a number: ");
                sc.next();
            }

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    sc.nextLine(); // consume newline
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter roll number: ");
                    String roll = sc.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = sc.nextLine();
                    System.out.print("Enter course: ");
                    String course = sc.nextLine();

                    if (name.isEmpty() || roll.isEmpty() || grade.isEmpty() || course.isEmpty()) {
                        System.out.println("All fields are required.");
                    } else {
                        Student s = new Student(name, roll, grade, course);
                        sms.addStudent(s);
                    }
                    break;

                case 2:
                    System.out.print("Enter roll number to remove: ");
                    String rollToRemove = sc.next();
                    sms.removeStudent(rollToRemove);
                    break;

                case 3:
                    System.out.print("Enter roll number to edit: ");
                    String rollToEdit = sc.next();
                    sms.editStudent(rollToEdit, sc);
                    break;

                case 4:
                    System.out.print("Enter roll number to search: ");
                    String rollToSearch = sc.next();
                    sms.searchStudent(rollToSearch);
                    break;

                case 5:
                    sms.displayAllStudents();
                    break;

                case 6:
                    System.out.println("Exiting... Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
