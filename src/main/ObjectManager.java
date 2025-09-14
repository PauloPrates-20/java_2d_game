package main;

import object.ObjBoots;
import object.ObjChest;
import object.ObjDoor;
import object.ObjKey;

public class ObjectManager {
    public GamePanel gp;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
    }

    public void spawnObject() {
        gp.objects[0] = new ObjKey(gp);
        gp.objects[0].worldX = 23 * gp.TILE_SIZE;
        gp.objects[0].worldY = 7 * gp.TILE_SIZE;

        gp.objects[1] = new ObjKey(gp);
        gp.objects[1].worldX = 23 * gp.TILE_SIZE;
        gp.objects[1].worldY = 40 * gp.TILE_SIZE;

        gp.objects[2] = new ObjKey(gp);
        gp.objects[2].worldX = 37 * gp.TILE_SIZE;
        gp.objects[2].worldY = 7 * gp.TILE_SIZE;

        gp.objects[3] = new ObjDoor(gp);
        gp.objects[3].worldX = 10 * gp.TILE_SIZE;
        gp.objects[3].worldY = 11 * gp.TILE_SIZE;

        gp.objects[4] = new ObjDoor(gp);
        gp.objects[4].worldX = 8 * gp.TILE_SIZE;
        gp.objects[4].worldY = 28 * gp.TILE_SIZE;

        gp.objects[5] = new ObjDoor(gp);
        gp.objects[5].worldX = 12 * gp.TILE_SIZE;
        gp.objects[5].worldY = 22 * gp.TILE_SIZE;

        gp.objects[6] = new ObjChest(gp);
        gp.objects[6].worldX = 10 * gp.TILE_SIZE;
        gp.objects[6].worldY = 7 * gp.TILE_SIZE;

        gp.objects[7] = new ObjBoots(gp);
        gp.objects[7].worldX = 37 * gp.TILE_SIZE;
        gp.objects[7].worldY = 42 * gp.TILE_SIZE;
    }
}
