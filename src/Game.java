import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

//Game
public class Game extends Canvas implements Runnable {
    //    private static final long serialVersionUID = 1L;

    //Very important variables
    private int spritesize = 8;
    private boolean isRunning = false;
    private Thread gameThread;
    private Thread musicThread;
    private Music music;
    //Class imports
    private Handler handler;
    private Sound sound;
    //ALL IMAGES ARE BELOW:

    //SpriteSheets and Level
    private BufferedImage level = null;
    private BufferedImage players = null;
    private BufferedImage lostHallsObjects8x8 = null;
    private BufferedImage lofiChar = null;
    private BufferedImage minionspr = null;
    private BufferedImage floor = null;
    private BufferedImage wood = null;
    private BufferedImage bossfloor = null;
    //Character and level Sprites
    public BufferedImage wall = null;
    public BufferedImage bullet = null;
    public BufferedImage warrchar = null;
    public BufferedImage[] warrCharRight = new BufferedImage[2];
    public BufferedImage[] warrCharUp = new BufferedImage[3];
    public BufferedImage[] warrCharDown = new BufferedImage[3];
    public BufferedImage[] warrCharLeft = new BufferedImage[2];
    public BufferedImage[] warrCharShootRight = new BufferedImage[2];
    public BufferedImage[] warrCharShootUp = new BufferedImage[2];
    public BufferedImage[] warrCharShootLeft = new BufferedImage[2];
    public BufferedImage[] warrCharShootDown = new BufferedImage[2];
    public BufferedImage shadow = null;
    public BufferedImage blackFloor = null;
    public BufferedImage[] Djinn = new BufferedImage[3];
    public BufferedImage[] DjinnShoot = new BufferedImage[2];
    public BufferedImage bossBullet = null;
    public BufferedImage[] minionLeft = new BufferedImage[3];
    public BufferedImage[] minionRight = new BufferedImage[3];
    private BufferedImage inventory1 = null;
    public BufferedImage hpPot = null;
    public BufferedImage mpPot = null;
    public BufferedImage bezrk = null;
    public BufferedImage helm = null;
    public BufferedImage snowsplotion = null;
    public BufferedImage trapDoor = null;
    public BufferedImage tutLevel = null;
    public BufferedImage level2 = null;
    public BufferedImage FBoss = null;
    public BufferedImage[] PurpleM = new BufferedImage[6];
    public BufferedImage[] PurpleSpin = new BufferedImage[4];
    public BufferedImage[] RedM = new BufferedImage[6];
    public BufferedImage[] RedSpin = new BufferedImage[4];
    public BufferedImage[] KnightM = new BufferedImage[3];
    public BufferedImage[] KnightS = new BufferedImage[3];
    public BufferedImage[] greyKnightMove = new BufferedImage[3];
    public BufferedImage[] greyKnightAtk = new BufferedImage[2];
    public BufferedImage wasd = null;
    public BufferedImage fv = null;
    public BufferedImage mouse = null;
    public BufferedImage minishot = null;
    public BufferedImage auraShot = null;
    public BufferedImage greyshot = null;
    public BufferedImage blackKnightMelee = null;
    public BufferedImage blackKnightRanged = null;
    public BufferedImage LoseScreen = null;
    public BufferedImage WinScreen = null;
    public BufferedImage Credits = null;
    public BufferedImage HowToPlay = null;
    public BufferedImage StartScreen = null;
    public BufferedImage[] blobright = new BufferedImage[2];
    public BufferedImage[] blobleft = new BufferedImage[2];



    //Starting info
    public final int WIDTH = 820;
    public final int HEIGHT = 640;
    private Camera camera;
    private SpriteSheet ss, sc;
    //All booleans
    private boolean won = false;
    private boolean shooting = false;
    private boolean SPEEDY = false;
    private boolean BEZERK = false;
    private boolean onCooldown = false;
    private boolean enoughMana = true;
    private boolean charDead = false;
    private boolean bossDead = false;
    private boolean spaceParticle = false;
    private boolean PURPLEKNIGHTFIX = false;
    private boolean REDKNIGHTFIX = false;
    private boolean BLACKKNIGHTFIX = false;
    private boolean PurpleAlive = true;
    private boolean RedAlive = true;
    public boolean newLvl = false;
    public boolean newLvl1 = false;
    public boolean lvl3 = false;
    private boolean paused = false;
    private boolean startscreen = true;
    private boolean howtoplay = false;
    private boolean winscreen = false;
    private boolean losescreen = false;
    private boolean credits = false;
    private boolean menu = true;


