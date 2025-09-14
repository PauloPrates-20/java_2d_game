package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import util.Scaler;

public class BaseObject {
    public GamePanel gp;
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = solidArea.x;
    public int solidAreaDefaultY = solidArea.y;

    public BaseObject(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X &&
            worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X &&
            worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y &&
            worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y
        ) {
            g2.drawImage(image, screenX, screenY, null);
        }
    }

    public void loadImage(String path) {
        try {
            image = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream(path)), gp.TILE_SIZE, gp.TILE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
