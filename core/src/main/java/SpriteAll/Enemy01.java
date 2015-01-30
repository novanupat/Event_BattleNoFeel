package SpriteAll;

import Event.BattleNoFeel.core.MyGame;
import Shop.Shop;
import SpriteScript.Sprite;
import SpriteScript.SpriteLoader;
import State.State01;
import State.State02;
import State.State03;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.UIScreen;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Anupat on 28/1/2557.
 */
public class Enemy01 extends UIScreen{

    private int contactCheck;
    private boolean contacted,contacted2,contacted3,contacted4;
    private int spriteIndex = 0;
    public boolean hasload = false;
    private Sprite sprite;
    public static final int UPDATE_RATE=25;
    private int e = 0;
    private int hh = 100;
    private int f = 0;
    private int offset = 0;
    private int i = 0;
    private float xx = 25.0f;
    public Body body;
    private World world;
    private Bullet b;
    private Shop sh;
    List<Enemy01> zzs = new ArrayList<Enemy01>();


    public enum State{
        IDLE,RUN,ATTK,IDLEL,RUNL,ATTKL,DIE
    };

    public State state = State.RUNL;


    public Enemy01(final World world, final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/Enemy/zealot.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x_px,y_px);

                body = initPhysicsBody(world,
                        State01.M_PER_PIXEL * x_px,
                        State01.M_PER_PIXEL * y_px);

                hasload = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!",cause);

            }

        });



        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                state = State.DIE;
                spriteIndex = -1;
                e = 0;
            }
        });


    }

    private Body initPhysicsBody (World world,float x,float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56 * State01.M_PER_PIXEL /2,
                sprite.layer().height()*State01.M_PER_PIXEL / 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.2f;
        body.createFixture(fixtureDef);

        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }

    public Layer layer(){
        return sprite.layer();
    }

    public void contact(Contact contact) {
        contacted = true;
        contactCheck = 0;

        state = State.RUN;


    }

    public void contact2(Contact contact) {
        contacted = true;
        contactCheck = 0;

        state = State.RUNL;


    }

    public void contact3(Contact contact) {
        contacted = true;
        contactCheck = 0;

        if (state == State.RUN) {
                body.applyLinearImpulse(new Vec2(-10000f, -3000f), body.getPosition());
        }
        if (state == State.RUNL) {
            body.applyLinearImpulse(new Vec2(10000f, -3000f), body.getPosition());

        }
    }

    public void contact4(Contact contact) {
        contacted4 = true;
        contactCheck = 0;
    }

    public void update(int delta) {
        if (contacted4){
            if (state == State.RUN) {
                body.applyLinearImpulse(new Vec2(0f, 10000), body.getPosition());
            }
            if (state == State.RUNL) {
                body.applyLinearImpulse(new Vec2(0f, 10000), body.getPosition());
            }
            i++;
            if (MyGame.levelGun == 0){
                if (i >= 4) {
                    body.setActive(false);
                    sprite.layer().destroy();
                    MyGame.coin += 20;
                    MyGame.EnemyDie = true;
                }
                contacted4 = false;
            }
            if (MyGame.levelGun == 1){
                if (i >= 3) {
                    body.setActive(false);
                    sprite.layer().destroy();
                    MyGame.coin += 20;
                    MyGame.EnemyDie = true;
                }
                contacted4 = false;
            }
            if (MyGame.levelGun == 2){
                if (i >= 2) {
                    body.setActive(false);
                    sprite.layer().destroy();
                    MyGame.coin += 20;
                    MyGame.EnemyDie = true;
                }
                contacted4 = false;
            }
            if (MyGame.levelGun == 3){
                if (i >= 1) {
                    body.setActive(false);
                    sprite.layer().destroy();
                    MyGame.coin += 20;
                    MyGame.EnemyDie = true;
                }
                contacted4 = false;
            }
            switch (MyGame.stateNow){
                case 1 :
                    State01.root2.removeAll();
                    State01.rootOpenCoin = true;
                    break;
                case 2 :
                    State02.root2.removeAll();
                    State02.rootOpenCoin = true;
                    break;
                case 3 :
                    State03.root2.removeAll();
                    State03.rootOpenCoin = true;
            }

        }
        if (!hasload) return;
        e += delta;

        switch (MyGame.stateNow){
            case 1:
                if (state == State.RUNL)
                    body.applyForce(new Vec2(-2000f, 0f), body.getPosition());

                if (state == State.RUN)
                    body.applyForce(new Vec2(2000f, 0f), body.getPosition());
                break;
            case 2:
                if (state == State.RUNL)
                    body.applyForce(new Vec2(-4000f, 0f), body.getPosition());

                if (state == State.RUN)
                    body.applyForce(new Vec2(4000f, 0f), body.getPosition());
                break;
            case 3:
                if (state == State.RUNL)
                    body.applyForce(new Vec2(-6000f, 0f), body.getPosition());

                if (state == State.RUN)
                    body.applyForce(new Vec2(6000f, 0f), body.getPosition());
        }


        if (e > 180){
            switch (state) {
                case IDLE:offset = 0;
                    break;
                case RUN:offset = 4;
                    break;
                case ATTK:offset = 8;
                    if (spriteIndex == 8){
                        state = State.IDLE;
                    }
                    break;
                case IDLEL:offset = 10;
                    break;
                case RUNL:offset = 14;
                    break;
                case ATTKL:offset = 18;
                    if (spriteIndex == 19){
                        state = State.IDLEL;
                    }
                    break;
                case DIE:offset = 21;
                    if (spriteIndex == 23){
                        state = State.DIE;
                        sprite.layer().destroy();
                        body.setActive(false);
                    }
                    break;
            }
            spriteIndex = offset + ((spriteIndex +1)%4);
            sprite.setSprite(spriteIndex);
            e = 0;
        }



        /*if (state == State.RUN){
                *//*xx += 2f ;
                sprite.layer().setTranslation(xx,380);*//*
                body.applyForce(new Vec2(20f, 0f), body.getPosition());

        }
        if (state == State.RUNL){
            *//*xx -= 2f ;
            sprite.layer().setTranslation(xx,380);*//*
            body.applyForce(new Vec2(-20f, 0f), body.getPosition());

        }*/

        /*if (hpzealot.state()== Hp.State.HPP){
            state = State.DIE;
        }*/

    }





    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if (!hasload) return;
        sprite.layer().setTranslation(
                (body.getPosition().x/State01.M_PER_PIXEL),
                (body.getPosition().y/State01.M_PER_PIXEL));
    }
}
