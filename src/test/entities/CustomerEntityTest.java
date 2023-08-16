package test.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import entities.CustomerEntity;

public class CustomerEntityTest {
    private CustomerEntity customerEntity;
    @Before
    public void setUp() {
        customerEntity = new CustomerEntity(" { CustomerId=1, Name=Thuyen, Fund=10000 }");
    }
    @Test
    public void testGetCustomerId() {
        assertEquals(1, customerEntity.getCustomerId());
    }

    @Test
    public void testGetFund() {
        assertEquals(10000, customerEntity.getFund());
    }

    @Test
    public void testGetName() {
        assertEquals("Thuyen", customerEntity.getName());
    }

    @Test
    public void testToString() {
        assertEquals("{ CustomerId=1, Name=Thuyen, Fund=10000 }", customerEntity.toString());
    }
}
