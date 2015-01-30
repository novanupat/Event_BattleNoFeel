package Event.BattleNoFeel.core;


import State.State01;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Anupat on 26/2/2557.
 */
public class HomeScreen extends UIScreen{

    private final ScreenStack ss;

    public HomeScreen(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasAdded() {
        super.wasAdded();

        Image bgImage = assets().getImage("images/HomeScreen/BGStart.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        layer.add(bgLayer);

        Image startImage = assets().getImage("images/HomeScreen/play.png");
        ImageLayer startLayer = graphics().createImageLayer(startImage);
        layer.add(startLayer);
        startLayer.setTranslation(140,130);
        startLayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                //super.onPointerEnd(event);
                ss.push(new State01(ss));
                State01.rootOpenAmount = true;
                State01.rootOpenCoin = true;
                State01.rootOpenGunLv = true;
            }
        });

        Image scoreImage = assets().getImage("images/HomeScreen/Score.png");
        ImageLayer scoreLayer = graphics().createImageLayer(scoreImage);
        layer.add(scoreLayer);
        scoreLayer.setTranslation(135,220);
        scoreLayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                //super.onPointerEnd(event);
                ss.push(new Score(ss));

            }
        });

        Image helpImage = assets().getImage("images/HomeScreen/help.png");
        ImageLayer helpLayer = graphics().createImageLayer(helpImage);
        layer.add(helpLayer);
        helpLayer.setTranslation(145,320);
        helpLayer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                //super.onPointerEnd(event);
                ss.push(new Help(ss));

            }
        });
    }


}
