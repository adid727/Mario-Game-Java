package com.humber.MarioLevel;

import javafx.scene.Camera;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.*;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tests.SpriteSheetFontTest;
import org.newdawn.slick.tiled.TiledMap;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;


import java.awt.*;
import java.sql.*;
import java.util.ArrayList;



/**
 * Created by Adid on 10/4/2016.
 */
public class MarioLevel extends BasicGameState implements ContactListener, KeyListener
{
    public static final float PTM = 10.0f;

    private float timeStep = 1.0f / 60.0f;
    private float timeStepMillis = timeStep * 1000.0f;
    private int velocityIterations = 6;
    private int positionIterations = 2;
    private float timeMultiplier = 5;
    private float timeAccumulator = 0;



    Font font;

    Camera camera;
    Vector2 cameraPosition = new Vector2(0, 0);

    public static TiledMap levelMap;
    World world;

    Mario mario;


    Input input;

    static int levelX = 0, levelY = 14;

    public static SpriteSheet POWERUP_SS;
    public static SpriteSheet GOOMBA_SS;
    public static SpriteSheet KOOPA_SS;

    Parallax pBackGround;
    Parallax pClouds;

    public static boolean PARALLAXING = false;

    ArrayList<Coin> coinList = new ArrayList<>();
    ArrayList<QBlock> QBlockList = new ArrayList<>();
    ArrayList<BasePowerUp> PowerUpList = new ArrayList<>();
    ArrayList<BaseEnemy> EnemyList = new ArrayList<>();

    //
    private Connection con = null;
    private PreparedStatement pst = null;

    private final String url = "jdbc:mysql://localhost:3306/marioworld?allowMultiQueries=true";
    private final String user =  "root";
    private final String password = "";
    private final String firstName = "Adid";
    private final String lastName = "Nissan";


    //

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        levelMap = new TiledMap("res/TMX/SMB3Level1-1.tmx");
        world = new World(new Vec2(0, 9.81f), true);
        world.setContactListener(this);

        font = new SpriteSheetFont(new SpriteSheet(new Image("res/Font/MarioFont.png"), 15, 15), ' ');
        try
        {
            //POWERUP_SS = new SpriteSheet("res/SpriteSheets/PowerUp-Spritesheet.png", 18, 18, 0);///////////////
            POWERUP_SS = new SpriteSheet("res/SpriteSheets/PowerUp-Spritesheet.png", 18, 18, 0);
            GOOMBA_SS = new SpriteSheet("res/SpriteSheets/Goomba-Spritesheet.png", 17, 16);
            KOOPA_SS = new SpriteSheet("res/SpriteSheets/Koopa-Spritesheet.png", 17, 27);
        }
        catch (SlickException e)
        {
            System.out.println("Failed To Load PowerUp SpriteSheet: " + e);//////////////////
        }

