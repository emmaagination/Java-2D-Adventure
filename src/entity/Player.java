package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(9, 20, 26, 26); // COLLISION BOX
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 25;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        up1 = setup("player_up_1");
        up2 = setup("player_up_2");
        down1 = setup("player_down_1");
        down2 = setup("player_down_2");
        left1 = setup("player_left_1");
        left2 = setup("player_left_2");
        right1 = setup("player_right_1");
        right2 = setup("player_right_2");

    }

    public BufferedImage setup(String imageName) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // In Java, the upper left corner is (0,0)
            // X values increase to the right
            // Y values increase as they go down

            if (keyH.upPressed && keyH.rightPressed) {
                direction = "up-right";
            } else if (keyH.upPressed && keyH.leftPressed) {
                direction = "up-left";
            } else if (keyH.downPressed && keyH.rightPressed) {
                direction = "down-right";
            } else if (keyH.downPressed && keyH.leftPressed) {
                direction = "down-left";
            } else if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            double horizontal_speed = speed / 1.414;
            if (!collisionOn) {
                switch (direction) {
                    case "up-right":
                        worldY -= horizontal_speed;
                        worldX += horizontal_speed;
                        break;
                    case "up-left":
                        worldY -= horizontal_speed;
                        worldX -= horizontal_speed;
                        break;
                    case "down-right":
                        worldX += horizontal_speed;
                        worldY += horizontal_speed;
                        break;
                    case "down-left":
                        worldX -= horizontal_speed;
                        worldY += horizontal_speed;
                        break;
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {



        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
            case "up-right":
            case "up-left":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
            case "down-right":
            case "down-left":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }
}
