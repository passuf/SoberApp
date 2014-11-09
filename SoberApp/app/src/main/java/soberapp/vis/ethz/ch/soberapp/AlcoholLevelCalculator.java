package soberapp.vis.ethz.ch.soberapp;


import android.content.Context;

import java.util.Date;
import java.util.List;

import soberapp.vis.ethz.ch.soberapp.data.Consum0r;
import soberapp.vis.ethz.ch.soberapp.data.Consume;
import soberapp.vis.ethz.ch.soberapp.data.Drink;
import soberapp.vis.ethz.ch.soberapp.data.Predict0r;

import static com.orm.SugarRecord.findAll;

/**
 * Created by schluemi on 08/11/14.
 */
public class AlcoholLevelCalculator {

    private static final AlcoholLevelCalculator instance = new AlcoholLevelCalculator();
    private Consum0r consumor;
    private long updateTime;
    private double alcoholLevel;
    private double reductionFactor;
    private double decFactor;
    private Settings settings;

    public void setup(Context context){
        this.consumor = new Consum0r(this);
        this.updateTime = consumor.time();
        this.alcoholLevel = consumor.level();
        this.settings = new Settings(context);
        if (settings.getSex() == "Female") {
            reductionFactor = 1055 * (0.203 - 0.07 * settings.getAge() + 0.1069 * settings.getHeight() + 0.2466 * settings.getWeight()) / (0.8);
            decFactor = 0.15 / 60;
        }
        else {
            reductionFactor = 1.055 * (2.447 - 0.09516 * settings.getAge() + 0.1074 * settings.getHeight() + 0.3362 * settings.getWeight()) / (0.8);
            decFactor = 0.15 / 60;
        }
        calculateAlcoholLevel();
    }
    private AlcoholLevelCalculator(){
    }
    public static AlcoholLevelCalculator getInstance(){
        return instance;
    }

    public Date timeSober()
    {
        calculateAlcoholLevel();
        return new Date(updateTime + (long)(alcoholLevel / decFactor * 60 * 1000));
    }

    public void addDrink(Drink drink) {
        alcoholLevel += levelUp(drink);
        calculateAlcoholLevel();
        consumor.consume(drink);

        //just for debug
        List<Consume> list = Predict0r.predictK(findAll(Consume.class), 5);
    }

    public double levelUp(Drink drink){
        return (drink.getSize() * drink.getPercent()/100 * 0.8) / (reductionFactor) * 0.9;
    }

    public long timeUp(long delta){
        return (long) Math.floor(delta / 1000 / 60 * decFactor);
    }

    private void calculateAlcoholLevel()
    {
        long currentTime = System.currentTimeMillis();

        alcoholLevel -= timeUp(currentTime - updateTime);
        if (alcoholLevel < 0)
            alcoholLevel = 0;
        updateTime = currentTime;
    }

    public double getAlcoholLevel()
    {
        calculateAlcoholLevel();
        return alcoholLevel;
    }

    public Consum0r getConsumor() {
        return consumor;
    }


}