package Event.BattleNoFeel.core;


import Shop.Shop;
import playn.core.Game;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

public class MyGame extends Game.Default {

    private ScreenStack ss = new ScreenStack();
    public static final int UPDATE_RATE=25;
    protected final Clock.Source clock = new Clock.Source(UPDATE_RATE);
    public static int coin = 10000,amount =40050,levelGun = 0;
    private Shop sh;
    public static int stateNow = 1;
    public static boolean EnemyDie = true;

  public MyGame() {
    super(UPDATE_RATE); // call update every 33ms (30 times per second)
  }

  @Override
  public void init() {
    // create and add background image layer
/*    Image bgImage = assets().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);*/


      ss.push(new HomeScreen(ss));

  }

  @Override
  public void update(int delta) {
      ss.update(delta);
      this.levelGun = sh.getLevelGun();
  }

    @Override
    public void paint(float alpha) {
        clock.paint(alpha);
        ss.paint(clock);
    }
}
