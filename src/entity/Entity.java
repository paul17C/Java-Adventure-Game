// This is the super class that will store variables that
// will be used in every player, monster and NPC classes.
package entity;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Configure collision area
    public Rectangle solidArea;         // collision area in character
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false; // collision switch on or off
}