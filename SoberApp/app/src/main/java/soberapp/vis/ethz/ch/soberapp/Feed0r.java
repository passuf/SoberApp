package soberapp.vis.ethz.ch.soberapp;

import soberapp.vis.ethz.ch.soberapp.data.Consum0r;
import soberapp.vis.ethz.ch.soberapp.data.Drink;

public class Feed0r {
    public static void main(String[] args){
        new Drink("Guiness", 4.8).save();
        new Drink("Long Island Ice Tea", 13.0).save();
        new Drink("Red Wine", 14.0).save();

        Consum0r c = new Consum0r();
        for(Drink d : c.drinks()){
            System.out.println(d.getName());
        }
    }
}
