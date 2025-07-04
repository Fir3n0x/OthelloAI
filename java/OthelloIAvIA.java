
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class OthelloIAvIA extends JFrame {
    // Othello Joueur contre IA
    private static JButton[][] boardButtons;
    JLabel blackScoreLabel;
    JLabel whiteScoreLabel;
    JLabel res;
    JLabel nbTourLabel;
    int nbTour;
    private int blackScore = 2; // Scores initiaux
    private int whiteScore = 2;

    public OthelloIAvIA() {
        setTitle("Othello");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        nbTour = 0;


        // Panel principal pour le plateau et le scoreboard
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel pour le titre
        JLabel titleLabel = new JLabel("Othello", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel pour le plateau de jeu avec quadrillage
        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        boardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Initialisation du plateau de jeu avec les boutons
        boardButtons = new JButton[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setBorderPainted(false);
                boardPanel.add(button);
                boardButtons[row][col] = button;
            }
        }

        // Board vide
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                boardButtons[i][j].setBorderPainted(true);
                boardButtons[i][j].setBackground(Color.LIGHT_GRAY);
                boardButtons[i][j].setEnabled(false);
            }
        }

        // Position initiale des jetons
        boardButtons[3][3].setBackground(Color.WHITE);
        boardButtons[3][4].setBackground(Color.BLACK);
        boardButtons[4][3].setBackground(Color.BLACK);
        boardButtons[4][4].setBackground(Color.WHITE);



        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Panel pour le scoreboard
        JPanel scorePanel = new JPanel(new GridLayout(2, 4));
        blackScoreLabel = new JLabel("Black: " + blackScore);
        whiteScoreLabel = new JLabel("White: " + whiteScore);
        res = new JLabel("");
        nbTourLabel = new JLabel("Tour " + nbTour);
        scorePanel.add(blackScoreLabel);
        scorePanel.add(whiteScoreLabel);
        scorePanel.add(res);
        scorePanel.add(nbTourLabel);
        mainPanel.add(scorePanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setVisible(true);
    }


    public static void main(String[] args) {
        OthelloIAvIA gui = new OthelloIAvIA();
        Jeu j = new Jeu();
        SearchNode root = new SearchNode(0,0,null,null);
        HashMap<Point,ArrayList<Point>> coups = j.getJoueurNoir().coupPossible(j.getGrille());
        System.out.println("========================\n");
        System.out.println("\tOTHELLO\n");
        System.out.println("========================\n");
        j.afficher();
        while(!j.estFini()){
            //On propose des choix en fonction des coups (coup 1,coup 2,coup 3....)
            if(!j.getTourBlanc()) {
                //appel solver pour faire jouer les blancs pour l'instant
                j.generationJeu(500,root);
                for(int i = 0; i<50; i++){
                    System.out.print("-");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("\n");

                //on récupère les coups possibles pour le joueurs pour ne pas qu'il puisse jouer autre chose (vérification)
                coups = j.getJoueurNoir().coupPossible(j.getGrille());
                if (!coups.isEmpty()) {
                    
                    Point coup = root.selection().getCoup();
                    boardButtons[coup.getX()][coup.getY()].setBackground(Color.BLACK);
                    ArrayList<Point> ModifGUI = j.modifGrilleGUI(coup);
                    for(Point p : ModifGUI){
                        boardButtons[p.getX()][p.getY()].setBackground(Color.BLACK);
                    }
                    j.modifGrille(coup);


                    //MISE A JOUR SCORE
                    int pionBlanc = j.getScore()[0];
                    gui.whiteScoreLabel.setText("White: " + Integer.toString(pionBlanc));
                    int pionNoir = j.getScore()[1];
                    gui.blackScoreLabel.setText("Black: " + Integer.toString(pionNoir));


                    //CREATION DE LA NODE A PARTIR DE NOTRE PION PLACE
                    root = root.changementNode(new Point(coup.getX(),coup.getY()));

                }
                else{
                    root = root.selection();
                    System.out.println("NOIR NE PEUT PAS JOUER !!!!!!!!");
                }
                System.out.println("\n************************************\n");
                System.out.println("\nTour noir\n");
                j.rotationTour();
                j.afficher();
            }
            else{
                //appel solver pour faire jouer les blancs pour l'instant
                j.generationJeu(500,root);
                for(int i = 0; i<50; i++){
                    System.out.print("-");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print("\n");


                coups = j.getJoueurBlanc().coupPossible(j.getGrille());
                if (!coups.isEmpty()) {

                    Point coup = root.selection().getCoup();
                    boardButtons[coup.getX()][coup.getY()].setBackground(Color.WHITE);
                    ArrayList<Point> ModifGUI = j.modifGrilleGUI(coup);
                    for(Point p : ModifGUI){
                        boardButtons[p.getX()][p.getY()].setBackground(Color.WHITE);
                    }
                    j.modifGrille(coup);


                    //MISE A JOUR SCORE
                    int pionBlanc = j.getScore()[0];
                    gui.whiteScoreLabel.setText("White: " + Integer.toString(pionBlanc));
                    int pionNoir = j.getScore()[1];
                    gui.blackScoreLabel.setText("Black: " + Integer.toString(pionNoir));

                    //CREATION DE LA NODE A PARTIR DE NOTRE PION PLACE
                    root = root.changementNode(new Point(coup.getX(),coup.getY()));


                }else{
                    root = root.selection();
                    System.out.println("BLANC NE PEUT PAS JOUER !!!!!!!!");
                }
                System.out.println("\n************************************\n");
                System.out.println("\nTour blanc\n");
                j.rotationTour();
                j.afficher();
            }
        gui.nbTour++;
        gui.nbTourLabel.setText("Tour " + gui.nbTour);

        }
        if(!j.noirGagne()) {
            System.out.println("Le Joueur Blanc remporte la partie");
            gui.res.setText("Le joueur BLANC remporte la partie");
        }
        else {
            System.out.println("Le Joueur Noir remporte la partie");
            gui.res.setText("Le joueur NOIR remporte la partie");
        }
    }
}