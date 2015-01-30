package SpriteAll;

import Event.BattleNoFeel.core.MyGame;
import SpriteScript.Sprite;
import SpriteScript.SpriteLoader;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.UIScreen;
import State.*;


/**
 * Created by Anupat on 28/1/2557.
 */
public class Hero extends UIScreen{

    private int contactCheck;
    private boolean contacted;
    private int spriteIndex = 0;
    public boolean hasload = false,jump_Zealot = false,jump = false;
    private Sprite sprite;
    private int e = 0;
    private int hh = 100;
    private int f = 0;
    private int offset = 0;
    private float xx = 25.0f,pos_X;
    public Body body;
    private int i = 0,j;
    private Bullet b;
    private float x_ze,y_ze;
    public boolean bulletFire = false;
    public float x_layer;

    public enum State{
        IDLE,RUN,ATTK,IDLEL,RUNL,ATTKL,DIE
    };

    public State state = State.IDLE;


    public Hero(final World world, final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/Hero/zealot.json");
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


        PlayN.keyboard().setListener(new Keyboard.Listener() {
            @Override
            public void onKeyDown(Keyboard.Event event) {

                if (event.key() == Key.UP){
                    if (contactCheck == 1){
                        body.applyLinearImpulse(new Vec2(0f,-60000f), body.getPosition());
                        contactCheck = 0 ;
                    }
                }

                if (event.key() == Key.RIGHT) {
                    state = State.RUN;
                    spriteIndex = 1;
                    e = 0;
                }
                if (event.key() == Key.LEFT) {
                    state = State.RUNL;
                    spriteIndex = 1;
                    e = 0;
                }
                if (MyGame.amount >  0){
                    if (event.key() == Key.SPACE) {
                        bulletFire = true;
                        if (state == State.IDLE) {
                            state = State.ATTK;
                            spriteIndex = -1;
                            e = 0;
                        }
                        if (state == State.IDLEL) {
                            state = State.ATTKL;
                            spriteIndex = 0;
                            e = 0;
                        }
                    }
                }
            }

            @Override
            public void onKeyTyped(Keyboard.TypedEvent event) {

            }

            @Override
            public void onKeyUp(Keyboard.Event event) {
                if (event.key() == Key.RIGHT) {
                    state = State.IDLE;
                    spriteIndex = 0;
                    e = 0;
                }
                if (event.key() == Key.LEFT) {
                    state = State.IDLEL;
                    spriteIndex = 0;
                    e = 0;
                }
                if (event.key() == Key.UP){
                    jump_Zealot = false;
                }

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
        //fixtureDef.isSensor = true;
        fixtureDef.shape = shape;
        fixtureDef.density = 1000f;
        fixtureDef.friction = 1f;
        body.createFixture(fixtureDef);


        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
      //System.out.println("ไม่ออก");


        return body;
    }

    public Layer layer(){
        return sprite.layer();
    }

    public Float x_ze(){
        return x_ze;
    }

    public Float y_ze(){
        return y_ze;
    }

    public Boolean bulletFire(){
        return bulletFire;
    }

    public void contact(Contact contact) {
        contacted = true;
        contactCheck = 1;

    }


    public void update(int delta) {
        super.update(delta);
        if (!hasload) return;
        e += delta;
        if (e > 100){
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
                case DIE:offset = 20;
                    if (spriteIndex == 22){
                        state = State.DIE;
                        //sprite.layer().destroy();
                        body.setActive(false);
                    }
                    break;
            }
            spriteIndex = offset + ((spriteIndex +1)%4);
            sprite.setSprite(spriteIndex);
            e = 0;

            x_ze = body.getPosition().x;
            y_ze = body.getPosition().y;
        }
        x_layer = body.getWorldCenter().y/State01.M_PER_PIXEL;
    }

    @Override
    public void paint(Clock clock) {
        if (!hasload) return;
        sprite.layer().setTranslation(
                (body.getPosition().x/State01.M_PER_PIXEL),
                (body.getPosition().y/State01.M_PER_PIXEL));
    }
}
