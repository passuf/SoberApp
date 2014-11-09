package soberapp.vis.ethz.ch.soberapp.data;

import com.orm.SugarRecord;

public class Drink extends SugarRecord<Drink> {
    private String name;
    private double percent;
    private int size; /* in ml */

    public Drink(){
    }

    public Drink(String name, double percent, int size){
        this.name = name;
        this.percent = percent;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public double getPercent() {
        return percent;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        return name + " - " + size + "ml - " + percent + "%";
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Drink){
            return ((Drink) o).name.equals(name) && ((Drink) o).size == size && ((Drink) o).percent == percent;
        }
        return false;
    }
}
