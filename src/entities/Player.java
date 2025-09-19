package entities;

import main.KeyHandler;
import main.SoundController;
import util.Scaler;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.enums.EntityDirections;
import main.GamePanel;

public class Player extends Entity {
    public GamePanel gp;
    public KeyHandler keyH;

    public int screenX;
    public int screenY;

    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = (gp.screenWidth / 2) - (gp.TILE_SIZE / 2);
        screenY = (gp.screenHeight / 2) - (gp.TILE_SIZE / 2);

        solidArea = new Rectangle(8, 16, 20, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.TILE_SIZE * 23;
        worldY = gp.TILE_SIZE * 21;
        speed = 4;
        direction = EntityDirections.DOWN;
    }

    public void setScreenSettings(int width, int height) {
        screenX = (width / 2) - (gp.TILE_SIZE / 2);
        screenY = (height / 2) - (gp.TILE_SIZE / 2);
    }

    public void getPlayerImage() {
        try {
            down1 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady0.png")), gp.TILE_SIZE, gp.TILE_SIZE);
            down2 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady1.png")), gp.TILE_SIZE, gp.TILE_SIZE);
            up1 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady2.png")), gp.TILE_SIZE, gp.TILE_SIZE);
            up2 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady3.png")), gp.TILE_SIZE, gp.TILE_SIZE);
            right1 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady4.png")), gp.TILE_SIZE, gp.TILE_SIZE);
            right2 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady5.png")), gp.TILE_SIZE, gp.TILE_SIZE);
            left1 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady6.png")), gp.TILE_SIZE, gp.TILE_SIZE);
            left2 = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/player/sprite_mushroom_lady7.png")), gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        moving = false;

        if(keyH.downPressed || keyH.upPressed || keyH.rightPressed || keyH.leftPressed) {
            if(keyH.upPressed){
                direction = EntityDirections.UP;
            } 
            else if(keyH.downPressed) {
                direction = EntityDirections.DOWN;
            }
            else if(keyH.leftPressed) {
                direction = EntityDirections.LEFT;
            }
            else if(keyH.rightPressed) {
                direction = EntityDirections.RIGHT;
            }

            moving = true;
        } else {
            spriteBuffer++;

            if(spriteBuffer >= 20) {
                spriteIndex = 0;
                spriteBuffer = 0;
            }
        }

        collisionOn = false;
        gp.collController.checkTileCollision(this);
        int objIndex = gp.collController.checkObjectCollision(this, this instanceof Player);
        pickUpObject(objIndex);

        if(!collisionOn && moving) {
            switch(direction) {
                case EntityDirections.UP:
                    worldY = Math.max(0, worldY - speed);
                    break;
                case EntityDirections.DOWN:
                    worldY = Math.min(worldY + speed, gp.WORLD_HEIGHT);
                    break;
                case EntityDirections.LEFT:
                    worldX = Math.max(worldX - speed, 0);
                    break;
                case EntityDirections.RIGHT:
                    worldX = Math.min(worldX + speed, gp.WORLD_WIDTH);
                    break;
                default:
                    break;
            }

            spriteCounter++;
        }
        if(spriteCounter > 10) {
            spriteIndex = spriteIndex == 0 ? 1 : 0;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.TILE_SIZE, gp.TILE_SIZE);

        BufferedImage image = null;

        switch(direction) {
            case EntityDirections.UP: image = spriteIndex == 0 ? up1 : up2; break;
            case EntityDirections.DOWN: image = spriteIndex == 0 ? down1 : down2; break;
            case EntityDirections.LEFT: image = spriteIndex == 0 ? left1 : left2; break;
            case EntityDirections.RIGHT: image = spriteIndex == 0 ? right1 : right2; break;
        }

        g2.drawImage(image, screenX, screenY, null);
    }

    public void pickUpObject(int i) {
        if(i >= 0) {
            String objectName = gp.objects[i].name;

            switch(objectName) {
                case "key":
                    gp.playSoundEffect(SoundController.COIN);
                    gp.objects[i] = null;
                    hasKey++;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "door":
                    if(hasKey > 0) {
                        gp.playSoundEffect(SoundController.UNLOCK);
                        hasKey--;
                        gp.objects[i] = null;
                        gp.ui.showMessage("Door unlocked!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "boots":
                    gp.playSoundEffect(SoundController.POWERUP);
                    speed += 2;
                    gp.objects[i] = null;
                    gp.ui.showMessage("Speed increased!");
                    break;
                case "chest":
                    gp.gameFinished = true;
                    gp.stopMusic();
                    gp.playSoundEffect(SoundController.FANFARE);
                    break;
            }
        }
    }
}
