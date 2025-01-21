package henson.midp;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

import henson.midp.View.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MIDlet1 extends MIDlet
{
  static MIDlet1 instance;

  public static RmsManager rmsManager=null;
  public static ScreenManager screenManager=null;
  TimeManager timeManager=null;
  MainScene scene=null;

  public MIDlet1()
  {
    instance = this;
  }

  public void startApp()
  {
    boolean isFoundSettings=false;
    //
    if(scene==null)
      scene=new MainScene();
    //
    if(rmsManager==null)
    {
      rmsManager = new RmsManager();
      isFoundSettings=rmsManager.isAvailable();
      rmsManager.loadRMS();
    }
    //
    if(screenManager==null)
    {
      screenManager = new ScreenManager(Display.getDisplay(this));
      screenManager.pushScreen(scene);
      if(!isFoundSettings)
      {
        // Установить дом
        ListForm lf=new ListForm("Locations", ListForm.TYPE_PLACES);
        lf.updateData(null);
        screenManager.pushScreen(lf);
        lf.selectHome();
      }
    }
    //
    if(timeManager==null)
      timeManager = new TimeManager(scene);
    timeManager.Start();
  }

  public void pauseApp()
  {
    if(timeManager!=null)
      timeManager.Stop();
  }

  public void destroyApp(boolean unconditional)
  {
    if(timeManager!=null)
      timeManager.Stop();
  }

  public static void quitApp()
  {
    instance.destroyApp(true);
    instance.notifyDestroyed();
    instance = null;
  }
}
