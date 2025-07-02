
import java.util.ArrayList;
import java.util.HashMap;

public class JoueurBlanc extends Joueur{
    private int id;
    public JoueurBlanc(){
        id = 1;
    }

    public int getId(){
        return this.id;
    }

    public boolean peutJouer(int[][] grille){
        // Renvoie si le joueur blanc peut jouer ou non
        HashMap<Point,ArrayList<Point>> coups = coupsJouable(grille,getId());
        for(Point p : coups.keySet()){
            if(!coups.get(p).isEmpty()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> listeCoupsPossibles(int[][] grille){
        // Renvoie la liste des coups possibles pour le Joueur blanc
        return listeCoupsPossibles(grille,getId());
    }

    public HashMap<Point,ArrayList<Point>> coupPossible(int[][] grille){
        // Renvoie la HashMap des coups possibles pour le Joueur blanc
        System.out.println("Le joueur blanc peut jouer : " + coupsJouable(grille,getId()));
        return coupsJouable(grille,getId());
    }

}
