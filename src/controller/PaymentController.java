package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.BillEntity;
import entities.BillStateEnum;
import entities.CustomerEntity;
import entities.PaymentEntity;
import entities.PaymentStateEnum;
import utils.FileHelper;

public class PaymentController {
    private String paymentsFile = "payments.txt";
    private static PaymentController INSTANCE;
    public static PaymentController getInstance() {
        if(INSTANCE == null) {
            INSTANCE =  new PaymentController(BillController.getInstance(),  CustomerController.getInstance()); 
        }
        
        return INSTANCE;
    }
    private List<PaymentEntity> payments;
    private int payQuantity;
    private BillController billController;
    private CustomerController customerController;

    public PaymentController() {
        payments = readPayments();
        payQuantity = payments.size();
    }

    public PaymentController(BillController billController, CustomerController customerController) {
        this.billController = billController;
        this.customerController = customerController;
        payments = readPayments();
        payQuantity = payments.size();
    }

    public List<PaymentEntity> getPayments() {
        return this.payments;
    }

    public void setPayments(List<PaymentEntity> payments) {
        this.payments = payments;
    }

    public int getPayQuantity() {
        return this.payQuantity;
    }

    public void setPayQuantity(int payQuantity) {
        this.payQuantity = payQuantity;
    }


    public String getPaymentsFile() {
        return this.paymentsFile;
    }

    public void setPaymentsFile(String paymentsFile) {
        this.paymentsFile = paymentsFile;
    }

   // Use for testing
    public int writePayment(PaymentEntity payment) 
    {
        int index = payQuantity;
        payment.setPaymentId(index);
        FileHelper.appendFile(payment.toString(), paymentsFile);
        payments.add(payment);
        setPayQuantity(payments.size());
        setPayments(payments);
        return index;
    }
    public Boolean deletePayment(int billId) 
    {
        boolean returnVal = false;
        PaymentEntity paymentDelete = payments.stream().filter(bill -> billId == bill.getBillId()).findAny().orElse(null);

        if(paymentDelete != null)
        {
            int index = payments.indexOf(paymentDelete);
            payments.remove(index);
            for(int i = index ; i < payments.size() ; i ++) {
                payments.get(i).setBillId(i);
            }

            List<String> billsString = payments.stream().map(bill -> bill.toString()).collect(Collectors.toList());
            FileHelper.updateFile(billsString, paymentsFile);
        } else {
            System.out.println("Invalid Bill ID, Bill not available");
        }

        setPayments(payments);
        setPayQuantity(payments.size());
        returnVal = true;
        return returnVal;
    }

    public Boolean createPayment(int billId, LocalDate date) 
    {
        Boolean returnVal = false;
        BillEntity bill =  billController.getBill(billId);
        boolean isPaymentAvailable = payments.stream().anyMatch(pay -> pay.getBillId() == billId);
        CustomerEntity customer = customerController.getCustomer();

        LocalDate currentDay = LocalDate.now();
        if(bill != null && bill.getState()!= BillStateEnum.PAID) {
            if(currentDay.equals(date)) {
                long fund = customer.getFund();
                if(fund >= bill.getAmount()) {
                    PaymentEntity payment = new PaymentEntity(payQuantity, bill.getAmount(), date, PaymentStateEnum.PROCESSED, billId);
                    if(isPaymentAvailable) {
                        update(billId, PaymentStateEnum.PROCESSED, date);
                    } else {
                        FileHelper.appendFile(payment.toString(), paymentsFile);
                    }
                    payments.add(payment);
                    setPayments(payments);
                    setPayQuantity(payments.size());
                    returnVal = true;
                }
            } else {
                PaymentEntity payment = new PaymentEntity(payQuantity, bill.getAmount(), date, PaymentStateEnum.PENDING, billId);
                if(isPaymentAvailable) {
                    update(billId, PaymentStateEnum.PROCESSED, date);
                } else {
                    FileHelper.appendFile(payment.toString(), paymentsFile);
                }
                payments.add(payment);
                setPayments(payments);
                setPayQuantity(payments.size());
                returnVal = true;
            }
        }
        else {
            if(bill == null)
            {
                System.out.println("Sorry! Not found a bill with such id");
            }
            else {
                System.out.println("This bill already paid");
            }
        }     

        return returnVal;
    }
    
    public Boolean createPayment(int billId) 
    {
        Boolean returnVal = false;
        BillEntity bill =  billController.getBill(billId);
        long fund = customerController.readFund();
        boolean isPaymentAvailable = payments.stream().anyMatch(pay -> pay.getBillId() == billId);

        if(bill != null && fund >= bill.getAmount()) {
            LocalDate currentDay = LocalDate.now();
            
            PaymentEntity payment = new PaymentEntity(payQuantity, bill.getAmount(), currentDay, PaymentStateEnum.PROCESSED, billId);
            if(isPaymentAvailable) {
                update(billId, PaymentStateEnum.PROCESSED, currentDay);
            } else {
                FileHelper.appendFile(payment.toString(), paymentsFile);
            }
            payments.add(payment);
            setPayments(payments);
            setPayQuantity(payments.size());

            bill.setState(BillStateEnum.PAID);
            billController.updated(bill);
            returnVal = true;
        }
        else {
            System.out.println("Sorry! Not found a bill with such id");
        }

        return returnVal;
    }

    public Boolean update(BillEntity billEntity) 
    {
        boolean returnVal = false;
        PaymentEntity paymentUpdate = payments.stream().filter(bill -> billEntity.getBillId() == bill.getBillId()).findAny().orElse(null);
        if(paymentUpdate != null) {
            System.out.println("Enter new data for bill");
            PaymentEntity updatedPayment = new PaymentEntity(paymentUpdate.getPaymentId(), billEntity.getAmount(), paymentUpdate.getPaymentDate(), paymentUpdate.getState(), billEntity.getBillId());
            int index = payments.indexOf(paymentUpdate);
            updatedPayment.setBillId(index);
            payments.set(index, updatedPayment);
            List<String> billsString = payments.stream().map(bill -> bill.toString()).collect(Collectors.toList());
            FileHelper.updateFile(billsString, paymentsFile);
        }
        else {
            System.out.println("Invalid Bill ID, Bill not available");
        }

        setPayments(payments);
        returnVal = true;
        return returnVal;
    }

    public Boolean update(int billId, PaymentStateEnum state, LocalDate date) 
    {
        boolean returnVal = false;
        PaymentEntity paymentUpdate = payments.stream().filter(bill -> billId == bill.getBillId()).findAny().orElse(null);

        if(paymentUpdate != null) {
            System.out.println("Enter new data for bill");
            PaymentEntity updatedPayment = new PaymentEntity(paymentUpdate.getPaymentId(), paymentUpdate.getAmount(), date, state, paymentUpdate.getBillId());
            int index = payments.indexOf(paymentUpdate);
            updatedPayment.setBillId(index);
            payments.set(index, updatedPayment);
            List<String> billsString = payments.stream().map(bill -> bill.toString()).collect(Collectors.toList());
            FileHelper.updateFile(billsString, paymentsFile);
        }
        else {
            System.out.println("Invalid Bill ID, Bill not available");
        }

        setPayments(payments);
        returnVal = true;
        return returnVal;
    }
    
    public List<PaymentEntity> readPayments() 
    {
        List<String> paymentData = FileHelper.readFile(paymentsFile);
        List<PaymentEntity> paymentList = new ArrayList<PaymentEntity>();
        for (String payment : paymentData) {
            paymentList.add(new PaymentEntity(payment));
        }

        return paymentList;
    }
}
