import java.util.Scanner;

public class CalculateMarks{
    public static void main(String args[]){
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter the total NUmber of Subjest you have");
        int TotalSubject = Sc.nextInt();
        while(TotalSubject>0){
            int[] sub = new int[TotalSubject];
            int Total = 0;
            for(int i=0;i<=TotalSubject;i++){
                System.out.println("Enter The marks of "+i+"Subject");
                sub[i] = Sc.nextInt();
                Total += sub[i];
            }
            int GrandTotal = TotalSubject * 100;

        }
    }
}