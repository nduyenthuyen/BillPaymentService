package test.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import controller.BillController;
import entities.BillEntity;

public class BillControllerTest {
    private BillController billController;

    @Before
    public void setUp() {
        billController = new BillController();
        billController.setBillsFile("billsTest.txt");
    }

    @Test
    public void testDeleteBill() {
        BillEntity bill = new BillEntity(" { BillId=0, Type=WATER, Amount=50000, DueDate=25/10/2024, State=NOT_PAID, Provider=Thuyen }");
        int index = billController.writeBill(bill);
        int currentQuantity = billController.getBillQuantity();
        billController.deleteBill(index);
        assertEquals(currentQuantity - 1, billController.getBillQuantity());
    }

    @Test
    public void testReadBills() {
        BillEntity bill = new BillEntity(" { BillId=0, Type=WATER, Amount=50000, DueDate=25/10/2024, State=NOT_PAID, Provider=Thuyen }");
        int index = billController.writeBill(bill);
        BillEntity readEntity = billController.readBills().get(0);
        assertEquals(bill.toString(), readEntity.toString());
        billController.deleteBill(index);
    }

}
