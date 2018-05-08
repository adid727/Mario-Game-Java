package com.humber.MarioLevel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
/**
 * Created by adid on 2016-11-30.
 */
public abstract class TileBody extends BaseTile
{
    public Body tileBody;

    public Vector2 Position;

    public float x;
    public float y;

    protected World worldCB;

    public TileBody(int tileX, int tileY, Vector2 pos, World world, TileType type, TagType tag)
    {
        super(tileX, tileY, type, tag);

        Position = pos;

        this.x = pos.getX();
        this.y = pos.getY();
        worldCB = world;
    }

    public abstract void CreateTileBody();
    public abstract void ProcessCollision(Fixture otherObject);

    public void finalize()
    {
        tileBody.destroyFixture(tileBody.getFixtureList());
        worldCB.destroyBody(tileBody);
    }



}
