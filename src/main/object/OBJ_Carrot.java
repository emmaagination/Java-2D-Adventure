package main.object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Carrot extends SuperObject {

    GamePanel gp;

    public OBJ_Carrot(GamePanel gp) {
        this.gp = gp;

        name = "Carrot";
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/carrot.png"));
            uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
