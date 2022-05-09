package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// The listener interface for receiving keyboard events (keystrokes)
public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent event) {
        // Not used in this game.
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();  // returns integer associated with the key pressed in this event

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {    // if W key was pressed
            upPressed = true;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {    // if A key was pressed
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {    // if S key was pressed
            downPressed = true;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {    // if D key was pressed
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();  // returns integer associated with the key released in this event

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {    // if W key was released
            upPressed = false;
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {    // if A key was released
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {    // if S key was released
            downPressed = false;
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {    // if D key was released
            rightPressed = false;
        }
    }
}