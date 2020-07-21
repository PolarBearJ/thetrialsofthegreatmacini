import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
/*
Very important class that "handles" all of the movement and user input.
This also "handles" the adding and removing of gameObjects
 */

public class Handler {
    LinkedList<GameObject> gameObjects = new LinkedList<GameObject>();

    private boolean up = false, down = false, right = false, left = false;
    public boolean RotatingRight = false, RotatingLeft = false, reset = false;
    public void tick(){
        for (GameObject gameObject : getGameObjects()) {
            if(gameObject.getId() != ID.BLOCK) {
                gameObject.tick();
            }
        }
    }

    private List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void render(Graphics g){
        for (int i = 0; i< gameObjects.size(); i++){
            GameObject tempObject = gameObjects.get(i);
            tempObject.render(g);
        }
    }
    public void addObject (GameObject tempObject){
        gameObjects.add(tempObject);
    }
    public void removeObject(GameObject tempObject){
        gameObjects.remove(tempObject);
    }
    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isLeft() {
        return left;
    }


    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setReset(boolean reset){
        this.reset = reset;
    }
}
