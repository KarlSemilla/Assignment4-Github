import java.io.*;
import java.util.*;

public class Client
{
    public static void main(String args[]) throws IOException
    {
        // define necessary variables here
        String ans;
        char choice = ' ';
        Scanner kb = new Scanner(System.in);
        
        Payroll pay = new Payroll();
        // place here the code for the processing requirements
        while (choice != 'Q')
        {
            showMenu();
            System.out.println ("Please enter your choice");
            ans = kb.nextLine();
            choice = ans.toUpperCase().charAt(0);
        
            switch (choice)
            {
                case 'A':
                    pay.addEmp();
                    break;                
                case 'I':
                    pay.printInfo();
                    break;
                case 'D':
                    pay.remove();
                    break;
                case 'S':
                    pay.printWklySal();
                    break;
                case 'T':
                    pay.printTop();
                    break;
                case 'P':
                    pay.salaryRpt();
                    break;
                case 'W':
                    pay.endOfWeek();
                    break;
                case 'Q':
                    break;
                default:
                    System.out.println ("That is not a valid choice.  Please choose again");
            }
        
        }

        System.out.println ("Thank you for using the Payroll Processing System");
    }
   
      
    //   The Payroll processing menu
     
    public static void showMenu()
    {
        System.out.println("\nMENU:");
        System.out.println("A - Add a new employee");
        System.out.println("I - Print information of an individual employee");
        System.out.println("D - Remove an employee from payroll");
        System.out.println("S - calculate and print the weekly pay of an employee");
        System.out.println("T - Top Sellers list");
        System.out.println("P - prints salary report");
        System.out.println("W - End of week processing");
        System.out.println();
        System.out.println("Q - Quit the system");
    }

}
