package van;

import coffee.Coffee;
import coffee.Coffee_block;
import coffee.Blocks_amount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import java.util.ArrayList;
import static coffee.Coffee_block_Volume.*;
import static org.junit.jupiter.api.Assertions.*;

class VanTest {
    @Mock
    private Van_type van_type = Van_type.SMALL;
    @Mock
    private Van van;
    @Mock
    private ArrayList<Blocks_amount> goods = new ArrayList<>();
    @BeforeEach
    void setup(){
        van = new Van(van_type);
        van.setGoods_price(1000);
        van.setGoods_volume(2.5);
        van.setGoods_weight(5.5);
        van.setFullness(van.determine_fullness());
       van.setGoods(goods);
    }
    @ParameterizedTest
    @ValueSource(doubles = {2.5})
    void setGoods_weight(double goods_weight) {
        van.setGoods_weight(goods_weight);
        assertEquals(8, van.getGoods_weight());
    }
    @ParameterizedTest
    @ValueSource(doubles = {2.5})
    void setGoods_volume(double goods_volume) {
        van.setGoods_volume(goods_volume);
        assertEquals(5, van.getGoods_volume());
    }
    @ParameterizedTest
    @ValueSource(ints = {25})
    void setGoods_price(int goods ){
        van.setGoods_price(goods);
        assertEquals(1025, van.getGoods_price());
    }

    @Test
    void determine_fullness() {
        assertEquals(10,van.determine_fullness());
    }

    @Test
    void isFull_by_volume_yes() {
        goods = new ArrayList<>();
        var block = new Blocks_amount(new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1)));
       goods.add(block);
       van.setGoods(goods);
        van.setFullness(100);
       assertTrue(van.isFull_by_volume(block.getBlock()));

    }
    @Test
    void isFull_by_volume_no() {
        var block = new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1));
        van.setFullness(10);
        assertFalse(van.isFull_by_volume(block));
    }

    @Test
    void isFull_by_weight_yes() {
        var block = new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1));
        van.setGoods_weight(2494.5);
        assertTrue(van.isFull_by_weight(block));
    }
    @Test
    void isFull_by_weight_maybe() {
        goods = new ArrayList<>();
        var block = new Blocks_amount(new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1)));
        goods.add(block);
        van.setGoods(goods);
        van.setGoods_weight(3000);
        assertTrue(van.isFull_by_weight(block.getBlock()));
    }
    @Test
    void isFull_by_weight_no() {
        var block = new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1));
        van.setGoods_weight(100);
        assertFalse(van.isFull_by_weight(block));
    }
    @ParameterizedTest
    @ValueSource(ints = {1000})
    void ifFull_by_price_yes(int pr) {
        var block = new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1));
        assertTrue(van.ifFull_by_price(pr,block));
    }
    @ParameterizedTest
    @ValueSource(ints = {10000})
    void isFull_by_price_no(int pr) {
        var block = new Blocks_amount(new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1)));
        assertFalse(van.ifFull_by_price(pr,block.getBlock()));
    }
   @ParameterizedTest
    @ValueSource(ints = {1500})
    void ifFull_by_price_maybe(int pr) {
       goods = new ArrayList<>();
       var block = new Blocks_amount(new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1)));
       goods.add(block);
       van.setGoods(goods);
       assertFalse(van.ifFull_by_price(pr,block.getBlock()));
    }
    @Test
    void volumeLeft() {
        assertEquals(22.5,van.volumeLeft());
    }
    @Test
    void isExist_yes() {
        goods = new ArrayList<>();
        goods.add(new Blocks_amount(new Coffee_block(MEDIUM_BLOCK,new Coffee(1,2,1,1))));
        van.setGoods(goods);
        var block = new Coffee_block(MEDIUM_BLOCK,new Coffee(1,2,1,1));
        assertEquals(0,van.isExist(block));
    }
    @Test
    void isExist_no() {
        goods = new ArrayList<>();
        goods.add(new Blocks_amount(new Coffee_block(MEDIUM_BLOCK,new Coffee(1,2,1,1))));
        van.setGoods(goods);
        var block = new Coffee_block(MEDIUM_BLOCK,new Coffee(1,1,1,1));
        assertEquals(-1,van.isExist(block));
    }
    @Test
    void addParametersOfCoffeeBlock_new() {
        var block =new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1));
       van.addParametersOfCoffeeBlock(block);
      assertEquals(4.5,van.getGoods_volume());
        assertEquals(485.5, van.getGoods_weight());
        assertEquals(236200, van.getGoods_price());
    }
    @Test
    void addParametersOfCoffeeBlock_exist() {
        var block =new Coffee_block(LARGE_BLOCK,new Coffee(1,2,1,1));
        van.addParametersOfCoffeeBlock(block);
        van.addParametersOfCoffeeBlock(block);
        assertEquals(2,van.getGoods().get(0).getAmount());
    }

    @Test
    void sorting_asc() {
        goods = new ArrayList<>();
        var block1 = new Blocks_amount(new Coffee_block(LARGE_BLOCK,new Coffee(1,1,1,1)));
        var block2 = new Blocks_amount(new Coffee_block(MEDIUM_BLOCK,new Coffee(1,1,1,2)));
        var block3 = new Blocks_amount(new Coffee_block(SMALL_BLOCK,new Coffee(1,1,1,3)));
        goods.add(block1); goods.add(block2); goods.add(block3);
        van.setGoods(goods);
        van.sorting("ASC");
        assertEquals(0,van.getGoods().indexOf(block3));
        assertEquals(1,van.getGoods().indexOf(block2));
        assertEquals(2, van.getGoods().indexOf(block1));
    }
    @Test
    void sorting_des() {
        goods = new ArrayList<>();
        var block1 = new Blocks_amount(new Coffee_block(LARGE_BLOCK,new Coffee(1,1,1,1)));
        var block2 = new Blocks_amount(new Coffee_block(MEDIUM_BLOCK,new Coffee(1,1,1,2)));
        var block3 = new Blocks_amount(new Coffee_block(SMALL_BLOCK,new Coffee(1,1,1,3)));
        goods.add(block2); goods.add(block3);goods.add(block1);
        van.setGoods(goods);
        van.sorting("DEC");
        assertEquals(0,van.getGoods().indexOf(block1));
        assertEquals(1,van.getGoods().indexOf(block2));
        assertEquals(2, van.getGoods().indexOf(block3));
    }




    @Test
    void findCoffee() {
        ArrayList<String> qual = new ArrayList<>(); qual.add("AA");
        goods = new ArrayList<>();
        var block1 = new Blocks_amount(new Coffee_block(LARGE_BLOCK,new Coffee(1,1,1,1)));
        var block2 = new Blocks_amount(new Coffee_block(MEDIUM_BLOCK,new Coffee(1,1,1,2)));
        var block3 = new Blocks_amount(new Coffee_block(SMALL_BLOCK,new Coffee(1,1,1,3)));
        goods.add(block1); goods.add(block2); goods.add(block3);
        van.setGoods(goods);
        ArrayList<Coffee> coffeeByQuality = van.findCoffee(qual);
        assertEquals(1,coffeeByQuality.size());

    }
}