package soberapp.vis.ethz.ch.soberapp;

import soberapp.vis.ethz.ch.soberapp.data.Consum0r;
import soberapp.vis.ethz.ch.soberapp.data.Drink;

public class Feed0r {
    public static void main(String[] args){
        new Drink("Beer", 4.8).save();
        Consum0r c = new Consum0r();

        for(Drink d : c.drinks()){
            System.out.println(d.getName());
        }
    }
}
