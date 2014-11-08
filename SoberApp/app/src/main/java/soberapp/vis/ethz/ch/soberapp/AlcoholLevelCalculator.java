package soberapp.vis.ethz.ch.soberapp;


import android.content.Context;

import java.util.Date;

import soberapp.vis.ethz.ch.soberapp.data.Consum0r;
import soberapp.vis.ethz.ch.soberapp.data.Consume;

/**
 * Created by schluemi on 08/11/14.
 */
public class AlcoholLevelCalculator {


    private long updateTime;
    private double alcoholLevel;
    private double reductionFactor;
    private double decFactor;
    private Context context;
    private Settings settings;

    public AlcoholLevelCalculator(Context context, Consum0r consumor)
    {
        this.updateTime = consumor.time().getTime();
        this.alcoholLevel = consumor.level();
        this.context = context;
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

    public Date timeSober()
    {
        return new Date(updateTime + (long)(alcoholLevel / decFactor * 60 * 1000));
    }

    public void addDrink(Consume consume) {
        alcoholLevel += (consume.getAmount() * consume.getDrink().getPercent()/100 * 0.8) / (settings.getWeight() * reductionFactor) * 0.9;
    }

    public double getAlcoholLevel()
    {
        long currentTime = System.currentTimeMillis();

        alcoholLevel -= (currentTime - updateTime) / 1000 / 60 * 0.0025;
        if (alcoholLevel < 0)
            alcoholLevel = 0;
        updateTime = currentTime;

        return alcoholLevel;
    }

}