package soberapp.vis.ethz.ch.soberapp.data;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import soberapp.vis.ethz.ch.soberapp.AlcoholLevelCalculator;

import static com.orm.SugarRecord.find;
import static com.orm.SugarRecord.findAll;
import static com.orm.SugarRecord.findWithQuery;


public class Consum0r {
    public static final int TOP_N = 3;
    private AlcoholLevelCalculator calc;

    public Consum0r(AlcoholLevelCalculator calc) {
        this.calc = calc;
    }

    public void consume(Drink drink) {
      new Consume(drink, last(), calc.getAlcoholLevel()).save();
    }

    public void pukeLast() {
        Consume last = last();
        if(last != null) {
            last.delete();
        }
    }

    public List<Drink> topN() {
        List<Drink> drinks = new LinkedList<Drink>();
        Consume item = last();
        int i = 0;
        while (item != null && i < TOP_N) {
            if (!drinks.contains(item.getDrink())) {
                drinks.add(item.getDrink());
                i++;
            }
            item = item.getLast();
        }
        // fill with default, assuming more then 3 drinks in DB
        Iterator<Drink> it = findAll(Drink.class);
        while (i < TOP_N && it.hasNext()) {
            Drink next = it.next();
            if (!drinks.contains(next)) {
                drinks.add(next);
                i++;
            }
        }
        return drinks;
    }

    public List<Drink> drinks() {
        return find(Drink.class, null, null, null, "name ASC", null);
    }

    public List<Consume> consumed(long since) {
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp > " + since);
    }

    public List<Consume> consumed(long since, long until) {
        return findWithQuery(Consume.class, "SELECT * FROM Consume WHERE tsp BETWEEN "+ since + " and " + until);
    }

    public Consume last(){
        List<Consume> list = find(Consume.class, null, null, null, "tsp DESC", "1");
        return !list.isEmpty() ? list.get(0) : null;
    }

    public double level() {
        Consume last = last();
        return last != null ? last.getLevel() : 0;
    }

    public long time() {
        Consume last = last();
        return last != null ? last.getTsp() : 0;
    }
}
