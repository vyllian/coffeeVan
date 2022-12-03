package functional;

import coffee.Blocks_amount;
import coffee.Coffee;
import coffee.Coffee_block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static coffee.Coffee_block_Volume.SMALL_BLOCK;
import static org.junit.jupiter.api.Assertions.*;

class Blocks_amountTest {

    private Blocks_amount block_am;
@BeforeEach
void setup(){
    Coffee_block block =new Coffee_block(SMALL_BLOCK,new Coffee(1,1,1,1));
    block_am = new Blocks_amount(block);
}
    @Test
    void increaseAmount() {
        block_am.increaseAmount();
       assertEquals(2,block_am.getAmount());

    }

    @Test
    void decreaseAmount() {
        block_am.decreaseAmount();
        assertEquals(0,block_am.getAmount());
    }
    @ParameterizedTest
    @ValueSource(ints ={3})
    void setAmount(int am){
    block_am.setAmount(am);
    assertEquals(3,block_am.getAmount());
    }

    @Test
    void setBlock(){
        block_am.setBlock(new Coffee_block(SMALL_BLOCK,new Coffee(1,1,1,1)));
        assertEquals(SMALL_BLOCK,block_am.getBlock().getBlock_Volume());
        assertTrue(block_am.getBlock().getCoffee().isEqual(new Coffee(1,1,1,1)));
    }
}