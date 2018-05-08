package com.humber.MarioLevel;

        import org.newdawn.slick.*;
        import org.newdawn.slick.Image;
        import org.newdawn.slick.state.BasicGameState;
        import org.newdawn.slick.state.StateBasedGame;
        import org.newdawn.slick.Graphics;

        import java.awt.*;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by adid on 2016-09-20.
 */

public class LevelReader extends BasicGameState{ // now we can draw

    public static Map<String, Image[]> ImageLoaderMap = new HashMap<>();
    ArrayList<LevelSprite> levelSpriteList = new ArrayList<>();

    class LevelSprite {
        public Animation anim;
        public int x;
        public int y;

        public LevelSprite(Animation anim, int x, int y) {
            this.anim = anim;
            this.x = x;
            this.y = y;
        }
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        // Start Number and Castle Blocks
        Image[] zeroArray = new Image[1];
        zeroArray[0] = new Image("res/Images/0.png");
        ImageLoaderMap.put("0", zeroArray);

        Image[] oneArray = new Image[1];
        oneArray[0] = new Image("res/Images/1.png");
        ImageLoaderMap.put("1", oneArray);

        Image[] twoArray = new Image[1];
        twoArray[0] = new Image("res/Images/2.png");
        ImageLoaderMap.put("2", twoArray);

        Image[] threeArray = new Image[1];
        threeArray[0] = new Image("res/Images/3.png");
        ImageLoaderMap.put("3", threeArray);

        Image[] fourArray = new Image[1];
        fourArray[0] = new Image("res/Images/4.png");
        ImageLoaderMap.put("4", fourArray);

        Image[] FiveArray = new Image[1];
        FiveArray[0] = new Image("res/Images/5.png");
        ImageLoaderMap.put("5", FiveArray);

        Image[] SixArray = new Image[1];
        SixArray[0] = new Image("res/Images/6.png");
        ImageLoaderMap.put("6", SixArray);

        Image[] SevenArray = new Image[1];
        SevenArray[0] = new Image("res/Images/7.png");
        ImageLoaderMap.put("7", SevenArray);

        Image[] EightArray = new Image[1];
        EightArray[0] = new Image("res/Images/8.png");
        ImageLoaderMap.put("8", EightArray);

        Image[] NineArray = new Image[1];
        NineArray[0] = new Image("res/Images/9.png");
        ImageLoaderMap.put("9", NineArray);

        Image[] TenArray = new Image[1];
        TenArray[0] = new Image("res/Images/!.png");
        ImageLoaderMap.put("!", TenArray);

        Image[] ElevenArray = new Image[1];
        ElevenArray[0] = new Image("res/Images/@.png");
        ImageLoaderMap.put("@", ElevenArray);

        Image[] TwelveArray = new Image[1];
        TwelveArray[0] = new Image("res/Images/#.png");
        ImageLoaderMap.put("#", TwelveArray);

        Image[] ThirteenArray = new Image[1];
        ThirteenArray[0] = new Image("res/Images/$.png");
        ImageLoaderMap.put("$", ThirteenArray);

        Image[] FourteenArray = new Image[1];
        FourteenArray[0] = new Image("res/Images/%.png");
        ImageLoaderMap.put("%", FourteenArray);

        // Sand and roads and pyramid

        Image[] FifteenArray = new Image[1];
        FifteenArray[0] = new Image("res/Images/d.png");
        ImageLoaderMap.put("d", FifteenArray);

        Image[] SixteenArray = new Image[1];
        SixteenArray[0] = new Image("res/Images/-.png");
        ImageLoaderMap.put("-", SixteenArray);

        Image[] SeventeenArray = new Image[1];
        SeventeenArray[0] = new Image("res/Images/l.png");
        ImageLoaderMap.put("l", SeventeenArray);

        Image[] EighteenArray = new Image[1];
        EighteenArray[0] = new Image("res/Images/_.png");
        ImageLoaderMap.put("_", EighteenArray);

        Image[] NineteenArray = new Image[1];
        NineteenArray[0] = new Image("res/Images/p.png");
        ImageLoaderMap.put("p", NineteenArray);

        Image[] TwentyArray = new Image[1];
        TwentyArray[0] = new Image("res/Images/x.png");
        ImageLoaderMap.put("x", TwentyArray);

