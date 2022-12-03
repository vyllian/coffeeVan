package coffee;

public enum Coffee_brand {
    JACOBS(1,40, "Jacobs"),
    LAVAZZA(2,30,"LavAzza"),
    NESCAFE(3,35,"Nescafe");

    final int typecode;
    final int brand_price;
    final String brand_name;
    Coffee_brand(int typecode ,int brand_price, String brand_name){
        this.typecode = typecode;
        this.brand_price=brand_price;
        this.brand_name = brand_name;
    }

    public static Coffee_brand getCoffee_brand(int typecode) {
        for (Coffee_brand brand : Coffee_brand.values() ){
            if (brand.typecode == typecode)
                return brand;
        }
        return null;
    }
    public int getBrand_price() {
        return brand_price;
    }

    public int getBrand_id() {
        return typecode;
    }

    @Override
    public String toString() {
        return brand_name;
    }
}
