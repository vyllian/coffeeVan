package functional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class Input_coffee_formatTest {
    private Input_coffee_format values = new Input_coffee_format();

    @ParameterizedTest
    @ValueSource(ints = {1})
    void setQualityCode(int par) {
        values.setQualityCode(par);
        assertEquals(1,values.getQualityCode());
    }
    @ParameterizedTest
    @ValueSource(ints = {1})
    void setBrandCode(int par) {
        values.setBrandCode(par);
        assertEquals(1,values.getBrandCode());
    }
    @ParameterizedTest
    @ValueSource(ints = {1})
    void setTypeCode(int par) {
        values.setTypeCode(par);
        assertEquals(1,values.getTypeCode());
    }
    @ParameterizedTest
    @ValueSource(ints = {1})
    void setPackCode(int par) {
        values.setPackCode(par);
        assertEquals(1,values.getPackCode());
    }
    @Test
    void isCorrect_t() {
        values = new Input_coffee_format(1,1,1,1);
        assertTrue(values.isCorrect());
    }
    @Test
    void isCorrect_f1() {
        values = new Input_coffee_format(0,1,1,1);
        assertFalse(values.isCorrect());
    }
    @Test
    void isCorrect_f2() {
        values = new Input_coffee_format(1,0,1,1);
        assertFalse(values.isCorrect());
    }
    @Test
    void isCorrect_f3() {
        values = new Input_coffee_format(1,1,0,1);
        assertFalse(values.isCorrect());
    }
    @Test
    void isCorrect_f4() {
        values = new Input_coffee_format(1,1,1,0);
        assertFalse(values.isCorrect());
    }

}