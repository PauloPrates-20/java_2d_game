package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entities.Player;
import object.BaseObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    public final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    public final int SCALE = 3;

    public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public int maxScreenCol = 16;
    public int maxScreenRow = 12;
    public int screenWidth = TILE_SIZE * maxScreenCol; // 768 pixels
    public int screenHeight = TILE_SIZE * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int MAX_WORLD_ROW = 76;
    public final int MAX_WORLD_COL = 76;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;

    // FPS
    public final int FPS = 60;

    public boolean gameFinished = false;
    public double playTime = 0;

    public KeyHandler keyH = new KeyHandler();
    public Thread gameThread;
    public Player player = new Player(this, keyH);
    public BaseObject[] objects = new BaseObject[10];

    public UI ui = new UI(this);
    public SoundController music = new SoundController();
    public SoundController soundEffect = new SoundController();
    public TileManager tileM = new TileManager(this);
    public CollisionController collController = new CollisionController(this);
    public ObjectManager objMan = new ObjectManager((this));

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setScreenSettings(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.maxScreenCol = (int)Math.ceil(screenWidth / TILE_SIZE);
        this.maxScreenRow = (int)Math.ceil(screenHeight / TILE_SIZE);
    }

    public void setupGame() {
        objMan.spawnObject();
        playMusic(SoundController.THEME);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    // public void run() {
    //     final double DRAW_INTERVAL = 1000000000/FPS; // in nanosseconds
    //     double nextDrawTime = System.nanoTime() + DRAW_INTERVAL;

    //     while(gameThread != null) {
    //         // 1 - Update information
    //         update();

    //         // 2 - Render
    //         repaint();

    //         try {
    //             double remainingTime = Math.max((nextDrawTime - System.nanoTime()) / 1000000, 0) ;
    //             Thread.sleep((long) remainingTime);

    //             nextDrawTime += DRAW_INTERVAL;
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }
    // }

    public void run() {
        final double DRAW_INTERVAL = 1000000000/FPS; // in nanosseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / DRAW_INTERVAL;
            lastTime = currentTime;

            if(delta >= 1) {
                playTime += 1.0/FPS;
                // 1 - Update information
                update();
                // 2 - Render
                repaint();
                delta--;
            }
        }
    }

    protected void update() {
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(screenHeight != getHeight() || screenWidth != getWidth()) {
            setScreenSettings(getWidth(), getHeight());
            player.setScreenSettings(screenWidth, screenHeight);
        }

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // TILES
        tileM.draw(g2);

        // OBJECTS
        for(BaseObject obj : objects) {
            if(obj != null) {
                obj.draw(g2, this);
            }
        }
        
        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
