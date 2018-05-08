package com.humber.MarioLevel;

import java.awt.*;
/**
 * Created by adid on 2016-09-28.
 */
public class GameConstants
{
    public static Point TILE_SIZE = new Point(60, 60);
    public static int ANIM_INTERVAL = 500;
    public static Dimension SCREEN_BOUNDS = new Dimension(800, 430);

    public static Dimension PowerUpSize = new Dimension(18,18);
    public static Dimension GoombaSize = new Dimension(16, 16);
    public static void DebugNotify()
    {
        System.out.println("***********DEBUG**************");
    }

    public static void DebugNotify1()
    {
        System.out.println("@@@");
    }

}