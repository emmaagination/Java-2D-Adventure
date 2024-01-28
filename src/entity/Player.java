package entity;

import main.Constants;
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
        direction = Constants.SOUTH;
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
                direction = Constants.NORTHEAST;
            } else if (keyH.upPressed && keyH.leftPressed) {
                direction = Constants.NORTHWEST;
            } else if (keyH.downPressed && keyH.rightPressed) {
                direction = Constants.SOUTHEAST;
            } else if (keyH.downPressed && keyH.leftPressed) {
                direction = Constants.SOUTHWEST;
            } else if (keyH.upPressed) {
                direction = Constants.NORTH;
            } else if (keyH.downPressed) {
                direction = Constants.SOUTH;
            } else if (keyH.leftPressed) {
                direction = Constants.WEST;
            } else if (keyH.rightPressed) {
                direction = Constants.EAST;
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
                    case Constants.NORTHEAST:
                        worldX = (int) Math.round(worldX + horizontal_speed);
                        worldY = (int) Math.round(worldY - horizontal_speed);
                        break;
                    case Constants.NORTHWEST:
                        worldX = (int) Math.round(worldX - horizontal_speed);
                        worldY = (int) Math.round(worldY - horizontal_speed);
                        break;
                    case Constants.SOUTHEAST:
                        worldX = (int) Math.round(worldX + horizontal_speed);
                        worldY = (int) Math.round(worldY + horizontal_speed);
                        break;
                    case Constants.SOUTHWEST:
                        worldX = (int) Math.round(worldX - horizontal_speed);
                        worldY = (int) Math.round(worldY + horizontal_speed);
                        break;
                    case Constants.NORTH:
                        worldY -= speed;
                        break;
                    case Constants.SOUTH:
                        worldY += speed;
                        break;
                    case Constants.WEST:
                        worldX -= speed;
                        break;
                    case Constants.EAST:
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

            // use later

        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case Constants.NORTH:
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case Constants.SOUTH:
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case Constants.WEST:
            case Constants.SOUTHWEST:
            case Constants.NORTHWEST:
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case Constants.EAST:
            case Constants.SOUTHEAST:
            case Constants.NORTHEAST:
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
