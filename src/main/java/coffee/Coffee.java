package coffee;

import java.util.Random;

import static coffee.Coffee_brand.*;
import static coffee.Coffee_packing.*;
import static coffee.Coffee_quality.*;
import static coffee.Coffee_type.*;

public class Coffee  {

    private final Coffee_packing packing_type;
    private final Coffee_brand brand;
    private final Coffee_quality quality;
    private final int price;

    public Coffee(int brand, int type, int packing, int quality){
        this.packing_type = getCoffee_packing(packing);
        this.packing_type.setType(getCoffee_type(type));
        this.brand = getCoffee_brand(brand);
        this.quality = getCoffee_quality(quality);
        this.price = (int)(this.packing_type.weight * (this.packing_type.coffeetype.kg_price+this.brand.getBrand_price()+this.quality.quality_price));
    }

    public Coffee_packing getPacking_type() {
        return packing_type;
    }
    public Coffee_brand getBrand() {
        return brand;
    }
    public int getPrice() {
        return price;
    }
    public Coffee_quality getQuality() {
        return quality;
    }

    /**
     * sets random parameters for coffee object
     * @return created object
     */
    public static Coffee randCoffee(){
        Random rand = new Random();
        int brand = rand.nextInt(3) + 1, type = rand.nextInt(4) + 1,
                packing = rand.nextInt(3)+1, quality = rand.nextInt(4)+1;
        return new Coffee(brand,type, packing, quality);
    }

    public int getKgPrice(){
        return (int)(this.price/this.packing_type.weight);
    }

    public boolean isEqual(Coffee another){
        if(!this.brand.equals(another.getBrand()))
            return false;
        if(!this.packing_type.equals(another.getPacking_type()))
            return false;
        if(!this.quality.equals(another.getQuality()))
            return false;
        return this.price == another.getPrice();

    }


    @Override
    public String toString() {
        return "\""+ brand +"\" - "+quality+" - "+ this.packing_type.typeWpack() +" - " + this.getKgPrice()+" грн/кг ";
    }
}
