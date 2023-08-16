package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.BillEntity;
import entities.CustomerEntity;
import entities.PaymentEntity;
import utils.StringHelper;

public class ServiceController {
    private static ServiceController INSTANCE;
    private BillController billController;
    private CustomerController customerController;
    private PaymentController paymentController;

    public ServiceController() {
    }

    public ServiceController(BillController billController, CustomerController customerController,PaymentController paymentController) {
        this.billController = billController;
        this.customerController = customerController;
        this.paymentController = paymentController;
    }

    public static ServiceController getInstance() {
        if(INSTANCE == null) {
            INSTANCE =  new ServiceController(BillController.getInstance() , CustomerController.getInstance(), PaymentController.getInstance()); 
        }
        
        return INSTANCE;
    }

    public void cashIn(int cash) {
        customerController.addFund(cash);
        CustomerEntity currentCus = customerController.getCustomer();
        System.out.println("Your available balance: " + currentCus.getFund());
    }

    public void createBill() {
        billController.createBill();
    }

    public void updateBill() {
        //To Do update bill also payment
    }

    public void deleteBill() {
        //To Do delete bill also payment
    }

    public void listBill() {
        List<BillEntity> bills = billController.readBills();
       printBill(bills);
    }

    public void SearchBillByProvider(String provider) {
        List<BillEntity> bills = billController.readBills();
        List<BillEntity> billsByProvider = bills.stream().filter(c -> c.getProvider().equals(provider)).collect(Collectors.toList());
        printBill(billsByProvider);
    }

    public void pay(List<Integer> billIds) {
        List<BillEntity> bills = new ArrayList<BillEntity>();
        billIds.forEach(bill -> {
            BillEntity billEntity = billController.getBill(bill);
            if(billEntity != null) bills.add(billController.getBill(bill));
        });

        bills.sort((a,b) -> a.getDueDate().compareTo(b.getDueDate()));

        bills.forEach(bill -> {
            paymentController.createPayment(bill.getBillId());
        });
        
    }

    public void Schedule(int billId, LocalDate date) {
        paymentController.createPayment(billId, date);
    }

    public void DueDay() {
        List<BillEntity> bills = billController.readBills();
        LocalDate now = LocalDate.now();
        LocalDate ago = now.minusDays( 30 ) ;

        List<BillEntity>  billDueDate = bills.stream().filter(bill -> bill.getDueDate().compareTo(ago) > 0 ).collect(Collectors.toList());
        printBill(billDueDate);
    }

    private void printBill(List<BillEntity> bills){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String billNo = "Bill NO.   "; // 12
        String type = "Type        "; // 12
        String amount = "Amount      "; // 10
        String dueDate = "Due Date    "; // 12
        String state = "State     "; //10
        String provider = "PROVIDER";

        System.out.println(billNo + type + amount + dueDate + state + provider);
        bills.forEach(bill -> {
            int billLength = String.valueOf(bill.getBillId()).length();
            int missingSpace = 11 - billLength;

            int typeLength = String.valueOf(bill.getType()).length();
            int missingSpaceType = 12 - typeLength;

            int amountLength = String.valueOf(bill.getAmount()).length();
            int missingSpaceAmount = 12 - amountLength;

            int dueDateLength = bill.getDueDate().format(formatter).length();
            int missingSpacePaymentDate = 12 - dueDateLength;

            int LengthState = String.valueOf(bill.getState()).length();
            int missingSpaceState = 10 - LengthState;

            System.out.println(
                StringHelper.padRight(String.valueOf(bill.getBillId()), missingSpace) +
                StringHelper.padRight(String.valueOf(bill.getType()), missingSpaceType) +
                StringHelper.padRight(String.valueOf(bill.getAmount()), missingSpaceAmount) +
                StringHelper.padRight(bill.getDueDate().format(formatter), missingSpacePaymentDate) +
                StringHelper.padRight(String.valueOf(bill.getState()), missingSpaceState) +
                bill.getProvider()
            );
        });
    }


    public void ListPayment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String billNo = "NO.     "; // 3
        String amount = "Amount    "; // 10
        String dueDate = "Payment Date        "; // 10
        String state = "State       "; // 10
        String provider = "Bill Id";

        System.out.println(billNo + amount + dueDate + state + provider);
        List<PaymentEntity> payments = paymentController.getPayments();
        payments.forEach(payment -> {
            int billLength = String.valueOf(payment.getPaymentId()).length();
            int missingSpace = 8- billLength;

            int amountLength = String.valueOf(payment.getAmount()).length();
            int missingSpaceAmount = 10 - amountLength;

            int paymentDateLength = payment.getPaymentDate().format(formatter).length();
            int missingSpacePaymentDate = 20 - paymentDateLength;

            int LengthState = String.valueOf(payment.getState()).length();
            int missingSpaceState = 12 - LengthState;

            System.out.println(
                StringHelper.padRight(String.valueOf(payment.getPaymentId()), missingSpace) +
                StringHelper.padRight(String.valueOf(payment.getAmount()), missingSpaceAmount) +
                StringHelper.padRight(payment.getPaymentDate().format(formatter), missingSpacePaymentDate) +
                StringHelper.padRight(String.valueOf(payment.getState()), missingSpaceState) +
                payment.getBillId()
            );
        });
    }

}