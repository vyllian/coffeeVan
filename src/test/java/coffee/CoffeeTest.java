package coffee;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import static functional.Input.getInt0_Limit;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeeTest {
    @Mock
    private Coffee coffee;

    @Test
    void getKgPrice() {
        coffee = new Coffee(1,1,1,1);
        assertEquals(550, coffee.getKgPrice());
    }

    @Test
    void isEqual_true() {
        coffee = new Coffee(1,1,1,1);
        var coffee1 = new Coffee(1,1,1,1);
        assertTrue(coffee.isEqual(coffee1));

    }
    @Test
    void isEqual_false_brand() {
        coffee = new Coffee(1,1,1,1);
        var coffee1 = new Coffee(2,1,1,1);
        assertFalse(coffee.isEqual(coffee1));
    }
    @Test
    void isEqual_false_type() {
        coffee = new Coffee(1,1,1,1);
        var coffee1 = new Coffee(1,2,1,1);
        assertFalse(coffee.isEqual(coffee1));
    }
    @Test
    void isEqual_false_pack() {
        coffee = new Coffee(1,1,1,1);
        var coffee1 = new Coffee(1,1,2,1);
        assertFalse(coffee.isEqual(coffee1));
    }
    @Test
    void isEqual_false_quality() {
        coffee = new Coffee(1,1,1,1);
        var coffee1 = new Coffee(1,1,1,2);
        assertFalse(coffee.isEqual(coffee1));
    }



}