        Image[] TwentyOneArray = new Image[1];
        TwentyOneArray[0] = new Image("res/Images/z.png");
        ImageLoaderMap.put("z", TwentyOneArray);

        Image[] TwentyTwoArray = new Image[1];
        TwentyTwoArray[0] = new Image("res/Images/^.png");
        ImageLoaderMap.put("^", TwentyTwoArray);




        // Tree and rock and house and pipe and gate and clock

        Image[] TwentyThreeArray = new Image[1];
        TwentyThreeArray[0] = new Image("res/Images/i.png");
        ImageLoaderMap.put("i", TwentyThreeArray);

        Image[] TwentyFourArray = new Image[1];
        TwentyFourArray[0] = new Image("res/Images/r.png");
        ImageLoaderMap.put("r", TwentyFourArray);

        Image[] TwentyFiveArray = new Image[1];
        TwentyFiveArray[0] = new Image("res/Images/h.png");
        ImageLoaderMap.put("h", TwentyFiveArray);

        Image[] TwentySixArray = new Image[1];
        TwentySixArray[0] = new Image("res/Images/w.png");
        ImageLoaderMap.put("w", TwentySixArray);

        Image[] TwentySevenArray = new Image[1];
        TwentySevenArray[0] = new Image("res/Images/g.png");
        ImageLoaderMap.put("g", TwentySevenArray);

        Image[] TwentyEightArray = new Image[1];
        TwentyEightArray[0] = new Image("res/Images/b.png");
        ImageLoaderMap.put("b", TwentyEightArray);

        // Water

        Image[] TwentyNine = new Image[1];
        TwentyNine[0] = new Image("res/Images/c.png");
        ImageLoaderMap.put("c", TwentyNine);

        Image[] Thirty = new Image[1];
        Thirty[0] = new Image("res/Images/t.png");
        ImageLoaderMap.put("t", Thirty);

        Image[] ThirtyOne = new Image[1];
        ThirtyOne[0] = new Image("res/Images/y.png");
        ImageLoaderMap.put("y", ThirtyOne);

        Image[] ThirtyTwo = new Image[1];
        ThirtyTwo[0] = new Image("res/Images/u.png");
        ImageLoaderMap.put("u", ThirtyTwo);

        Image[] ThirtyThree = new Image[1];
        ThirtyThree[0] = new Image("res/Images/e.png");
        ImageLoaderMap.put("e", ThirtyThree);

        Image[] ThirtyFour = new Image[1];
        ThirtyFour[0] = new Image("res/Images/o.png");
        ImageLoaderMap.put("o", ThirtyFour);

        Image[] ThirtyFive = new Image[1];
        ThirtyFive[0] = new Image("res/Images/v.png");
        ImageLoaderMap.put("v", ThirtyFive);

        CreateLevel();
    }

    public void update (GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        for(int i = 0; i < levelSpriteList.size(); i++){
            LevelSprite sprite = levelSpriteList.get(i);
            sprite.anim.draw(sprite.x, sprite.y, GameConstants.TILE_SIZE.x, GameConstants.TILE_SIZE.y);
        }
    }

    public int getID() {
        return 0;
    }

    public LevelReader() {

    }

    public void CreateLevel() {
        FileReader reader = null;
        try {
            reader = new FileReader(new File("res/Readers/MarioOverworld.txt"));

            int c;
            int x = 0 , y = 0;
            while ((c = reader.read()) != -1) {
                String _char = Character.toString((char) c);

                if(_char.equals("\n")) {
                    y++;
                    x = 0;
                }
                else {

                    Image[] imgArray = ImageLoaderMap.get(_char);

                    if (imgArray == null)
                        continue;

                    Animation anim = new Animation();

                    for (Image img:imgArray) {
                        anim.addFrame(img, GameConstants.ANIM_INTERVAL);
                    }

                    LevelSprite levelSprite = new LevelSprite(anim, GameConstants.TILE_SIZE.x * x, GameConstants.TILE_SIZE.y * y);

                    levelSpriteList.add(levelSprite);

                    System.out.println(_char);

                    x++;
                }
            }
        }
        catch (IOException e) {
            System.out.println("Could not read file with exception: " + e);
        }
    }
    //size of the screen
    public static Dimension getPreferredSize() {
        return new Dimension(GameConstants.TILE_SIZE.x * 13, GameConstants.TILE_SIZE.y * 8);
    }

}