        int layerIndex = levelMap.getLayerIndex("Tile Layer 1");
        for (int tileX = 0; tileX < levelMap.getWidth(); tileX++)
        {
            for (int tileY = 0; tileY < levelMap.getHeight(); tileY++)
            {
                int tileID = levelMap.getTileId(tileX, tileY, layerIndex);
                String type = levelMap.getTileProperty(tileID, "type", null);
                Vector2 position = tileToWorld(tileX, tileY);
                Vec2 physicsPosition;
                Vec2[] points = new Vec2[2];

                if(type!=null)
                {
                    switch (type) {
                        case "ground":
                            position.add(levelMap.getTileWidth() / 2, levelMap.getTileHeight() / 2);
                            physicsPosition = toPhysicsVector(position);
                            createBoxBody(physicsPosition, levelMap.getTileWidth(), levelMap.getTileHeight(), BodyType.STATIC, world, new BaseTile(tileX, tileY, BaseTile.TileType.Ground, BaseTile.TagType.None));
                            break;
                        case "lowSlope":
                            physicsPosition = toPhysicsVector(position);
                            points[0] = new Vec2(0 / PTM, 15 / PTM);
                            points[1] = new Vec2(15 / PTM, 7 / PTM);
                            createPolygonBody(physicsPosition, points, BodyType.STATIC, world, new BaseTile(tileX, tileY, BaseTile.TileType.LeftSlant, BaseTile.TagType.LowSlant));
                            break;
                        case "midSlope":
                            physicsPosition = toPhysicsVector(position);
                            points[0] = new Vec2(0 / PTM, 7 / PTM);
                            points[1] = new Vec2(15 / PTM, 0 / PTM);
                            createPolygonBody(physicsPosition, points, BodyType.STATIC, world, new BaseTile(tileX, tileY, BaseTile.TileType.LeftSlant, BaseTile.TagType.MidSlant));
                            break;
                        case "highSlope":
                            physicsPosition = toPhysicsVector(position);
                            points[0] = new Vec2(0 / PTM, 15 / PTM);
                            points[1] = new Vec2(15 / PTM, 0 / PTM);
                            createPolygonBody(physicsPosition, points, BodyType.STATIC, world, new BaseTile(tileX, tileY, BaseTile.TileType.LeftSlant, BaseTile.TagType.HighSlant));
                            break;
                        case "platform":
                            new Platform(tileX, tileY, position, world);
                            break;
                        case "coin":
                            position.add(levelMap.getTileWidth() / 2, levelMap.getTileHeight() / 2);
                            coinList.add(new Coin(tileX, tileY, position, world));
                            levelMap.setTileId(tileX, tileY, layerIndex, 657);
                            break;
                        case "QBlock":
                            position.add(levelMap.getTileWidth()/2, levelMap.getTileHeight()/2);
                            QBlockList.add(new QBlock(tileX, tileY, position, world));
                            levelMap.setTileId(tileX, tileY, layerIndex, 657);
                            break;
                        case "mushroom":
                            position.add(GameConstants.PowerUpSize.width/2f, GameConstants.PowerUpSize.height/2f);
                            PowerUpList.add(new Mushroom(position, world));
                            levelMap.setTileId(tileX, tileY, layerIndex, 657);
                            break;
                        case "goomba":
                            position.add(GameConstants.GoombaSize.width/2f, GameConstants.GoombaSize.height/2f);
                            EnemyList.add(new Goomba(position, world));
                            levelMap.setTileId(tileX, tileY, layerIndex, 657);
                            break;
                    }
                }

            }
        }

        pBackGround = new Parallax(3, "res\\Parallax\\SMB3-Background.png", new Point(0, 260), 0.5f);
        pClouds = new Parallax(5, "res\\Parallax\\SMB3-Clouds.png", new Point(0,0), 2);

        mario = new Mario(world);

        input = gc.getInput();
        input.addKeyListener(this);

