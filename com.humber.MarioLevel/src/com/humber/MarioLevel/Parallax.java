package com.humber.MarioLevel;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.awt.*;

/**
 * Created by adid on 2016-10-05.
 */
public class Parallax
{
    ParallaxElement[] parallaxSet;
    float parallaxSpeed;
    Point pos = new Point(0,0);
    int finalElementIndex;
    int firstElementIndex;


    public class ParallaxElement
    {
        Image img;
        int x, y;
        int worldX, worldY;

        public ParallaxElement(Image img, int x, int y)
        {
            this.img = img;
            this.worldX = this.x = x;
            this.worldY = this.y = y;
        }
    }

    public Parallax(int numElements, String path, Point pos, float speed)
    {
        parallaxSet = new ParallaxElement[numElements];

        for (int i=0; i<parallaxSet.length; i++)
        {
            try
            {
                Image img = new Image(path);
                parallaxSet[i] = new ParallaxElement(img, pos.x + i*img.getWidth(), pos.y);
            }
            catch (SlickException e)
            {
                System.out.println("Exception: " + e);
            }

        }

        parallaxSpeed = speed;

        firstElementIndex = 0;
        finalElementIndex = parallaxSet.length -1;
    }

    public void ParallaxRight()
    {
        for (int i=0; i<parallaxSet.length; i++)
        {
            ParallaxElement p = parallaxSet[i];

            if(p.worldX+p.img.getWidth()<0)
            {
                p.x  = parallaxSet[finalElementIndex].x + parallaxSet[finalElementIndex].img.getWidth();
                finalElementIndex = i;

                firstElementIndex = (firstElementIndex>=parallaxSet.length-1) ? 0 : firstElementIndex+1;
            }
        }
    }

    public void ParallaxLeft()
    {
        for (int i=0; i<parallaxSet.length; i++)
        {
            ParallaxElement p = parallaxSet[i];

            if(p.worldX>GameConstants.SCREEN_BOUNDS.width)
            {
                p.x  = parallaxSet[firstElementIndex].x - p.img.getWidth();
                firstElementIndex = i;

                finalElementIndex= (finalElementIndex<=0) ? parallaxSet.length-1 : finalElementIndex-1;
            }
        }
    }



    public void draw()
    {

        for (int i=0; i<parallaxSet.length; i++)
        {
            parallaxSet[i].worldX = parallaxSet[i].x + pos.x;
            parallaxSet[i].worldY = parallaxSet[i].y + pos.y;

            parallaxSet[i].img.draw(parallaxSet[i].worldX , parallaxSet[i].worldY);
        }
    }
}