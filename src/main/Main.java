package main;
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame("Blue Boy Adventure");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel(); // create game panel
        window.add(gamePanel);  // add game panel to window

        // causes window to be sized to fit the preferred size
        // and layouts of its subcomponents (GamePanel)
        window.pack();

        window.setLocationRelativeTo(null); // window will be displayed at the center of the screen
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
