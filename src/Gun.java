import javax.swing.*;
import java.awt.*;
/*
This class is what is used to control the players shots
it was called gun because at first the player had a gun that
shot and i changed it to a projectile sword.

 */
public abstract class Gun extends GameObject {

    public static final int NANOS_PER_SECOND = 1000000000;
    private final Sound sound;
    private final Warrior warrior;
    private final Handler handler;
    private final Game game;
    private double lastFireNanoTime = 0;
    private long currentNanoTime = 0;
    private MouseInput MI;
    public int SprSw = 1;

    public int getSprDirShoot() {
        return SprDirShoot;
    }

    public void setSprDirShoot(int sprDirShoot) {
        SprDirShoot = sprDirShoot;
    }

    private int SprDirShoot = 1; // 1 = right, 2 = up, 3 = left 4 = down.

    PointerInfo a = MouseInfo.getPointerInfo();
    Point b = a.getLocation();
    public Gun(Warrior warrior, Handler handler, Game game, SpriteSheet ss, Sound sound) {
        super(warrior.getX(), warrior.getY(), ID.GUN, ss);
        this.warrior = warrior;
        this.handler = handler;
        this.game = game;
        this.sound = sound;
    }
    abstract int getShotsPerSecond();
    public double LineAni(double x){
        return 0.6*x-20;
    }
    public double LineAniNegative(double x){
        return (-0.6*x) + 640;
    }

    public void fire(){
        if(game.isShooting()) {
            Camera camera = game.getCamera();
            if (camera == null) {
                return;
            }
            PointerInfo a = MouseInfo.getPointerInfo();
            Point b = new Point(a.getLocation());
            SwingUtilities.convertPointFromScreen(b, game);
            int mx = (int) ((int) b.getX());
            int my = (int) ((int) b.getY());
            mx += (int) camera.getX();
            my += (int) camera.getY();
            mx = (int) (mx*Math.cos(warrior.warAng) - mx*Math.sin(warrior.warAng));
            my = (int) (my*Math.cos(warrior.warAng) + my*Math.sin(warrior.warAng));
            sound.playSound("BladeSwing.wav");
            handler.addObject(new Bullet(x + 16, y + 24, ID.BULLET, handler, mx, my, ss, game, sound));
//            System.out.println("Mouse X :" + b.getX() + ", Mouse Y :" +  b.getY());
//            System.out.println("Function's return Y = " + LineAniNegative((int) b.getX()));
            SprSw++;
            if(SprSw ==2) SprSw = 0;



            if(b.getY() < LineAni(b.getX()) && b.getY() < LineAniNegative(b.getX())){
                setSprDirShoot(2);
            } else if (b.getY() > LineAni(b.getX()) && b.getY() < LineAniNegative(b.getX())){
                setSprDirShoot(3);
            }else if (b.getY() < LineAni(b.getX()) && b.getY() > LineAniNegative(b.getX())){
                setSprDirShoot(1);
            }else if (b.getY() > LineAni(b.getX()) && b.getY() > LineAniNegative(b.getX())){
               setSprDirShoot(4);
            }
//





        }
    }
    @Override
    public void tick() {
        currentNanoTime++;
        this.x = warrior.getX();
        this.y = warrior.getY();
        if((currentNanoTime) - (lastFireNanoTime) >= 60/getShotsPerSecond()){
            fire();
            lastFireNanoTime = currentNanoTime;
        }
    }

    @Override
    public void render(Graphics g) {
// TODO draw the current gun on the bottom of the screen
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
