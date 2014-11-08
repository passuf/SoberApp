package soberapp.vis.ethz.ch.soberapp.data;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static com.orm.SugarRecord.find;
import static com.orm.SugarRecord.findWithQuery;


public class Consum0r {
    private static Consum0r instance = new Consum0r();
    public static final int TOP_N = 3;
    private Consume last;

    public static Consum0r getInstance() {
        return instance;
    }

    private Consum0r() {
        List<Consume> list = new LinkedList<Consume>();//find(Consume.class, null, null, null, "tsp DESC", "1");
        last = !list.isEmpty() ? list.get(0) : null;
    }

    public void consume(Drink drink) {
        // TODO recalculate level
        Consume c = new Consume(drink, last, 0);
        c.save();
        last = c;
    }

    public void pukeLast(){
        Consume c = last;
        last = last.getLast();
        c.delete();
    }

    public List<Drink> topN() {
        List<Drink> drinks = new LinkedList<Drink>();
        Consume item = last;
        for(int i = 0; i < TOP_N && item != null; i++){
            drinks.add(item.getDrink());
            item = item.getLast();
        }
        return drinks;
    }

    public List<Drink> drinks() {
        return find(Drink.class, null, null, null, "name ASC", null);
    }

    public List<Consume> consumed(Timestamp since) {
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp > " + since.toString());
    }

    public List<Consume> consumed(Timestamp since, Timestamp until) {
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp BETWEEN " + since.toString() + " and " + since.toString());
    }

    public double level() {
        return last != null ? last.getLevel() : 0;
    }

    public Timestamp time() {
        return last != null ? last.getTsp() : new Timestamp(0);
    }
}
