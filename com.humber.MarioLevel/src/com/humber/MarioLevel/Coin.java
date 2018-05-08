package com.humber.MarioLevel;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.lwjgl.Sys;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

    /**
     * Created by Adid on 11/1/2016.
     */
    public class Coin extends TileAnim {

    public boolean isCollected = false;

    public Coin(int tileX, int tileY, Vector2 pos, World world) {

        super(tileX, tileY, pos, world, TileType.Coin, TagType.None); //Base class constructor -> for inheriting the base class

    }

    public void CreateTileAnim() {

        tileAnim = new Animation();

        try {
            tileAnim.addFrame(new Image("res/images/coin/SMB3-Tiles2a_371.png"), duration);
            tileAnim.addFrame(new Image("res/images/coin/SMB3-Tiles2a_372.png"), duration);
            tileAnim.addFrame(new Image("res/images/coin/SMB3-Tiles2a_373.png"), duration);
            tileAnim.addFrame(new Image("res/images/coin/SMB3-Tiles2a_374.png"), duration);
        }
        catch (SlickException e) {
            System.out.println("Exception Loading Coin: " + e.toString());
        }
    }

    public void CreateTileBody() {
        tileBody = MarioLevel.createCircleBody(MarioLevel.toPhysicsVector(new Vector2(x, y)), tileAnim.getWidth()/2f, BodyType.KINEMATIC, worldCB, true, this);
    }

    public void ProcessCollision(Fixture otherObject) {
        isCollected = true;
    }

}