package Shop;

import Event.BattleNoFeel.core.MyGame;
import State.State01;
import State.State02;
import State.State03;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.Sound;
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
 * Created by Anupat on 15/3/2557.
 */
public class Shop extends UIScreen{

    private final ScreenStack ss;
    private Image shop,BGShop,MoneyNo,basicGunIM,nomalGunIm,heavyGunIm,amountIm;
    private ImageLayer shopLayer,MoneyNoLayer,BGShopLayer,basicGunLayer,nomalGunLayer,heavyGunLayer,amountLayer;
    public boolean basicGun = true,nomalGun = false,heavyGun = false,canRemove = false,checkGun = true;
    private int t;
    public static int levelGun = 0;
    private Root root3,root4;
    private static Sound soundBuy = assets().getSound("sound/Cowboy_with_spurs-G-rant-1371954508");

    public Shop(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasAdded() {

        State01.rootOpenAmount = true;
        State01.rootOpenCoin = true;

        BGShop = assets().getImage("images/Shop/State1.png");
        BGShopLayer = graphics().createImageLayer(BGShop);
        layer.add(BGShopLayer);

        shop = assets().getImage("images/Shop/Back.png");
        shopLayer = graphics().createImageLayer(shop);
        layer.add(shopLayer);
        shopLayer.setTranslation(15,20);
        shopLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                ss.remove(Shop.this);
                /*if (MyGame.stateNow == 1){
                    State01.root.removeAll();
                    State01.root1.removeAll();
                    State01.root2.removeAll();
                    State01.rootOpenAmount = true;
                    State01.rootOpenCoin = true;
                    State01.rootOpenGunLv = true;
                }
                if (MyGame.stateNow == 2){
                    State02.root.removeAll();
                    State02.root1.removeAll();
                    State02.root2.removeAll();
                    State02.rootOpenAmount = true;
                    State02.rootOpenCoin = true;
                    State02.rootOpenGunLv = true;
                }*/
                switch (MyGame.stateNow){
                    case 1 :
                        State01.root.removeAll();
                        State01.root1.removeAll();
                        State01.root2.removeAll();
                        State01.rootOpenAmount = true;
                        State01.rootOpenCoin = true;
                        State01.rootOpenGunLv = true;
                        break;
                    case 2:
                        State02.root.removeAll();
                        State02.root1.removeAll();
                        State02.root2.removeAll();
                        State02.rootOpenAmount = true;
                        State02.rootOpenCoin = true;
                        State02.rootOpenGunLv = true;
                        break;
                    case 3:
                        State03.root.removeAll();
                        State03.root1.removeAll();
                        State03.root2.removeAll();
                        State03.rootOpenAmount = true;
                        State03.rootOpenCoin = true;
                        State03.rootOpenGunLv = true;
                }
            }
        });

        amountIm = assets().getImage("images/Shop/Amount.png");
        amountLayer = graphics().createImageLayer(amountIm);
        layer.add(amountLayer);
        amountLayer.setTranslation(250,350);
        amountLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                if (MyGame.coin >= 100){
                    soundBuy.play();
                    MyGame.coin = MyGame.coin-100;
                    MyGame.amount += 20;
                    root4.removeAll();
                    State01.rootOpenCoin = true;
                    root3.removeAll();
                    State01.rootOpenAmount = true;
                }else {
                    MoneyNo = assets().getImage("images/MoneyNo.png");
                    MoneyNoLayer = graphics().createImageLayer(MoneyNo);
                    layer.add(MoneyNoLayer);
                    canRemove = true;
                    t = 0;
                }
            }
        });

            basicGunIM = assets().getImage("images/Shop/Basic.png");
        basicGunLayer = graphics().createImageLayer(basicGunIM);
        layer.add(basicGunLayer);
        basicGunLayer.setTranslation(50,200);
        basicGunLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                if (MyGame.levelGun == 0){
                    if (MyGame.coin >= 20){
                        soundBuy.play();
                        MyGame.coin = MyGame.coin-20;



                        levelGun = 1;
                        root4.removeAll();

                        State01.root2.removeAll();
                        State01.rootOpenCoin = true;
                        State01.root1.removeAll();
                        State01.rootOpenGunLv = true;
                        checkGun = true;
                        basicGunIM = assets().getImage("images/Shop/Basic_buy.png");
                        basicGunLayer = graphics().createImageLayer(basicGunIM);
                        layer.add(basicGunLayer);
                        basicGunLayer.setTranslation(50,200);
                    }else {
                        MoneyNo = assets().getImage("images/MoneyNo.png");
                        MoneyNoLayer = graphics().createImageLayer(MoneyNo);
                        layer.add(MoneyNoLayer);
                        canRemove = true;
                        t = 0;
                    }
                }
            }
        });


        nomalGunIm = assets().getImage("images/Shop/Nomal.png");
        nomalGunLayer = graphics().createImageLayer(nomalGunIm);
        layer.add(nomalGunLayer);
        nomalGunLayer.setTranslation(250,200);
        nomalGunLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                if (MyGame.levelGun == 0 || MyGame.levelGun == 1){
                    if (MyGame.coin >= 40){
                        soundBuy.play();
                        MyGame.coin = MyGame.coin-40;


                        levelGun = 2;

                        root4.removeAll();

                        State01.root2.removeAll();
                        State01.rootOpenCoin = true;
                        State01.root1.removeAll();
                        State01.rootOpenGunLv = true;
                        checkGun = true;
                        nomalGunIm = assets().getImage("images/Shop/Nomal_Buy.png");
                        nomalGunLayer = graphics().createImageLayer(nomalGunIm);
                        layer.add(nomalGunLayer);
                        nomalGunLayer.setTranslation(250,200);
                    }else {
                        MoneyNo = assets().getImage("images/Shop/MoneyNo.png");
                        MoneyNoLayer = graphics().createImageLayer(MoneyNo);
                        layer.add(MoneyNoLayer);
                        canRemove = true;
                        t = 0;
                    }
                }
            }
        });

        heavyGunIm = assets().getImage("images/Shop/Heavy.png");
        heavyGunLayer = graphics().createImageLayer(heavyGunIm);
        layer.add(heavyGunLayer);
        heavyGunLayer.setTranslation(450,200);
        heavyGunLayer.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                if (MyGame.levelGun == 0 || MyGame.levelGun == 1 || MyGame.levelGun == 2){
                    if (MyGame.coin >= 60){
                        soundBuy.play();
                        MyGame.coin = MyGame.coin-60;


                        levelGun = 3;

                        root4.removeAll();
                        State01.root2.removeAll();
                        State01.rootOpenCoin = true;
                        State01.root1.removeAll();
                        State01.rootOpenGunLv = true;

                        checkGun = true;
                        heavyGunIm = assets().getImage("images/Shop/Heavy_buy.png");
                        heavyGunLayer = graphics().createImageLayer(heavyGunIm);
                        layer.add(heavyGunLayer);
                        heavyGunLayer.setTranslation(450,200);
                    }else {
                        MoneyNo = assets().getImage("images/Shop/MoneyNo.png");
                        MoneyNoLayer = graphics().createImageLayer(MoneyNo);
                        layer.add(MoneyNoLayer);
                        canRemove = true;
                        t = 0;
                    }
                }
            }
        });
    }

    /*public boolean isBasicGun(){
        return basicGun;
    }
    public boolean isNomalGun(){
        return nomalGun;
    }
    public boolean isHeavyGun(){
        return heavyGun;
    }*/

    public static int getLevelGun(){
        return levelGun;
    }

    @Override
    public void update(int delta) {
        System.out.println(MyGame.stateNow+" <<< StateNow");
        t += delta;
        if (canRemove)
            if (t == 200){
                layer.remove(MoneyNoLayer);
                canRemove = false;
            }
        System.out.println(levelGun + "<<< Level Gun");
        if (checkGun == true){
            if (levelGun == 1 || levelGun == 2 || levelGun == 3){
                basicGunIM = assets().getImage("images/Shop/Basic_buy.png");
                basicGunLayer = graphics().createImageLayer(basicGunIM);
                layer.add(basicGunLayer);
                basicGunLayer.setTranslation(50,200);
                checkGun = false;
            }
            if (levelGun == 2 || levelGun == 3){
                basicGunIM = assets().getImage("images/Shop/Basic_buy.png");
                basicGunLayer = graphics().createImageLayer(basicGunIM);
                layer.add(basicGunLayer);
                basicGunLayer.setTranslation(50,200);
                nomalGunIm = assets().getImage("images/Shop/Nomal_Buy.png");
                nomalGunLayer = graphics().createImageLayer(nomalGunIm);
                layer.add(nomalGunLayer);
                nomalGunLayer.setTranslation(250,200);
                checkGun = false;
            }
            if (levelGun == 3){
                basicGunIM = assets().getImage("images/Shop/Basic_buy.png");
                basicGunLayer = graphics().createImageLayer(basicGunIM);
                layer.add(basicGunLayer);
                basicGunLayer.setTranslation(50,200);
                nomalGunIm = assets().getImage("images/Shop/Nomal_Buy.png");
                nomalGunLayer = graphics().createImageLayer(nomalGunIm);
                layer.add(nomalGunLayer);
                nomalGunLayer.setTranslation(250,200);
                heavyGunIm = assets().getImage("images/Shop/Heavy_buy.png");
                heavyGunLayer = graphics().createImageLayer(heavyGunIm);
                layer.add(heavyGunLayer);
                heavyGunLayer.setTranslation(450,200);
                checkGun = false;
            }
        }

        /*if (MyGame.stateNow == 1){*/
            if (State01.rootOpenAmount){
                root3 = iface.createRoot(
                        AxisLayout.vertical().gap(15),
                        SimpleStyles.newSheet(), layer);
                root3.setSize(700, 50);
                root3.add(new Label("Amount "+MyGame.amount).addStyles(Style.FONT.is(State01.TITLE_FONT)));
                State01.rootOpenAmount = false;
            }

            if (State01.rootOpenCoin){
                root4 = iface.createRoot(
                        AxisLayout.vertical().gap(15),
                        SimpleStyles.newSheet(), layer);
                root4.setSize(1050, 50);
                root4.add(new Label("Money "+MyGame.coin).addStyles(Style.FONT.is(State01.TITLE_FONT)));
                State01.rootOpenCoin = false;
            }
        /*}*/
        /*if (MyGame.stateNow == 2){
            if (State01.rootOpenAmount){
                root3 = iface.createRoot(
                        AxisLayout.vertical().gap(15),
                        SimpleStyles.newSheet(), layer);
                root3.setSize(700, 50);
                root3.add(new Label("Amount "+MyGame.amount).addStyles(Style.FONT.is(State01.TITLE_FONT)));
                State01.rootOpenAmount = false;
            }

            if (State01.rootOpenCoin){
                root4 = iface.createRoot(
                        AxisLayout.vertical().gap(15),
                        SimpleStyles.newSheet(), layer);
                root4.setSize(1050, 50);
                root4.add(new Label("Money "+MyGame.coin).addStyles(Style.FONT.is(State01.TITLE_FONT)));
                State01.rootOpenCoin = false;
            }
        }*/
    }
}
