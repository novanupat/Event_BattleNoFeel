package Event.BattleNoFeel.core;

import State.State01;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Anupat on 21/3/2557.
 */
public class Score extends UIScreen{

    private ScreenStack ss;
    private Image backImage;
    private ImageLayer backLayer;
    private Root root,root2;
    private int hightState = 0;

    public Score(ScreenStack ss){
        this.ss = ss;
    }


    @Override
    public void wasAdded() {
        super.wasAdded();

        if (MyGame.stateNow >= hightState){
            hightState = MyGame.stateNow;
        }

        root = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer);
        root.setSize(300, 270);
        root.add(new Label("Hight State "+hightState).addStyles(Style.FONT.is(State01.TITLE_FONT)).addStyles(Style.COLOR.is(0xFFFFFFFF)));

        root2 = iface.createRoot(
                AxisLayout.vertical().gap(15),
                SimpleStyles.newSheet(), layer);
        root2.setSize(650, 150);
        root2.add(new Label("Score").addStyles(Style.FONT.is(State01.TITLE_FONT)).addStyles(Style.COLOR.is(0xFFFFFFFF)));

        backImage = assets().getImage("images/State/back.png");
        backLayer = graphics().createImageLayer(backImage);
        layer.add(backLayer);
        backLayer.setTranslation(15,20);
        backLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(Score.this);
            }
        });
    }


}
