package com.humber.MarioLevel;

import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

/**
 * Created by adid on 2016-12-07.
 */
public class Goomba extends BaseEnemy {

    public Goomba(Vector2 pos, World world)
    {
        super(pos, world, EnemyType.Goomba);

        img = MarioLevel.GOOMBA_SS.getSubImage(0, 0);

        moveState = BaseEntity.MoveState.Right;

        anim.addFrame(MarioLevel.GOOMBA_SS.getSubImage(2,0), duration);
        anim.addFrame(MarioLevel.GOOMBA_SS.getSubImage(3,0), duration);

        CreateBody();

        body.m_fixtureList.m_friction = 0;
    }



    public void ProcessCollisionWithMario(Fixture otherContact)
    {
        IsAlive = true;
        super.ProcessCollisionWithMario(otherContact);
    }
}
