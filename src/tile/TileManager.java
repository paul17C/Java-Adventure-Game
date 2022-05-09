// This class handles and manages the tile objects

package tile;
import main.GamePanel;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][]; // map coordinates

    // One-parameter constructor
    public TileManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tile[10]; // set size of 10 kinds of different tiles
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // set size of 2D array map

        getTileImage();
        loadMap("/maps/World01.txt");
    }

    // Load tile images from resource folder to tile array
    public void getTileImage() {

        // assign tile images to tile array elements
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // load values from map txt file into 2D map array
    public void loadMap(String filePath) {

        try {
            // import text from map text file.
            InputStream is = getClass().getResourceAsStream(filePath);
            // read the text contents from the input stream to br.
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;    // initial value & place-holder for col
            int row = 0;    // initial value & place-holder for row

            // loop until whole map is covered
            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();    // read a line of text from br & assign to line

                // split and assign each number in the line to each element on the String
                // number[0] = 1, number[1] = 0, ....number[15] = 1
                while (col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");   // split the space from the line & store the numbers to the array
                    int num = Integer.parseInt(numbers[col]);   // change from String to int
                    mapTileNum[col][row] = num;     // store the extracted number in the mapTileNum[][]
                    col++;
                }
                // check if max col is reached
                if (col == gp.maxWorldCol) {
                    col = 0;    // reset col
                    row++;      // increment row by 1 (next row)
                }
            }
            br.close(); // close br resource
        }
        catch (Exception e) {

        }
    }

    // draw the tile images efficiently
    // Automating tile drawing process
    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        // loop through entire map
        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow]; // what type of tile image to draw based on text map

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;    // x-position in the camera or screen
            int screenY = worldY - gp.player.worldY + gp.player.screenY;    // y-position in the camera or screen

            // to make code more efficient, draw only tiles within the screen area vs entire world map
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                // draw the actual tile
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            worldCol++;     // increment worldCol

            // when worldCol reaches max screen worldCol
            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;    // reset worldCol
                worldRow++;      // increment worldRow
            }
        }
    }
}