package com.humber.MarioLevel;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Image;

/**
 * Created by adid on 2016-11-30.
 */

public abstract class BaseEntity
{
    public enum EntityType
    {
        PowerUp,
        Enemy
    }
    public EntityType entityType;
    Vector2 Position;
    Body body;
    World worldCB;
    public BaseEntity(Vector2 pos, World world, EntityType type)
    {
        Position = pos;
        entityType = type;
        worldCB = world;
    }
    public enum MoveState
    {
        Left,
        Right,
        Idle
    }
    protected Image img;
    public MoveState moveState = MoveState.Idle;
    public abstract void CreateBody();
    public void ProcessCollisionWithTile(Fixture otherContact)
    {
        BaseTile baseTile = (BaseTile)otherContact.getBody().getUserData();
        if(baseTile.tileType.equals(BaseTile.TileType.LeftSlant))
        {
            if(moveState == MoveState.Right)
            {
                body.m_fixtureList.m_friction = 0;
                body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -1f));
            }
            else if(moveState == MoveState.Left)
            {
                body.m_fixtureList.m_friction = 10;
                body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -1f));
            }
        }
        else if(baseTile.tileType.equals(BaseTile.TileType.RightSlant))
        {
            if(moveState == MoveState.Right)
            {
                body.m_fixtureList.m_friction = 10;
                body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -1f));
            }
            else if(moveState == MoveState.Left)
            {
                body.m_fixtureList.m_friction = 0;
                body.setLinearVelocity(new Vec2(body.getLinearVelocity().x, -1f));
            }
        }
        else if(baseTile.tileType.equals(BaseTile.TileType.Ground))
        {
            body.m_fixtureList.m_friction = 0;
            Vector2 otherPos = MarioLevel.toWorldVector(otherContact.getBody().getPosition());
            //collided from the Left side
            if(Position.getX() < otherPos.getX()
                    &&  Position.getY()+img.getHeight()/3f > otherPos.getY()-BaseTile.GetTileSize().getY()/2f
                    &&  Position.getY()+img.getHeight()/3f < otherPos.getY()+BaseTile.GetTileSize().getY()/2f )
            {
                moveState = MoveState.Left;
            }
            else  if(Position.getX() > otherPos.getX()
                    &&  Position.getY()+img.getHeight()/3f > otherPos.getY()-BaseTile.GetTileSize().getY()/2f
                    &&  Position.getY()+img.getHeight()/3f < otherPos.getY()+BaseTile.GetTileSize().getY()/2f )
            {
                moveState = MoveState.Right;
            }
        }
        System.out.println("collided with Tile");
    }
    public void ProcessCollisionWithMario(Fixture otherContact)
    {
    }
    public void finalize()
    {
        body.destroyFixture(body.getFixtureList());
        worldCB.destroyBody(body);
    }
    public void update(float delta)
    {
        Vector2 worldPos = MarioLevel.toWorldVector(body.getPosition());
        Vector2 tileMapPos =  MarioLevel.WorldToTileMap(worldPos);
        if(!MarioLevel.PARALLAXING)
        {
            if (moveState == BasePowerUp.MoveState.Right)
            {
                tileMapPos.setX(tileMapPos.getX() + 3);
                Vector2 WorldPos = MarioLevel.tileMapToWorld(tileMapPos.getX(), tileMapPos.getY());
                Vec2 physicsPos = MarioLevel.toPhysicsVector(WorldPos);
                body.setTransform(physicsPos, 0f);
            } else if (moveState == BasePowerUp.MoveState.Left)
            {
                tileMapPos.setX(tileMapPos.getX() - 3);
                Vector2 WorldPos = MarioLevel.tileMapToWorld(tileMapPos.getX(), tileMapPos.getY());
                Vec2 physicsPos = MarioLevel.toPhysicsVector(WorldPos);
                body.setTransform(physicsPos, 0f);
            }
        }
        else if(MarioLevel.PARALLAXING)
        {
            if(moveState == BasePowerUp.MoveState.Right)
            {
                if(Mario.moveState == Mario.MoveState.Right)
                    tileMapPos.setX(tileMapPos.getX()-3);
                else if(Mario.moveState == Mario.MoveState.Left)
                    tileMapPos.setX(tileMapPos.getX()+9);
                Vector2 WorldPos = MarioLevel.tileMapToWorld(tileMapPos.getX(), tileMapPos.getY());
                Vec2 physicsPos = MarioLevel.toPhysicsVector(WorldPos);
                body.setTransform(physicsPos, 0f);
            }
            else if (moveState == BasePowerUp.MoveState.Left)
            {
                if(Mario.moveState == Mario.MoveState.Left)
                    tileMapPos.setX(tileMapPos.getX()+3);
                else if(Mario.moveState == Mario.MoveState.Right)
                    tileMapPos.setX(tileMapPos.getX()-9);
                Vector2 WorldPos = MarioLevel.tileMapToWorld(tileMapPos.getX(), tileMapPos.getY());
                Vec2 physicsPos = MarioLevel.toPhysicsVector(WorldPos);
                body.setTransform(physicsPos, 0f);
            }
        }
        body.applyForce(new Vec2(0, 0), new Vec2());
    }
}