    //Used for cameras angle
    public double CamA = 0;
    //ALL integers
    private int px;
    private int py;
    private int pvx;
    private int pvy;
    public int IX;
    public int IY;
    public int Phealth = 100;
    public int PMana = 100;
    public int Bhealth = 1000;
    private int exitGameX = 0;
    private int exitGameY = 0;
    private int playAgainX = 0;
    private int playAgainY = 0;
    private int hpPotNum = 6;
    private int mpPotNum = 6;
    private int PurpHP = 1000;
    private int RedHP = 1000;
    private int BlackHP = 2000;
    private int levelNumber = 1;

//Constructor class which is run in the main method
    public Game() throws IOException {
        new Window(WIDTH, HEIGHT, "The trials of the great Macini", this);
        handler = new Handler();
        sound = new Sound(this);
        camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handler, this, sound));
         Images();
        this.addMouseListener(new MouseInput(handler, camera, ss, sound, this));

        loadLevel(tutLevel);
        start();
    }

    //This method is for when the player dies, they reset the game
    //Note, if they have already killed the first boss then it will no longer respawn
    //Same goes for the mini bosses IF the player has killed BOTH
    public void RESET() {
        if (!won) {
            music.setSongNumber(-1);
            music.changeSong();
            newLvl = false;
            newLvl1 = false;
            REDKNIGHTFIX = false;
            PURPLEKNIGHTFIX = false;
            BLACKKNIGHTFIX = false;
            if (!PurpleAlive && !RedAlive) {
                PurpHP = -1;
                RedHP = -1;
            } else {
                PurpHP = 1000;
                RedHP = 1000;
                PurpleAlive = true;
                RedAlive = true;
            }
            BlackHP = 2000;
            if (bossDead) {
                Bhealth = 0;
            } else {
                Bhealth = 1000;
            }
            lvl3 = false;
            levelNumber = 1;
            Phealth = 100;
            PMana = 100;
            hpPotNum = 6;
            mpPotNum = 6;
            BEZERK = false;
            SPEEDY = false;
            onCooldown = false;
            setCharDead(false);
            loadLevel(tutLevel);
        } else {
            music.setSongNumber(-1);
            music.changeSong();
            newLvl = false;
            newLvl1 = false;
            music.setSongNumber(0);
            REDKNIGHTFIX = false;
            PURPLEKNIGHTFIX = false;
            BLACKKNIGHTFIX = false;
            PurpHP = 1000;
            RedHP = 1000;
            PurpleAlive = true;
            RedAlive = true;
            BlackHP = 2000;
            Bhealth = 1000;
            lvl3 = false;
            levelNumber = 1;
            Phealth = 100;
            PMana = 100;
            hpPotNum = 6;
            mpPotNum = 6;
            BEZERK = false;
            SPEEDY = false;
            onCooldown = false;
            setCharDead(false);
            loadLevel(tutLevel);
            won = false;
        }
    }

    //These methods are called by the trapdoor to load new levels
    public void Level1() {
        newLvl = true;
        loadLevel(level);
    }

    public void Level2() {
        newLvl1 = true;
        Bhealth = 1000;
        loadLevel(level2);
    }
    //this loads Every single image that is imported
    private void Images() throws IOException {
        BufferedImageLoader loader = new BufferedImageLoader();
        //LOAD ALL THE SCREENS
        WinScreen = loader.loadImage("/WinScreen.png");
        LoseScreen =loader.loadImage("/LoseScreen.png");
        StartScreen = loader.loadImage("/StartScreen.png");
        Credits = loader.loadImage("/Credits.png");
        HowToPlay = loader.loadImage("/HowToPlay.png");

        //Tutorial Stuff
        wasd = loader.loadImage("/wasd.png");
        fv = loader.loadImage("/fv.png");
        mouse = loader.loadImage("/mouse.png");
        inventory1 = loader.loadImage("/inven.png");
        minionspr = loader.loadImage("/minisprites.png");
        //LEVELS
        level = loader.loadImage("/warrior_level.png");
        tutLevel = loader.loadImage("/warrior_tutorial.png");
        level2 = loader.loadImage("/warrior_level_2.png");
        FBoss = loader.loadImage("/finalboss.png");
        players = loader.loadImage("/players.png");
        lostHallsObjects8x8 = loader.loadImage("/lostHallsObjects8x8.png");
        lofiChar = loader.loadImage("/lofiChar.png");
        helm = loader.loadImage("/helm.png");
        BufferedImage shadowLoader = loader.loadImage("/shadow.png");
        BufferedImage BOSSHEET = loader.loadImage("/chars16x16dMountains1.png");
        SpriteSheet shadowSprite = new SpriteSheet(shadowLoader);
        SpriteSheet BossSPR = new SpriteSheet(BOSSHEET);
        SpriteSheet minions = new SpriteSheet(minionspr);
        SpriteSheet FBosses = new SpriteSheet(FBoss);
        ss = new SpriteSheet(lostHallsObjects8x8);
        //Final Boss Sprites
        //Purples move
        PurpleM[0] = FBosses.grabImage(1, 1, 16, 16);
        PurpleM[1] = FBosses.grabImage(3, 1, 16, 16);
        PurpleM[2] = FBosses.grabImage(5, 1, 16, 16);
        PurpleM[3] = FBosses.grabImage(1, 3, 16, 16);
        PurpleM[4] = FBosses.grabImage(3, 3, 16, 16);
        PurpleM[5] = FBosses.grabImage(5, 3, 16, 16);
        //Purples spin attack
        PurpleSpin[0] = FBosses.grabImage(9, 1, 16, 16);
        PurpleSpin[1] = FBosses.grabImage(11, 1, 16, 16);
        PurpleSpin[2] = FBosses.grabImage(9, 3, 16, 16);
        PurpleSpin[3] = FBosses.grabImage(11, 3, 16, 16);
        //Red Move
        RedM[0] = FBosses.grabImage(1, 11, 16, 16);
        RedM[1] = FBosses.grabImage(3, 11, 16, 16);
        RedM[2] = FBosses.grabImage(5, 11, 16, 16);
        RedM[3] = FBosses.grabImage(1, 13, 16, 16);
        RedM[4] = FBosses.grabImage(3, 13, 16, 16);
        RedM[5] = FBosses.grabImage(5, 13, 16, 16);
        //Red Spin attack
        RedSpin[0] = FBosses.grabImage(9, 11, 16, 16);
        RedSpin[1] = FBosses.grabImage(11, 11, 16, 16);
        RedSpin[2] = FBosses.grabImage(9, 13, 16, 16);
        RedSpin[3] = FBosses.grabImage(11, 13, 16, 16);
        //BK Move
        KnightM[0] = FBosses.grabImage(1, 17, 16, 16);
        KnightM[1] = FBosses.grabImage(3, 17, 16, 16);
        KnightM[2] = FBosses.grabImage(5, 17, 16, 16);
        //BK ATK
        KnightS[0] = FBosses.grabImage(1, 19, 16, 16);
        KnightS[1] = FBosses.grabImage(3, 19, 16, 16);
        KnightS[2] = FBosses.grabImage(5, 19, 16, 16);
        //Grey knight Move
        greyKnightMove[0] = FBosses.grabImage(1, 15, 16, 16);
        greyKnightMove[1] = FBosses.grabImage(3, 15, 16, 16);
        greyKnightMove[2] = FBosses.grabImage(5, 15, 16, 16);
        //Grey Knight atk
        greyKnightAtk[0] = FBosses.grabImage(9, 15, 16, 16);
        greyKnightAtk[1] = FBosses.grabImage(11, 15, 16, 16);
//Black knight attakcs
        blackKnightMelee = ss.grabImage(15, 2, 8, 8);
        blackKnightRanged = ss.grabImage(7, 11, 8, 8);
//Grey minion attacks
        greyshot = ss.grabImage(14, 8, 8, 8);


        //Knights first shots:
        minishot = ss.grabImage(5, 8, 8, 8);
        auraShot = ss.grabImage(9, 9, 8, 8);


        //Djinn boss Animations:
        Djinn[0] = BossSPR.grabImage(1, 7, 16, 16);
        Djinn[1] = BossSPR.grabImage(3, 7, 16, 16);
        Djinn[2] = BossSPR.grabImage(5, 7, 16, 16);
        DjinnShoot[0] = BossSPR.grabImage(9, 7, 16, 16);
        DjinnShoot[1] = BossSPR.grabImage(11, 7, 16, 16);
        minionRight[0] = minions.grabImage(1, 1, 8, 8);
        minionRight[1] = minions.grabImage(2, 1, 8, 8);
        minionRight[2] = minions.grabImage(3, 1, 8, 8);
        minionLeft[0] = minions.grabImage(1, 2, 8, 8);
        minionLeft[1] = minions.grabImage(2, 2, 8, 8);
        minionLeft[2] = minions.grabImage(3, 2, 8, 8);
        //MR BLOB
        blobright[0] = minions.grabImage(1, 3, 8, 8);
        blobright[1] = minions.grabImage(2, 3, 8, 8);
        blobleft[0] = minions.grabImage(1, 4, 8, 8);
        blobleft[1] = minions.grabImage(2, 4, 8, 8);


        //Pots
        hpPot = ss.grabImage(14, 10, 8, 8);
        mpPot = ss.grabImage(16, 10, 8, 8);
        //Misc
        trapDoor = ss.grabImage(15, 3, 8, 8);
        shadow = shadowSprite.grabImage(1, 1, 8, 8);
        snowsplotion = ss.grabImage(12, 10, 8, 8);
        bezrk = ss.grabImage(9, 8, 8, 8);
        bossBullet = ss.grabImage(8, 7, 8, 8);
        blackFloor = ss.grabImage(8, 2, 8, 8);
        floor = ss.grabImage(1, 1, 8, 8);
        wood = ss.grabImage(10, 3, 8, 8);
        bossfloor = ss.grabImage(16, 1, 8, 8);
        wall = ss.grabImage(1, 2, 8, 8);
        bullet = ss.grabImage(16, 8, 8, 8);
        sc = new SpriteSheet(players);
        //Player
        warrchar = sc.grabImage(1, 13, 8, 8);
        warrCharRight[0] = sc.grabImage(1, 13, 8, 8);
        warrCharRight[1] = sc.grabImage(2, 13, 8, 8);
        warrCharUp[0] = sc.grabImage(1, 15, 8, 8);
        warrCharUp[1] = sc.grabImage(2, 15, 8, 8);
        warrCharUp[2] = sc.grabImage(3, 15, 8, 8);
        warrCharDown[0] = sc.grabImage(1, 14, 8, 8);
        warrCharDown[1] = sc.grabImage(2, 14, 8, 8);
        warrCharDown[2] = sc.grabImage(3, 14, 8, 8);
        warrCharLeft[1] = sc.grabImage(1, 16, 8, 8);
        warrCharLeft[0] = sc.grabImage(2, 16, 8, 8);
        warrCharShootRight[0] = sc.grabImage(5, 13, 8, 8);
        warrCharShootRight[1] = sc.grabImage(6, 13, 16, 8);
        warrCharShootUp[0] = sc.grabImage(5, 15, 8, 8);
        warrCharShootUp[1] = sc.grabImage(6, 15, 8, 8);
        warrCharShootDown[0] = sc.grabImage(5, 14, 8, 8);
        warrCharShootDown[1] = sc.grabImage(6, 14, 8, 8);
        warrCharShootLeft[0] = sc.grabImage(5, 16, 8, 8);
        warrCharShootLeft[1] = sc.grabImage(6, 16, 16, 8);
    }


    //Start the actual game
    private void start() {
        isRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
        music = new Music(this);
        musicThread = new Thread(music);
        musicThread.start();

    }

    //This is called when the game is stopped AKA when player exits game
    private void stop() {
        isRunning = false;
        try {
            musicThread.join();
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Upon launch
    public void run() {
        //Request focus of the computer
        this.requestFocus();
        long lastTime = System.nanoTime();
        double ticksPerSecond = 60;
        double nsPerTick = 1000000000 / ticksPerSecond;
        double delta = 0;
        long timer = System.currentTimeMillis();
//This part is if the computer hasnt drawn the past couple of ticks
        //Then it will do so quickly to catch up
        //Therefore if the ticks dont match the renders
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }

        }
        stop();
    }


    //Do all the ticks for every object
    //Calls all tick methods for every GameObject
    public void tick() {
        camera.tick(px, py);
        if(!paused) {
            handler.tick();
        }
    }

    //Methods called by KeyInput to use Potions
    public void useHPPot() {
        Phealth += 50;
    }

    public void useMPPot() {
        PMana += 50;
    }

    //Draws the hud, HP BAR, how man pots left, Gear and ability on cooldown
    private void HUD(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        float alpha = 1f;
        AlphaComposite alcom = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alcom);

        //Draw background for hud
        g.setColor(Color.BLACK);
        g.fillRect((int) camera.getX(), (int) camera.getY() + 540, WIDTH, 50);
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int) camera.getX(), (int) camera.getY() + 550, WIDTH, 200);
        //HP AND MANA
        if (Phealth < 0) {
            Phealth = 0;
        }
        if (PMana < 0) {
            PMana = 0;
        }
        //DRAW HEALTH BAR
        g.setColor(Color.red);
        g.fillRect((int) camera.getX() + 10, (int) camera.getY() + 560, 200, 30);
        g.setColor(Color.green);
        g.fillRect((int) camera.getX() + 10, (int) camera.getY() + 560, Phealth * 2, 30);
        Font h = new Font("TimesRoman", Font.PLAIN, 15);
        g.setColor(Color.black);
        g.setFont(h);
        g.drawString(Phealth + " / " + 100, (int) camera.getX() + 85, (int) camera.getY() + 580);

        //DRAW MANA
        g.setColor(Color.GRAY);
        g.fillRect((int) camera.getX() + 230, (int) camera.getY() + 560, 200, 30);
        g.setColor(Color.cyan);
        g.fillRect((int) camera.getX() + 230, (int) camera.getY() + 560, PMana * 2, 30);
        g.setColor(Color.black);
        g.drawString(PMana + " / " + 100, (int) camera.getX() + 305, (int) camera.getY() + 580);

        g.drawImage(hpPot, (int) camera.getX() + 450, (int) camera.getY() + 550, 24, 24, null);
        g.drawImage(mpPot, (int) camera.getX() + 450, (int) camera.getY() + 570, 24, 24, null);
        g.setColor(Color.white);

        g.drawString(hpPotNum + " / " + 6, (int) camera.getX() + 475, (int) camera.getY() + 568);
        g.drawString(mpPotNum + " / " + 6, (int) camera.getX() + 475, (int) camera.getY() + 588);

        g.drawImage(inventory1, (int) camera.getX() + 575, (int) camera.getY() + 550, 200, 50, null);
        if (onCooldown) {
            alpha = .5f;
            alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(alcom);
        } else {
            alpha = 0f;
            alcom = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(alcom);
        }
        g.setColor(Color.DARK_GRAY);
        g.fillRect((int) camera.getX() + 625, (int) camera.getY() + 550, 50, 50);


    }
    //Pause screen if player hits p
    private void pauseScreen(Graphics g){
        Font h = new Font("TimesRoman", Font.PLAIN, 35);
        g.setColor(Color.white);
        g.setFont(h);
        g.drawString("PAUSED", (int) camera.getX()+WIDTH/2-70, (int) camera.getY()+HEIGHT/2-75);
    }
    //This is to show all of the menu screens
    private void showScreens(Graphics g){
        if(startscreen && !howtoplay){
            g.drawImage(StartScreen, (int)camera.getX(), (int)camera.getY(), WIDTH, HEIGHT, null);
        } else if (startscreen && howtoplay){
            g.drawImage(HowToPlay,(int)camera.getX(), (int)camera.getY(), WIDTH, HEIGHT, null);
        } else if(losescreen){
            g.drawImage(LoseScreen,(int)camera.getX(), (int)camera.getY(), WIDTH, HEIGHT-30, null);
        } else if (winscreen){
            g.drawImage(WinScreen,(int)camera.getX(), (int)camera.getY(), WIDTH, HEIGHT-30, null);
        } else if (credits){
            g.drawImage(Credits,(int)camera.getX(), (int)camera.getY(), WIDTH, HEIGHT-30, null);
        }
    if(startscreen || winscreen || losescreen || credits){
        menu = true;
    } else {
        menu = false;
    }



    }




    //Render is drawing all of the GOs (GameObjecs)
    public void render() {
        BufferStrategy bs = this.getBufferStrategy(); //created null
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        ////////////////////////// between these lines are what is being shown
//Translates the frame to be from the -camx -camy
        g2d.translate(-camera.getX(), -camera.getY());
//This is to draw all the tiles on the ground
        //Level number coresponds to the current level
        for (int xx = 0; xx < 30 * 72; xx += 64) {
            for (int yy = 0; yy < 30 * 72; yy += 64) {
                if (levelNumber == 1) {
                    if (xx >= 8 * 64 && xx <= 22 * 64 && yy >= 7 * 64 && yy <= 23 * 64) {
                        g.drawImage(wood, xx, yy, 64, 64, null);
                    } else {
                        g.drawImage(blackFloor, xx, yy, 64, 64, null);
                    }
                } else if (levelNumber == 2 && !lvl3) {
                    if (xx >= 8 * 64 && xx <= 22 * 64 && yy >= 7 * 64 && yy <= 23 * 64) {
                        g.drawImage(floor, xx, yy, 64, 64, null);
                    } else {
                        g.drawImage(blackFloor, xx, yy, 64, 64, null);
                    }
                } else if (lvl3) {
                    if (xx >= 8 * 64 && xx <= 22 * 64 && yy >= 7 * 64 && yy <= 23 * 64) {
                        g.drawImage(bossfloor, xx, yy, 64, 64, null);
                    } else {
                        g.drawImage(blackFloor, xx, yy, 64, 64, null);
                    }
                }

            }
        }
        showScreens(g);
        if(!menu) {
            if (paused) {
                pauseScreen(g);
            }
//Draw all the GOs
            //Draw hud on top
            handler.render(g);
            HUD(g);

        }
            /////////////////////////
            g.dispose();
            bs.show();

    }

    //Loading the level
    //Method that takes in a image and scans it to create the layout for the map
    //This creates the blocks, enemies and the player (even the tutorial)
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if (red == 255 && green != 255 && blue <= 100) {
                    handler.addObject(new Block(xx * 32, yy * 32, ID.BLOCK, ss, this));
                }
                if (red == 100 && green == 150 && blue == 0) {
                    if (BLACKKNIGHTFIX) {
                        handler.addObject(new BlackKnight(xx * 32, yy * 32, ID.BKNIGHT, ss, this, handler, sound));
                    } else {
                        BLACKKNIGHTFIX = true;
                    }
                }

                if (red == 100 && green == 0 && blue == 100) {
                    if (RedAlive) {
                        if (REDKNIGHTFIX) {
                            handler.addObject(new RedKnight(xx * 32, yy * 32, ID.RKNIGHT, ss, this, handler, sound));
                        } else {
                            REDKNIGHTFIX = true;
                        }
                    }
                }

                if (red == 255 && blue == 255 && green == 0) {
                    handler.addObject(new TrapDoor(xx * 32, yy * 32, ID.DOOR, ss, this, handler, sound));
                }
                if (red == 150 && green == 150 && blue == 150) {
                    if (PurpleAlive) {
                        if (PURPLEKNIGHTFIX) {
                            handler.addObject(new PurpleKnight(xx * 32, yy * 32, ID.PKNIGHT, ss, this, handler, sound));
                            PURPLEKNIGHTFIX = false;
                        } else {
                            PURPLEKNIGHTFIX = true;
                        }
                    }
                }

                if (blue == 255 && green != 255 && red != 255) {
                    handler.addObject(new Warrior(xx * 32, yy * 32, ID.PLAYER, handler, this, sound, ss));
                }
                if (green == 255 && red != 255 & blue == 0) {
                    handler.addObject(new Enemy(xx * 32, yy * 32, ID.ENEMY, handler, ss, this, sound));
                }
