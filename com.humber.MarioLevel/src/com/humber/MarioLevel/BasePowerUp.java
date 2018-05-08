package com.humber.MarioLevel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

/**
 * Created by adid on 2016-11-30.
 */
public class BasePowerUp extends BaseEntity
{


    public enum PowerUpType
    {
        Mushroom,
        Fireflower
    }

    public PowerUpType powerUpType;

    public boolean IsCollected = false;



    public BasePowerUp(Vector2 pos, World world, PowerUpType type)
    {
        super(pos, world, EntityType.PowerUp);
    }

    public void CreateBody()
    {
        body = MarioLevel.createCircleBody(MarioLevel.toPhysicsVector(Position), img.getWidth()/2.5f, BodyType.DYNAMIC, worldCB,
                false, this);
    }


    public void update(float delta)
    {
        super.update(delta);
    }

    public void ProcessCollisionWithTile(Fixture otherContact)
    {
        super.ProcessCollisionWithTile(otherContact);
    }

    public void ProcessCollisionWithMario(Fixture otherContact)
    {
        super.ProcessCollisionWithMario(otherContact);
    }

    public void render()
    {
        Vec2 temp = body.getPosition();
        Position.set(temp.x, temp.y).mult(MarioLevel.PTM);
        Position.add(-img.getWidth()/2f, -img.getHeight()/2f);

        img.draw(Position.getX(), Position.getY());
    }

}