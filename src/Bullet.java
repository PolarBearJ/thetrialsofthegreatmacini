import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bullet extends GameObject {
    private Handler handler;
    private final Game game;
    private final Sound sound;
    private double rotAngle;
    private float atkspd = 20;
    private int lifetime = 15;
    private int counter = 0;
    public Bullet(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss, Game game, Sound sound){
        super(x, y, id, ss);
        this.handler = handler;
        this.game = game;
        this.sound = sound;
        double h = Math.sqrt((mx-x)*(mx-x) + (my-y)*(my-y));
        if(game.isBEZERK()){
            atkspd+= (float) (atkspd * .9);
            lifetime -= (int) (lifetime/.65);
        }
        velX = (float) ((mx-x)*atkspd/h);
        velY = (float) ((my-y)*atkspd/h);
    }


    public void tick() {
counter++;
if(counter%lifetime == 0){
    handler.removeObject(this);
}

        x += velX;
        y += velY;
        for(int i = 0; i<handler.gameObjects.size()-1; i++){
            GameObject tempObject = handler.gameObjects.get(i);
            if(tempObject.getId()==ID.BLOCK){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                    return;
                }
            }
                if (tempObject.getId() == ID.BOSS) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        game.setBhealth(game.getBhealth() - 50);
                        sound.playSound("Djinn_hit.wav");
                        handler.removeObject(this);
                        return;
                    }
                }

                if (tempObject.getId() == ID.PKNIGHT) {
                    if (getBounds().intersects(tempObject.getBounds())) {
                        game.setPurpHP(game.getPurpHP() - 50);
                        sound.playSound("Rocks_hit.wav");
                        handler.removeObject(this);
                        return;
                    }
                }

            if(tempObject.getId()==ID.RKNIGHT){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.setRedHP(game.getRedHP()-50);
                    sound.playSound("Rocks_hit.wav");
                    handler.removeObject(this);
                    return;
                }
            }
            if(tempObject.getId()==ID.BKNIGHT){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.removeObject(this);
                    sound.playSound("Minion_of_oryx_hit.wav");
                    game.setBlackHP(game.getBlackHP()-50);
                }
            }
        }
    }

    public void render(Graphics g) {

         rotAngle = Math.atan(velY/velX);
        Graphics2D g2d = (Graphics2D) g;
        float alpha = 1f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);

        AffineTransform identity = new AffineTransform();
        AffineTransform trans = new AffineTransform();
        trans.setTransform(identity);
        trans.translate(x, y);
        trans.rotate(rotAngle);
        trans.scale(8,4);
        g2d.drawImage(game.bullet, trans, null);

    }


//        g2d.rotate(rotAngle);
//        System.out.println(rotAngle);
//        g.drawImage(game.bullet, x, y, 48, 48,null);



    public Rectangle getBounds() {
        return new Rectangle(x,y,24,48);
    }
}
