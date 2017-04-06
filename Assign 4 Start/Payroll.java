import java.util.*;
import java.io.*;

public class Payroll
{
    ArrayList <Employee> personnel;
    Scanner kb = new Scanner(System.in);

    public Payroll () throws IOException
    {
        personnel = new ArrayList <Employee> ();
        loadData();
    }

    // processing methods

    // adding an employee (Option A)
    public void addEmp()
    {
        Employee e;
        String nbr;

        System.out.println("Please enter the employee number you wish to add: ");
        nbr = kb.nextLine();
        try {
            e = validateNum(nbr);
            if (e != null)
            {
                System.out.println("That employee number is already in the payroll.  Cannot add employee");
            }
            else
            {
                e = readData(nbr);
                personnel.add(e); 
                System.out.println ("Employee " + nbr + " added to the Payroll");
            }
        }
        catch(Exception x)
        {

        }
    }

    // printing individual employee info (Option I)
    public void printInfo()
    {
        Employee e;
        String nbr;

        System.out.println ("Please enter the employee number ");
        nbr = kb.nextLine();

        e = validateNum(nbr);
        if (e != null)
        {
            System.out.println (e.toString());
        }
        else
        {
            System.out.println("Employee does not exist in the payroll");
        }
    }

    // removing an employee (Option D)
    public void remove()
    {
        Employee e;
        String nbr;
        char ans;

        System.out.println ("Please enter the employee number to be removed ");
        nbr = kb.nextLine();

        e = validateNum(nbr);
        if (e != null)
        {
            System.out.println ("Do you really want to remove " + e.getEmpName() + " with number " + e.getEmpNum() + " Y or N?");
            ans = kb.nextLine().charAt(0);
            if (ans == 'Y')
            {
                personnel.remove(e);
                System.out.println ("Employee " + nbr + " removed from Payroll");
            }
            else
            {
                System.out.println ("You answered 'N'. " + e.getEmpName() + " will not be removed.");
            }
        }
        else
        {
            System.out.println("Employee does not exist in the payroll.  None removed");
        }

    }

    // prints the weekly salary of a particular employee (Option S)
    public void printWklySal()
    {
        Employee e;
        String nbr;

        System.out.println ("Please enter the employee number ");
        nbr = kb.nextLine();

        e = validateNum(nbr);
        if (e != null)
        {
            System.out.println ("The weekly salary of " + e.getEmpName() + " is " + e.calcWeeklySalary());
        }
        else
        {
            System.out.println("Employee does not exist in the payroll");
        }
    }

    // prints the top sellers (Option T)
    public void printTop()
    {
        Employee e;
        boolean found = false;
        char type;

        int i = 0;
        while (i < personnel.size())
        {
            e = personnel.get(i);
            type = e.getType();
            if (type == 'C')
            {
                if (e.topSeller())
                {
                    System.out.println (e.getEmpName() + " with employee number " + e.getEmpNum() + " is a Top Seller.");
                    found = true;
                }
            }
            i++;
        }
        if(!found)
            System.out.println ("There are no Top Sellers");

    }

    // prints the weekly salary report
    public void salaryRpt()
    {
        Employee e;
        char type;
        String empType = "";

        int i = 0;
        while (i < personnel.size())
        {
            e = personnel.get(i);
            type = e.getType();
            switch (type)
            {
                case 'S':
                empType = "Salary   ";
                break;
                case 'H':
                empType = "Hourly    ";
                break;
                case 'C':
                empType = "Commission";
                break;
            }
            System.out.println (e.getEmpName() + "\t" + e.getEmpNum() + "\t" + empType + "\t" + e.calcWeeklySalary());
            i++;
        }
    }

    // end of week (Option W) (for now now writing to file)
    public void endOfWeek() throws IOException
    {
        Employee e;
        char type;

        int i = 0;
        while (i < personnel.size())
        {
            e = personnel.get(i);
            type = e.getType();
            switch (type)
            {
                case 'H':
                e.setHrsWorked(0.0);
                break;
                case 'C':
                int numWeeks = e.getNumWeeks() + 1;   // adds one week to the number of weeks
                e.setNumWeeks(numWeeks);  
                double y_sales = e.getYrSales() + e.getWklySales();
                e.setYrSales(y_sales);
                e.setWklySales (0.0);
                case 'S':
                break;
            }
            i++;
        }
        kb.nextLine();
        writeToFile();
    }

    // helper methods
    // validate employee number
    public Employee validateNum(String num)
    {
        Employee emp = null;
        boolean found = false; 
        int i = 0;

        while (i < personnel.size() && !found)
        {
            if (personnel.get(i).getEmpNum().equals(num))
            {
                emp = personnel.get(i);
                found = true;
            }
            i++;
        }
        return emp;
    }

