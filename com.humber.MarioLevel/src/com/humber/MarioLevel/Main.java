package com.humber.MarioLevel;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by adid on 2016-09-13.
 */

public class Main extends StateBasedGame{
    public static final int playstate = 0;

    public static void main(String[] args) {

        try {

            AppGameContainer appgc = new AppGameContainer(new Main("Super Mario Bros 3"));

            appgc.setDisplayMode(GameConstants.SCREEN_BOUNDS.width, GameConstants.SCREEN_BOUNDS.height, false);

            appgc.setDisplayMode(LevelReader.getPreferredSize().width, LevelReader.getPreferredSize().height, false);
            appgc.setTargetFrameRate(30);
            appgc.setShowFPS(false);

            appgc.start();

        } catch (SlickException ex) {
            System.out.println("Slick2D Error");
        }


        /*
        JFrame jframe = new JFrame("Mario Level");
        jframe.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                super.windowClosing(e);
            }
        });

        ImageLoader gameView = new ImageLoader();
        gameView.LoadImage(null);
        jframe.add(gameView);

        jframe.add(levelReader);
        jframe.pack();
        jframe.setVisible(true);

        ContentLoader contentLoader = new ContentLoader();
        LevelReader levelReader = new LevelReader();

        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();

            while(!Display.isCloseRequested()) {
                Display.update();
            }

            Display.destroy();
        } catch(LWJGLException e) {
            e.printStackTrace();
        }
        */
    }

    public Main(String gameName) {
        super (gameName);
        //this.addState(new LevelReader());
        this.addState(new MarioLevel());
    }

    public void initStatesList (GameContainer gc) throws SlickException {
        this.enterState(playstate);
    }
}
