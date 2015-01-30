package SpriteAll;

import SpriteScript.Sprite;
import SpriteScript.SpriteLoader;
import State.State01;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.UIScreen;


/**
 * Created by Anupat on 28/1/2557.
 */
public class Bullet extends UIScreen{

    private int contactCheck;
    private boolean contacted;
    private int spriteIndex = 0;
    private boolean hasload = false;
    private Sprite sprite;
    public static final int UPDATE_RATE=25;
    private int e = 0;
    private int hh = 100;
    private int f = 0;
    private int offset = 0;
    private float xx = 25.0f,x;
    public Body body;
    private int i = 0;
    private World world;
    public boolean atk_LR;



    public enum State{
        BULLETF
    };

    public State state = State.BULLETF;


    public Bullet(final World world, final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/Bullet/bullet.json");
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
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 0.1f;
        body.createFixture(fixtureDef);


        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }

    public Layer layer(){
        return sprite.layer();
    }

    public void atkL(boolean atk_LR){
        this.atk_LR = atk_LR;
    }

    public void contact(Contact contact) {
        contacted = true;
        contactCheck = 0;
    }

    public void update(int delta) {
        super.update(delta);
        if (contacted){
            body.setActive(false);
            sprite.layer().destroy();
            State01.attackZealot = false;
            contacted = false;
        }
        if (!hasload) return;
        e += delta;
        if (e > 50){
            switch (state) {
                case BULLETF:offset = 0;
                    if (spriteIndex == 6){
                        state = State.BULLETF;
                    }
                    break;
            }
            spriteIndex = offset + ((spriteIndex +1)%8);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
        if (!atk_LR){
            body.applyLinearImpulse(new Vec2(1000f, 0f), body.getPosition());
        }
        else {
            body.applyLinearImpulse(new Vec2(-1000f, 0f), body.getPosition());
        }
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
