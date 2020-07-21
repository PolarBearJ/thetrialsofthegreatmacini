import java.awt.*;
/*
Grey shots are the shots used by the grey minions
 */
public class GreyShots extends GameObject {
    private final Handler handler;
    private final Game game;
    private final Sound sound;
    private int shotTimer = 0;
    private float atkspd = 20;

    public GreyShots(int x, int y, ID id, Handler handler, int sx, int sy, SpriteSheet ss, Game game, Sound sound){
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        this.sound = sound;
        double h = Math.sqrt((sx-x)*(sx-x) + (sy-y)*(sy-y));
        velX = (float) ((sx-x)*atkspd/h);
        velY = (float) ((sy-y)*atkspd/h);
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
                    game.setPhealth(game.getPhealth()-5);
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
        g.drawImage(game.greyshot, x, y, 40, 40, null);
    }




    public Rectangle getBounds() {
        return new Rectangle(x,y,40,40);
    }
}
