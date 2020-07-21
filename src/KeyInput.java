import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyInput extends KeyAdapter {
        Handler handler;
        private final Game game;
        private final Sound sound;
        public KeyInput(Handler handler, Game game, Sound sound){
            this.handler = handler;
            this.game = game;
            this.sound = sound;
        }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(!game.isStartscreen()) {
            if (key == KeyEvent.VK_P) {
                if (game.isPaused()) {
                    game.setPaused(false);
                } else {
                    game.setPaused(true);
                }
            }
            if (key == KeyEvent.VK_W) {
                handler.setUp(true);
            }
            if (key == KeyEvent.VK_S) handler.setDown(true);
            if (key == KeyEvent.VK_A) handler.setLeft(true);
            if (key == KeyEvent.VK_D) handler.setRight(true);
            if (key == KeyEvent.VK_N) handler.setReset(true);
            if (key == KeyEvent.VK_SPACE) {
                if (!game.isOnCooldown() && game.isEnoughMana()) {
                    game.setSpaceParticle(true);
                    game.setBEZERK(true);
                    game.setSPEEDY(true);
                    game.setOnCooldown(true);
                    game.setPMana(game.getPMana() - 65);
                } else {
                    sound.playSound("No_mana.wav");
                }
            }
            if (key == KeyEvent.VK_F) {
                if (game.getHpPotNum() > 0) {
                    game.setHpPotNum(game.getHpPotNum() - 1);
                    game.useHPPot();
                    sound.playSound("Use-Potion.wav");
                } else {
                    sound.playSound("Error.wav");
                }
            }
            if (key == KeyEvent.VK_V) {
                if (game.getMpPotNum() > 0) {
                    game.setMpPotNum(game.getMpPotNum() - 1);
                    game.useMPPot();
                    sound.playSound("Use-Potion.wav");
                } else {
                    sound.playSound("Error.wav");
                }
            }
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_W) handler.setUp(false);
                if(key == KeyEvent.VK_S) handler.setDown(false);
                if(key == KeyEvent.VK_A) handler.setLeft(false);
                if(key == KeyEvent.VK_D) handler.setRight(false);
                if(key == KeyEvent.VK_N)  handler.setReset(false);
        if(key == KeyEvent.VK_SPACE) {
            game.setSpaceParticle(false);
        }
        }
}
