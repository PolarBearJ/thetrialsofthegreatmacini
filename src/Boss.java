import java.awt.*;
import java.awt.image.BufferedImage;
/*
Boss - this is the first enemy you encounter
this boss includes 4 phases
1 - set pattern of shots
2 - movement tracking
3 - CHASE
4 - minions and set pattern shots


 */
public class Boss extends GameObject{
    private final Game game;
    private final Sound sound;
    private int bounds = 128;
    private int shoottimer = 0;
    private final Handler handler;
    private int px, py, pvx, pvy;
    private int SPRNUM = 0;
    private BufferedImage Sprite;
    private int phaseNum = 0;
    public Boss(int x, int y, ID id, SpriteSheet ss, Game game, Sound sound, Handler handler) {
        super(x, y, id, ss);
        this.game = game;
        this.sound = sound;
        this.handler = handler;
    }

    private void burstPhase1(){
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+100+bounds/2, y+bounds/2, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+50+bounds/2, y+bounds/2+50, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x-50+bounds/2, y+bounds/2-50, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x-50+bounds/2, y+bounds/2+50, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+50+bounds/2, y+bounds/2-50, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x-100+bounds/2, y+bounds/2, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+bounds/2, y+100+bounds/2, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+bounds/2, y-100+bounds/2, ss, game, sound, 0));
    }
    private void burstPhase2(){
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+75+bounds/2, y+bounds/2+25, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+25+bounds/2, y+bounds/2+75, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x-25+bounds/2, y+bounds/2-75, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x-25+bounds/2, y+bounds/2+75, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+25+bounds/2, y+bounds/2-75, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x-75+bounds/2, y+bounds/2+25, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+bounds/2-25, y+100+bounds/2+25, ss, game, sound, 0));
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler, x+bounds/2-25, y-100+bounds/2+25, ss, game, sound, 0));
    }

    private void burstPhase(int shoottimer){
        if(shoottimer % 30 == 0 && shoottimer% 60 == 0){
            burstPhase1();
        } else if (shoottimer % 30 == 0 && shoottimer % 60 != 0){
            burstPhase2();
        }
        if(game.getBhealth() <= 0){
            handler.removeObject(this);
        }
    }
    private void prdShotTime(int shoottimer){
        if(shoottimer % 20 == 0){
            predictionShot();
        }
    }

    private void predictionShot(){
        px = game.getPx();
        py = game.getPy();
        px +=  18 * game.getPvx();
        py += 18*game.getPvy();
        handler.addObject(new BossShot(x+bounds/2, y+bounds/2, ID.BOSSSHOT, handler,px , py, ss, game, sound, 0));
    }

    private void chase(){
        x += Math.signum(game.getPx() -x )*2;
        y+= Math.signum(game.getPy() -y)*2;
    }

    private void CollPlayer(){
                if(getBounds().intersects(game.getPlayerBounds())){
                    game.setPhealth(game.getPhealth()-50);
                }
    }
private void resetPos() {

        if(Math.abs(x) - Math.abs(game.IX) < 10 && Math.abs(y) - Math.abs(game.IY) < 10) return;
        x += Math.signum(game.IX - x) * 5;
        y += Math.signum(game.IY - y) * 5;
}
private void finalPhase(int shoottimer){
        burstPhase(shoottimer);
        if(shoottimer%90 == 0){
            handler.addObject(new Minion(x+bounds/2, y+bounds, ID.MINION, handler, ss, sound, game));
        }
}



    public void tick() {
        CollPlayer();
        shoottimer++;
        if (game.getBhealth() <= 1000 && game.getBhealth() >= 750) {
            phaseNum = 1;
            if(shoottimer % 20 == 0){
                SPRNUM++;
            }
            if(SPRNUM > 2){
                SPRNUM = 0;
            }
            burstPhase(shoottimer);
        } else if (game.getBhealth() <= 750 && game.getBhealth() >= 500) {
            phaseNum = 2;
            if(shoottimer% 20 == 0){
                SPRNUM++;
            }
            if(SPRNUM > 1){
                SPRNUM = 0;
            }


            prdShotTime(shoottimer);

        } else if(game.getBhealth() <= 500 && game.getBhealth() >= 250) {
            phaseNum = 3;
            if(shoottimer % 20 == 0){
                SPRNUM++;
            }
            if(SPRNUM > 2){
                SPRNUM = 0;
            }


            chase();
        }else if(game.getBhealth() <= 250 && game.getBhealth() >= 0) {
            phaseNum = 4;
            resetPos();
            if(shoottimer % 20 == 0){
                SPRNUM++;
            }
            if(SPRNUM > 4){
                SPRNUM = 0;
            }


            finalPhase(shoottimer);
        }
        if(game.getBhealth() <= 0){
            sound.playSound("Djinn-death.wav");
            handler.addObject(new TrapDoor(x, y, ID.DOOR, ss, game, handler, sound));
            game.setBossDead(true);
            handler.removeObject(this);
        }


    }


    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //Draw shadow
        float alpha = 0.3f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
        g2d.drawImage(game.shadow, x+5, y+32, 128, 128, null);
        if(phaseNum == 1){
           Sprite = game.Djinn[SPRNUM];
        } else if (phaseNum == 2){
            Sprite = game.DjinnShoot[SPRNUM];
        } else if (phaseNum == 3){
            Sprite = game.Djinn[SPRNUM];
        } else if (phaseNum == 4){
            if(SPRNUM <= 2) {
                Sprite = game.Djinn[SPRNUM];
            } else if(SPRNUM >= 3){
                Sprite = game.DjinnShoot[SPRNUM-3];
            }
        }
         alpha = 1f;
         alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);
//        g.setColor(Color.black);
//        g.fillRect(x , y, bounds, bounds);
        g.drawImage(Sprite, x, y, bounds, bounds, null);



        //Health Bar
        g.setColor(Color.red);
        g.fillRect(x +14, y+bounds, 100, 10);
        g.setColor(Color.green);
        g.fillRect(x +14, y+bounds, game.getBhealth()/10, 10);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, bounds, bounds);
    }


}
