import java.awt.*;
/*
MiniShots are the shots used by the Mini bosses before the final boss
- have adjustable damage for different phases
 */
public class MiniShots extends GameObject {
    private final Handler handler;
    private final Game game;
    private final Sound sound;
    private int shotTimer = 0;
    private int damage = 0;
    public MiniShots(int x, int y, ID id, Handler handler, int sx, int sy, SpriteSheet ss, Game game, Sound sound, int dmg){
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        this.sound = sound;
        velX = (sx-x)/10;
        velY = (sy-y)/10;
        damage = dmg;
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
                    sound.playSound("Warrior_hit.wav");
                    game.setPhealth(game.getPhealth()-damage);
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
        g.drawImage(game.minishot, x, y, 40, 40, null);
    }




    public Rectangle getBounds() {
        return new Rectangle(x,y,40,40);
    }
}