//                if (red == 255 && green == 255) {
//                    handler.addObject(new Crate(xx * 32, yy * 32, ID.CRATE, ss));
//                }
                if (blue == 255 && green == 255 && red == 0) {
                    IX = xx * 32;
                    IY = yy * 32;
                    handler.addObject(new Boss(xx * 32, yy * 32, ID.BOSS, ss, this, sound, handler));
                }
//                if (blue == 255 && green == 255 && red == 255) {
//                    handler.addObject(new Tutorial(xx * 32, yy * 32, ID.TUT, ss, this));
//                }
            }
        }
    }

    //Actually Start the game:
    public static void main(String args[]) throws IOException {
        new Game();
    }

    //GETTERS AND SETTERS :
    public Camera getCamera() {
        return camera;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public boolean isSPEEDY() {
        return SPEEDY;
    }

    public void setSPEEDY(boolean SPEEDY) {
        this.SPEEDY = SPEEDY;
    }

    public boolean isBEZERK() {
        return BEZERK;
    }

    public void setBEZERK(boolean BEZERK) {
        this.BEZERK = BEZERK;
    }

    public boolean isOnCooldown() {
        return onCooldown;
    }

    public void setOnCooldown(boolean onCooldown) {
        this.onCooldown = onCooldown;
    }

    public int getPvx() {
        return pvx;
    }

    public void setPvx(int pvx) {
        this.pvx = pvx;
    }

    public int getPvy() {
        return pvy;
    }

    public void setPvy(int pvy) {
        this.pvy = pvy;
    }

    public int getPx() {
        return px;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public Rectangle getPlayerBounds() {
        return new Rectangle(px, py, 48, 48);
    }

    public int getBhealth() {
        return Bhealth;
    }

    public void setBhealth(int bhealth) {
        Bhealth = bhealth;
    }

    public int getPhealth() {
        return Phealth;
    }

    public void setPhealth(int health) {
        this.Phealth = health;
    }

    public boolean isSpaceParticle() {
        return spaceParticle;
    }

    public void setSpaceParticle(boolean spaceParticle) {
        this.spaceParticle = spaceParticle;
    }

    public int getPMana() {
        return PMana;
    }

    public void setPMana(int PMana) {
        this.PMana = PMana;
    }

    public boolean isEnoughMana() {
        return enoughMana;
    }

    public void setEnoughMana(boolean enoughMana) {
        this.enoughMana = enoughMana;
    }

    public int getHpPotNum() {
        return hpPotNum;
    }

    public void setHpPotNum(int hpPotNum) {
        this.hpPotNum = hpPotNum;
    }

    public int getMpPotNum() {
        return mpPotNum;
    }

    public void setMpPotNum(int mpPotNum) {
        this.mpPotNum = mpPotNum;
    }

    public boolean isCharDead() {
        return charDead;
    }

    public void setCharDead(boolean charDead) {
        this.charDead = charDead;
    }

    public int getExitGameX() {
        return exitGameX;
    }

    public void setExitGameX(int exitGameX) {
        this.exitGameX = exitGameX;
    }

    public int getExitGameY() {
        return exitGameY;
    }

    public void setExitGameY(int exitGameY) {
        this.exitGameY = exitGameY;
    }

    public int getPlayAgainX() {
        return playAgainX;
    }

    public void setPlayAgainX(int playAgainX) {
        this.playAgainX = playAgainX;
    }

    public int getPlayAgainY() {
        return playAgainY;
    }

    public void setPlayAgainY(int playAgainY) {
        this.playAgainY = playAgainY;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public boolean isLvl3() {
        return lvl3;
    }

    public void setLvl3(boolean lvl3) {
        this.lvl3 = lvl3;
    }

    public boolean isPurpleAlive() {
        return PurpleAlive;
    }

    public void setPurpleAlive(boolean purpleAlive) {
        PurpleAlive = purpleAlive;
    }

    public boolean isRedAlive() {
        return RedAlive;
    }

    public void setRedAlive(boolean redAlive) {
        RedAlive = redAlive;
    }

    public int getPurpHP() {
        return PurpHP;
    }

    public void setPurpHP(int purpHP) {
        PurpHP = purpHP;
    }

    public int getRedHP() {
        return RedHP;
    }

    public void setRedHP(int redHP) {
        RedHP = redHP;
    }

    public int getBlackHP() {
        return BlackHP;
    }

    public void setBlackHP(int blackHP) {
        BlackHP = blackHP;
    }

    public boolean isBossDead() {
        return bossDead;
    }

    public void setBossDead(boolean bossDead) {
        this.bossDead = bossDead;
    }


    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public boolean isNewLvl() {
        return newLvl;
    }

    public void setNewLvl(boolean newLvl) {
        this.newLvl = newLvl;
    }

    public Music getMusic() {
        return music;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isStartscreen() {
        return startscreen;
    }

    public void setStartscreen(boolean startscreen) {
        this.startscreen = startscreen;
    }

    public boolean isHowtoplay() {
        return howtoplay;
    }

    public void setHowtoplay(boolean howtoplay) {
        this.howtoplay = howtoplay;
    }

    public boolean isWinscreen() {
        return winscreen;
    }

    public void setWinscreen(boolean winscreen) {
        this.winscreen = winscreen;
    }

    public boolean isLosescreen() {
        return losescreen;
    }

    public void setLosescreen(boolean losescreen) {
        this.losescreen = losescreen;
    }

    public boolean isCredits() {
        return credits;
    }

    public void setCredits(boolean credits) {
        this.credits = credits;
    }

}
