package coffee;

public enum Coffee_packing {
    BAG(1,60,0.25,"мішки"),
    JAR(2,1.5,0.00325,"банки"),
    PACK(3,0.35, 0.003,"пачки");

final int typecode;
     Coffee_type coffeetype;
     final double weight;
     final double volume;
     final String title;

    Coffee_packing(int typecode, double weight, double volume, String title){
       this.typecode = typecode;
        this.weight = weight;
       this.volume = volume;
       this.title=title;
   }
    public static Coffee_packing getCoffee_packing(int typecode) {
        for (Coffee_packing pack : Coffee_packing.values() ){
            if (pack.typecode == typecode)
                return pack;
        }
        return null;
    }
    public void setType(Coffee_type coffeetype) {
        this.coffeetype = coffeetype;
    }
    public Coffee_type getCoffeetype() {
        return coffeetype;
    }
    public int getPack_id() {
        return typecode;
    }
    public String typeWpack() {
        return this.coffeetype + " - " + this.title;
    }
    @Override
    public String toString() {return title;}

}

