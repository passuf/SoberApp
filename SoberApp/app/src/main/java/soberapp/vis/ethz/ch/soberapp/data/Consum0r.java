package soberapp.vis.ethz.ch.soberapp.data;

import java.security.Timestamp;
import java.util.List;

import static com.orm.SugarRecord.find;
import static com.orm.SugarRecord.findWithQuery;
import static com.orm.SugarRecord.listAll;


public class Consum0r {
    public static final String TOP_N = "5";

    public Consum0r(){

    }

    public void consume(Drink drink, int amount){
        Consume c = new Consume(drink, amount);
        c.save();
    }

    public List<Drink> topN(){
        return find(Drink.class, "", null, "", "lastAccess DESC", TOP_N);
    }

    public List<Drink> drinks(){
        return listAll(Drink.class);
    }

    public List<Consume> consumed(Timestamp since){
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp > " + since.toString());
    }

    public List<Consume> consumed(Timestamp since, Timestamp until){
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp BETWEEN " + since.toString() + " and " + since.toString());
    }
}
