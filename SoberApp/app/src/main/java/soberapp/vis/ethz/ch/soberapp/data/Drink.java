package soberapp.vis.ethz.ch.soberapp.data;

import com.orm.SugarRecord;

public class Drink extends SugarRecord<Drink> {
    private String name;
    private double units;
    private long lastAccess;
    
    public Drink(){
    }

    public Drink(String name, double units){
        this.name = name;
        this.units = units;
        this.lastAccess = 0;
    }

    public Drink(String name, double units, long lastAccess){
        this.name = name;
        this.units = units;
        this.lastAccess = lastAccess;
    }

    public String getName() {
        return name;
    }

    public double getUnits() {
        return units;
    }

    public long getLastAccess() {
        return lastAccess;
    }
}
