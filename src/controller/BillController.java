package controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import entities.BillEntity;
import entities.BillStateEnum;
import entities.BillTypeEnum;
import utils.FileHelper;

public class BillController {
    private static BillController INSTANCE;

    private String billsFile = "bills.txt";

    private List<BillEntity> bills;
    
    private int billQuantity;

    public BillController() {
        bills = readBills();
        billQuantity = bills.size();
    }
  
    public BillController(List<BillEntity> bills) {
        this.bills = bills;
    }

    public static BillController getInstance() {
        if(INSTANCE == null) {
            INSTANCE =  new BillController(); 
        }
        
        return INSTANCE;
    }

    public int getBillQuantity() {
        return this.billQuantity;
    }

    public void setBillQuantity(int billQuantity) {
        this.billQuantity = billQuantity;
    }

    public List<BillEntity>  getBills() {
        return this.bills;
    }

    public void setBills(List<BillEntity> bills) {
        this.bills = bills;
    }

    public String getBillsFile() {
        return this.billsFile;
    }

    public void setBillsFile(String billsFile) {
        this.billsFile = billsFile;
    }

    public BillEntity getBill(int billId) {
        try{
            return this.bills.get(billId);
        }
        catch(Exception ex) {
            System.out.println("Sorry! Not found a bill with such id" + billId);
            return null;
        }

    }


    public Boolean createBill() 
    {
        Boolean returnVal = false;
        BillEntity bill = processInput();
        bill.setBillId(billQuantity);
        FileHelper.appendFile(bill.toString(), billsFile);
        bills.add(bill);
        setBillQuantity(bills.size());
        setBills(bills);
        returnVal = true;
        return returnVal;
    }

    // Use for testing
    public int writeBill(BillEntity bill) 
    {
        int index = billQuantity;
        bill.setBillId(index);
        FileHelper.appendFile(bill.toString(), billsFile);
        bills.add(bill);
        setBillQuantity(bills.size());
        setBills(bills);
        return index;
    }

    public boolean updated(BillEntity updatedBill) 
    {
        boolean returnVal = false;
        BillEntity billUpdate = bills.stream().filter(x -> x.getBillId() == updatedBill.getBillId()).findAny().orElse(null);
        if(billUpdate != null) {
            System.out.println("Enter new data for bill");
            int index = bills.indexOf(billUpdate);
            updatedBill.setBillId(index);
            bills.set(index, updatedBill);
            List<String> billsString = bills.stream().map(bill -> bill.toString()).collect(Collectors.toList());
            FileHelper.updateFile(billsString, billsFile);
        }
        else {
            System.out.println("Invalid Bill ID, Bill not available");
        }

        setBills(bills);
        returnVal = true;
        return returnVal;
    }

