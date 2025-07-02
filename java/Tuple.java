
import java.util.ArrayList;

public class Tuple {
    boolean gagne;
    ArrayList<Point> chemin;

    public Tuple(boolean g, ArrayList<Point> c){
        gagne = g;
        chemin = c;
    }

    public ArrayList<Point> getChemin() {
        return chemin;
    }

    public boolean getBoolean(){
        return gagne;
    }
}
