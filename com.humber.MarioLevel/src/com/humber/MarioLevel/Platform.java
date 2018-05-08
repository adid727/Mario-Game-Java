package com.humber.MarioLevel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

/**
 * Created by adid on 2016-11-30.
 */
public class Platform extends TileBody
{
    public Platform(int tileX, int tileY, Vector2 pos, World world)
    {
        super(tileX, tileY, pos, world, TileType.Platform, TagType.None);

        CreateTileBody();
    }

    public void CreateTileBody()
    {
        Vector2 position = MarioLevel.tileToWorld(tileX, tileY);
        position.add(MarioLevel.levelMap.getTileWidth()/2f, MarioLevel.levelMap.getTileHeight()/2f);

        Vec2 physicsPosition = MarioLevel.toPhysicsVector(position);

        tileBody = MarioLevel.createBoxBody(physicsPosition, MarioLevel.levelMap.getTileWidth(),
                MarioLevel.levelMap.getTileHeight(), BodyType.KINEMATIC, worldCB, this);

    }

    public void ProcessCollision(Fixture otherObject)
    {
        Mario mario = (Mario)otherObject.getBody().getUserData();

        Vector2 tileBodyPos = MarioLevel.toWorldVector(tileBody.getPosition());

        if(mario.y > tileBodyPos.getY() && mario.isJumping)
        {
            tileBody.m_fixtureList.m_isSensor = true;
        }
        else if(mario.x < tileBodyPos.getX() - GetTileSize().getX()/2f
                || mario.x > tileBodyPos.getX() + GetTileSize().getX()/2f)
        {
            tileBody.m_fixtureList.m_isSensor = false;
        }
        else if( mario.y < tileBodyPos.getY())
        {
            tileBody.m_fixtureList.m_isSensor = false;
            mario.isJumping = false;
        }
    }
}
