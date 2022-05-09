package main;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.lang.Thread;

// acts as a game screen, needs Runnable to use Thread
public class GamePanel extends JPanel implements Runnable {

    // SCREEN PARAMETER SETTINGS
    final int originalTileSize = 16; // 16x16 tiles size
    final int scale = 3; // to scale 16x16 to modern screen resolutions

    public final int tileSize = originalTileSize * scale; // 48x48 tiles size
    public final int maxScreenCol = 16; // 16 tiles for width
    public final int maxScreenRow = 12; // 12 tiles for height
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels horizontally
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels vertically

    // WORLD PARAMETER SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // Set FPS (Frames Per Second) for the game
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();  // instantiate key handler
    Sound sound = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;  // set game clock to track time, can be start or stopped

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];

    // zero-parameter constructor
    public GamePanel() {

        // set the size of the GamePanel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);   // improve game's rendering performance
        this.addKeyListener(keyH);      // so key handler can be recognized
        this.setFocusable(true);        // game panel can be focused to receive key input
    }

    public void setupGame() {

        aSetter.setObject();
        playMusic(0);       // play background music
    }

    public void startGameThread() {
        // pass this class GamePanel to the Thread constructor
        // how a Thread is instantiated
        gameThread = new Thread(this);
        gameThread.start(); // start gameThread, automatically calls run() method
    }

    @Override
    // When an object (GamePanel) implementing interface Runnable is used to create
    // a thread, starting the thread causes the object's run method to be
    // called in that separately executing thread.
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // Delta Method Game Loop
        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                // System.out.println("FPS:" + drawCount); // used for troubleshooting only
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // 1. UPDATE: update information such as character positions
    public void update() {  // upper-left corner in Java is 0,0

        player.update();
    }

    // 2. DRAW: draw the screen with the updated information
    // built-in method in java to draw on a JPanel
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2);     // draw tiles first

        // OBJECT
        for (int i = 0; i < obj.length; i++) {

            if (obj[i] != null) {   // check first if there is an object in the array
                obj[i].draw(g2, this);  // draw objects second
            }
        }

        // PLAYER
        player.draw(g2);    // draw player third
        g2.dispose(); // dispose graphics context & release system resources
    }

    public void playMusic(int i) {

        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {

        sound.stop();
    }

    public void playSE(int i) {

        sound.setFile(i);
        sound.play();
    }
}