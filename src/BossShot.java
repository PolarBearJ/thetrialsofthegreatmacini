import java.awt.*;
import java.awt.image.BufferedImage;
/*
Shot used by the first boss


 */
public class BossShot extends GameObject {
    private final Handler handler;
    private final Game game;
    private final Sound sound;
    private int shotTimer = 0;
    private int SPR;
    private BufferedImage Sprite;
    private float atkspd = 10;
    private int bounds = 50;
    public BossShot(int x, int y, ID id, Handler handler, int sx, int sy, SpriteSheet ss, Game game, Sound sound, int spritetype){
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        this.sound = sound;
        double h = Math.sqrt((sx-x)*(sx-x) + (sy-y)*(sy-y));

        velX = (float) ((sx-x)*atkspd/h);
        velY = (float) ((sy-y)*atkspd/h);
        SPR = spritetype;
    }

    public void tick() {
        shotTimer++;
        if(shotTimer % 60 == 0){
            handler.removeObject(this);
        }
        for(int i = 0; i<handler.gameObjects.size()-1; i++){
            GameObject tempObject = handler.gameObjects.get(i);
            if(tempObject.getId()==ID.PLAYER){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                    game.setPhealth(game.getPhealth()-10);
                    sound.playSound("Warrior_hit.wav");
                }
            }
        }
        x += velX;
        y += velY;

    }

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        float alpha = 1f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
//        g.setColor(Color.blue);
//        g.fillRect(x,y,20,20);
        if(SPR == 0) Sprite = game.bossBullet;
        if(SPR == 1) Sprite = game.blackKnightRanged;
            g.drawImage(Sprite, x, y, bounds, bounds, null);

    }




    public Rectangle getBounds() {
        return new Rectangle(x,y,bounds,bounds);
    }
}
