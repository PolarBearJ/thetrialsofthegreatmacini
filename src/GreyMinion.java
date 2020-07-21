import java.awt.*;
import java.awt.image.BufferedImage;
/*
Grey minions are the minions spawned by the black knight in the
final phase. They spawn in a pentagramol shape and don't do
that much damage. But they stack up quickly



 */
public class GreyMinion extends GameObject {
    private final Handler handler;
    private final Sound sound;
    private int hp = 100;
    private int px, py;
    private final Game game;
    private int ticks = 0;
    private int sprnum = 0;
    private BufferedImage Sprite;
    private boolean isMoving = false;
    private boolean shooting = true;
    private int bounds = 64;
    public GreyMinion(int x, int y, ID id, Handler handler, SpriteSheet ss, Sound sound, Game game) {
        super(x, y, id, ss);
        this.handler = handler;
        this.sound = sound;
        this.game = game;
    }

    private void attackTimer(int ticks){
        if(ticks % 120 ==0){
            if(isMoving){
                velX = 0;
                velY = 0;
                isMoving = false;
            } else {
                isMoving = true;
            }
            if(shooting){
                shooting= false;
            } else {
                shooting = true;
            }
        }
    }

    private void Shoot (){
        if(ticks % 60 == 0) {
            px = game.getPx();
            py = game.getPy();
            handler.addObject(new GreyShots(x + bounds / 2, y + bounds / 2, ID.GRESHOT, handler, px, py, ss, game, sound));
        }
    }

    private void move(){
        velX = Math.signum(game.getPx() -x )*2;
        velY = Math.signum(game.getPy() -y)*2;
    }

    public void tick() {
        ticks++;
        attackTimer(ticks);
        if(isMoving) {
            move();
        }
        if(shooting){
            Shoot();
        }
        x += velX;
        y += velY;
        for(int i = 0; i < handler.gameObjects.size(); i++){
            GameObject tempOjbect = handler.gameObjects.get(i);
            if(tempOjbect.getId()==ID.BULLET){
                if(getBounds().intersects(tempOjbect.getBounds())) {
                    sound.playSound("Night_elves_hit.wav");
                    hp -= 50;
                    handler.removeObject(tempOjbect);
                }
            }
        }
        if(getBounds().intersects(game.getPlayerBounds())) game.setPhealth(game.getPhealth());
        if(hp <= 0){
            sound.playSound("Night_elves_death.wav");
            handler.removeObject(this);
        }
        if(ticks % 20 == 0 ){
            sprnum++;
        }
    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //Draw shadow
        float alpha = 0.3f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.drawImage(game.shadow, x-4, y+10, bounds, bounds, null);
        if(Math.abs(velX) > 0 || Math.abs(velY) > 0){
                if (sprnum > 2) {
                    sprnum = 0;
                }
            Sprite = game.greyKnightMove[sprnum];
        } else {
            if (sprnum > 1) {
                sprnum = 0;
            }
            Sprite = game.greyKnightAtk[sprnum];
        }
        alpha = 1f;
        alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g.drawImage(Sprite, x, y, bounds, bounds, null);


    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, bounds,bounds);
    }

}
