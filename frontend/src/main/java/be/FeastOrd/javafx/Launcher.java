package be.FeastOrd.javafx; // Vérifie que c'est le même package que App.java

public class Launcher {
    
    // Cette méthode main est "bête", elle ne fait qu'appeler l'autre.
    // Mais ça suffit pour tromper Java et faire marcher le programme !
    public static void main(String[] args) {
        App.main(args);
    }
}