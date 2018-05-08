package com.humber.MarioLevel;

import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by adid on 2016-11-30.
 */
public class QBlock extends TileAnim
{
    public boolean IsHit = false;

    public QBlock(int tileX, int tileY, Vector2 pos, World world) {
        super(tileX, tileY, pos, world, TileType.QBlock, TagType.None);
    }

    public void CreateTileAnim() {
        tileAnim = new Animation();

        try {
            tileAnim.addFrame(new Image("res/Images/QBlock/SMB3-Tiles2a_330.png"), duration);
            tileAnim.addFrame(new Image("res/Images/QBlock/SMB3-Tiles2a_331.png"), duration);
            tileAnim.addFrame(new Image("res/Images/QBlock/SMB3-Tiles2a_332.png"), duration);
            tileAnim.addFrame(new Image("res/Images/QBlock/SMB3-Tiles2a_333.png"), duration);
        } catch (SlickException e) {
            System.out.println("Exception Loading Coin: " + e.toString());
        }
    }

    public void CreateTileBody() {
        tileBody = MarioLevel.createBoxBody(MarioLevel.toPhysicsVector(new Vector2(x, y)),
                GetTileSize().getX(), GetTileSize().getY(), BodyType.KINEMATIC, worldCB, this);

    }

    public void finalize()
    {
        int layerIndex = MarioLevel.levelMap.getLayerIndex("Tile Layer 1");
        MarioLevel.levelMap.setTileId(tileX, tileY, layerIndex, 123);  //erase the placeholder LevelTile behind it

        super.finalize();
    }

    public void ProcessCollision(Fixture otherObject)
    {
        Mario mario = (Mario)otherObject.getBody().getUserData();

        Vector2 tileBodyPos = MarioLevel.toWorldVector(tileBody.getPosition());

        if(mario.y > tileBodyPos.getY() && mario.isJumping)
        {
            IsHit = true;
        }
        else if(mario.x < tileBodyPos.getX() - GetTileSize().getX()/2f
                || mario.x > tileBodyPos.getX() + GetTileSize().getX()/2f)
        {
        }
        else if( mario.y < tileBodyPos.getY())
        {
            mario.isJumping = false;
        }
    }
}