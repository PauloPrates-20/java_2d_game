package entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entities.enums.EntityDirections;

public class Entity {
    // MOVEMENT CONTROL
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public EntityDirections direction;
    public boolean moving = false;

    // SPRITE CONTROL
    public int spriteCounter = 0;
    public int spriteIndex = 0;
    public int spriteBuffer = 0;

    // COLLISION DETECTION
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public int pixelCounter = 0;
}
