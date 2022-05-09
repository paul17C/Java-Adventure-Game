package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;   // doesn't change throughout game, player always in the middle of the screen
    public final int screenY;   // doesn't change throughout game, player always in the middle of the screen

    int hasKey = 0; // indicates how many keys the player currently has

    // 2-Parameter Constructor
    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize/2);   // player's x position in camera or screen
        screenY = gp.screenHeight / 2 - (gp.tileSize/2);  // player's y position in camera or screen

        solidArea = new Rectangle();    // collision area for Character
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
    }

    // Set Player Default Values
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;   // player's starting x position in world map
        worldY = gp.tileSize * 21;   // player's starting y position in world map
        speed = 4; // 4 pixels
        direction = "down";
    }

    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getResource("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResource("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResource("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResource("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResource("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResource("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResource("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResource("/player/boy_right_2.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        // spriteCounter only increments if any of these 4 keys are pressed
        if (keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true) {

            if (keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (collisionOn == false) {

                switch (direction) {
                    case "up":
                        worldY -= speed;     // y values decrease as objects go up
                        break;
                    case "down":
                        worldY += speed;     // y values increase as objects go down
                        break;
                    case "left":
                        worldX -= speed;     // x values decrease as objects go left
                        break;
                    case "right":
                        worldX += speed;     // x values increase as objects go right
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {   // sets the alternating image speed
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else  if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;  // reset spriteCounter
            }
        }
    }

    // What happens when player touches an object
    public void pickUpObject(int i) {

        // if index is 999, means we did not touch an object
        // if index is NOT 999, means we touched an object
        if (i != 999) {

            String objectName = gp.obj[i].name; // get object name

            switch (objectName) {

                case "Key":
                    gp.playSE(1);    // play sound effect
                    hasKey++;           // increment key counter
                    gp.obj[i] = null;   // deletes the object the player touched from the object array (disappear)
                    System.out.println("Key: " + hasKey);   // display current number of keys
                    break;
                case "Door":
                    if (hasKey > 0) {       // check first if player has key
                        gp.playSE(3);    // play sound effect
                        gp.obj[i] = null;   // Door will disappear
                        hasKey--;           // decrement key counter
                        System.out.println("Key: " + hasKey);   // display current number of keys
                    }
                    break;
                case "Boots":
                    gp.playSE(2);     // play sound effect
                    speed += 2;         // increase walk speed
                    gp.obj[i] = null;   // Boots will disappear
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {

        // used for testing before
        // g2.setColor(Color.WHITE);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up" :
                if (spriteNum == 1) {
                    image = up1;
                }
                else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down" :
                if (spriteNum == 1) {
                    image = down1;
                }
                else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left" :
                if (spriteNum == 1) {
                    image = left1;
                }
                else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right" :
                if (spriteNum == 1) {
                    image = right1;
                }
                else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        // draw the image on the screen, based on the switch image.
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}