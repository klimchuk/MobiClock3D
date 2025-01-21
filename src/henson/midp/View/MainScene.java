package henson.midp.View;

import javax.microedition.lcdui.*;
import javax.microedition.m3g.*;
import java.util.*;
import henson.midp.*;
import henson.midp.Model.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class MainScene extends Canvas implements CommandListener, ITimeHandler
{
  private final static int SCREEN_MAIN=0;
  private final static int SCREEN_ASTRO=1;
  private final static int SCREEN_CALENDAR=2;
  private final static int SCREEN_EVENTS=3;
  private final static int SCREEN_WEATHER=4;
  private final static int SCREEN_MAP=5;

  private Graphics3D iG3D;

  private Background iBackground=new Background();
  private Camera iCamera;
  private Light iLight=null;
  private Light iLightAmbient=null;
  private Light[] iLightSnap=null;

  private Plate upPanel=null;
  private Cylinder[] disks=null;
  private Plate downPanel=null;
  private Plate skyPanel=null;
  private Plate groundPanel=null;

  String message="";
  Random rand=new Random();

  int screen=SCREEN_MAIN;
  Image earthMap=null;

  public MainScene()
  {
    // Set up this Displayable to listen to command events
    setCommandListener(this);
    // add the Exit command
    addCommand(CommandManager.exitCommand);
    addCommand(CommandManager.placesCommand);
    addCommand(CommandManager.refreshweatherCommand);
    addCommand(CommandManager.reportweatherCommand);
    addCommand(CommandManager.addeventCommand);
    addCommand(CommandManager.agentsCommand);
    addCommand(CommandManager.placesCommand);
  }

  public void commandAction(Command command, Displayable displayable)
  {
    if(command==CommandManager.exitCommand)
      MIDlet1.screenManager.popScreen();
    if(command==CommandManager.placesCommand)
    {
      ListForm lf=new ListForm("Locations", ListForm.TYPE_PLACES);
      MIDlet1.screenManager.pushScreen(lf);
      lf.updateData(null);
    }
    if(command==CommandManager.refreshweatherCommand)
    {
      Place p=MIDlet1.rmsManager.getCurrentPlace();
      p.getWeather();
    }
    if(command==CommandManager.reportweatherCommand)
    {
      Place p=MIDlet1.rmsManager.getCurrentPlace();
      if(p!=null & p.weather!=null)
      {
        InfoForm info = new InfoForm(p.weather.getDescription());
        MIDlet1.screenManager.pushScreen(info);
      }
    }
  }

  protected void paint(Graphics g)
  {
    if(iG3D!=null)
    {
      iG3D.bindTarget(g, true, Graphics3D.ANTIALIAS | Graphics3D.DITHER | Graphics3D.TRUE_COLOR);
      //
      iG3D.clear(iBackground);
      //
      Transform transform = new Transform();
      transform.postTranslate(0, 0, 300);
      iG3D.setCamera(iCamera, transform);
      // Поворачиваем диски
      Place h=MIDlet1.rmsManager.getHomePlace();
      Place p=MIDlet1.rmsManager.getCurrentPlace();
      Calendar cal=Calendar.getInstance();
      //
      float diff=p.TimeZone-h.TimeZone;
      long t=cal.getTime().getTime()+(long)Math.ceil(diff*3600000);
      // Отвелекаем внимание
      Calendar cal2=Calendar.getInstance();
      cal.setTime(new Date(t));
      int hour=cal.get(Calendar.HOUR_OF_DAY);
      int minute=cal.get(Calendar.MINUTE);
      //
      Astro a=new Astro();
      a.Calculate(p, cal);
      //double sunset=Sun.calculate(false, p, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR), Sun.ZENITH_OFFICIAL);
      //double sunrise=Sun.calculate(true, p, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH)+1, cal.get(Calendar.YEAR), Sun.ZENITH_OFFICIAL);
      //
      iG3D.resetLights();
      double altit=Double.parseDouble(a.altity);
      if(screen==SCREEN_MAIN)
      {
        if (altit > -18.0)
          iLight.setIntensity(0.1f + (float)((altit+18.0)/120.0));
        else
          iLight.setIntensity(0.1f);
      }
      else
        iLight.setIntensity(0.1f);
      //
      iG3D.addLight(iLight, transform);
      if(screen==SCREEN_MAIN)
        iLightAmbient.setIntensity(1.0f);
      else
        iLightAmbient.setIntensity(0.1f);
      iG3D.addLight(iLightAmbient, transform);
      //
      iG3D.addLight(iLightSnap[0], transform);
      transform.postTranslate(-70f, 0, 0);
      iG3D.addLight(iLightSnap[1], transform);
      transform.postTranslate(140f, 0, 0);
      iG3D.addLight(iLightSnap[2], transform);
      //
      transform.setIdentity();
      groundPanel.render(iG3D, transform);
      //upPanel.render(iG3D, transform);
      //downPanel.render(iG3D, transform);
      skyPanel.render(iG3D, transform);
      //
      int n0=hour/10;
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-n0*36+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, -80f);
      disks[0].render(iG3D, transform);
      //
      int n1=hour-n0*10;
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-n1*36+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, -50f);
      disks[1].render(iG3D, transform);
      //
      int n2=minute/10;
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-n2*36+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, -15f);
      disks[2].render(iG3D, transform);
      //
      int n3=minute-n2*10;
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-n3*36+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, 15f);
      disks[3].render(iG3D, transform);
      //
      int n4=cal.get(Calendar.SECOND)/10;
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-n4*36+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, 50f);
      disks[4].render(iG3D, transform);
      //
      int n5=cal.get(Calendar.SECOND)-n4*10;
      int n6=cal.get(Calendar.MILLISECOND);
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-(n5*36.0f+n6*36.0f/1000.0f)+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, 80f);
      disks[5].render(iG3D, transform);
      //
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-(n5*36.0f+n6*36.0f/1000.0f)+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, -32.5f);
      disks[6].render(iG3D, transform);
      //
      transform.setIdentity();
      transform.postRotate(90, 0.0f, 1.0f, 0.0f);
      transform.postRotate(-(n5*36.0f+n6*36.0f/1000.0f)+180, 0.0f, 0.0f, 1.0f);
      transform.postTranslate(0.0f, 0.0f, 32.5f);
      disks[7].render(iG3D, transform);
      //
      iG3D.releaseTarget();
      //g.drawString(n0+""+n1+":"+n2+""+n3+":"+n4+""+n5, 1, 10, Graphics.TOP|Graphics.LEFT);
      g.drawString(p.toString(), 1, 10, Graphics.TOP|Graphics.LEFT);
      //g.drawString((cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.YEAR)+" "+a.risy+" "+a.sety+" "+a.altity+" "+a.decly+" "+a.azimy, 1, 20, Graphics.TOP|Graphics.LEFT);
      //
      switch(screen)
      {
      case SCREEN_ASTRO:
         {
           int d = getWidth() * 8 / 10;
           int bx = getWidth() / 10;
           int by = (getHeight() - d) / 2;
           //
           g.setColor(0xff, 0x40, 0x40);
           g.fillArc(bx, by, d, d, 0, (int) Double.parseDouble(a.altity));
           //
           g.setColor(0xff, 0xff, 0x80);
           g.drawArc(bx, by, d, d, 0, 360);
           g.drawArc( (int) (bx + d * 0.05), (int) (by + d * 0.05),
                     (int) (d * 0.9), (int) (d * 0.9), 0, 360);
           // North
           drawArrow(g, 270.0, d / 2, 0);
           g.drawString("North", getWidth() / 2, by - 2,
                        Graphics.HCENTER | Graphics.BOTTOM);
           // Sun
           drawArrow(g, Double.parseDouble(a.azimy) + 270.0, d * 0.45, 5);
           g.drawString("Az:" + a.azimy + " Alt:" + a.altity, getWidth() / 2,
                        getHeight() - by + 2, Graphics.HCENTER | Graphics.TOP);
           // Sunset and sunrise
           g.drawString(a.risy, getWidth()/2-d/4, getHeight()/2-d/4, Graphics.HCENTER | Graphics.TOP);
           g.drawString(a.sety, getWidth()/2+d/4, getHeight()/2-d/4, Graphics.HCENTER | Graphics.TOP);
           //
           drawMarks(g, d / 2);
         }
         //
         break;
       case SCREEN_WEATHER:
          {
            int d = getWidth() * 8 / 10;
            int bx = getWidth() / 10;
            int by = (getHeight() - d) / 2;
            //
            //g.setColor(0xff, 0x40, 0x40);
            //g.fillArc(bx, by, d, d, 0, (int) Double.parseDouble(a.altity));
            //
            g.setColor(0xff, 0xff, 0x80);
            g.drawArc(bx, by, d, d, 0, 360);
            g.drawArc( (int) (bx + d * 0.05), (int) (by + d * 0.05),
                      (int) (d * 0.9), (int) (d * 0.9), 0, 360);
            // North
            drawArrow(g, 270.0, d / 2, 0);
            g.drawString("North", getWidth() / 2, by - 2,
                         Graphics.HCENTER | Graphics.BOTTOM);
            // Wind
            if(p.weather!=null)
            {
              drawArrow(g, p.weather.getWindDirection() + 90.0, d * 0.45, 5);
              drawArrow(g, p.weather.getWindDirection() + 270.0, d * 0.45, 0);
              //
              g.drawString(p.weather.getObjectText("timestamp"), getWidth()/2, getHeight()/2+d/4, Graphics.TOP|Graphics.HCENTER);
              //
              g.drawString("t:" + p.weather.getTemperature() + "C wind:" +
                           p.weather.getWindDirection() + " " +
                           p.weather.getWindSpeed()+" m/s", getWidth() / 2,
                           getHeight() - by + 2, Graphics.HCENTER | Graphics.TOP);
              // Top Left
              g.drawString(p.weather.getVisibilityQualifier(), 2, by - 2, Graphics.BOTTOM|Graphics.LEFT);
              g.drawString(""+p.weather.getVisibility()+" m", 2, by + 8, Graphics.BOTTOM|Graphics.LEFT);
              // Top Right

            }
            //
            drawMarks(g, d / 2);
          }
          //
          break;
      case SCREEN_MAP:
        int xCenter=getWidth()/2;
        int yCenter=getHeight()/2;
        //
        if(earthMap==null)
          earthMap=Util.makeImage("/earthmap.png");
        int x=(int)((p.Longitude+180.0)*2.0);
        int y=360-(int)((p.Latitude+90.0)*2.0);
        int bx=-x+xCenter;
        if(bx>0) bx=0;
        if(bx<getWidth()-earthMap.getWidth()) bx=getWidth()-earthMap.getWidth();
        //
        int by=-y+yCenter;
        if(by>0) by=0;
        if(by<getHeight()-earthMap.getHeight()) by=getHeight()-earthMap.getHeight();
        //
        g.drawImage(earthMap, bx, by, Graphics.LEFT|Graphics.TOP);
        // Cursor
        g.setColor(0);
        g.drawLine((x+bx)-3, (y+by), (x+bx)+3, (y+by));
        g.drawLine((x+bx), (y+by)-3, (x+bx), (y+by)+3);
        //
        g.drawString(p.toString(), getWidth(), 2, Graphics.TOP|Graphics.RIGHT);
        //
        break;
      }
    }
    //
    g.drawString(message, 1, 1, Graphics.TOP|Graphics.LEFT);
  }

  private void drawArrow(Graphics g, double angle, double r, double diff)
  {
    int xCenter=getWidth()/2;
    int yCenter=getHeight()/2;
    //
    int x=(int)(Math.cos(Math.toRadians(angle))*r+xCenter);
    int y=(int)(Math.sin(Math.toRadians(angle))*r+yCenter);
    //
    int x1=(int)(Math.cos(Math.toRadians(angle+diff))*(r*0.8)+xCenter);
    int y1=(int)(Math.sin(Math.toRadians(angle+diff))*(r*0.8)+yCenter);
    //
    int x2=(int)(Math.cos(Math.toRadians(angle-diff))*(r*0.8)+xCenter);
    int y2=(int)(Math.sin(Math.toRadians(angle-diff))*(r*0.8)+yCenter);
    //
    g.drawLine(xCenter, yCenter, x, y);
    g.drawLine(x1, y1, x, y);
    g.drawLine(x2, y2, x, y);
  }

  private void drawMarks(Graphics g, double r)
  {
    int xCenter=getWidth()/2;
    int yCenter=getHeight()/2;
    //
    for(int i=0; i<360; i+=30)
    {
      int x = (int) (Math.cos(Math.toRadians(i)) * (r*0.95) + xCenter);
      int y = (int) (Math.sin(Math.toRadians(i)) * (r*0.95) + yCenter);
      g.drawLine(x-2, y, x+2, y);
      g.drawLine(x, y-2, x, y+2);
    }
  }

  public void initTime()
  {
    // Background
    iBackground.setColor(0xa0a0ff);
    // Camera
    iCamera=new Camera();
    iCamera.setPerspective(60.0f, (float)getWidth()/(float)getHeight(), 1.0f, 1000.0f);
    // Light
    iLight=new Light();
    iLight.setColor(0xffff80);
    iLight.setMode(Light.DIRECTIONAL);
    //
    iLightAmbient=new Light();
    iLightAmbient.setColor(0xffffff);
    iLightAmbient.setMode(Light.AMBIENT);
    //
    iLightSnap=new Light[3];
    //
    iLightSnap[0]=new Light();
    iLightSnap[0].setMode(Light.SPOT);
    iLightSnap[0].setColor(0xffffff);
    iLightSnap[0].setSpotAngle(10.0f);
    iLightSnap[0].setIntensity(0.9f);
    iLightSnap[0].setSpotExponent(0.5f);
    //
    iLightSnap[1]=new Light();
    iLightSnap[1].setMode(Light.SPOT);
    iLightSnap[1].setColor(0xffffff);
    iLightSnap[1].setSpotAngle(10.0f);
    iLightSnap[1].setIntensity(0.9f);
    iLightSnap[1].setSpotExponent(0.5f);
    //
    iLightSnap[2]=new Light();
    iLightSnap[2].setMode(Light.SPOT);
    iLightSnap[2].setColor(0xffffff);
    iLightSnap[2].setSpotAngle(10.0f);
    iLightSnap[2].setIntensity(0.9f);
    iLightSnap[2].setSpotExponent(0.5f);
    //
    upPanel=new Plate();
    disks=new Cylinder[8];
    for(int i=0; i<disks.length; i++)
      disks[i]=new Cylinder(80, (i<6?30:5), 10);
    downPanel=new Plate();
    skyPanel=new Plate();
    groundPanel=new Plate();
    //
    short[] vert6={ 100,100,80, -100,100,80, 100,40,80, -100,40,80 };
    byte[] norm6={ 0,0,127, 0,0,127, 0,0,127, 0,0,127 };
    upPanel.load(0x00ffffff, 0x00ffffff, vert6, norm6);
    //
    short[] vert7={ 100,-40,80, -100,-40,80, 100,-120,80, -100,-120,80 };
    byte[] norm7={ 0,0,127, 0,0,127, 0,0,127, 0,0,127 };
    downPanel.load(0x8000ffff, 0x8000ffff, vert7, norm7);
    //
    short[] vert8={ -1000,500,0, 1000,500,0, -1000,0,-500, 1000,0,-500 };
    byte[] norm8={ 0,0,127, 0,0,127, 0,0,127, 0,0,127 };
    short[] tex8={ 1,0, 0,0, 1,1, 1,0 };
    skyPanel.load("/Sky.png", vert8, norm8, tex8, 5.0f, 0x00ffffff);
    //
    short[] vert9={ 1000,-500,0, -1000,-500,0, 1000,0,-500, -1000,0,-500 };
    byte[] norm9={ 0,0,127, 0,0,127, 0,0,127, 0,0,127 };
    short[] tex9={ 1,0, 0,0, 1,1, 1,0 };
    groundPanel.load("/Ground.png", vert9, norm9, tex9, 5.0f, 0x00ffffff);
    //
    iG3D=Graphics3D.getInstance();
  }

  public void stepTime(long delay)
  {
    message=""+delay;
    //
    try
    {
      repaint();
      serviceRepaints();
      Thread.sleep(10);
    }
    catch (Exception e)
    {
      System.out.println("RUN => "+e.getMessage());
    }
  }

  protected void keyPressed(int keyCode)
  {
    switch(keyCode)
    {
    case Canvas.KEY_NUM0: MIDlet1.rmsManager.placeIndex=0; return;
    case Canvas.KEY_NUM1: MIDlet1.rmsManager.placeIndex=1; return;
    case Canvas.KEY_NUM2: MIDlet1.rmsManager.placeIndex=2; return;
    case Canvas.KEY_NUM3: MIDlet1.rmsManager.placeIndex=3; return;
    case Canvas.KEY_NUM4: MIDlet1.rmsManager.placeIndex=4; return;
    case Canvas.KEY_NUM5: MIDlet1.rmsManager.placeIndex=5; return;
    case Canvas.KEY_NUM6: MIDlet1.rmsManager.placeIndex=6; return;
    case Canvas.KEY_NUM7: MIDlet1.rmsManager.placeIndex=7; return;
    case Canvas.KEY_NUM8: MIDlet1.rmsManager.placeIndex=8; return;
    case Canvas.KEY_NUM9: MIDlet1.rmsManager.placeIndex=9; return;
    }
    //
    switch(this.getGameAction(keyCode))
    {
      case Canvas.LEFT:
        if (screen == SCREEN_MAIN)
          screen = SCREEN_ASTRO;
        break;
      case Canvas.RIGHT:
        if (screen == SCREEN_MAIN)
          screen = SCREEN_WEATHER;
        break;
      case Canvas.UP:
        if (screen == SCREEN_MAIN)
          screen = SCREEN_CALENDAR;
        break;
      case Canvas.DOWN:
        if (screen == SCREEN_MAIN)
          screen = SCREEN_EVENTS;
        break;
      case Canvas.FIRE:
        if (screen != SCREEN_MAIN)
          screen = SCREEN_MAIN;
        else
          screen = SCREEN_MAP;
        break;
    }
  }
}