        // camera = new Camera(levelMap, gc);
    }

    @Override
    public void keyPressed(int key, char c)
    {
        if (!mario.isJumping && (key == Input.KEY_UP || key == Input.KEY_W))
        {
            mario.body.applyLinearImpulse(new Vec2(0, -Mario.JUMP_FORCE), new Vec2());
            mario.isJumping = true;
        }
    }

    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object dataA = fixtureA.getBody().getUserData();
        Object dataB = fixtureB.getBody().getUserData();

        Fixture marioFixture = null;
        Fixture tileBodyFixture = null;

        if(dataA!=null && dataA.getClass().equals(Mario.class))
            marioFixture = fixtureA;
        else if(dataB!=null && dataB.getClass().equals(Mario.class) )
            marioFixture = fixtureB;

        if(dataA!=null && TileBody.class.isAssignableFrom(dataA.getClass()))
            tileBodyFixture = fixtureA;
        else if(dataB!=null && TileBody.class.isAssignableFrom(dataB.getClass()) )
            tileBodyFixture = fixtureB;


        if(dataA!=null && dataA.getClass().equals(BaseTile.class)
                && dataB!=null && dataB.getClass().equals(Mario.class))
        {
            mario.isJumping = false;
        }

        if(dataB!=null && dataB.getClass().equals(BaseTile.class)
                && dataA!=null && dataA.getClass().equals(Mario.class))
        {
            mario.isJumping = false;
        }

        /*** COIN COLLISION ***/
        if(dataA!=null && dataA.getClass().equals(Coin.class))
        {
            Coin coin = (Coin)dataA;
            coin.ProcessCollision(fixtureB);
        }

        if(dataB!=null && dataB.getClass().equals(Coin.class))
        {
            Coin coin = (Coin)dataB;
            coin.ProcessCollision(fixtureA);
        }

        /***PLATFORM COLLISION***/
        if(dataA!=null && dataA.getClass().equals(Platform.class)
                && dataB!=null && dataB.getClass().equals(Mario.class))
        {
            Platform platform = (Platform)dataA;
            platform.ProcessCollision(fixtureB);
        }

        if(dataB!=null && dataB.getClass().equals(Platform.class)
                && dataA!=null && dataA.getClass().equals(Mario.class))
        {
            Platform platform = (Platform)dataB;
            platform.ProcessCollision(fixtureA);
        }

        /*** QBLOCK COLLISION ***/
        if(tileBodyFixture!=null && marioFixture!=null)
        {
            BaseTile baseTile = (BaseTile)tileBodyFixture.getBody().getUserData();

            if(baseTile.tileType.equals(BaseTile.TileType.QBlock))
            {
                QBlock qBlock = (QBlock)baseTile;
                qBlock.ProcessCollision(marioFixture);
            }
        }






        // System.out.println("collision with " + contact.getFixtureA().m_userData);
        //FixtureDef bodyA = var1.getFixtureA();
        // bodyA.userData
    }

    public void endContact(Contact var1)
    {

    }

    public void preSolve(Contact var1, Manifold var2)
    {
        Fixture fixtureA = var1.getFixtureA();
        Fixture fixtureB = var1.getFixtureB();
        Object dataA = fixtureA.getBody().getUserData();
        Object dataB = fixtureB.getBody().getUserData();

        Fixture marioFixture = null;
        Fixture tileBodyFixture = null;
        Fixture baseEntityFixture = null;

        if(dataA!=null && dataA.getClass().equals(Mario.class))
            marioFixture = fixtureA;
        else if(dataB!=null && dataB.getClass().equals(Mario.class) )
            marioFixture = fixtureB;

        if(dataA!=null && dataA instanceof BaseTile)
            tileBodyFixture = fixtureA;
        else if(dataB!=null && dataB instanceof BaseTile )
            tileBodyFixture = fixtureB;

        if(dataA!=null && BaseEntity.class.isAssignableFrom(dataA.getClass()))
            baseEntityFixture = fixtureA;
        else if(dataB!=null && BaseEntity.class.isAssignableFrom(dataB.getClass()) )
            baseEntityFixture = fixtureB;


        /**ENTITY COLLISION CHECK**/
        if(marioFixture!=null && baseEntityFixture!=null)
        {
            BaseEntity entity = (BaseEntity)baseEntityFixture.getBody().getUserData();
            entity.ProcessCollisionWithMario(marioFixture);
        }

        if(tileBodyFixture!=null && baseEntityFixture!=null)
        {
            BaseEntity entity = (BaseEntity)baseEntityFixture.getBody().getUserData();
            entity.ProcessCollisionWithTile(tileBodyFixture);
        }


        /**PREVIOUS LOGIC< NEEDS CLEANING**/
        if(dataA!=null && dataA.getClass().equals(BaseTile.class) &&
                dataB!=null && dataB.getClass().equals(Mario.class))
        {
            BaseTile tile = (BaseTile)dataA;

            if(tile.tileType.equals(BaseTile.TileType.LeftSlant))
            {
                if(mario.animState == Mario.AnimationState.CROUCH)
                    fixtureA.m_friction = 0;
                else if(!mario.isMoving)
                    fixtureA.m_friction = 10;
                else
                {
                    if (mario.moveState == Mario.MoveState.Right)
                    {
                        fixtureA.m_friction = 0;

                        if(PARALLAXING)
                            mario.body.applyForce( toPhysicsVector(new Vec2(50, 0)), new Vec2());


                        if(tile.tag.equals(BaseTile.TagType.HighSlant))
                            mario.body.setLinearVelocity(new Vec2(0, -5f));

                    }
                    else if (mario.moveState == Mario.MoveState.Left)
                    {
                        fixtureA.m_friction = 10;
                    }
                }
            }
            else if(tile.tileType.equals(BaseTile.TileType.RightSlant))
            {
                if(mario.animState == Mario.AnimationState.CROUCH)
                {
                    fixtureA.m_friction = 0;
                }
                else if(!mario.isMoving || PARALLAXING)
                    fixtureA.m_friction = 10;
                else
                {
                    if (mario.moveState == Mario.MoveState.Right)
                    {
                        fixtureA.m_friction = 10;
                    }
                    else if (mario.moveState == Mario.MoveState.Left)
                    {
                        fixtureA.m_friction = 0;

                        if(tile.tag.equals(BaseTile.TagType.HighSlant))
                            mario.body.setLinearVelocity(new Vec2(0, -5f));

                    }
                }
            }


        }

        if(dataB!=null && dataB.getClass().equals(BaseTile.class) &&
                dataA!=null && dataA.getClass().equals(Mario.class))
        {
            BaseTile tile = (BaseTile)dataB;

            if(tile.tileType.equals(BaseTile.TileType.LeftSlant))
            {
                if(mario.animState == Mario.AnimationState.CROUCH)
                {
                    fixtureB.m_friction = 0;
                }
                else if(!mario.isMoving)
                    fixtureB.m_friction = 10;
                else
                {
                    if (mario.moveState == Mario.MoveState.Right)
                    {
                        fixtureB.m_friction = 0;

                        if(PARALLAXING)
                            mario.body.applyForce( toPhysicsVector(new Vec2(50, 0)), new Vec2());


                        if(tile.tag.equals(BaseTile.TagType.HighSlant))
                            mario.body.setLinearVelocity(new Vec2(0, -5f));
                    }
                    else if (mario.moveState == Mario.MoveState.Left)
                    {
                        fixtureB.m_friction = 10;
                    }
                }
            }
            else if(tile.tileType.equals(BaseTile.TileType.RightSlant))
            {
                if(mario.animState == Mario.AnimationState.CROUCH)
                {
                    fixtureB.m_friction = 0;
                }
                else if(!mario.isMoving || PARALLAXING)
                    fixtureB.m_friction = 10;
                else
                {
                    if (mario.moveState == Mario.MoveState.Right) {
                        fixtureB.m_friction = 10;
                    } else if (mario.moveState == Mario.MoveState.Left)
                    {
                        fixtureB.m_friction = 0;

                        if(tile.tag.equals(BaseTile.TagType.HighSlant))
                            mario.body.setLinearVelocity(new Vec2(0, -5f));

                    }
                }
            }
        }
    }

    public void postSolve(Contact var1, ContactImpulse var2)
    {

    }

    /**UTILITY METHODS**/
    public static Vector2 tileMapToWorld(float x, float y)
    {
        return new Vector2(levelX + x, levelY + y);
    }

    public static Vector2 WorldToTileMap(Vector2 pos)
    {
        return new Vector2(pos.getX() - levelX, pos.getY() - levelY);
    }

    public static Vector2 tileToWorld(int tileX, int tileY)
    {
        return new Vector2(levelX + levelMap.getTileWidth() * tileX, levelY + levelMap.getTileHeight() * tileY);
    }


    public static Body createCircleBody(Vec2 pos, float radius, BodyType type, World world, boolean isSensor, Object userData)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position = pos;
        bodyDef.type = type;
        bodyDef.linearDamping = 0.5f;

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = radius / PTM;
        fixtureDef.shape = circleShape;
        fixtureDef.isSensor = isSensor;


        Body body = world.createBody(bodyDef);

        if(userData!=null)
            body.setUserData(userData);

        body.createFixture(fixtureDef);

        return body;
    }

    public static Body createBoxBody(Vec2 pos, float width, float height, BodyType type, World world, Object userdata)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position = pos;
        bodyDef.type = type;
        bodyDef.linearDamping = 0.5f;

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsBox(width * 0.5f / PTM, height * 0.5f / PTM);
        fixtureDef.shape = polyShape;

        Body body = world.createBody(bodyDef);

        if (userdata != null)
            body.setUserData(userdata);

        body.createFixture(fixtureDef);

        return body;
    }

    public static Body createPolygonBody(Vec2 pos, Vec2[] points, BodyType type, World world, Object userdata)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position = pos;
        bodyDef.type = type;
        bodyDef.linearDamping = 0.5f;

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polyShape = new PolygonShape();
        polyShape.setAsEdge(points[0], points[1]);
        fixtureDef.shape = polyShape;

        Body body = world.createBody(bodyDef);

        if(userdata!=null)
            body.setUserData(userdata);

        body.createFixture(fixtureDef);

        return body;
    }

    public static Vec2 toPhysicsVector(Vector2 vector)
    {
        return new Vec2(vector.getX() / PTM, vector.getY() / PTM);
    }

    public static Vec2 toPhysicsVector(Vec2 vector)
    {
        return new Vec2(vector.x / PTM, vector.y / PTM);
    }

    public static Vector2 toWorldVector(Vec2 vector)
    {
        return new Vector2(vector.x * PTM, vector.y * PTM);
    }

    public static Vector2 toWorldVector(Vector2 vector)
    {
        return new Vector2(vector.getX() * PTM, vector.getY() * PTM);
    }


    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
    {
        timeAccumulator += delta * timeMultiplier;

        while(timeAccumulator > timeStepMillis)
        {
            world.step(timeStep, velocityIterations, positionIterations);
            timeAccumulator -= timeStepMillis;
        }

        //update mario pos
        mario.Update(delta);

        input = gc.getInput();

        float seconds = delta / 1000.0f;
        float force = seconds * mario.MoveForce;

        mario.isDucking = false;
        mario.isMoving = false;
        PARALLAXING = false;

        Vec2 marioVelocity = mario.body.getLinearVelocity();

        if(input.isKeyDown(Input.KEY_S))
        {
            mario.isDucking = true;
            mario.animState = Mario.AnimationState.CROUCH;
            mario.body.applyForce(new Vec2(0, 10f), new Vec2());
        }
        else
        {
            if (input.isKeyDown(Input.KEY_D))
            {
                pClouds.ParallaxRight();
                pBackGround.ParallaxRight();
                mario.isMoving = true;

                mario.moveState = Mario.MoveState.Right;

                mario.animState = Mario.AnimationState.WALK;

                if (mario.x < GameConstants.SCREEN_BOUNDS.width / 2)
                {
                    mario.body.applyForce(new Vec2(force, 0), new Vec2());
                }
                else
                {
                    PARALLAXING = true;

                    levelX -= force/PTM;
                    pBackGround.pos.x -= mario.movementSpeed * delta * pBackGround.parallaxSpeed;
                    pClouds.pos.x -= mario.movementSpeed * delta * pClouds.parallaxSpeed;

                    mario.body.applyForce(new Vec2(), new Vec2());
                }

            } else if (input.isKeyDown(Input.KEY_A)) {
                pClouds.ParallaxLeft();
                pBackGround.ParallaxLeft();
                mario.isMoving = true;

                mario.moveState = Mario.MoveState.Left;
                mario.animState = Mario.AnimationState.WALK;

                if (levelX < 0)
                {
                    PARALLAXING = true;

                    levelX += force/PTM;
                    pBackGround.pos.x += mario.movementSpeed * delta * pBackGround.parallaxSpeed;
                    pClouds.pos.x += mario.movementSpeed * delta * pClouds.parallaxSpeed;
                    mario.body.applyForce(new Vec2(), new Vec2());
                }
                else if (mario.body.getPosition().x - toPhysicsVector(mario.getSize()).x / 2f > 0)
                {
                    mario.body.applyForce(new Vec2(-force, 0), new Vec2());
                }


            }
        }

        if(mario.body.getPosition().x - toPhysicsVector(mario.getSize()).x/2f < 0 )
        {
            Vec2 limitPos = new Vec2(0+toPhysicsVector(mario.getSize()).x/2f, mario.body.getPosition().y );
            mario.body.setTransform( limitPos,  0f);
            mario.body.setLinearVelocity(new Vec2(0, mario.body.m_linearVelocity.y));
        }


        if(!mario.isDucking)
        {
            if (mario.body.m_linearVelocity.x != 0 || PARALLAXING) {
                mario.animState = Mario.AnimationState.WALK;
            } else {
                mario.animState = Mario.AnimationState.IDLE;
            }
        }

        //camera.centerOn(mario.x, mario.y);

        Body b = world.getBodyList();
        while( b != null )
        {
            //update the bodies position
            if(b.getUserData()!=null && (b.getUserData().getClass().equals(BaseTile.class)
                    || BaseTile.class.isAssignableFrom(b.getUserData().getClass())))
            {
                BaseTile baseTile = (BaseTile)b.getUserData();

                Vector2 position = tileToWorld(baseTile.tileX, baseTile.tileY);

                if(!baseTile.tileType.equals(BaseTile.TileType.LeftSlant) &&
                        !baseTile.tileType.equals(BaseTile.TileType.RightSlant) )
                    position.add(levelMap.getTileWidth() / 2, levelMap.getTileHeight() / 2);


                Vec2 physicsPosition = toPhysicsVector(position);

                b.setTransform(physicsPosition, 0);
            }

            b = b.getNext();
        }

        for(int i=0; i<PowerUpList.size(); i++)
        {
            PowerUpList.get(i).update(seconds);
        }
        for(int i=0; i<EnemyList.size(); i++)
        {
            EnemyList.get(i).update(seconds);
        }


    }



    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        pBackGround.draw();
        pClouds.draw();


        levelMap.render(levelX, levelY, 0, 0, 200, 100, true);



        float deltaX = mario.x;
        Vec2 temp = mario.body.getPosition();
        mario.Position.set(temp.x, temp.y).mult(PTM);

        mario.setX(mario.Position.getX() - (mario.getSize().getX() / 2));
        mario.setY(mario.Position.getY() - (mario.getSize().getY() / 2));

        // cameraPosition.set(marioPosition.getX(), 0);
        //g.translate(-cameraPosition.getX(), -cameraPosition.getY());
        //camera.translate(g, mario);
        // camera.translateGraphics();
        //  camera.drawMap(0, -50);

        for(int i=0; i<coinList.size(); i++)
        {
            if(coinList.get(i).isCollected)
            {
                mario.coinsCollected++;
                upDateScore();
                coinList.get(i).finalize();
                coinList.set(i, null);
                coinList.remove(i);
                i--;
                continue;
            }
            coinList.get(i).render();
        }

        for(int i=0; i<QBlockList.size(); i++)
        {

            if(QBlockList.get(i).IsHit)
            {
                Vector2 spawnPoint = QBlockList.get(i).Position;
                spawnPoint.add(0, -BaseTile.GetTileSize().getY());
                PowerUpList.add(new Mushroom(spawnPoint, world));
                QBlockList.get(i).finalize();
                QBlockList.set(i, null);
                QBlockList.remove(i);
                i--;
                continue;
            }
            QBlockList.get(i).render();
        }

        for(int i=0; i<PowerUpList.size(); i++)
        {
            if(PowerUpList.get(i).IsCollected)
            {
                PowerUpList.get(i).finalize();
                PowerUpList.set(i, null);
                PowerUpList.remove(i);
                i--;
                continue;
            }
            PowerUpList.get(i).render();
        }

        for(int i=0; i<EnemyList.size(); i++)
        {
            if(EnemyList.get(i).IsAlive)
            {
                EnemyList.get(i).finalize();
                EnemyList.set(i, null);
                EnemyList.remove(i);
                i--;
                continue;
            }
            EnemyList.get(i).render();
        }



        mario.draw();

        g.setFont(font);
        g.drawString("Score: " + mario.coinsCollected, 20.0f, 20.0f);
    }
    void UpdateScore()
    {
        try
        {
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement("UPDATE highscore SET Score=" + mario.coinsCollected.toString() + " WHERE FirstName='" + firstName + "'");
            pst.executeUpdate();
            boolean isResult = pst.execute();
        }
        catch (SQLException ex)
        {
            System.out.println("SQL Error " + ex.getMessage());
        }
        finally
        {
            try
            {
                if (pst != null)
                {
                    pst.close();
                }
                if (con != null)
                {
                    con.close();
                }
            }
            catch (SQLException ex)
            {
                System.out.println("SQL Error " + ex.getMessage());
            }
        }
    }
    void upDateScore(){

        try{
            con = DriverManager.getConnection(url, user, password);
            pst = con.prepareStatement("UPDATE highscore SET Score= " + mario.coinsCollected.toString() + " WHERE FirstName='" +
                    firstName +  "'");
            pst.executeUpdate();
            boolean isResult = pst.execute();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error " + ex.getMessage());
        }
            finally
            {
                try
                {
                    if (pst != null)
                    {
                        pst.close();
                    }
                    if (con != null)
                    {
                        con.close();
                    }
                }
                catch (SQLException ex)
                {
                    System.out.println("SQL Error " + ex.getMessage());
                }
        }

    }


    public int getID()
    {
        return 0;
    }
}