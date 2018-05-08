package com.humber.MarioLevel;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
/**
 * Created by Owner on 2016-12-06.
 */
public class BaseEnemy extends BaseEntity
{
    public enum EnemyType
    {
        Goomba,
        Koopa
    }
    public Animation anim;
    public boolean IsAlive;
    protected final int duration = 111;
    public EnemyType enemyType;
    public BaseEnemy(Vector2 pos, World world, EnemyType type)
    {
        super(pos, world, EntityType.Enemy);
        anim = new Animation();
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
        anim.draw(Position.getX(), Position.getY());
    }
}