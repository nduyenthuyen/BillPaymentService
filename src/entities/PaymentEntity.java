package entities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import utils.StringHelper;

public class PaymentEntity {
    private int PaymentId;
    private long Amount;
    private LocalDate PaymentDate;
    private PaymentStateEnum State;
    private int BillId;

    public PaymentEntity() {
    }

    public PaymentEntity(String PaymentString) {
        try {
            final String regexBillId = "PaymentId=(\\d*)";
            String paymentId = StringHelper.getField(PaymentString, regexBillId);
            PaymentId = paymentId != null? Integer.parseInt(paymentId) : 0;
    
            final String regexAmount = "Amount=(\\d*)";
            String amount = StringHelper.getField(PaymentString, regexAmount);
            Amount = Long.parseLong(amount);
    
            final String regexDueDate = "PaymentDate=(\\d*/\\d*/\\d*)";
            String paymentDate = StringHelper.getField(PaymentString, regexDueDate);
            PaymentDate = LocalDate.parse(paymentDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            //LocalDate myObj = LocalDate.now(); // Create a date object
    
            final String regexState = "State=(\\w*)";
            String state = StringHelper.getField(PaymentString, regexState);
            State = PaymentStateEnum.valueOf(state);
    
            final String regexProvider = "BillId=(\\d*)";
            String billId = StringHelper.getField(PaymentString, regexProvider);
            BillId = billId != null? Integer.parseInt(billId) : 0;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            throw ex;
        }
       
    };

    public PaymentEntity(int PaymentId, long Amount, LocalDate PaymentDate, PaymentStateEnum State, int BillId) {
        this.PaymentId = PaymentId;
        this.Amount = Amount;
        this.PaymentDate = PaymentDate;
        this.State = State;
        this.BillId = BillId;
    }

    public int getPaymentId() {
        return this.PaymentId;
    }

    public void setPaymentId(int PaymentId) {
        this.PaymentId = PaymentId;
    }

    public long getAmount() {
        return this.Amount;
    }

    public void setAmount(long Amount) {
        this.Amount = Amount;
    }

    public LocalDate getPaymentDate() {
        return this.PaymentDate;
    }

    public void setPaymentDate(LocalDate PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public PaymentStateEnum getState() {
        return this.State;
    }

    public void setState(PaymentStateEnum State) {
        this.State = State;
    }

    public int getBillId() {
        return this.BillId;
    }

    public void setBillId(int BillId) {
        this.BillId = BillId;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedPaymentDateString = getPaymentDate().format(formatter);

        return "{" +
            " PaymentId=" + getPaymentId() +
            ", Amount=" + getAmount() +
            ", PaymentDate=" + formattedPaymentDateString +
            ", State=" + getState() +
            ", BillId=" + getBillId() +
            " }";
    }
}