package soberapp.vis.ethz.ch.soberapp.data;

import java.sql.Timestamp;
import java.util.List;

import static com.orm.SugarRecord.find;
import static com.orm.SugarRecord.findWithQuery;
import static com.orm.SugarRecord.listAll;


public class Consum0r {
    private static Consum0r instance = new Consum0r();
    public static final String TOP_N = "5";
    private Consume last;

    public static Consum0r getInstance() {
        return instance;
    }

    private Consum0r() {
//        List<Consume> list = findWithQuery(Consume.class, "SELECT * FROM Consume ORDERBY tsp DESC LIMIT 1");
//        last = !list.isEmpty() ? list.get(0) : new Consume(null, 0, 0);
    }

    public void consume(Drink drink, int amount) {
        Consume c = new Consume(drink, amount);
        c.save();
    }

    public List<Drink> topN() {
        return find(Drink.class, "", null, "", "lastAccess DESC", TOP_N);
    }

    public List<Drink> drinks() {
        return listAll(Drink.class);
    }

    public List<Consume> consumed(Timestamp since) {
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp > " + since.toString());
    }

    public List<Consume> consumed(Timestamp since, Timestamp until) {
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp BETWEEN " + since.toString() + " and " + since.toString());
    }

    public double level() {
        return last.getLevel();
    }

    public Timestamp time() {
        return last.getTsp();
    }
}
