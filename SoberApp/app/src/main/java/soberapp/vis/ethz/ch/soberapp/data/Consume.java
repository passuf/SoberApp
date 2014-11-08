package soberapp.vis.ethz.ch.soberapp.data;

import com.orm.SugarRecord;

import java.sql.Timestamp;

public class Consume extends SugarRecord<Consume> {
    private Drink drink;
    private Timestamp tsp;
    private int amount; /* in ml */
    
    public Consume(){
    }

    public Consume(Drink drink, int amount){
        this.drink = drink;
        this.tsp = new Timestamp(System.currentTimeMillis());
        this.amount = amount;
    }

    public Consume(Drink drink, Timestamp tsp, int amount){
        this.drink = drink;
        this.tsp = tsp;
        this.amount = amount;
    }

    public Drink getDrink() {
        return drink;
    }

    public Timestamp getTsp() {
        return tsp;
    }

    public int getAmount() {
        return amount;
    }
}
