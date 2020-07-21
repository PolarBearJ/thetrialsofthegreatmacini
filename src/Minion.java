import java.awt.*;
import java.awt.image.BufferedImage;
/*
First minion you encounter in the game, walks towards you and blows itself up

 */
public class Minion extends GameObject {
    private Handler handler;
    private final Sound sound;
    private int hp = 100;
    private int px, py;
    private final Game game;
    private int aliveTime = 0;
    private int sprnum = 0;
    private BufferedImage Sprite;
    public Minion(int x, int y, ID id, Handler handler, SpriteSheet ss, Sound sound, Game game) {
        super(x, y, id, ss);
        this.handler = handler;
        this.sound = sound;
        this.game = game;
    }

    private void selfDestruct(int aliveTime){
        if(aliveTime % 180 ==0){
            handler.removeObject(this);
            handler.addObject(new Bomb(x, y, ID.BOMB, handler, ss, game, sound));
        }
    }

    public void tick() {
        velX = Math.signum(game.getPx() -x )*2;
        velY = Math.signum(game.getPy() -y)*2;
        aliveTime++;
        selfDestruct(aliveTime);
        x += velX;
        y += velY;
        for(int i = 0; i < handler.gameObjects.size(); i++){
            GameObject tempOjbect = handler.gameObjects.get(i);
            if(tempOjbect.getId()==ID.BULLET){
                if(getBounds().intersects(tempOjbect.getBounds())) {
                    hp -= 50;
                    sound.playSound("Djinn_hit.wav");
                    handler.removeObject(tempOjbect);
                }
            }
        }
        if(getBounds().intersects(game.getPlayerBounds())) game.setPhealth(game.getPhealth()-20);
        if(hp <= 0) {
            sound.playSound("Djinn-death.wav");
            handler.removeObject(this);
        }
        if(aliveTime % 20 == 0 ){
            sprnum++;
            if(sprnum > 2){
                sprnum = 0;
            }
        }

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //Draw shadow
        float alpha = 0.3f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.drawImage(game.shadow, x-4, y+10, 32, 32, null);
        if(velX > 0){
            Sprite = game.minionRight[sprnum];
        } else {
            Sprite = game.minionLeft[sprnum];
        }
         alpha = 1f;
         alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g.drawImage(Sprite, x, y, 32, 32, null);


    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32,32);
    }
    public Rectangle getBoundsBig() {
        return new Rectangle(x-16, y-16, 64,64);
    }
}
