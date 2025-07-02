
import java.util.ArrayList;
import java.util.HashMap;

public class JoueurNoir extends Joueur{
    private int id;
    public JoueurNoir(){
        id = 2;
    }

    public int getId(){
        return this.id;
    }

    public boolean peutJouer(int[][] grille){
        // Renvoie si le joueur noir peut jouer ou non
        HashMap<Point,ArrayList<Point>> coups = coupsJouable(grille,getId());
        for(Point p : coups.keySet()){
            if(!coups.get(p).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> listeCoupsPossibles(int[][] grille){
        // Renvoie la liste des coups possibles pour le Joueur noir
        return listeCoupsPossibles(grille,getId());
    }

    public HashMap<Point,ArrayList<Point>> coupPossible(int[][] grille){
        // Renvoie la HashMap des coups possibles pour le Joueur noir
        return coupsJouable(grille,getId());
    }
    

}