    // reading data of employee from keyboard
    public Employee readData(String n1)
    {
        Employee e1 = null;
        String name;
        String dept;
        char type;    

        System.out.println ("Please enter the employee type: S, H, or C");
        type = kb.nextLine().charAt(0); 

        if (type == 'S' || type == 'H' || type == 'C') 
        {
            System.out.println ("Please enter the name of the employee");
            name = kb.nextLine();
            System.out.println ("Please enter the department of " + name);
            dept = kb.nextLine();

            switch (type)
            {
                case 'S':
                System.out.println ("Please enter the yearly salary of " + name);
                double sal = kb.nextDouble();
                e1 = new Salary (name, n1, dept, sal);
                break;
                case 'H':
                System.out.println("Please enter the hourly rate of " + name);
                double rate = kb.nextDouble();
                System.out.println("Please enter the number of hours worked by " + name + " this week");
                double hrs = kb.nextDouble();
                e1 = new Hourly(name, n1, dept, rate, hrs);
                break;
                case 'C':
                System.out.println ("Please enter the number of weeks " + name + " has been working");
                int wks = kb.nextInt();
                System.out.println ("Please enter the base salary of " + name);
                double base = kb.nextDouble();
                System.out.println ("Please enter this week's sales for " + name);
                double w_sales = kb.nextDouble();
                System.out.println ("Please enter the year to date sales for " + name);
                double y_sales = kb.nextDouble();
                System.out.println("Please enter the commission rate of " + name);
                double comm = kb.nextDouble();
                e1 = new Commission(name, n1, dept, wks, base, w_sales, y_sales, comm);
                break;                
            }
            kb.nextLine();
        }
        return e1;
    }

    // prints info about all employees
    public void printAll()
    {
        int i = 0;
        while (i < personnel.size())
        {
            Employee e;
            e = personnel.get(i);
            System.out.println (e.toString());
            System.out.println();
            i++;
        }
    }

    //prints all employee numbers
    public void printAllNum()
    {
        int i = 0;
        while (i < personnel.size())
        {
            Employee e;
            e = personnel.get(i);
            System.out.println (e.getEmpNum());
            i++;
        }
    }

    // loads data from file
    public void loadData() throws IOException
    {
        String fname = "";
        String empName = "";
        String empNum = "";
        String dept = "";
        char type = ' ';
        double hrRate = 0.0;
        double hrsWorked;
        double yrSal;
        int numWeeks;
        double base;
        double wkSales;
        double yrSales;
        double commRate;
        Scanner in;
        System.out.println ("Please enter the filename containg the employee information");
        int i = 0;
        boolean found = false;
        while(i < 3 && found != true){
            try{
                fname = kb.nextLine();
                in = new Scanner (new File(fname));
                found = true;
            }
            catch(Exception x){
                System.out.println("File was not found. " + (3 - i) + " attempts remaining.");
                i++;
            }
        }

        while(in.hasNext())
        {
            Employee e;
            empName = in.next();
            empNum = in.next();
            dept = in.next();
            type = in.next().charAt(0);

            switch(type)
            {
                case 'H':
                try{
                    hrRate = in.nextDouble();
                }
                catch(InputMismatchException x){
                    System.out.println("Hourly rate is not in the right format.");
                }
                hrsWorked = in.nextDouble();
                System.out.println ("Please enter the number of hours worked this week by " + empName); 
                hrsWorked = kb.nextDouble();
                e = new Hourly(empName, empNum, dept, hrRate, hrsWorked);
                personnel.add(e);
                break;
                case 'S':
                yrSal = in.nextDouble();
                e = new Salary(empName, empNum, dept, yrSal);
                personnel.add(e);
                break;
                case 'C':
                numWeeks = in.nextInt();
                base = in.nextDouble();
                wkSales = in.nextDouble();
                yrSales = in.nextDouble();
                commRate = in.nextDouble();
                System.out.println ("Please enter the weekly sales of " + empName);
                wkSales = kb.nextDouble();
                e = new Commission(empName, empNum, dept, numWeeks, base, wkSales, yrSales, commRate);
                personnel.add(e);
                break;
                default:
                System.out.println(type + " is not a valid type");
            }
        }
        kb.nextLine();
        /*
        Hourly h1 = new Hourly ("Sally", "333-333-333", "Finance", 15.75, 50);
        Salary s1 = new Salary ("Arnie", "222-222-222", "Human Resources", 52000);
        Commission c1 = new Commission ("Bobby", "111-111-111", "Appliances", 10, 300, 900, 10000, 10.0);

        personnel.add(h1);
        personnel.add(s1);
        personnel.add(c1);

        enterWeekData();
         */
    }

    // entering this weeks data
    private void enterWeekData()
    {
        Employee e;
        char type;

        int i = 0;
        while (i < personnel.size())
        {
            e = personnel.get(i);
            type = e.getType();
            switch (type)
            {
                case 'H':
                System.out.println ("Please enter the number of hours worked this week by " + e.getEmpName()); 
                double hrs = kb.nextDouble();
                e.setHrsWorked(hrs);
                break;
                case 'C':
                System.out.println ("Please enter the weekly sales of " + e.getEmpName());
                double w_sales = kb.nextDouble();
                e.setWklySales(w_sales);
                break;
                case 'S':
                break;
            }
            i++;
        }
        kb.nextLine();
    }

    // writing data to file
    private void writeToFile() throws IOException
    {
        String outfile;
        Employee e;
        int i;
        PrintWriter output;

        System.out.println ("Please enter the full output file name");
        outfile = kb.nextLine();

        output = new PrintWriter(new File(outfile));

        i = 0;
        while (i < personnel.size())
        {
            e = personnel.get(i);
            e.writeData(output);
            i++;
        }
        output.close();
    }

}
