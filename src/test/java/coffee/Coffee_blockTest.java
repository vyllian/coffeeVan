package coffee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static coffee.Coffee_block_Volume.*;

import static org.junit.jupiter.api.Assertions.*;


class Coffee_blockTest {
    @Mock
    private Coffee coffee = new Coffee(1,1,1,1);
    @Mock
    private Coffee_block_Volume volume = SMALL_BLOCK;
    @InjectMocks
    private Coffee_block block;
    @BeforeEach
    public void  setUp(){
        block = new Coffee_block(volume, coffee);
    }
    @Test
    void setCoffee(){
        block.setCoffee(new Coffee(1,2,1,1));
        assertTrue(block.getCoffee().isEqual(new Coffee(1,2,1,1)));
    }

    @Test
    void isEqual_true() {
        var block2 = new Coffee_block(volume,coffee);
        assertEquals(block.getCoffee(),block2.getCoffee());
        assertEquals(block.getBlock_Volume(),block2.getBlock_Volume());
        assertTrue(block.isEqual(block2));
    }
    @Test
    void isEqual_false_coffee() {
        var coffee2=new Coffee(1,2,1,1);
        var block2 = new Coffee_block(Coffee_block_Volume.LARGE_BLOCK,coffee2);
        assertNotEquals(block.getCoffee(),block2.getCoffee());
        assertFalse(block.isEqual(block2));
    }
    @Test
    void isEqual_false_vol() {
        var block2 = new Coffee_block(Coffee_block_Volume.LARGE_BLOCK,coffee);
        assertEquals(block.getCoffee(),block2.getCoffee());
        assertNotEquals(block.getBlock_Volume(),block2.getBlock_Volume());
        assertFalse(block.isEqual(block2));
    }



}