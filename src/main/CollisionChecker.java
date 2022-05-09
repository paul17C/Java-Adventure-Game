package main;
import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        // get four corners of the collision area in the character
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;  // top-left of collision area
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;    // top-right of collision area
        int entityTopWorldY = entity.worldY + entity.solidArea.y;   // top of collision area
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;  // bottom of collision area

        // based on four points above, get the row and col coordinates
        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {

            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    // Check if the player is hitting an object, and if it is true
    // return the index of the object.
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        // scan each object in obj array from gp
        for (int i = 0; i < gp.obj.length; i++) {

            // check first if obj in array is not null
            if (gp.obj[i] != null) {

                // Get entity's (character's) solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // Check entity's movement and where it will be after it moved
                switch (entity.direction) {

                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // Check if both rectangle areas touch or intersect
                            if (gp.obj[i].collision == true) {  // Check first if object is solid or not
                                entity.collisionOn = true;
                            }
                            if (player == true) {   // check if player, can pick up objects
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // Check if both rectangle areas touch or intersect
                            if (gp.obj[i].collision == true) {  // Check first if object is solid or not
                                entity.collisionOn = true;
                            }
                            if (player == true) {   // check if player, can pick up objects
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // Check if both rectangle areas touch or intersect
                            if (gp.obj[i].collision == true) {  // Check first if object is solid or not
                                entity.collisionOn = true;
                            }
                            if (player == true) {   // check if player, can pick up objects
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) { // Check if both rectangle areas touch or intersect
                            if (gp.obj[i].collision == true) {  // Check first if object is solid or not
                                entity.collisionOn = true;
                            }
                            if (player == true) {   // check if player, can pick up objects
                                index = i;
                            }
                        }
                        break;
                }
                entity.solidArea.x = entity.solidAreaDefaultX;  // reset position at every loop
                entity.solidArea.y = entity.solidAreaDefaultY;  // reset position at every loop

                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;    // reset position at every loop
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;    // reset position at every loop
            }

        }
        return index;
    }
}