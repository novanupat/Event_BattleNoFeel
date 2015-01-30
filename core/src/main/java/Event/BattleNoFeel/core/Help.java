package Event.BattleNoFeel.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Anupat on 21/3/2557.
 */
public class Help extends UIScreen {

    private ScreenStack ss;
    private Image backImage;
    private ImageLayer backLayer;

    public Help (ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();
        backImage = assets().getImage("images/State/back.png");
        backLayer = graphics().createImageLayer(backImage);
        layer.add(backLayer);
        backLayer.setTranslation(15,20);
        backLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(Help.this);
            }
        });

    }
}
