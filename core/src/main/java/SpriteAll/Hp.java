package SpriteAll;

import SpriteScript.Sprite;
import SpriteScript.SpriteLoader;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import tripleplay.game.UIScreen;


/**
 * Created by Anupat on 29/1/2557.
 */
public class Hp extends UIScreen{

    private int spriteIndex = 6;
    private boolean hasload = false;
    private Sprite sprite;
    private int contactCheck,freeState;
    private boolean contacted;
    private int i = 0;

    public enum State{
        HPP,HPP1,HPP2
    };

    public State state = State.HPP;

    private int e = 0;
    private int hh = 100;
    private int f = 0;
    private int offset = 0;
    private float xx = 25.0f;

    public Hp(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/HP/hp.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f,
                        sprite.height() / 2f);
                sprite.layer().setTranslation(x + 25.0f, y);
                hasload = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image HP!",cause);
            }
        });
        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                state = State.HPP1;
                spriteIndex = -1;
                e = 0;
            }
        });

    }

    public void contact(Contact contact) {
        contacted = true;
        contactCheck = 0;
    }

    public void update(int delta) {
        freeState += delta;
        if (contacted){
            ++i;

            if (freeState >= 500){
                if(i == 1) {
                    state = State.HPP1;
                }
                else if(i == 2) {
                    state = State.HPP2;
                    i = 0;
                }
                contacted = false;
                freeState = 0;
            }
        }
        if (!hasload) return;
        e += delta;

        if (e > 1000){
            switch (state) {
                case HPP:offset = 6;
                    break;
                case HPP1:offset= 3;
                    break;
                case HPP2:offset = 0;
                    break;
            }
            spriteIndex = offset + ((spriteIndex + 1)%3);
            sprite.setSprite(spriteIndex);
            e = 0;
        }
    }
    @Override
    public void paint(Clock clock) {
        super.paint(clock);
    }

    public Layer layer(){
        return sprite.layer();
    }

}
