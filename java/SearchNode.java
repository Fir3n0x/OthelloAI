
import java.util.ArrayList;

public class SearchNode {
    private int partiesGagnees;
    private int partiesJouees;
    private SearchNode parent;
    private Point coup;
    private ArrayList<SearchNode> enfants;

    public SearchNode(int pg, int pj, SearchNode p, Point c) {
        partiesGagnees = pg;
        partiesJouees = pj;
        parent = p;
        enfants = new ArrayList<SearchNode>();
        coup = c;
    }

    public int getPartiesGagnees() {
        // Renvoie l'attribut partiesGagnees
        return partiesGagnees;
    }

    public int getPartiesJouees() {
        // Renvoie l'attribut partiesJouees
        return partiesJouees;
    }

    public Point getCoup() {
        // Renvoie l'attribut coup
        return coup;
    }

    public SearchNode selection() {
        // Choisi la meilleure node en terme de ratio partiesGagnees / partiesJouees
        double max = -1;
        SearchNode maxS = null;
        for (SearchNode s : enfants) {
            //debug.add((s.getCoup()));
            System.out.println("Score : " + (double) s.partiesGagnees / s.partiesJouees + " dont le coup est " + s.getCoup().toString());
            if ((double) s.partiesGagnees / s.partiesJouees > max) {
                max = (double) s.partiesGagnees / s.partiesJouees;
                maxS = s;
            }
        }
        System.out.println("L'IA joue le coup " + maxS.getCoup().toString());
        return maxS;
    }

    public void expansion(ArrayList<Point> chemin, boolean gagne) {
        // Crée des nodes dans l'arbre si le chemin n'a jamais été parcouru, sinon il passe continue la recherche plus loin
        if (!chemin.isEmpty()) {
            boolean nodeDejaDansArbre = false;
            Point p = chemin.remove(0);
            for (SearchNode s : enfants) {
                if (s.getCoup().equals(p)) {
                    nodeDejaDansArbre = true;
                    if (gagne) {
                        setPartiesGagnees(getPartiesGagnees() + 1);
                    }
                    setPartiesJouees(getPartiesJouees() + 1);
                    s.expansion(chemin, gagne);
                }
            }
            if (!nodeDejaDansArbre) {
                if (gagne) {
                    SearchNode enfant = new SearchNode(1, 1, this, p);
                    //System.out.println(this.toString());
                    enfants.add(enfant);
                    enfant.expansion(chemin, gagne);
                } else {
                    SearchNode enfant = new SearchNode(0, 1, this, p);
                    enfants.add(enfant);
                    enfant.expansion(chemin, gagne);
                }
            }
        }
    }

    public SearchNode changementNode(Point p){
        for(SearchNode s : enfants){
            if(s.getCoup().equals(p)){
                return s;
            }
        }
        return new SearchNode(0,0,null,p);
    }

    public void setPartiesGagnees(int partiesGagnees) {
        this.partiesGagnees = partiesGagnees;
    }

    public void setPartiesJouees(int partiesJouees) {
        this.partiesJouees = partiesJouees;
    }

    public String toString() {
        return ("Parties Gagnées : " + partiesGagnees + " Parties Jouees : " + partiesJouees + " Parent : " + parent);
    }
}

