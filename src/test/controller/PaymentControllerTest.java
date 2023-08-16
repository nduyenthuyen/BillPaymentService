package test.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import controller.PaymentController;
import entities.PaymentEntity;

public class PaymentControllerTest {
    private PaymentController paymentController;
    @Before
    public void setUp() {
        paymentController = new PaymentController();
        paymentController.setPaymentsFile("paymentTexts.txt");
    }

    @Test
    public void testDeleteBill() {
        PaymentEntity payment = new PaymentEntity(" { PaymentId=0, Amount=1000, PaymentDate=20/08/2023, State=PENDING, BillId=1 }");
        paymentController.writePayment(payment);
        int currentQuantity = paymentController.getPayQuantity();
        paymentController.deletePayment(1);
        assertEquals(currentQuantity - 1, paymentController.getPayQuantity());
    }

    @Test
    public void testReadBills() {
        PaymentEntity payment = new PaymentEntity(" { PaymentId=0, Amount=1000, PaymentDate=20/08/2023, State=PENDING, BillId=1 }");
        paymentController.writePayment(payment);
        PaymentEntity readEntity = paymentController.readPayments().get(0);
        assertEquals(payment.toString(), readEntity.toString());
        paymentController.deletePayment(1);
    }
}
