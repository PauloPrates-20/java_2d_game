package main;

import entities.Entity;
import entities.enums.EntityDirections;

public class CollisionController {
    private GamePanel gp;

    public CollisionController(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTileCollision(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY= entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.TILE_SIZE;
        int entityRightCol = entityRightWorldX / gp.TILE_SIZE;
        int entityTopRow = entityTopWorldY / gp.TILE_SIZE;
        int entityBottomRow = entityBottomWorldY / gp.TILE_SIZE;

        int tileNum1, tileNum2;

        switch(entity.direction) {
            case EntityDirections.UP:
                entityTopRow = (entityTopWorldY - entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];

                if(gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case EntityDirections.DOWN: 
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

                if(gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case EntityDirections.LEFT: 
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityLeftCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityLeftCol];

                if(gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case EntityDirections.RIGHT: 
                entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTileNum[entityTopRow][entityRightCol];
                tileNum2 = gp.tileM.mapTileNum[entityBottomRow][entityRightCol];

                if(gp.tileM.tiles[tileNum1].collision || gp.tileM.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int checkObjectCollision(Entity entity, boolean player) {
        int index = -1;

        for(int i = 0; i < gp.objects.length; i++) {
            if(gp.objects[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                // Get the object's solid area position
                gp.objects[i].solidArea.x += gp.objects[i].worldX;
                gp.objects[i].solidArea.y += gp.objects[i].worldY;

                switch(entity.direction) {
                    case EntityDirections.UP:
                        entity.solidArea.y -= entity.speed;

                        if(entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            entity.collisionOn = gp.objects[i].collision;

                            if(player) {
                                index = i;
                            }
                        }
                        break;
                    case EntityDirections.DOWN:
                        entity.solidArea.y += entity.speed;

                        if(entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            entity.collisionOn = gp.objects[i].collision;

                            if(player) {
                                index = i;
                            }
                        }
                        break;
                    case EntityDirections.LEFT:
                        entity.solidArea.x -= entity.speed;

                        if(entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            entity.collisionOn = gp.objects[i].collision;

                            if(player) {
                                index = i;
                            }
                        }
                        break;
                    case EntityDirections.RIGHT:
                        entity.solidArea.x += entity.speed;

                        if(entity.solidArea.intersects(gp.objects[i].solidArea)) {
                            entity.collisionOn = gp.objects[i].collision;

                            if(player) {
                                index = i;
                            }
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.objects[i].solidArea.x = gp.objects[i].solidAreaDefaultX;
                gp.objects[i].solidArea.y = gp.objects[i].solidAreaDefaultY;
            }
        }

        return index;
    }
}
