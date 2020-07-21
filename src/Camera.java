import java.awt.geom.AffineTransform;
/*
Camera is what follows the player and shows the player
what is on the screen
 */
public class Camera {
    Handler handler;
    private float x, y;


    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }
    public void tick(int px, int py){

        x += ((px - x) - 772/2);
        y += ((py - y) - 500/2);

    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
