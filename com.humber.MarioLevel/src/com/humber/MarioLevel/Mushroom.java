package com.humber.MarioLevel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

/**
 * Created by adid on 2016-11-30.
 */
public class Mushroom extends BasePowerUp
{
    public Mushroom(Vector2 pos, World world)
    {
        super(pos, world, PowerUpType.Mushroom);

        img = MarioLevel.POWERUP_SS.getSubImage(0, 0);

        moveState = MoveState.Right;

        CreateBody();

        body.m_fixtureList.m_friction = 0;
    }

    public void ProcessCollisionWithMario(Fixture otherContact)
    {
        IsCollected = true;
        super.ProcessCollisionWithMario(otherContact);
    }
}