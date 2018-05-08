package com.humber.MarioLevel;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;

import javax.swing.text.Position;
/**
 * Created by adid on 2016-09-28.
 */
public abstract class TileAnim extends TileBody
{
    public Animation tileAnim;


    final int duration = 100;


    public TileAnim(int tileX, int tileY, Vector2 pos, World world, TileType type, TagType tag)
    {
        super(tileX, tileY, pos, world, type, tag);

        CreateTileAnim();
        CreateTileBody();
    }

    public abstract void CreateTileAnim();


    public void Update(GameContainer gc, int delta)
    {
    }

    public void render()
    {
        Vec2 temp = tileBody.getPosition();
        Position.set(temp.x, temp.y).mult(MarioLevel.PTM);
        Position.add(-MarioLevel.levelMap.getTileWidth()/2f, -MarioLevel.levelMap.getTileHeight()/2f);

        tileAnim.draw(Position.getX(), Position.getY());
    }

}