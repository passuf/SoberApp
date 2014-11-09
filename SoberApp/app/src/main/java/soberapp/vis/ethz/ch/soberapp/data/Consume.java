package soberapp.vis.ethz.ch.soberapp.data;

import com.orm.SugarRecord;

public class Consume extends SugarRecord<Consume> {
    private Drink drink;
    private long tsp;
    private double level;
    private Consume last;
    
    public Consume(){
    }

    public Consume(Drink drink, Consume last, double level){
        this.drink = drink;
        this.tsp = System.currentTimeMillis();
        this.level = level;
        this.last = last;
    }

    public Consume(Drink drink, Consume last, double level, long tsp){
        this.drink = drink;
        this.tsp = tsp;
        this.level = level;
        this.last = last;
    }

    public Drink getDrink() {
        return drink;
    }

    public long getTsp() {
        return tsp;
    }

    public double getLevel() {
        return level;
    }

    public Consume getLast() {
        return last;
    }
}
