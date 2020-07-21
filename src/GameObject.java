import java.awt.*;
/*
Game object is an abstract class that many of the enemies and
player use. This has all the information about the mob.
x, y, velX, velY, ID, Sprite


 */
public abstract class GameObject {

    protected int x, y;
    protected float velX = 0;
    protected float velY = 0;
    protected ID id;
    protected SpriteSheet ss;


    public GameObject(int x, int y, ID id, SpriteSheet ss){
        this.x = x;
        this.y = y;
        this.id = id;
        this.ss = ss;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }





}
