package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("maps/map03.txt");
    }

    public void getTileImage() {

        System.out.println("Hello from git!");

        setup(0, "grad1", false);
        setup(1, "grass_1", false);
        setup(2, "grass_1", false);
        setup(3, "grass_1", false);
        setup(4, "grass_1", false);
        setup(5, "grass_1", false);
        setup(6, "grass_1", false);
        setup(7, "grass_1", false);
        setup(9, "grass_1", false);

        // Grass tiles
        setup(10, "grass_1", false); // Without grass sprouts
        setup(11, "grass_2", false); // With grass sprouts

        // Water tiles
        setup(12, "water_1", true); // Water
        setup(13, "water_2", true); // Water with ripples
        setup(14, "water_up_l", false);
        setup(15, "water_up", false);
        setup(16, "water_up_r", false);
        setup(17, "water_left", false);
        setup(18, "water_right", false);
        setup(19, "water_down_l", false);
        setup(20, "water_down", false);
        setup(21, "water_down_r", false);
        setup(22, "water_bottom_l", false); // Corner
        setup(23, "water_bottom_r", false); // Corner
        setup(24, "water_top_l", false); // Corner
        setup(25, "water_top_r", false); // Corner

        // Environment tiles
        setup(26, "small_tree", true);  // Tree

        // House tiles
        setup(27, "wood_wall", true);  // Wood wall
        setup(28, "brick_floor", false); // Brick floor

        // Path tiles
        setup(29, "dirt_tile", false); // Dirt (replace with path)
        // add path tiles

    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/" + imageName + ".png")); // Grass
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadMap(String mapPath) {

        try {

            InputStream is = getClass().getClassLoader().getResourceAsStream(mapPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }

}
