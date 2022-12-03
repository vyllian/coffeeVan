package coffee;


public class Coffee_block {
    private final Coffee_block_Volume block_volume;
    private Coffee coffee;
    private final int block_price;
    private final double block_weight;

    public Coffee_block(Coffee_block_Volume volume, Coffee coffee){
        this.coffee = coffee;
        this.block_volume = volume;
        int packs_number = (int) (this.block_volume.block_volume / coffee.getPacking_type().volume);
        this.block_price = packs_number *coffee.getPrice();
        this.block_weight = packs_number * coffee.getPacking_type().weight;

    }
    public Coffee_block_Volume getBlock_Volume() {
        return block_volume;
    }
    public Coffee getCoffee() {
        return coffee;
    }
    public double getBlock_weight() {
        return block_weight;
    }
    public int getBlock_price() {
        return block_price;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    /**
     * compares 2 blocks
     * @param another block to compare with
     * @return true if equal, else - false
     */
    public boolean isEqual(Coffee_block another){
        if (this.block_volume != another.getBlock_Volume())
            return false;
        return this.coffee.isEqual(another.getCoffee());
    }

    @Override
    public String toString() {
        return  block_volume.title + " блок { " + coffee +'}' ;
    }
}