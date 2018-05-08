package com.humber.MarioLevel;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.*;

public class Mario
{
    public static  final float JUMP_FORCE = 20;
    private SpriteSheet marioSS;

    public Integer coinsCollected = 0;

    private Animation marioIdleLeft;
    private Animation marioIdleRight;
    private Animation marioWalkRight;
    private Animation marioWalkLeft;
    private Animation marioDuckLeft;
    private Animation marioDuckRight;

    public boolean isJumping = false;
    public boolean isDucking = false;
    public boolean isMoving = false;

    Body body;
    public Vector2 spawnPoint;
    public Vector2 Position = new Vector2();

    public float MoveForce = 2500;

    public enum MoveState
    {
        Left,
        Right
    }
    public static MoveState moveState = MoveState.Right;

    public enum AnimationState
    {
        IDLE,
        WALK,
        RUN,
        JUMP,
        CROUCH,
        FLY,
        DEAD,
    }

    public AnimationState animState = AnimationState.IDLE;

    private final int duration = 111;

    public float x, y = 0;

    public double movementSpeed = 0.1;

    World worldCB;

    public Mario(World world)
    {
        worldCB = world;

        try
        {
            marioSS = new SpriteSheet("res/SpriteSheets/SMB3-Mario-SS.png", 20, 28, 10);

            marioIdleLeft = new Animation();
            marioIdleRight = new Animation();
            marioWalkRight = new Animation();
            marioWalkLeft = new Animation();
            marioDuckRight = new Animation();
            marioDuckLeft = new Animation();

            for(int i=0; i<3; i++)
            {
                marioWalkRight.addFrame( marioSS.getSubImage(8+i, 0), duration);
                marioWalkLeft.addFrame( marioSS.getSubImage(8+i, 0).getFlippedCopy(true, false), duration);
            }

            marioIdleRight.addFrame( marioSS.getSubImage(8,0), duration);
            marioIdleLeft.addFrame( marioSS.getSubImage(8,0).getFlippedCopy(true, false), duration);

            marioDuckRight.addFrame( marioSS.getSubImage(14,2), duration);
            marioDuckLeft.addFrame( marioSS.getSubImage(14,2).getFlippedCopy(true, false), duration);

            float marioRadius = getSize().getX() / 2f;
            spawnPoint = MarioLevel.tileMapToWorld(50,388);
            body = MarioLevel.createCircleBody(MarioLevel.toPhysicsVector(spawnPoint), marioRadius, BodyType.DYNAMIC, world, false, this);

            setX(body.getPosition().x - (getSize().getX() / 2));
            setY(body.getPosition().y - (getSize().getY() / 2));


        }
        catch (SlickException e)
        {
            System.out.println("Exception: " + e);
        }
    }

    public void Update(int delta)
    {
        if(body.m_linearVelocity.y < -15)
            isJumping = true;
    }
    public void setX(float x)
    {
        this.x = x;
    }
    public void setY(float y)
    {
        this.y = y;
    }
    public Vector2 getSize()
    {
        Image currentFrame = null;
        switch (animState) {
            case IDLE:
                currentFrame = marioIdleRight.getCurrentFrame();
                break;
            case WALK:
                currentFrame = marioWalkRight.getCurrentFrame();
                break;
            case CROUCH:
                currentFrame = marioDuckRight.getCurrentFrame();
                break;
        }
        return new Vector2( currentFrame.getWidth(), currentFrame.getHeight());
    }
    public void draw()
    {
        switch (animState)
        {
            case IDLE:
                if(moveState==MoveState.Right)
                    marioIdleRight.draw(x, y, marioIdleRight.getWidth(), marioIdleRight.getHeight());
                else if(moveState == MoveState.Left)
                    marioIdleLeft.draw(x, y, marioIdleLeft.getWidth(), marioIdleLeft.getHeight());
                break;
            case WALK:
                if(moveState == MoveState.Right)
                    marioWalkRight.draw(x, y, marioWalkRight.getWidth(), marioWalkRight.getHeight());
                else if(moveState == MoveState.Left)
                    marioWalkLeft.draw(x, y, marioWalkLeft.getWidth(), marioWalkLeft.getHeight());
                break;
            case RUN:
                break;
            case JUMP:
                break;
            case CROUCH:
                if(moveState == MoveState.Right)
                    marioDuckRight.draw(x, y, marioDuckRight.getWidth(), marioDuckRight.getHeight());
                else if(moveState == MoveState.Left)
                    marioDuckLeft.draw(x, y, marioDuckLeft.getWidth(), marioDuckLeft.getHeight());
                break;
            case FLY:
                break;
            case DEAD:
                break;
            default:
        }
    }
}