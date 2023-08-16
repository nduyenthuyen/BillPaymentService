package entities;

import utils.StringHelper;

public class CustomerEntity {
    private int CustomerId;
    private String Name;
    private long Fund;

    public CustomerEntity() {
    }

    public CustomerEntity(String CustomerString) {
        try {
            final String regexBillId = "CustomerId=(\\d*)";
            String customerId = StringHelper.getField(CustomerString, regexBillId);
            CustomerId = customerId != null? Integer.parseInt(customerId) : 0;

            final String regexType = "Name=(\\w*)";
            String name = StringHelper.getField(CustomerString, regexType);
            Name = name;

            final String regexAmount = "Fund=(\\d*)";
            String fund = StringHelper.getField(CustomerString, regexAmount);
            Fund = Long.parseLong(fund);
        } 
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    };

    public CustomerEntity(int CustomerId, String Name, long Fund) {
        this.CustomerId = CustomerId;
        this.Name = Name;
        this.Fund = Fund;
    }

    public int getCustomerId() {
        return this.CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public long getFund() {
        return this.Fund;
    }

    public void setFund(long Fund) {
        this.Fund = Fund;
    }

    @Override
    public String toString() {
        return "{" +
            " CustomerId=" + getCustomerId() +
            ", Name=" + getName()+
            ", Fund=" + getFund()+
            " }";
    }
}
