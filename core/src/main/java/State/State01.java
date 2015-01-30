package State;

import Event.BattleNoFeel.core.DebugDrawBox2D;
import Event.BattleNoFeel.core.HomeScreen;
import Event.BattleNoFeel.core.MyGame;
import Shop.Shop;
import SpriteAll.Bullet;
import SpriteAll.Enemy01;
import SpriteAll.Hero;
import SpriteAll.Hp;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

import java.util.ArrayList;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Anupat on 26/2/2557.
 */
public class State01 extends UIScreen {

    private final ScreenStack ss;
    private World world;
    private static int width = 24;
    private static int height = 18;
    public static float M_PER_PIXEL = 1 / 26.666667f;

    private Image bgImage,backImage,clear,shop,heroDie,newGame,saveMe;
    private ImageLayer bgLayer,backLayer,clearLayer,shopLayer,heroDieLayer,newGameLayer,saveMeLayer;

    private Hp hp;
    private Hero hero;
    private Enemy01 enemy01,enemy02;
    private Bullet b;
    private Shop sh;

    private DebugDrawBox2D debugDraw;
    private boolean showDebugDraw = false;

    public static boolean attackZealot = false;
    private Body ground,ground2,ground3,ground4,ground5;
    private PolygonShape groundShape;
    private int x_BG = 640,enemy01Loop = 0,delay;
    private int e,finish,ff,start = 10000;
    public static int levelGun = 0;
    public static Root root,root1,root2;
    public static boolean rootOpenAmount = true,rootOpenCoin = true,rootOpenGunLv = true,checkHeroDie=false,saveMeBoolean = false;
    private boolean clearThisState = false;
    public static Sound soundTest = assets().getSound("sound/Realistic_Punch-Mark_DiAngelo-1609462330");

    ArrayList<Enemy01> enemy01ArrayList = new ArrayList<Enemy01>();

    public State01(ScreenStack ss){
        this.ss = ss;
    }

    public static final Font TITLE_FONT = PlayN.graphics().createFont(
            "Helvetica",
            Font.Style.BOLD,
            24
    );

    @Override
    public void wasShown() {
        super.wasShown();

    }

    @Override
    public void wasAdded() {
        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody() == hero.body) {             //โดน Enemy ชน ลด เลือด (1)
                    hp.contact(contact);
                    enemy01.contact3(contact);
                    hero.contact(contact);
                    System.out.println("ชนแบบ 1");
                } else if(contact.getFixtureB().getBody() == hero.body) {       //กระโดด 1 ที
                    hero.contact(contact);
                    System.out.println("ตัวหลักเหยีบพื้น");
                }

                if (contact.getFixtureA().getBody() == enemy01.body&&contact.getFixtureB().getBody() == hero.body) {
                    hp.contact(contact);
                    enemy01.contact3(contact);
                    hero.contact(contact);
                    System.out.println("ชนแบบ 2");
                }


                if (contact.getFixtureA().getBody() == enemy01.body) {
                    System.out.println("ศัตรูชน A");
                }
                if(contact.getFixtureB().getBody() == enemy01.body) {
                    System.out.println("ศัตรูชน B");
                }


                if (contact.getFixtureB().getBody() == enemy01.body && contact.getFixtureA().getBody() == ground2) {
                    enemy01.contact(contact);
                    System.out.println("ศัตรู ชน ขอบซ้าย");
                }

                if (contact.getFixtureB().getBody() == enemy01.body && contact.getFixtureA().getBody() == ground3) {
                    enemy01.contact2(contact);
                    System.out.println("ศัตรู ชน ขอบขวา");
                }


