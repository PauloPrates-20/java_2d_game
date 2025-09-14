package util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Scaler {
    public static BufferedImage scaleImage(BufferedImage img, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, img.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
