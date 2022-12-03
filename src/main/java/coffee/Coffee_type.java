package coffee;

public enum Coffee_type {
    GRAIN(1,400,"зернова"),
    GROUND_INSTANT(2,340,"мелена розчинна"),
    GROUND_BREWED(3,360,"мелена заварна"),
    STICKS_3IN1(4,170,"стіки 3в1");

    final int typecode;
    final int kg_price;
    final String title;
    Coffee_type(int typecode,int kg_price, String title){
        this.typecode = typecode;
        this.kg_price=kg_price;
        this.title = title;
    }
    public static Coffee_type getCoffee_type(int typecode) {
        for (Coffee_type type : Coffee_type.values() ){
            if (type.typecode == typecode)
                return type;
        }
        return null;
    }
    public int getType_id() {
        return typecode;
    }
    @Override
    public String toString() {
        return  title;
    }
}
