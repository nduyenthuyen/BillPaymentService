package test.entities;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import entities.PaymentEntity;
import entities.PaymentStateEnum;

public class PaymentEntityTest {
    private PaymentEntity paymentEntity;
    @Before
    public void setUp() {
        paymentEntity = new PaymentEntity(" {PaymentId=1, Amount=100, PaymentDate=20/10/2023, State=PROCESSED, BillId=1}");
    }

    @Test
    public void testGetAmount() {
        assertEquals(100, paymentEntity.getAmount());
    }

    @Test
    public void testGetBillId() {
        assertEquals(1, paymentEntity.getBillId());

    }

    @Test
    public void testGetPaymentDate() {
        assertEquals(LocalDate.parse("20/10/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")), paymentEntity.getPaymentDate());

    }

    @Test
    public void testGetPaymentId() {
        assertEquals(1, paymentEntity.getPaymentId());

    }

    @Test
    public void testGetState() {
        assertEquals(PaymentStateEnum.PROCESSED, paymentEntity.getState());
    }

    @Test
    public void testToString() {
        assertEquals("{ PaymentId=1, Amount=100, PaymentDate=20/10/2023, State=PROCESSED, BillId=1 }", paymentEntity.toString());
    }
}
