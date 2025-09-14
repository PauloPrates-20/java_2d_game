package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.ObjKey;

public class UI {
    GamePanel gp;
    Font arial_30, arial_40, arial_70B;
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;

    public UI (GamePanel gp) {
        this.gp = gp;

        arial_30 = new Font("Arial", Font.PLAIN, 30);
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_70B = new Font("Arial", Font.BOLD, 70);
        keyImage = new ObjKey(gp).image;
    }

    public void showMessage(String message) {
        messageOn = true;
        this.message = message;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if(gp.gameFinished) {
            String text = "YOU FOUND THE TREASURE!";
            int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.SCREEN_WIDTH / 2 - textLength / 2;
            int y = gp.SCREEN_HEIGHT / 2 - gp.TILE_SIZE * 3;

            g2.drawString(text, x, y);

            g2.setFont(arial_70B);;
            g2.setColor(Color.YELLOW);

            text = "CONGRATULATIONS!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.SCREEN_WIDTH / 2 - textLength / 2;
            y = gp.SCREEN_HEIGHT / 2 + gp.TILE_SIZE * 2;

            g2.drawString(text, x, y);

            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            text = "Total play time: " + String.format("%.2f", gp.playTime);
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.SCREEN_WIDTH / 2 - textLength / 2;
            y = gp.SCREEN_HEIGHT / 2 + gp.TILE_SIZE * 4;

            g2.drawString(text, x, y);

            gp.gameThread = null;
        } else {
            g2.drawImage(keyImage, gp.TILE_SIZE / 2, gp.TILE_SIZE / 2, gp.TILE_SIZE, gp.TILE_SIZE, null);
            g2.drawString("x " + gp.player.hasKey, 74, 65);
    

            g2.drawString("Time: " + String.format("%.2f", gp.playTime), gp.TILE_SIZE * 11, 65);
            // MESSAGE
            if(messageOn) {
                g2.setFont(arial_30);
                g2.drawString(message, gp.TILE_SIZE / 2, gp.TILE_SIZE * 5);
    
                messageCounter++;
    
                if(messageCounter > 120) {
                    messageOn = false;
                    message = "";
                    messageCounter = 0;
                }
            }
        }
    }
}