                if ((attackZealot)){
                    if (contact.getFixtureA().getBody() == b.body) {
                        b.contact(contact);
                        System.out.println("กระสุนโดน A");
                    }

                    if (contact.getFixtureB().getBody() == b.body) {
                        b.contact(contact);
                        /*enemy01.contact4(contact);*/
                        System.out.println("กระสุนโดน B");

                    }

                    if(contact.getFixtureA().getBody() == b.body && contact.getFixtureB().getBody() == enemy01.body) {
                        b.contact(contact);
                        enemy01.contact4(contact);
                        System.out.println("โดนยิงแบบที่ 1");
                    }
                    if(contact.getFixtureB().getBody() == b.body && contact.getFixtureA().getBody() == enemy01.body) {
                        b.contact(contact);
                        enemy01.contact4(contact);
                        System.out.println("โดนยิงแบบที่ 2");
                    }
                    if(contact.getFixtureB().getBody() == b.body && contact.getFixtureA().getBody() != ground2 &&
                            contact.getFixtureA().getBody() != ground3) {
                        b.contact(contact);
                        enemy01.contact4(contact);
                        System.out.println("โดนยิงแบบที่ 3");
                    }
                    if (contact.getFixtureA().getBody() == b.body) {
                        b.contact(contact);
                        enemy01.contact4(contact);
                        System.out.println("โดนยิงแบบที่ 4");
                    }
                    if (contact.getFixtureA().getBody() == ground2 && contact.getFixtureB().getBody() == b.body
                        || contact.getFixtureA().getBody() == ground3 && contact.getFixtureB().getBody() == b.body){
                         b.contact(contact);
                    }

                    ////////////////////////////////////////////////////// enemy 2 ////////////////////////////////////////////////////

                    /*if (contact.getFixtureA().getBody() == enemy02.body&&contact.getFixtureB().getBody() == hero.body) {
                        hp.contact(contact);
                        enemy02.contact3(contact);
                        hero.contact(contact);
                        System.out.println("ชนแบบ 2");
                    }

                    if (contact.getFixtureB().getBody() == enemy02.body && contact.getFixtureA().getBody() == ground2) {
                        enemy02.contact(contact);
                        System.out.println("ศัตรู ชน ขอบซ้าย");
                    }

                    if (contact.getFixtureB().getBody() == enemy02.body && contact.getFixtureA().getBody() == ground3) {
                        enemy02.contact2(contact);
                        System.out.println("ศัตรู ชน ขอบขวา");
                    }

                    if(contact.getFixtureA().getBody() == b.body && contact.getFixtureB().getBody() == enemy02.body) {
                        b.contact(contact);
                        enemy02.contact4(contact);
                        System.out.println("โดนยิงแบบที่ 1");
                    }
                    if(contact.getFixtureB().getBody() == b.body && contact.getFixtureA().getBody() == enemy02.body) {
                        b.contact(contact);
                        enemy02.contact4(contact);
                        System.out.println("โดนยิงแบบที่ 2");
                    }


*/
                }

            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {
            }
        });


        MyGame.EnemyDie = true;

        bgImage = assets().getImage("images/State/BGRoad(OK).png");
        bgLayer = graphics().createImageLayer(bgImage);
        layer.add(bgLayer);
        bgLayer.setTranslation(0, 0);

        clear = assets().getImage("images/State/Clear.png");
        clearLayer = graphics().createImageLayer(clear);

        heroDie = assets().getImage("images/State/HeroDie.png");
        heroDieLayer = graphics().createImageLayer(heroDie);

        saveMe = assets().getImage("images/State/SaveMe.png");
        saveMeLayer = graphics().createImageLayer(saveMe);

        newGame = assets().getImage("images/State/NewGame.png");
        newGameLayer = graphics().createImageLayer(newGame);

        shop = assets().getImage("images/State/shop.png");
        shopLayer = graphics().createImageLayer(shop);
        layer.add(shopLayer);
        shopLayer.setTranslation(120,20);
        shopLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.push(new Shop(ss));
            }
        });


        backImage = assets().getImage("images/State/back.png");
        backLayer = graphics().createImageLayer(backImage);
        layer.add(backLayer);
        backLayer.setTranslation(15,20);
        backLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(State01.this);
            }
        });


        if (showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int)(width / State01.M_PER_PIXEL),
                    (int)(height / State01.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit|
                    DebugDraw.e_jointBit|
                    DebugDraw.e_aabbBit
            );
            debugDraw.setCamera(0,0,1f/State01.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);
        }

        //เส้นล่าง
        ground = world.createBody(new BodyDef());
        groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(-5,height-1),
                new Vec2(width+5,height-1));
        ground.createFixture(groundShape, 0.0f);

        //เส้นซ้าย
        ground2 = world.createBody(new BodyDef());
        PolygonShape groundShape2 = new PolygonShape();
        groundShape2.setAsEdge(new Vec2(-5f,0),
                new Vec2(-5f,height-1));
        ground2.createFixture(groundShape2, 0.0f);

        //เส้นขวา
        ground3 = world.createBody(new BodyDef());
        PolygonShape groundShape3 = new PolygonShape();
        groundShape3.setAsEdge(new Vec2(width+5f,0f),
                new Vec2(width+5f,height-1));
        ground3.createFixture(groundShape3, 0.0f);

        ground4 = world.createBody(new BodyDef());
        PolygonShape groundShape4 = new PolygonShape();
        groundShape4.setAsEdge(new Vec2(10.5f,10f),
                new Vec2(10.5f,height-4));
        ground4.createFixture(groundShape4, 0.0f);

        ground5 = world.createBody(new BodyDef());
        PolygonShape groundShape5 = new PolygonShape();
        groundShape5.setAsEdge(new Vec2(13.2f,10f),
                new Vec2(13.2f,height-4));
        ground5.createFixture(groundShape5, 0.0f);





        hero = new Hero(world,320,200f);
        layer.add(hero.layer());
        hp = new Hp(500f,30f);
        layer.add(hp.layer());
        /*enemy02 = new Enemy01(world,700,400f);
        layer.add(enemy02.layer());*/



    }

    @Override
    public void update(final int delta) {
        world.step(0.033f, 10, 10);
        start += delta;
        //waitClear += delta;
        if (MyGame.EnemyDie == true){
            enemy01Loop++;
            enemy01Loop = enemy01Loop%2;
            if (start >= 10000){
                if (enemy01Loop == 0){
                    enemy01 = new Enemy01(world,750,400f);
                    enemy01ArrayList.add(enemy01);
                    layer.add(enemy01.layer());
                    MyGame.EnemyDie = false;
                }else if (enemy01Loop == 1){
                    enemy01 = new Enemy01(world,-100,400f);
                    enemy01ArrayList.add(enemy01);
                    layer.add(enemy01.layer());
                    MyGame.EnemyDie = false;
                }
                start = 0;
            }
        }

        for (Enemy01 enemy01 : enemy01ArrayList){
            enemy01.update(delta);
        }

        /*enemy02.update(delta);*/

        hero.update(delta);
        hp.update(delta);
        start += delta;
        e += delta;
        ff += delta;
        if (attackZealot){
            b.update(delta);
        }

        if (hero.state == Hero.State.RUN){
            x_BG += 5;
            if (x_BG >= 4780/*4780*/){
                x_BG = 4780 ;
                MyGame.stateNow = 2;
                layer.add(clearLayer);
                hero.body.setActive(false);
                enemy01.body.setActive(false);
                clearThisState = true;
            }
        }

        if (clearThisState == true){
            finish += 5;
            if (finish >= 500){
                clearThisState = false;
                ss.push(new State02(ss));
            }
        }

        if (hero.state == Hero.State.RUNL){
            x_BG -= 5;
            if (x_BG <= 0){
                x_BG = 0;
            }
        }
        bgLayer.setOrigin(x_BG, 0);

        if (e >= 400){
            if (hero.state == Hero.State.ATTK){
                if (hero.bulletFire() == true){
                    soundTest.play();
                b = new Bullet(world,(hero.x_ze()/M_PER_PIXEL)+80,hero.y_ze()/M_PER_PIXEL);
                    layer.add(b.layer());
                    attackZealot = true;
                    hero.bulletFire = false;
                    b.atkL(false);
                    e = 0;
                    MyGame.amount -= 1;
                    root.removeAll();
                    rootOpenAmount = true;
                }
            }
            if (hero.state == Hero.State.ATTKL){
                if (hero.bulletFire() == true){
                    soundTest.play();
                    b = new Bullet(world,(hero.x_ze()/M_PER_PIXEL)-80,hero.y_ze()/M_PER_PIXEL);
                    layer.add(b.layer());
                    attackZealot = true;
                    hero.bulletFire = false;
                    b.atkL(true);
                    e = 0;
                    MyGame.amount -= 1;
                    root.removeAll();
                    rootOpenAmount = true;
                }
            }
        }

        if (hp.state == Hp.State.HPP2){
            hero.state = Hero.State.DIE;
        }
        if (hero.state == Hero.State.DIE){
            checkHeroDie = true;
        }

        if (checkHeroDie){
            delay += 5;
            layer.add(heroDieLayer);
            heroDieLayer.setTranslation(230,130);
            layer.add(newGameLayer);
            newGameLayer.setTranslation(130,270);
            newGameLayer.addListener(new Pointer.Adapter() {
                @Override
                public void onPointerEnd(Pointer.Event event) {
                   if (delay >= 500){
                        ss.push(new HomeScreen(ss));
                        checkHeroDie = false;
                        delay = 0;
                   }
                }
            });

            layer.add(saveMeLayer);
            saveMeLayer.setTranslation(320,270);
            saveMeLayer.addListener(new Pointer.Adapter() {
                @Override
                public void onPointerEnd(Pointer.Event event) {
                    if (delay >= 500){
                        if (MyGame.coin >= 200){
                            hp.state = Hp.State.HPP;
                            saveMeBoolean = true;
                            checkHeroDie = false;
                            MyGame.coin = MyGame.coin-200;
                            hero.body.setActive(true);
                            hero.state = Hero.State.IDLE;
                            root2.removeAll();
                            rootOpenCoin = true;
                            delay = 0;
                        }
                    }
                }
            });
        }

        if (saveMeBoolean){
            layer.remove(heroDieLayer);
            layer.remove(saveMeLayer);
            layer.remove(newGameLayer);
            saveMeBoolean = false;
        }

        if (rootOpenAmount){
            root = iface.createRoot(
                    AxisLayout.vertical().gap(15),
                    SimpleStyles.newSheet(), layer);
            root.setSize(1100, 200);
            root.add(new Label("Amount "+MyGame.amount).addStyles(Style.FONT.is(State01.TITLE_FONT)));
            rootOpenAmount = false;
        }

        if (rootOpenGunLv){
            root1 = iface.createRoot(
                    AxisLayout.vertical().gap(15),
                    SimpleStyles.newSheet(), layer);
            root1.setSize(1075, 340);
            root1.add(new Label("Weapon Lv. "+Shop.levelGun).addStyles(Style.FONT.is(State01.TITLE_FONT)));
            rootOpenGunLv = false;
        }

        if (rootOpenCoin){
            root2 = iface.createRoot(
                    AxisLayout.vertical().gap(15),
                    SimpleStyles.newSheet(), layer);
            root2.setSize(1100, 270);
            root2.add(new Label("Money "+MyGame.coin).addStyles(Style.FONT.is(State01.TITLE_FONT)));
            rootOpenCoin = false;
        }


        /*if (enemy01.hasload == true && hero.hasload == true){
        LineJointDef joint_def = new LineJointDef();
        joint_def.bodyA = enemy01.body;
        joint_def.bodyB = hero.body;
        joint_def.enableLimit=true;
        joint_def.enableMotor=true;
        joint_def.maxMotorForce = 00.0f;
        joint_def.motorSpeed =0.0f;
        //connect the centers - center in local coordinate - relative to body is 0,0
        // add the joint to the world
        world.createJoint(joint_def);
        }*/


    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if (attackZealot){
            b.paint(clock);
        }
        hero.paint(clock);
        for (Enemy01 enemy01 : enemy01ArrayList){
            enemy01.paint(clock);
        }
        /*enemy02.paint(clock);*/
        hp.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }

    }
}