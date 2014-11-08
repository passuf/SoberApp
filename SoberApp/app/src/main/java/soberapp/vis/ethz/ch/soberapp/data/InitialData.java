package soberapp.vis.ethz.ch.soberapp.data;

import java.util.List;

import static com.orm.SugarRecord.deleteAll;
import static com.orm.SugarRecord.listAll;

public class InitialData {
    public static void initDB(){
        deleteAll(Drink.class);
        new Drink("Stange", 5.0, 300).save();
        new Drink("Chübel", 5.0, 500).save();
        new Drink("Mass", 5.0, 1000).save();
        new Drink("Stifel", 5.0, 2000).save();
        new Drink("Cider", 4.0, 500).save();
        new Drink("Champagne", 12, 100).save();
        new Drink("Wine", 13, 100).save();
        new Drink("Wine", 13, 200).save();
        new Drink("Shot", 40.0, 40).save();
        new Drink("Alcopops", 3.6, 330).save();
        new Drink("Liquer", 17.0, 60).save();
        new Drink("Cocktail", 8.0, 300).save();
        new Drink("Long Island Ice Tea", 13.0, 300).save();
        List<Drink> dummy = listAll(Drink.class);
    }
}
