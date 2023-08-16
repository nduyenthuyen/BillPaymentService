package controller;
import java.util.List;

import entities.CustomerEntity;
import utils.FileHelper;

public class CustomerController {
    private String customerFile = "customer.txt";
    private static CustomerController INSTANCE;

    public static CustomerController getInstance() {
        if(INSTANCE == null) {
            INSTANCE =  new CustomerController(); 
        }
        
        return INSTANCE;
    }

    private CustomerEntity customer;

    public CustomerController() {
        customer = new CustomerEntity();
    }
  
    public CustomerController(CustomerEntity customer) {
        this.customer = customer;
    }

    public CustomerEntity getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public String getCustomerFile() {
        return this.customerFile;
    }

    public void setCustomerFile(String customerFile) {
        this.customerFile = customerFile;
    }

    public Boolean addFund(long fund) 
    {
        Boolean returnVal = false;
        customer.setFund(fund);
        FileHelper.appendFile(customer.toString(), customerFile);
        returnVal = true;
        return returnVal;
    }

    public Boolean useFund(long fund) 
    {
        Boolean returnVal = false;
        if(readFund()>= fund) {
            returnVal = addFund(readFund() - fund);
        }

        return returnVal;
    }
    
    public long readFund() 
    {
        List<String> customerData = FileHelper.readFile(customerFile);     
        CustomerEntity customer = new CustomerEntity(customerData.get(customerData.size()-1));
        return customer.getFund();
    }
    
}
