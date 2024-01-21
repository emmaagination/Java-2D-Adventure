package main;

import main.object.OBJ_Key;
import main.object.OBJ_Door;
import main.object.OBJ_Chest;
import main.object.OBJ_Carrot;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = 17 * gp.tileSize;
        gp.obj[0].worldY = 39 * gp.tileSize;

        gp.obj[1] = new OBJ_Door(gp);
        gp.obj[1].worldX = 12 * gp.tileSize;
        gp.obj[1].worldY = 16 * gp.tileSize;

        gp.obj[2] = new OBJ_Door(gp);
        gp.obj[2].worldX = 33 * gp.tileSize;
        gp.obj[2].worldY = 34 * gp.tileSize;

        gp.obj[3] = new OBJ_Chest(gp);
        gp.obj[3].worldX = 12 * gp.tileSize;
        gp.obj[3].worldY = 13 * gp.tileSize;

        gp.obj[4] = new OBJ_Carrot(gp);
        gp.obj[4].worldX = 30 * gp.tileSize;
        gp.obj[4].worldY = 16 * gp.tileSize;

        gp.obj[5] = new OBJ_Key(gp);
        gp.obj[5].worldX = 21 * gp.tileSize;
        gp.obj[5].worldY = 9 * gp.tileSize;

    }

}
