package soberapp.vis.ethz.ch.soberapp.data;

import static com.orm.SugarRecord.deleteAll;

public class InitialData {
    public static void initDB(){
        deleteAll(Drink.class);
        new Drink("Beer", 4.0).save();
        new Drink("Beer", 5.0).save();
        new Drink("Cider", 4.5).save();
        new Drink("Cider", 5.3).save();
        new Drink("Cider", 7.5).save();
        new Drink("Champagne", 12).save();
        new Drink("Wine", 11.5).save();
        new Drink("Wine", 12).save();
        new Drink("Wine", 14).save();
        new Drink("Wnite Spirits", 37.5).save();
        new Drink("Dark Spirits", 40.0).save();
        new Drink("Alcopops", 4.0).save();
        new Drink("Cream Liquer", 17.0).save();
    }
}
