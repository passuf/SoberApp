package soberapp.vis.ethz.ch.soberapp.data;

import com.orm.SugarRecord;

public class Drink extends SugarRecord<Drink> {
    private String name;
    private double percent;

    private long lastAccess;
    
    public Drink(){
    }

    public Drink(String name, double percent){
        this.name = name;
        this.percent = percent;
        this.lastAccess = 0;
    }

    public Drink(String name, double percent, long lastAccess){
        this.name = name;
        this.percent = percent;
        this.lastAccess = lastAccess;
    }

    public String getName() {
        return name;
    }

    public double getPercent() {
        return percent;
    }

    public long getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }

    @Override
    public String toString(){
        return this.getName() + " - " + this.getPercent() + "%";
    }
}
