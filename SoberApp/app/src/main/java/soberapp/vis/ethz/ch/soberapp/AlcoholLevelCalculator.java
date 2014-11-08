package soberapp.vis.ethz.ch.soberapp;


import android.content.Context;

import java.util.Date;

import soberapp.vis.ethz.ch.soberapp.data.Consum0r;
import soberapp.vis.ethz.ch.soberapp.data.Consume;
import soberapp.vis.ethz.ch.soberapp.data.Drink;

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
        this.updateTime = consumor.time().getTime();
        this.alcoholLevel = consumor.level();
        this.settings = new Settings(context);
        if (settings.getSex() == "Female") {
            reductionFactor = 1055 * (0.203 - 0.07 * settings.getAge() + 0.1069 * settings.getHeight() + 0.2466 * settings.getWeight()) / (0.8 * settings.getWeight());
            decFactor = 0.15 / 60;
        }
        else {
            reductionFactor = 1055 * (2.447 - 0.09516 * settings.getAge() + 0.1074 * settings.getHeight() * 0.3362 * settings.getWeight()) / (0.8 * settings.getWeight());
            decFactor = 0.15 / 60;
        }
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
        consumor.consume(drink);
        alcoholLevel += (drink.getSize() * drink.getPercent()/100 * 0.8) / (settings.getWeight() * reductionFactor) * 0.9;
    }

    private void calculateAlcoholLevel()
    {
        long currentTime = System.currentTimeMillis();

        alcoholLevel -= (currentTime - updateTime) / 1000 / 60 * 0.0025;
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