package test.entities;
import org.junit.Test;

import entities.BillEntity;
import entities.BillStateEnum;
import entities.BillTypeEnum;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;

public class BillEntityTest {
    private BillEntity billEntity;
    @Before
    public void setUp() {
        billEntity = new BillEntity(" {BillId=1, Type=ELECTRIC, Amount=100 , DueDate=20/10/2023, State=NOT_PAID, Provider=VNPT}");
    }

    @Test
    public void testGetAmount() {
        assertEquals(100,billEntity.getAmount());
    }

    @Test
    public void testGetBillId() {
        assertEquals(1,billEntity.getBillId());
    }

    @Test
    public void testGetDueDate() {
        assertEquals(LocalDate.parse("20/10/2023", DateTimeFormatter.ofPattern("dd/MM/yyyy")),billEntity.getDueDate());
    }

    @Test
    public void testGetProvider() {
        assertEquals("VNPT",billEntity.getProvider());
    }

    @Test
    public void testGetState() {
        assertEquals(BillStateEnum.NOT_PAID, billEntity.getState());
    }

    @Test
    public void testGetType() {
        assertEquals(BillTypeEnum.ELECTRIC, billEntity.getType());

    }

    @Test
    public void testToString() {
        assertEquals("{ BillId=1, Type=ELECTRIC, Amount=100, DueDate=20/10/2023, State=NOT_PAID, Provider=VNPT }", billEntity.toString());

    }
}
