import java.awt.*;
import java.awt.image.BufferedImage;
/*
AuraShot is a attack pattern used by the final
boss as well as the Red Knight
It is a class which creates 8 shots coming out of the
enemy.


 */
public class AuraShot extends GameObject {
    private Handler handler;
    private final Game game;
    private int shotTimer = 0;
    private int damage;
    private int spritenum;
    private BufferedImage Sprite = null;
    public AuraShot(int x, int y, ID id, Handler handler, int sx, int sy, SpriteSheet ss, Game game, int dmg, int SPR){
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        velX = (sx-x)/10;
        velY = (sy-y)/10;
        damage = dmg;
        spritenum = SPR;
    }

    public void tick() {
        shotTimer++;
        if(shotTimer % 10 == 0){
            handler.removeObject(this);
        }
        for(int i = 0; i<handler.gameObjects.size()-1; i++){
            GameObject tempObject = handler.gameObjects.get(i);
            if(tempObject.getId()==ID.PLAYER){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                    game.setPhealth(game.getPhealth()-damage);
                }
            }
        }
        x += velX;
        y += velY;

    }

    public void render(Graphics g) {
        if(spritenum == 0){
            Sprite = game.auraShot;
        } else if (spritenum == 1){
            Sprite = game.blackKnightMelee;
        }
        Graphics2D g2d = (Graphics2D) g;
        float alpha = 1f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
//        g.setColor(Color.blue);
//        g.fillRect(x,y,20,20);
        g.drawImage(Sprite, x, y, 64, 64, null);
    }




    public Rectangle getBounds() {
        return new Rectangle(x,y,64,64);
    }
}
