package van;

public enum Van_type {
    SMALL(1,"MERCEDES Sprinter 316",25,2500),
    MEDIUM(2,"    MAN TGL 2013     ",50,12000),
    LARGE(3,"      DAF  XF  480  FT ", 92, 24000);

    private final int type;
    private final String name;
    private final int volume;
    private final int max_weight;

    Van_type(int type,String name, int volume, int max_weight){
        this.type = type;
        this.name = name;
        this.volume = volume;
        this.max_weight = max_weight;
    }
    public static Van_type getType(int typecode) {
        for (Van_type type : Van_type.values() ){
            if (type.type == typecode)
                return type;
        }
        return null;
    }
    public int getVolume() {
        return volume;
    }
    public int getMax_weight() {
        return max_weight;
    }
    public int getType() {
        return type;
    }
    @Override
    public String toString() {
        if(type==1)
            return "MERCEDES Sprinter";
        return name;
    }
}
