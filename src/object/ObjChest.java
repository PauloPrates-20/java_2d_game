package object;

import main.GamePanel;

public class ObjChest extends BaseObject {
    public ObjChest(GamePanel gp) {
        super(gp);
        name = "chest";

        loadImage("/objects/chest.png");
    }
}
