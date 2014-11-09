package soberapp.vis.ethz.ch.soberapp.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import soberapp.vis.ethz.ch.soberapp.AlcoholLevelCalculator;

/**
 * Created by phgamper on 11/9/14.
 */
public class Predict0r {

    public static long leAlgorithm(long phi, long delta, int n) {
        return (long) ((Math.ceil((n - 1) / 2) * phi + delta) / (Math.ceil((n - 1) / 2) + 1));
    }

    public static List<Consume> predictK(Iterator<Consume> it, int k) {
        AlcoholLevelCalculator alc = AlcoholLevelCalculator.getInstance();
        List<Consume> predictions = new LinkedList<Consume>();
        if (it.hasNext()) {
            long avg = 0;
            long delta = 0;
            int n = 1;
            Consume a = it.next();
            while (it.hasNext()) {
                Consume b = it.next();
                delta = b.getTsp() - a.getTsp();
                a = b;
                avg += delta;
                n++;
            }
            avg = avg / n;

            for (int i = 0; i < k; i++) {
                long phi = leAlgorithm(avg, delta, n + i);
                double level = alc.levelUp(a.getDrink()) + a.getLevel();
                level -= alc.timeUp(phi);
                a = new Consume(a.getDrink(), a, level, a.getTsp()+phi);
                predictions.add(a);
            }
        }
        return predictions;
    }
}
