package object;

import main.GamePanel;

public class ObjDoor extends BaseObject {
    public ObjDoor(GamePanel gp) {
        super(gp);
        name = "door";
        collision = true;

        loadImage("/objects/door.png");
    }
}
