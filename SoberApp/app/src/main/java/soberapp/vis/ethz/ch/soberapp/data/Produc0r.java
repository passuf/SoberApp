package soberapp.vis.ethz.ch.soberapp.data;

public class Produc0r {
    private static Produc0r instance = new Produc0r();
    public static final String TOP_N = "3";

    public static Produc0r getInstance() {
        return instance;
    }

    private Produc0r() {

    }

    public Drink addDrink(String name, double percent, int size) {
        Drink drink = new Drink(name, percent, size);
        drink.save();
        return drink;
    }
}
