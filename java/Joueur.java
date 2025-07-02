
import java.util.ArrayList;
import java.util.HashMap;

public class Joueur {
    
    public Joueur(){

    }

    //le int k permet de définir quel joueur joue, 1=blanc, 2=noir
    public HashMap<Point,ArrayList<Point>> coupsJouable(int[][] grille, int k){
        // Renvoie une HashMap des coups jouables contenant les coordonées du point de départ et la liste des coups jouables à partir de ce point
        HashMap<Point,ArrayList<Point>> coups = new HashMap<Point,ArrayList<Point>>();
        ArrayList<Point> jetonsAdverse = new ArrayList<Point>();
        ArrayList<Point> jetonsAllies = new ArrayList<Point>();
        for(int i = 0; i<grille.length; i++){
            for(int j = 0; j<grille.length; j++){
                if(grille[i][j] != k && grille[i][j] !=0){
                    jetonsAdverse.add(new Point(i,j));
                }
                if(grille[i][j] == k){
                    jetonsAllies.add(new Point(i,j));
                }
            }
        }
        for(Point p : jetonsAllies){
            int x = p.getX();
            int y = p.getY();
            //Je crée mes points à partir de l'angle pi (cercle trigo) puis je vais dans le sens trigo
            ArrayList<Point> l = new ArrayList<Point>();
            Point p1 = new Point(x,y-1);
            Point p2 = new Point(x+1,y-1);
            Point p3 = new Point(x+1,y);
            Point p4 = new Point(x+1,y+1);
            Point p5 = new Point(x,y+1);
            Point p6 = new Point(x-1,y+1);
            Point p7 = new Point(x-1,y);
            Point p8 = new Point(x-1,y-1);
            if(p1.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x,y-i)).in(jetonsAdverse) && !horsJeu(new Point(x,y-i)) && !estCaseVide){
                    if(!horsJeu(new Point(x,y-(i+1))) && grille[x][y-(i+1)] == 0){
                        l.add(new Point(x,y-(i+1)));
                        estCaseVide = true;
                    }
                    i++;
                }
            }if(p2.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x+i,y-i)).in(jetonsAdverse) && !horsJeu(new Point(x+i,y-i)) && !estCaseVide){
                    if(!horsJeu(new Point(x+(i+1),y-(i+1))) && grille[x+(i+1)][y-(i+1)] == 0){
                        l.add(new Point(x+(i+1),y-(i+1)));
                        estCaseVide = true;
                    }
                    i++;
                }
            }if(p3.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x+i,y)).in(jetonsAdverse) && !horsJeu(new Point(x+i,y)) && !estCaseVide){
                    if(!horsJeu(new Point(x+(i+1),y)) && grille[x+(i+1)][y] == 0){
                        l.add(new Point(x+(i+1),y));
                        estCaseVide = true;
                    }
                    i++;
                }
            }if(p4.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x+i,y+i)).in(jetonsAdverse) && !horsJeu(new Point(x+i,y+i)) && !estCaseVide){
                    if(!horsJeu(new Point(x+(i+1),y+(i+1))) && grille[x+(i+1)][y+(i+1)] == 0){
                        l.add(new Point(x+(i+1),y+(i+1)));
                        estCaseVide = true;
                    }
                    i++;
                }
            }if(p5.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x,y+i)).in(jetonsAdverse) && !horsJeu(new Point(x,y+i)) && !estCaseVide){
                    if(!horsJeu(new Point(x,y+(i+1))) && grille[x][y+(i+1)] == 0){
                        l.add(new Point(x,y+(i+1)));
                        estCaseVide = true;
                    }
                    i++;
                }
            }if(p6.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x-i,y+i)).in(jetonsAdverse) && !horsJeu(new Point(x-i,y+i)) && !estCaseVide){
                    if(!horsJeu(new Point(x-(i+1),y+(i+1))) && grille[x-(i+1)][y+(i+1)] == 0){
                        l.add(new Point(x-(i+1),y+(i+1)));
                        estCaseVide = true;
                    }
                    i++;
                }
            }if(p7.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x-i,y)).in(jetonsAdverse) && !horsJeu(new Point(x-i,y)) && !estCaseVide){
                    if(!horsJeu(new Point(x-(i+1),y)) && grille[x-(i+1)][y] == 0){
                        l.add(new Point(x-(i+1),y));
                        estCaseVide = true;
                    }
                    i++;
                }
            }if(p8.in(jetonsAdverse)){
                int i = 1;
                boolean estCaseVide = false;
                while((new Point(x-i,y-i)).in(jetonsAdverse) && !horsJeu(new Point(x-i,y-i)) && !estCaseVide){
                    if(!horsJeu(new Point(x-(i+1),y-(i+1))) && grille[x-(i+1)][y-(i+1)] == 0){
                        l.add(new Point(x-(i+1),y-(i+1)));
                        estCaseVide = true;
                    }
                    i++;
                }
            }
            if(!l.isEmpty()) {
                coups.put(p, l);
            }
            //System.out.println(coups.toString());
        }
        return coups;
    }



    public ArrayList<Point> listeCoupsPossibles(int grille[][], int k){
        // Transforme la HashMap précédente en liste
        HashMap<Point,ArrayList<Point>> coups = coupsJouable(grille,k);
        ArrayList<Point> listecoups = new ArrayList<Point>();
        for(ArrayList<Point> l : coups.values()){
            listecoups.addAll(l);
        }
        return listecoups;
    }


    public boolean horsJeu(Point p){
        // Renvoie si un point est dans la grille ou non
        int x = p.getX();
        int y = p.getY();
        if(x < 0 || x>7 || y < 0 || y> 7){
            return true;
        }
        return false;
    }

    public void afficherCoups(HashMap<Point,ArrayList<Point>> c){
        // Affichage des coups
        int i = 1;
        int nbCoupTot = 0;
        System.out.println("\n");
        if(this instanceof JoueurNoir) System.out.println("<JoueurNoir>\n");
        else System.out.println("<JoueurBlanc>\n");
        for(Point p : c.keySet()){
            System.out.println("Point " + i + ": " + p.toString() + "\n");
            System.out.println(c.get(p).size() + " coup(s) pour ce point");
            for(Point pint : c.get(p)){
                System.out.println(pint.toString() + "\n");
                nbCoupTot++;
            }
            System.out.println("----------------------------\n");
            i++;
        }
        System.out.println(nbCoupTot + " coups possibles au total pour ce joueur -_-\n");
    }

}
