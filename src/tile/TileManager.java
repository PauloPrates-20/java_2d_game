package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import util.Scaler;

public class TileManager {
    public GamePanel gp;
    public Tile[] tiles;
    public int[][] mapTileNum;
    public String[] imgPaths = new String[] {
        "grass",
        "wall",
        "water",
        "earth",
        "tree",
        "sand",
    };
    public boolean[] collisions = new boolean[] {
        false,
        true,
        true,
        false,
        true,
        false
    };

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTileNum = new int[gp.MAX_WORLD_ROW][gp.MAX_WORLD_COL];
        
        loadMap("/maps/world01.txt");
        makeTile();
    }

    public void loadMap(String mapName) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(mapName)))) {
            for(int row = 0; row < gp.MAX_WORLD_ROW; row++) {
                String line = br.readLine();
                String[] tiles = line.split(" ");

                for(int col = 0; col < gp.MAX_WORLD_COL; col++) {
                    int tileNum = Integer.parseInt(tiles[col]);

                    mapTileNum[row][col] = tileNum;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeTile() {
        try {
            for(int i = 0; i < imgPaths.length; i++) {
                tiles[i] = new Tile();
                tiles[i].image = Scaler.scaleImage(ImageIO.read(getClass().getResourceAsStream("/tiles/" + imgPaths[i] + ".png")), gp.TILE_SIZE, gp.TILE_SIZE);
                tiles[i].collision = collisions[i];
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void draw(Graphics2D g2) {
        for(int worldRow = 0; worldRow < gp.MAX_WORLD_ROW; worldRow++) {
            for(int worldCol = 0; worldCol < gp.MAX_WORLD_COL; worldCol++) {
                int tileNum = mapTileNum[worldRow][worldCol];

                int worldX = worldCol * gp.TILE_SIZE;
                int worldY = worldRow * gp.TILE_SIZE;
                int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
                int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

                if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X &&
                   worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X &&
                   worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y &&
                   worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y
                ) {
                    g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }
}
