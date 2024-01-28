package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        // Row 0, 1, 2
        // Column 0, 1, 2 etc
        int tileNum1, tileNum2, tileNum3;

        switch(entity.direction) {
            case Constants.NORTH:
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.SOUTH:
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.WEST:
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.EAST:
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.NORTHEAST:
                // Future positions
                int futureTopY = (entityTopWorldY - entity.speed) / gp.tileSize;
                int futureRightX = (entityRightWorldX + entity.speed) / gp.tileSize;

                // Directly North tile
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][futureTopY];
                // Directly East tile
                tileNum2 = gp.tileM.mapTileNum[futureRightX][entityTopRow];
                // Diagonally Northeast tile
                tileNum3 = gp.tileM.mapTileNum[futureRightX][futureTopY];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.NORTHWEST:
                // Future positions
                int futureTopY_NW = (entityTopWorldY - entity.speed) / gp.tileSize;
                int futureLeftX = (entityLeftWorldX - entity.speed) / gp.tileSize;

                // Directly North tile
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][futureTopY_NW];
                // Directly West tile
                tileNum2 = gp.tileM.mapTileNum[futureLeftX][entityTopRow];
                // Diagonally Northwest tile
                tileNum3 = gp.tileM.mapTileNum[futureLeftX][futureTopY_NW];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.SOUTHEAST:
                // Future positions
                int futureBottomY_SE = (entityBottomWorldY + entity.speed) / gp.tileSize;
                int futureRightX_SE = (entityRightWorldX + entity.speed) / gp.tileSize;

                // Directly South tile
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][futureBottomY_SE];
                // Directly East tile
                tileNum2 = gp.tileM.mapTileNum[futureRightX_SE][entityBottomRow];
                // Diagonally Southeast tile
                tileNum3 = gp.tileM.mapTileNum[futureRightX_SE][futureBottomY_SE];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;

            case Constants.SOUTHWEST:
                // Future positions
                int futureBottomY_SW = (entityBottomWorldY + entity.speed) / gp.tileSize;
                int futureLeftX_SW = (entityLeftWorldX - entity.speed) / gp.tileSize;

                // Directly South tile
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][futureBottomY_SW];
                // Directly West tile
                tileNum2 = gp.tileM.mapTileNum[futureLeftX_SW][entityBottomRow];
                // Diagonally Southwest tile
                tileNum3 = gp.tileM.mapTileNum[futureLeftX_SW][futureBottomY_SW];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;

        }
    }
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get the object's solid
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                // Predicting where player will be
                switch(entity.direction) {
                    // TODO: Add cases for up-right, etc.
                    // Ensure that you check for updated X and Y at the same time.
                    // Ensure that the math works the same as the movement speed updates.
                    case Constants.NORTH:
                        entity.solidArea.y -= entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                    case Constants.SOUTH:
                        entity.solidArea.y += entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                    case Constants.WEST:
                        entity.solidArea.x -= entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                    case Constants.EAST:
                        entity.solidArea.x += entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                    case Constants.NORTHEAST:
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y -= entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                    case Constants.NORTHWEST:
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y -= entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                    case Constants.SOUTHEAST:
                        entity.solidArea.x += entity.speed;
                        entity.solidArea.y += entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                    case Constants.SOUTHWEST:
                        entity.solidArea.x -= entity.speed;
                        entity.solidArea.y += entity.speed;
                        index = getIndex(entity, player, i, index);
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;

    }

    private int getIndex(Entity entity, boolean player, int i, int index) {
        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
            if (gp.obj[i].collision) {
                entity.collisionOn = true;
            }
            if (player) {
                index = i;
            }
        }
        return index;
    }




}
