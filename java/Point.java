
import java.util.ArrayList;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        // Renvoie la coordonnée x
        return x;
    }

    public int getY(){
        // Renvoie la coordonnée y
        return y;
    }

    public boolean in(ArrayList<Point> l){
        // Renvoie vrai si le point est dans la liste de points
        for(Point p : l){
            if(p.getX() == x && p.getY() == y){
                return true;
            }
        }
        return false;
    }

    public String toString(){
        // Affichage toString d'un point
        return "x: " + getX() + ", y: " + getY();
    }

    public boolean equals(Point p){
        return p.getX() == x && p.getY() == y;
    }
}