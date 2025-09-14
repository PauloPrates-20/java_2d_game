package object;

import main.GamePanel;

public class ObjKey extends BaseObject {
    public ObjKey(GamePanel gp) {
        super(gp);
        name = "key";

        loadImage("/objects/key.png");
    }
}
