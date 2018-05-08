package com.humber.MarioLevel;

import jdk.nashorn.internal.ir.GetSplitState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by adid on 2016-09-20.
 */
public class ContentLoader {

    public static Map<String, BufferedImage> ImageLoaderMap = new HashMap<>();

   /* public ContentLoader(){

        //Terrain
        try{




            //Elements

            //Number Blocks

        }catch(IOException e){
            System.out.println("Exception caught: " + e);
        }


    }*/

    public static Dimension GetSpriteDimensions(){

        return new Dimension(ImageLoaderMap.get("0").getWidth(null), ImageLoaderMap.get("0").getHeight(null));
    }


}
