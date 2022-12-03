package coffee;

public enum Coffee_quality {
    AA(1,"AA",110),
    AB(2,"AB",100),
    BA(3,"BA",70),
    BB(4,"BB",30);

    final int typecode;
    final String quality;
    final int quality_price;

    Coffee_quality(int typecode,String quality, int quality_price) {
        this.typecode = typecode;
        this.quality=quality;
        this.quality_price = quality_price;
    }
    public static Coffee_quality getCoffee_quality(int typecode) {
        for (Coffee_quality qual : Coffee_quality.values() ){
            if (qual.typecode == typecode)
                return qual;
        }
        return null;
    }
    public int getQuality_id(){
        return typecode;
    }
    @Override
    public String toString() {
        return  quality;
    }
}
