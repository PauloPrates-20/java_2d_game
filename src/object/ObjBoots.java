package object;

import main.GamePanel;

public class ObjBoots extends BaseObject {
    public ObjBoots(GamePanel gp) {
        super(gp);
        name = "boots";

        loadImage("/objects/boots.png");
    }
}