    private BillEntity processInput()
    {
        String s = "Please input bill information.";  
        //Create scanner Object and pass string in it  
        Scanner scan = new Scanner(s);  
        //Check if the scanner has a token  
        System.out.println("Boolean Result: " + scan.hasNext());  
        //Print the string  
        System.out.println("String: " +scan.nextLine());  
        scan.close();           
        System.out.println("--------Enter Your Bill Details-------- ");  
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your Bill Type: ");
        Boolean enter = true;
        Boolean exit = false;
        BillEntity bill = new BillEntity();
        String type;
        String amount;
        String dueDate;
        String sate;
        String providerData;
        while(enter){
            System.out.println("Bill Type(ELECTRIC, WATER, INTERNET)");  
            type = in.next();
            if (type.compareToIgnoreCase("ELECTRIC")== 0 || type.compareToIgnoreCase("WATER")== 0  || type.compareToIgnoreCase("INTERNET")== 0  || type.compareToIgnoreCase("EXIT")== 0 ) {
                enter = false;
                if(type.compareToIgnoreCase("EXIT")== 0) 
                {
                    exit = true;
                    return null;
                }

                bill.setType(BillTypeEnum.valueOf(type.toUpperCase()));
            } else {
                System.out.println("Wrong type please try again or press EXIT to exit");  
            }
        }

        if(!exit){
            System.out.print("Enter your Amount: ");  
            enter = true;
            exit = false;
            while(enter){
                System.out.println("Amount: ");  
                amount = in.next();
                if (Long.parseLong(amount) <= 0 ) {
                    System.out.println("Wrong amount please try again or press exit to exit");  
                } else {
                    if(amount.compareToIgnoreCase("EXIT")== 0) exit =true;
                    enter = false;
                    bill.setAmount(Long.parseLong(amount));
                }
            }
        }
        
        if(!exit){
            System.out.print("Enter bill DueDate: ");
            enter = true;
            exit = false;
            LocalDate dueDateData;
            while(enter){
                System.out.println("DueDate with format dd/MM/yyyy");  
                dueDate = in.next();
                if(dueDate.compareToIgnoreCase("EXIT")== 0) exit =true;
                try {
                    dueDateData = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    bill.setDueDate(dueDateData);
                    enter = false;
                } catch (Exception ex) {
                    System.out.println("Wrong amount please try again or press exit to exit");
                }
            }
        }

        if(!exit){
            System.out.print("Enter bill State: ");  
            enter = true;
            exit = false;
            while(enter){
                System.out.println("Bill state(NOT_PAID, PAID)");  
                sate = in.next();
                if (sate.compareToIgnoreCase("NOT_PAID")== 0 || sate.compareToIgnoreCase("PAID")== 0 || sate.compareToIgnoreCase("EXIT")== 0 ) {
                    enter = false;
                    if(sate.compareToIgnoreCase("EXIT")== 0) 
                    {
                        exit = true;
                        return null;
                    }

                    bill.setState(BillStateEnum.valueOf(sate.toUpperCase()));
                } else {        
                    System.out.println("Wrong type please try again or press exit to exit");  
                }
            }
        }

        System.out.print("Enter bill Provider: ");  
        providerData = in.next();
        bill.setProvider(providerData);
        return bill;
    }

    public Boolean deleteBill(int billId) 
    {
        boolean returnVal = false;
        BillEntity billDelete = bills.stream().filter(bill -> billId == bill.getBillId()).findAny().orElse(null);

        if(billDelete != null)
        {
            int index = bills.indexOf(billDelete);
            bills.remove(index);
            for(int i = index ; i < bills.size() ; i ++) {
                bills.get(i).setBillId(i);
            }

            List<String> billsString = bills.stream().map(bill -> bill.toString()).collect(Collectors.toList());
            FileHelper.updateFile(billsString, billsFile);
        } else {
            System.out.println("Invalid Bill ID, Bill not available");
        }

        setBills(bills);
        setBillQuantity(bills.size());
        returnVal = true;
        return returnVal;
    }

    public Boolean update(int billId) 
    {
        boolean returnVal = false;
        BillEntity billUpdate = bills.stream().filter(bill -> billId == bill.getBillId()).findAny().orElse(null);
        if(billUpdate != null) {
            System.out.println("Enter new data for bill");
            BillEntity updatedBill = processInput();
            int index = bills.indexOf(billUpdate);
            updatedBill.setBillId(index);
            bills.set(index, updatedBill);
            List<String> billsString = bills.stream().map(bill -> bill.toString()).collect(Collectors.toList());
            FileHelper.updateFile(billsString, billsFile);
        }
        else {
            System.out.println("Invalid Bill ID, Bill not available");
        }

        setBills(bills);
        returnVal = true;
        return returnVal;
    }
    
    public List<BillEntity> readBills() 
    {
        List<String> billData = FileHelper.readFile(billsFile);
        List<BillEntity> bills = new ArrayList<BillEntity>();
        for (String bill : billData) {
            bills.add(new BillEntity(bill));
        }

        return bills;
    }
    
}
