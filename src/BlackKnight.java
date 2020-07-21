import java.awt.*;
import java.awt.image.BufferedImage;
/*
Final Boss in the game
The black knight has 4 different phases depending on how much damage the player has done
1 - knight spawns minions and has a set pattern in which he moves doing melee damage
2 - spawms minions then cirlces the room doing ranged damage
3 - combination of the two but first minion phase
4 - comibination and spawns minions every 8 seconds

 */
public class BlackKnight extends GameObject {
    private final Game game;
    private final Handler handler;
    private final Sound sound;
    private int ticks = 0;
    private int bounds = 128;
    private boolean minions = true;
    private boolean minions1 = true;
    private boolean minions2 = true;
    private int oldtick = 0;
    private boolean tickB = true;
    private boolean moving = false;
    private int side = 0; // 0 == right 1 == left 2 == up
    private int dirx, diry;
    private BufferedImage Sprite = null;
    private int sprnum = 0;
    private boolean middle = false;
    private double theata = 0;
    private boolean first = true;
    private boolean second = false;
    public BlackKnight(int x, int y, ID id, SpriteSheet ss, Game game, Handler handler, Sound sound) {
        super(x, y, id, ss);
        this.game = game;
        this.handler = handler;
        this.sound = sound;
    }
    private void constantMinion(){
        if(ticks % 420 == 0) {
            handler.addObject(new GreyMinion(game.getPx() + 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx() - 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx() + 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx() - 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx(), game.getPy() - 300, ID.GMINION, handler, ss, sound, game));
        }

    }
    private void spawnMinions(){
        if(!minions) return;
            handler.addObject(new GreyMinion(game.getPx() + 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx() - 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx() + 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx() - 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
            handler.addObject(new GreyMinion(game.getPx(), game.getPy() - 300, ID.GMINION, handler, ss, sound, game));

    }
    private void spawnMinions1(){
        if(!minions1) return;
        handler.addObject(new GreyMinion(game.getPx() + 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx() - 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx() + 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx() - 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx(), game.getPy() - 300, ID.GMINION, handler, ss, sound, game));

    }
    private void spawnMinions2(){
        if(!minions2) return;
        handler.addObject(new GreyMinion(game.getPx() + 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx() - 300, game.getPy(), ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx() + 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx() - 150, game.getPy() + 300, ID.GMINION, handler, ss, sound, game));
        handler.addObject(new GreyMinion(game.getPx(), game.getPy() - 300, ID.GMINION, handler, ss, sound, game));

    }

    private void oldTicks(){
        if (tickB){
            oldtick = ticks;
            tickB = false;
        }
    }
    private void aura(int ticks){
        int b2 = bounds/2-10;
        int adjust = 60;
        if(ticks % 20 == 0 ){
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+0+b2, y+100+b2, ss, game, 30, 1));
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+100+b2, y+0+b2, ss, game, 30, 1));
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+-100+b2, y+0+b2, ss, game, 30, 1));
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+0+b2, y+-100+b2, ss, game, 30, 1));
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+50+b2, y+50+b2, ss, game, 30, 1));
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+-50+b2, y+50+b2, ss, game, 30, 1));
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+50+b2, y+-50+b2, ss, game, 30, 1));
            handler.addObject(new AuraShot(x+b2-adjust, y+b2, ID.AURASHOT, handler, x+-50+b2, y+-50+b2, ss, game, 30, 1));
        }
    }

    private void move(int dir){
        moving = true;
        if (dir == 0) {
            dirx = 1300;
            diry = 1000;
        }  else if (dir == 1) {
            dirx = 700;
            diry = 1000;
        } else if (dir == 2) {
            dirx = 1000;
            diry = 1300;
        }
        velX = Math.signum(dirx - x) * 5;
        velY = Math.signum(diry - y) * 5;
        if(Math.abs(velX) < 3) velX = 0;
        if(Math.abs(velY) < 3) velY = 0;
        int deltax = Math.abs(dirx-x);
        int deltay = Math.abs(diry-y);
        if( deltax < 10 && deltax > 0  && deltay < 10&& deltay > 0) {
            moving = false;
            velX = 0;
            velY = 0;
        } else if (deltax < 10 && deltax > 0){
            velX = 0;
        } else if (deltay < 10 && deltay > 0){
            velY = 0;
        }

    }
    private void triangularMovement(int ticks){

        if (ticks % 180 == 0) {
            side++;
            if(side > 2) {
                side = 0;
            }
        }
        move(side);
    }

    private void firsMove(int ticks){
        if(ticks < 120) {
            velY = 3;
            velX = 0;
            moving = true;
        } else {
            moving = false;
            velY = 0;
        }
    }

    private void firstPhase(){
        // first melee attack
            aura(ticks);
        // move in triangular pattern
        triangularMovement(ticks);
    }
    private void circularMovement(int ticks){
     theata++;
     if(theata/40 >= 6.2831855) theata = 0;
        velX = (float) (Math.cos(theata/40)*10+0.5);
        velY = (float) (Math.sin(theata/40)*10+0.5);
    }

