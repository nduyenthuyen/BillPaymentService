package test.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import controller.CustomerController;

public class CustomerControllerTest {
    private CustomerController customerController;
    @Before
    public void setUp() {
        customerController = new CustomerController();
        customerController.setCustomerFile("customerTest.txt");
    }

    @Test
    public void testAddAndReadFund() {
        customerController.addFund(1000);
        assertEquals(1000,customerController.readFund());
    }


    @Test
    public void testUseAndReadFund() {
        customerController.addFund(10000);
        customerController.useFund(300);
        assertEquals(9700,customerController.readFund());
    }
}
