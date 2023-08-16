package entities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import utils.StringHelper;

public class BillEntity {
    private int BillId;
    private BillTypeEnum Type;
    private long Amount;
    private LocalDate DueDate;
    private BillStateEnum State;
    private String Provider;

    public BillEntity() {};

    public BillEntity(String billString) {
        try {
            final String regexBillId = "BillId=(\\d*)";
            String billId = StringHelper.getField(billString, regexBillId);
            BillId = billId != null? Integer.parseInt(billId) : 0;

            final String regexType = "Type=(\\w*)";
            String type = StringHelper.getField(billString, regexType);
            Type = BillTypeEnum.valueOf(type);

            final String regexAmount = "Amount=(\\d*)";
            String amount = StringHelper.getField(billString, regexAmount);
            Amount = Long.parseLong(amount);

            final String regexDueDate = "DueDate=(\\d*/\\d*/\\d*)";
            String dueDate = StringHelper.getField(billString, regexDueDate);
            DueDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            //LocalDate myObj = LocalDate.now(); // Create a date object


            final String regexState = "State=(\\w*)";
            String state = StringHelper.getField(billString, regexState);
            State = BillStateEnum.valueOf(state);

            final String regexProvider = "Provider=(\\w*)";
            String provider = StringHelper.getField(billString, regexProvider);
            Provider = provider;
        } 
        catch (Exception ex)
        {
            System.out.println(ex.toString());
            throw ex;
        }
    };

    public BillEntity(int billId, BillTypeEnum type, long amount, LocalDate dueDate, BillStateEnum State, String Provider)
    {
        this.BillId = billId;
        this.Type = type;
        this.Amount = amount;
        this.DueDate = dueDate;
        this.State = State;
        this.Provider = Provider;
    };

    public int getBillId() {
        return BillId;
    }

    public void setBillId(int billId) {
        BillId = billId;
    }

    public BillTypeEnum getType() {
        return this.Type;
    }

    public void setType(BillTypeEnum Type) {
        this.Type = Type;
    }

    public long getAmount() {
        return this.Amount;
    }

    public void setAmount(long Amount) {
        this.Amount = Amount;
    }

    public LocalDate getDueDate() {
        return this.DueDate;
    }

    public void setDueDate(LocalDate DueDate) {
        this.DueDate = DueDate;
    }

    public BillStateEnum getState() {
        return this.State;
    }

    public void setState(BillStateEnum State) {
        this.State = State;
    }

    public String getProvider() {
        return this.Provider;
    }

    public void setProvider(String Provider) {
        this.Provider = Provider;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDueDateString = getDueDate().format(formatter);

        return "{" +
            " BillId=" + getBillId() +
            ", Type=" + getType() +
            ", Amount=" + getAmount() +
            ", DueDate=" + formattedDueDateString +
            ", State=" + getState() +
            ", Provider=" + getProvider() +
            " }";
    }
}
