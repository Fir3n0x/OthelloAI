
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

public class OthelloPvP extends JFrame {
    // Othello Joueur contre Joueur
    private static JButton[][] boardButtons;
    private JLabel blackScoreLabel;
    private JLabel whiteScoreLabel;
    private JLabel res;
    private JLabel nbTourLabel;
    private int nbTour;
    private int blackScore = 2; // Scores initiaux
    private int whiteScore = 2;
    private static boolean buttonClicked;
    private static Point clique; //Bouton cliqué

    public OthelloPvP() {
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
                button.addActionListener(new ButtonClickListener(row, col)); // Ajout du listener
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

    // ActionListener pour gérer les clics sur les boutons du plateau
    public static class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            // Changer la couleur de la case
            if (button.getBackground() == Color.LIGHT_GRAY) {
                button.setBackground(Color.BLACK);
            }
            clique = new Point(row,col);
            buttonClicked = true;
        }
    }

    public static void main(String[] args) {
        OthelloPvP gui = new OthelloPvP();
        Jeu j = new Jeu();
        HashMap<Point,ArrayList<Point>> coups = j.getJoueurNoir().coupPossible(j.getGrille());
        System.out.println("========================\n");
        System.out.println("\tOTHELLO\n");
        System.out.println("========================\n");
        j.afficher();
        while(!j.estFini()){
            //On propose des choix en fonction des coups (coup 1,coup 2,coup 3....)
            if(!j.getTourBlanc()) {
                //on récupère les coups possibles pour le joueurs pour ne pas qu'il puisse jouer autre chose (vérification)
                coups = j.getJoueurNoir().coupPossible(j.getGrille());
                if (!coups.isEmpty()) {
                    for (Point pint : coups.keySet()) {
                        for (Point pint2 : coups.get(pint)) {
                            boardButtons[pint2.getX()][pint2.getY()].setBackground(Color.CYAN);
                            boardButtons[pint2.getX()][pint2.getY()].setEnabled(true);
                        }
                    }
                    gui.res.setText("C'est le tour du joueur Noir");
                    //ATTENTE DE L'ACTION DU JOUEUR NOIR (NOUS)
                    while (!buttonClicked) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } // Attendre un court instant
                    }
                    buttonClicked = false;

                    //ENLEVER LES PIECE JAUNES DES COUPS POSSIBLES
                    for (Point pint : coups.keySet()) {
                        for (Point pint2 : coups.get(pint)) {
                            boardButtons[pint2.getX()][pint2.getY()].setBackground(Color.LIGHT_GRAY);
                            boardButtons[pint2.getX()][pint2.getY()].setEnabled(false);
                        }
                    }

                    //MODIF LES PIONS QUI SONT ENTOURES SUR LA GUI
                    int X = clique.getX();
                    int Y = clique.getY();
                    boardButtons[X][Y].setBackground(Color.BLACK);
                    ArrayList<Point> ModifGUI = j.modifGrilleGUI(new Point(X, Y));
                    for (Point p : ModifGUI) {
                        boardButtons[p.getX()][p.getY()].setBackground(Color.BLACK);
                    }
                    j.modifGrille(new Point(X, Y));

                }
                else{
                    gui.res.setText("C'est le tour du joueur Blanc");
                }
                System.out.println("\n************************************\n");
                System.out.println("\nTour noir\n");
            }
            else{
                coups = j.getJoueurBlanc().coupPossible(j.getGrille());
                if (!coups.isEmpty()) {
                    for (Point pint : coups.keySet()) {
                        for (Point pint2 : coups.get(pint)) {
                            boardButtons[pint2.getX()][pint2.getY()].setBackground(Color.CYAN);
                            boardButtons[pint2.getX()][pint2.getY()].setEnabled(true);
                        }
                    }
                    gui.res.setText("C'est le tour du joueur Blanc");
                    //ATTENTE DE L'ACTION DU JOUEUR NOIR (NOUS)
                    while (!buttonClicked){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } // Attendre un court instant
                    }
                    buttonClicked = false;

                    //ENLEVER LES PIECE JAUNES DES COUPS POSSIBLES
                    for (Point pint : coups.keySet()) {
                        for (Point pint2 : coups.get(pint)) {
                            boardButtons[pint2.getX()][pint2.getY()].setBackground(Color.LIGHT_GRAY);
                            boardButtons[pint2.getX()][pint2.getY()].setEnabled(false);
                        }
                    }

                    //MODIF LES PIONS QUI SONT ENTOURES SUR LA GUI
                    int X = clique.getX();
                    int Y = clique.getY();
                    boardButtons[X][Y].setBackground(Color.WHITE);
                    ArrayList<Point> ModifGUI = j.modifGrilleGUI(new Point(X, Y));
                    for (Point p : ModifGUI) {
                        boardButtons[p.getX()][p.getY()].setBackground(Color.WHITE);
                    }
                    j.modifGrille(new Point(X, Y));

                }
                else{
                    gui.res.setText("C'est le tour du joueur Noir");
                }
                System.out.println("\n************************************\n");
                System.out.println("\nTour Blanc\n");

            }
            int pionBlanc = j.getScore()[0];
            gui.whiteScoreLabel.setText("White: " + Integer.toString(pionBlanc));
            int pionNoir = j.getScore()[1];
            gui.blackScoreLabel.setText("Black: " + Integer.toString(pionNoir));
            gui.nbTour++;
            gui.nbTourLabel.setText("Tour " + gui.nbTour);
            j.rotationTour();
            j.afficher();

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