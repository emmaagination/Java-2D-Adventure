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
        boolean northCollision, southCollision, eastCollision, westCollision, diagonalCollision;

        // TODO: The added diagonal collision may allow you to enter a tile slightly, but it is easy to exit the tile. Adjust.

        switch(entity.direction) {
            case Constants.NORTH:
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;

                // Left north tile
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                // Right north tile
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.SOUTH:
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;

                // Left south tile
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                // Right south tile
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.WEST:
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;

                // Top west tile
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                // Bottom west tile
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.EAST:
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;

                // Top east tile
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                // Bottom east tile
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case Constants.NORTHEAST:
                int futureTopY = (entityTopWorldY - entity.speed) / gp.tileSize;
                int futureRightX = (entityRightWorldX + entity.speed) / gp.tileSize;

                // North tile
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][futureTopY];
                // East tile
                tileNum2 = gp.tileM.mapTileNum[futureRightX][entityTopRow];
                // Diagonally northeast tile
                tileNum3 = gp.tileM.mapTileNum[futureRightX][futureTopY];

                northCollision = gp.tileM.tile[tileNum1].collision;
                eastCollision = gp.tileM.tile[tileNum2].collision;
                diagonalCollision = gp.tileM.tile[tileNum3].collision;

                if (northCollision && eastCollision) {
                    entity.collisionOn = true;
                } else if (northCollision) {
                    entity.direction = Constants.EAST;
                } else if (eastCollision) {
                    entity.direction = Constants.NORTH;
                } else if (diagonalCollision) {
                    entity.collisionOn = true;
                }

                break;
            case Constants.NORTHWEST:
                int futureTopY_NW = (entityTopWorldY - entity.speed) / gp.tileSize;
                int futureLeftX = (entityLeftWorldX - entity.speed) / gp.tileSize;

                // North tile
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][futureTopY_NW];
                // West tile
                tileNum2 = gp.tileM.mapTileNum[futureLeftX][entityTopRow];
                // Diagonally northwest tile
                tileNum3 = gp.tileM.mapTileNum[futureLeftX][futureTopY_NW];

                northCollision = gp.tileM.tile[tileNum1].collision;
                westCollision = gp.tileM.tile[tileNum2].collision;
                diagonalCollision = gp.tileM.tile[tileNum3].collision;

                if (northCollision && westCollision) {
                    entity.collisionOn = true;
                } else if (northCollision) {
                    entity.direction = Constants.WEST;
                } else if (westCollision) {
                    entity.direction = Constants.NORTH;
                } else if (diagonalCollision) {
                    entity.collisionOn = true;
                }

                break;
            case Constants.SOUTHEAST:
                int futureBottomY_SE = (entityBottomWorldY + entity.speed) / gp.tileSize;
                int futureRightX_SE = (entityRightWorldX + entity.speed) / gp.tileSize;

                // South tile
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][futureBottomY_SE];
                // East tile
                tileNum2 = gp.tileM.mapTileNum[futureRightX_SE][entityBottomRow];
                // Diagonally southeast tile
                tileNum3 = gp.tileM.mapTileNum[futureRightX_SE][futureBottomY_SE];

                southCollision = gp.tileM.tile[tileNum1].collision;
                eastCollision = gp.tileM.tile[tileNum2].collision;
                diagonalCollision = gp.tileM.tile[tileNum3].collision;

                if (southCollision && eastCollision) {
                    entity.collisionOn = true;
                } else if (southCollision) {
                    entity.direction = Constants.EAST;
                } else if (eastCollision) {
                    entity.direction = Constants.SOUTH;
                } else if (diagonalCollision) {
                    entity.collisionOn = true;
                }

                break;
            case Constants.SOUTHWEST:
                int futureBottomY_SW = (entityBottomWorldY + entity.speed) / gp.tileSize;
                int futureLeftX_SW = (entityLeftWorldX - entity.speed) / gp.tileSize;

                // South tile
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][futureBottomY_SW];
                // West tile
                tileNum2 = gp.tileM.mapTileNum[futureLeftX_SW][entityBottomRow];
                // Diagonally southwest tile
                tileNum3 = gp.tileM.mapTileNum[futureLeftX_SW][futureBottomY_SW];

                southCollision = gp.tileM.tile[tileNum1].collision;
                westCollision = gp.tileM.tile[tileNum2].collision;
                diagonalCollision = gp.tileM.tile[tileNum3].collision;

                if (southCollision && westCollision) {
                    entity.collisionOn = true;
                } else if (southCollision) {
                    entity.direction = Constants.WEST;
                } else if (westCollision) {
                    entity.direction = Constants.SOUTH;
                } else if (diagonalCollision) {
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