private void moveToMiddle(){
        int dirx = 950;
        int diry = 500;
        if(!middle) {
            velX = Math.signum(dirx - x) * 8;
            velY = Math.signum(diry - y) * 8;

    int deltax = Math.abs(dirx-x);
    int deltay = Math.abs(diry-y);
    if( deltax < 10 && deltax > 0  && deltay < 10&& deltay > 0) {
        middle = true;
        velX = 0;
        velY = 0;
    } else if (deltax < 10 && deltax > 0){
        velX = 0;
    } else if (deltay < 10 && deltay > 0){
        velY = 0;
    }
        }
}
private void shotgun(){
        if(ticks % 60 == 0) {
            int px = game.getPx();
            int py = game.getPy();
            handler.addObject(new BossShot(x + bounds / 2, y - 40 + bounds / 2, ID.BOSSSHOT, handler, px, py, ss, game, sound, 1));
            handler.addObject(new BossShot(x + bounds / 2 + 40, y+5 + bounds / 2, ID.BOSSSHOT, handler, px + 40, py+5, ss, game, sound, 1));
            handler.addObject(new BossShot(x + bounds / 2 - 40, y-5 + bounds / 2, ID.BOSSSHOT, handler, px - 40, py-5, ss, game, sound, 1));

        }
}
    private void secondPhase(){
        if(!middle) {
            //move to middle
            moveToMiddle();
        } else {
        //move in a circle
        circularMovement(ticks);
        //shoot ranged attacks at the player
        shotgun();
        }
    }
    private void thirdPhase(){
        //do second and first phase alternating every 5 seconds
        if(ticks % 600 == 0){
            theata = 0;
            first = true;
            second = false;
        } else if (ticks % 300 == 0){
            middle = false;
            first = false;
            second = true;
        }
    }
    private void lastPhase(){
        //same as third but melee will paralyze and melee doesn't paralyze anymore

    }



    public void tick() {
         int hp = game.getBlackHP();
        if(!game.isRedAlive() && !game.isPurpleAlive()) {
            ticks++;
            spawnMinions();
            minions = false;
            firsMove(ticks);

            if(hp <= 2000 && hp >= 1500) {
                    firstPhase();
            } else if (hp <= 1500 && hp >= 1000){
                spawnMinions1();
                minions1 = false;
                secondPhase();
            } else if (hp <= 1000 && hp >= 500){
                spawnMinions2();
                minions2 = false;
                thirdPhase();
                if(first){
                    firstPhase();
                } else if (second){
                    secondPhase();
                }
            } else if (hp <= 500 && hp >= 0){
                constantMinion();
                thirdPhase();
                if(first){
                    firstPhase();
                } else if (second){
                    secondPhase();
                }
            } else if(hp <= 0) {
                game.setWon(true);
                sound.playSound("Minion_of_oryx_death.wav");
                game.getMusic().stopSong();
                game.setWinscreen(true);
                for (int i = handler.gameObjects.size()-1; i >= 0; i--) {
                    GameObject tempOjbect = handler.gameObjects.get(i);
                    if(tempOjbect.getId() != ID.BKNIGHT) {
                        handler.removeObject(tempOjbect);
                    }
                }
                handler.removeObject(this);
            }
        }

            x += velX;
            y += velY;

        if(Math.abs(velX) > 0 || Math.abs(velY) > 0){
            if(ticks % 15 == 0) {
                sprnum++;
            }
            if(sprnum > 2){
                sprnum = 0;
            }
            Sprite = game.KnightM[sprnum];
        } else {
            if(ticks % 10 == 0) {
                sprnum++;
            }
            if(sprnum > 1){
                sprnum = 0;
            }
            Sprite = game.KnightS[sprnum];
        }
    }

    public void render(Graphics g) {
        if(!game.isRedAlive() && !game.isPurpleAlive()) {
            Graphics2D g2d = (Graphics2D) g;
            //Draw shadow
            float alpha = 0.3f;
            AlphaComposite alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(alcom);
            g2d.drawImage(game.shadow, x + 5, y + 32, bounds, bounds, null);
            alpha = 1f;
            alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(alcom);
            g.drawImage(Sprite, x, y, bounds, bounds, null);
//        g.setColor(Color.green);
//        g.drawRect(x, y, bounds, bounds);
            //Health Bar
            g.setColor(Color.red);
            g.fillRect(x-30, y + bounds, 200, 10);
            g.setColor(Color.green);
            g.fillRect(x-30, y + bounds, game.getBlackHP() / 10, 10);
        }

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, bounds, bounds);
    }
}
