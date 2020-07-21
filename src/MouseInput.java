import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/*
Handles mouse input for in game as well as during the menu screens
 */
public class MouseInput extends MouseAdapter {
private Handler handler;
private Camera camera;
private SpriteSheet ss;
private final Sound sound;
Game game;
public MouseInput(Handler handler, Camera camera, SpriteSheet ss, Sound sound, Game game){
    this.handler = handler;
    this.camera = camera;
    this.ss = ss;
    this.sound = sound;
    this.game = game;
}
    public void mousePressed(MouseEvent e) {
    int mx = (int) (e.getX()+camera.getX());
    int my = (int) (e.getY()+camera.getY());
    game.setShooting(true);
    if(game.isStartscreen()){
        //BEGIN
if(mx >= 650 && mx <= 809 && my >= 822 && my <= 861){
    sound.playSound("Button-Click.wav");
    game.setStartscreen(false);
}
        //EXIT GAME
if(mx >= 668 && mx <= 786 && my >= 929 && my <= 966){
    sound.playSound("Button-Click.wav");
    System.exit(1);
}
        //HOW TO PLAY
if(mx >= 1209 && mx <= 1385 && my >= 848 && my <= 945){
    sound.playSound("Button-Click.wav");
    game.setHowtoplay(true);
}
//BEGIN
if(game.isHowtoplay()){
    if(mx >= 933 && mx <= 1096 && my >= 1009 && my <= 1044){
        sound.playSound("Button-Click.wav");
        game.setHowtoplay(false);
        game.setStartscreen(false);
    }
}
    }
if(game.isLosescreen()){
    int distanceX = mx - game.getPx();
    int distanceY = my - game.getPy();
    //PLAY AGAIN
    if(distanceX >= -350 && distanceX <= -146 && distanceY >= 97 && distanceY <= 128){
        for (int i = handler.gameObjects.size()-1; i >= 0; i--) {
            GameObject tempOjbect = handler.gameObjects.get(i);
                handler.removeObject(tempOjbect);
        }
        sound.playSound("Button-Click.wav");
        game.RESET();
        game.setLosescreen(false);
    }
    //EXIT GAME
    if(distanceX >= 233 && distanceX <= 409 && distanceY >= 102 && distanceY <= 126){
        sound.playSound("Button-Click.wav");
        System.exit(0);
    }
}
if(game.isWinscreen()){
    int distanceX = mx - game.getPx();
    int distanceY = my - game.getPy();
//PLAY AGAIN
    if(distanceX >= -355 && distanceX <= -51 && distanceY >= -170 && distanceY <= -30){
        sound.playSound("Button-Click.wav");
        game.RESET();
        game.setWinscreen(false);
    }
//CREDITS
    if(distanceX >= 274 && distanceX <= 397 && distanceY >= -44 && distanceY <= -25){
        sound.playSound("Button-Click.wav");
        game.setWinscreen(false);
        game.setCredits(true);
    }
//EXIT GAME
    if(distanceX >= -47 && distanceX <= 103 && distanceY >= 324 && distanceY <= 349){
        sound.playSound("Button-Click.wav");
        System.exit(0);
    }

}
if(game.isCredits()){
    int distanceX = mx - game.getPx();
    int distanceY = my - game.getPy();
    //MAIN MENU
if(distanceX >= -52 && distanceX <= 89 && distanceY >= 247 && distanceY <= 270){
    sound.playSound("Button-Click.wav");
    game.RESET();
    game.setCredits(false);
    game.setStartscreen(true);
}
//EXIT GAME
if(distanceX >= -50 && distanceX <= 89 && distanceY >= 294 && distanceY <= 316){
    sound.playSound("Button-Click.wav");
    System.exit(0);
}

}



}




    public void mouseReleased(MouseEvent e){
        game.setShooting(false);
//        System.out.println("released");
    }


}
