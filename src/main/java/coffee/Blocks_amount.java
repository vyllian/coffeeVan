package coffee;

public class Blocks_amount {
    private Coffee_block block;
    private int amount = 1;
// constructors
    public Blocks_amount() {}
    public Blocks_amount(Coffee_block block) {
        this.block = block;
    }

    // getters
    public Coffee_block getBlock() {
        return block;
    }
    public int getAmount() {
        return amount;
    }
// setters
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setBlock(Coffee_block block) {
        this.block = block;
    }

    /**
     * increases amount of main.java.coffee block by 1
     */
    public void increaseAmount(){
        this.amount+=1;
    }

    /**
     * decreases amount of main.java.coffee block by 1
     */
    public void decreaseAmount(){
        this.amount-=1;
    }

    @Override
    public String toString() {
        return String.format("%s - %d",block,amount);
    }
}
