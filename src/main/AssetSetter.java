package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    // One-Parameter Constructor
    public AssetSetter(GamePanel gp) {

        this.gp = gp;

    }

    // instantiate some objects & place them in the map
    public void setObject() {

        // instantiate 1st key object
        gp.obj[0] = new OBJ_Key();              // subclass of SuperObject so can instantiate
        gp.obj[0].worldX = 23 * gp.tileSize;    // set x-position of key in world map
        gp.obj[0].worldY = 7 * gp.tileSize;     // set y-position of key in world map

        // instantiate 2nd key object
        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = 23 * gp.tileSize;
        gp.obj[1].worldY = 40 * gp.tileSize;

        // instantiate 3rd key object
        gp.obj[2] = new OBJ_Key();
        gp.obj[2].worldX = 37 * gp.tileSize;
        gp.obj[2].worldY = 9 * gp.tileSize;

        // instantiate 1st Door object
        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 10 * gp.tileSize;
        gp.obj[3].worldY = 11 * gp.tileSize;

        // instantiate 2nd Door object
        gp.obj[4] = new OBJ_Door();
        gp.obj[4].worldX = 8 * gp.tileSize;
        gp.obj[4].worldY = 28 * gp.tileSize;

        // instantiate 3rd Door object
        gp.obj[5] = new OBJ_Door();
        gp.obj[5].worldX = 12 * gp.tileSize;
        gp.obj[5].worldY = 22 * gp.tileSize;

        // instantiate Chest object
        gp.obj[6] = new OBJ_Chest();
        gp.obj[6].worldX = 10 * gp.tileSize;
        gp.obj[6].worldY = 8 * gp.tileSize;

        // instantiate Boots object
        gp.obj[7] = new OBJ_Boots();
        gp.obj[7].worldX = 36 * gp.tileSize;
        gp.obj[7].worldY = 40 * gp.tileSize;
    }
}