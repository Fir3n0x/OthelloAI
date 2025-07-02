
import java.util.ArrayList;
import java.util.Random;

public class Jeu {
    private final int N = 8;
    private int[][] grille;
    private boolean tourBlanc;
    private JoueurBlanc j1;
    private JoueurNoir j2;

    //JEU IA RANDOM

    //Dans ce jeu, les pions noirs commencent à jouer
    //Pour l'instant on part du principe que le joueur (non IA) commence à jouer donc il prend les noirs
    //JEU INITIAL
    public Jeu(){
        grille = new int[N][N];
        tourBlanc = false;
        j1 = new JoueurBlanc();
        j2 = new JoueurNoir();
        initialiser();
    }

    //0->vide
    //1->blanc
    //2->noir
    public void initialiser(){
        // Initialise la grille au plateau de départ du jeu
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                this.grille[i][j] = 0;
            }
        }
        this.grille[3][3] = 1;
        this.grille[4][4] = 1;
        this.grille[3][4] = 2;
        this.grille[4][3] = 2;
    }


    public boolean estFini(){
        // Renvoit si le jeu est fini
        return (!this.j1.peutJouer(this.grille) && !this.j2.peutJouer(this.grille));
    }

    public int[] getScore(){
        // Renvoie les scores des deux joueurs
        int[] score = new int[2];
        int nbPionNoir = 0;
        int nbPionBlanc = 0;
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(this.grille[i][j] == 2){
                    nbPionNoir++;
                }else if(grille[i][j] == 1){
                    nbPionBlanc++;
                }
            }
        }
        score[0] = nbPionBlanc;
        score[1] = nbPionNoir;
        return score;
    }

    public boolean noirGagne(){
        // Renvoie si le joueur noir a gagné
        int nbPionNoir = 0;
        int nbPionBlanc = 0;
        for(int i = 0; i<N; i++){
            for(int j = 0; j<N; j++){
                if(grille[i][j] == 2){
                    nbPionNoir++;
                }else if(grille[i][j] == 1){
                    nbPionBlanc++;
                }
            }
        }
        return nbPionNoir > nbPionBlanc;
    }

    public int[][] getGrille(){
        return this.grille;
    }

    public void afficher(){
        // Affiche la grille dans le terminal
        System.out.println("    0   1   2   3   4   5   6   7  ");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        for(int i = 0; i<N; i++){
            System.out.print(i+" |");
            for(int j = 0; j<N; j++){
                System.out.print(" " + grille[i][j] + " |");
            }
            System.out.print("\n");
        }
        System.out.print("  +---+---+---+---+---+---+---+---+\n");
    }

    public void rotationTour(){
        // Change le tour du joueur actuel
        if(this.tourBlanc) this.tourBlanc = false;
        else this.tourBlanc = true;
    }

    public Jeu copyJeu(){
        // Effectue une copie du jeu
        Jeu jeuCopie = new Jeu();
        int [][] tab = new int[N][N];
        for(int i=0 ; i<N ; i++){
            System.arraycopy(grille[i], 0, tab[i], 0, N);
        }
        jeuCopie.setGrille(tab);
        jeuCopie.setJ1(j1);
        jeuCopie.setJ2(j2);
        jeuCopie.setTourBlanc(tourBlanc);
        return jeuCopie;
    }

    public Tuple jeuFictif(){
        // Génère un autre jeu aléatoirement jusqu'à la fin d'une partie
        Jeu random = copyJeu();
        ArrayList<Point> coupJeu = new ArrayList<Point>();
        while(!random.estFini()){
            Random rand = new Random();
            if(random.getTourBlanc()){
                ArrayList<Point> coupBlanc = random.j1.listeCoupsPossibles(random.grille);
                if(!coupBlanc.isEmpty()) {
                    Point p1 = coupBlanc.get(rand.nextInt(coupBlanc.size()));
                    coupJeu.add(p1);
                    random.modifGrille(p1);
                }
                random.rotationTour();
            }else{
                ArrayList<Point> coupNoir = random.j2.listeCoupsPossibles(random.grille);
                if(!coupNoir.isEmpty()) {
                    Point p2 = coupNoir.get(rand.nextInt(coupNoir.size()));
                    coupJeu.add(p2);
                    random.modifGrille(p2);
                }
                random.rotationTour();
            }
        }
        return new Tuple(!random.noirGagne(),coupJeu);
    }

    public void modifGrille(Point p){
        // Modifie la grille avec le coup donné en paramètre
        //k -> joueur courant, j -> joueur adverse
        int k = 2, j = 1 ;
        int x = p.getX();
        int y = p.getY();
        if(tourBlanc){
            k=1;j=2;
        }
        this.grille[x][y] = k;
        if(!j1.horsJeu(new Point(x-1,y)) && this.grille[x-1][y] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x-i,y)) && this.grille[x-i][y] == j && !estCouleurAlliee){
                coup.add(new Point(x-i,y));
                if(!j1.horsJeu(new Point(x-(i+1),y)) && this.grille[x-(i+1)][y] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x-1,y-1)) && this.grille[x-1][y-1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x-i,y-i)) && this.grille[x-i][y-i] == j && !estCouleurAlliee){
                coup.add(new Point(x-i,y-i));
                if(!j1.horsJeu(new Point(x-(i+1),y-(i+1))) && this.grille[x-(i+1)][y-(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x,y-1)) && this.grille[x][y-1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x,y-i)) && this.grille[x][y-i] == j && !estCouleurAlliee){
                coup.add(new Point(x,y-i));
                if(!j1.horsJeu(new Point(x,y-(i+1))) && this.grille[x][y-(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x+1,y-1))&& this.grille[x+1][y-1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x+i,y-i)) && this.grille[x+i][y-i] == j && !estCouleurAlliee){
                coup.add(new Point(x+i,y-i));
                if(!j1.horsJeu(new Point(x+(i+1),y-(i+1))) && this.grille[x+(i+1)][y-(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x+1,y)) && this.grille[x+1][y] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x+i,y)) && this.grille[x+i][y] == j && !estCouleurAlliee){
                coup.add(new Point(x+i,y));
                if(!j1.horsJeu(new Point(x+(i+1),y)) && this.grille[x+(i+1)][y] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x+1,y+1))&& this.grille[x+1][y+1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x+i,y+i)) && this.grille[x+i][y+i] == j && !estCouleurAlliee){
                coup.add(new Point(x+i,y+i));
                if(!j1.horsJeu(new Point(x+(i+1),y+(i+1))) && this.grille[x+(i+1)][y+(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x,y+1)) && this.grille[x][y+1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x,y+i)) && this.grille[x][y+i] == j && !estCouleurAlliee){
                coup.add(new Point(x,y+i));
                if(!j1.horsJeu(new Point(x,y+(i+1))) && this.grille[x][y+(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x-1,y+1)) && this.grille[x-1][y+1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x-i,y+i)) && this.grille[x-i][y+i] == j && !estCouleurAlliee){
                coup.add(new Point(x-i,y+i));
                if(!j1.horsJeu(new Point(x-(i+1),y+(i+1))) && this.grille[x-(i+1)][y+(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }
    }


    public ArrayList<Point> modifGrilleGUI(Point p){
        // Modifie la grille version interface graphique
        //k -> joueur courant, j -> joueur adverse
        ArrayList<Point> pointRes = new ArrayList<Point>();
        int k,j;
        int x = p.getX();
        int y = p.getY();
        if(tourBlanc){
            k=1;j=2;
        }
        else{
            k=2;j=1;
        }
        this.grille[x][y] = k;
        if(!j1.horsJeu(new Point(x-1,y)) && this.grille[x-1][y] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x-i,y)) && this.grille[x-i][y] == j && !estCouleurAlliee){
                coup.add(new Point(x-i,y));
                if(!j1.horsJeu(new Point(x-(i+1),y)) && this.grille[x-(i+1)][y] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x-1,y-1)) && this.grille[x-1][y-1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x-i,y-i)) && this.grille[x-i][y-i] == j && !estCouleurAlliee){
                coup.add(new Point(x-i,y-i));
                if(!j1.horsJeu(new Point(x-(i+1),y-(i+1))) && this.grille[x-(i+1)][y-(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x,y-1)) && this.grille[x][y-1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x,y-i)) && this.grille[x][y-i] == j && !estCouleurAlliee){
                coup.add(new Point(x,y-i));
                if(!j1.horsJeu(new Point(x,y-(i+1))) && this.grille[x][y-(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x+1,y-1))&& this.grille[x+1][y-1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x+i,y-i)) && this.grille[x+i][y-i] == j && !estCouleurAlliee){
                coup.add(new Point(x+i,y-i));
                if(!j1.horsJeu(new Point(x+(i+1),y-(i+1))) && this.grille[x+(i+1)][y-(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x+1,y)) && this.grille[x+1][y] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x+i,y)) && this.grille[x+i][y] == j && !estCouleurAlliee){
                coup.add(new Point(x+i,y));
                if(!j1.horsJeu(new Point(x+(i+1),y)) && this.grille[x+(i+1)][y] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x+1,y+1))&& this.grille[x+1][y+1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x+i,y+i)) && this.grille[x+i][y+i] == j && !estCouleurAlliee){
                coup.add(new Point(x+i,y+i));
                if(!j1.horsJeu(new Point(x+(i+1),y+(i+1))) && this.grille[x+(i+1)][y+(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x,y+1)) && this.grille[x][y+1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x,y+i)) && this.grille[x][y+i] == j && !estCouleurAlliee){
                coup.add(new Point(x,y+i));
                if(!j1.horsJeu(new Point(x,y+(i+1))) && this.grille[x][y+(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }if(!j1.horsJeu(new Point(x-1,y+1)) && this.grille[x-1][y+1] == j){
            int i = 1;
            ArrayList<Point> coup = new ArrayList<Point>();
            boolean estCouleurAlliee = false;
            while(!j1.horsJeu(new Point(x-i,y+i)) && this.grille[x-i][y+i] == j && !estCouleurAlliee){
                coup.add(new Point(x-i,y+i));
                if(!j1.horsJeu(new Point(x-(i+1),y+(i+1))) && this.grille[x-(i+1)][y+(i+1)] == k){
                    for(Point pin : coup){
                        this.grille[pin.getX()][pin.getY()] = k;
                        pointRes.add(pin);
                    }
                    estCouleurAlliee = true;
                }
                i++;
            }
        }
        return pointRes;
    }



    public void generationJeu(int n, SearchNode dernierParcourue){
        // Génère n jeux aléatoirement et crée les nodes pour le MCTS
        for(int i = 0; i<n; i++){
            Tuple jeu = jeuFictif();
            dernierParcourue.expansion(jeu.getChemin(),jeu.getBoolean());
        }
    }

    public void setGrille(int[][] grille) {
        this.grille = grille;
    }
    public void setJ1(JoueurBlanc j1) {
        this.j1 = j1;
    }
    public void setJ2(JoueurNoir j2) {
        this.j2 = j2;
    }
    public void setTourBlanc(boolean tourBlanc) {
        this.tourBlanc = tourBlanc;
    }
    public boolean getTourBlanc(){
        return tourBlanc;
    }
    public JoueurNoir getJoueurNoir() {
        return j2;
    }
    public JoueurBlanc getJoueurBlanc(){
        return j1;
    }
}
