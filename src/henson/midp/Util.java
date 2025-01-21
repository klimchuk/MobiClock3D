package henson.midp;

import javax.microedition.lcdui.*;
import java.util.*;
import java.io.*;
import henson.midp.Model.*;

public class Util
{
  public static Image makeImage(String filename)
  {
    Image image = null;
    try
    {
      image = Image.createImage(filename);
    }
    catch (Exception e)
    {
    }
    return image;
  }

  public static WeatherStation getNearestStation(String filename)
  {
    WeatherStation s=null;
    Place p=MIDlet1.rmsManager.getCurrentPlace();
    if(p==null)
      return null;
    //
    double min=360.0;
    Vector v=new Vector();
    StringBuffer sb=new StringBuffer();
    InputStream is=v.getClass().getResourceAsStream(filename);
    try
    {
      while(true)
      {
        int n = is.read();
        if(n==-1)
          break;
        char ch=(char)n;
        if(n>32)
            sb.append(ch);
        else
        {
          if(sb.length()>0)
          {
            v.addElement(sb.toString());
            sb.setLength(0);
            //
            if(v.size()==3)
            {
              WeatherStation c=new WeatherStation((String)v.elementAt(0), Float.parseFloat((String)v.elementAt(1)), Float.parseFloat((String)v.elementAt(2)));
              double diff=Math.abs(p.Latitude-c.Latitude)+Math.abs(p.Longitude-c.Longitude);
              if(diff<min)
              {
                s=c;
                min=diff;
              }
              v.removeAllElements();
            }
          }
        }
      }
      //
      if(sb.length()>0)
        v.addElement(sb.toString());
      //
      if(v.size()==3)
      {
        WeatherStation c=new WeatherStation((String)v.elementAt(0), Float.parseFloat((String)v.elementAt(1)), Float.parseFloat((String)v.elementAt(2)));
        double diff=Math.abs(p.Latitude-c.Latitude)+Math.abs(p.Longitude-c.Longitude);
        if(diff<min)
        {
          s=c;
          min=diff;
        }
        v.removeAllElements();
      }
    }
    catch (IOException ex)
    {
      return null;
    }
    return s;
  }

  public static double normalize(double angle, int level)
  {
    while(angle<0)
      angle+=level;
    while(angle>=level)
      angle-=level;
    return angle;
  }

  public static String toHourMin(double val)
  {
    if(val==Double.MIN_VALUE)
      return "--:--";
    if(val==Double.MAX_VALUE)
      return "++:++";
    //
    int hour=(int)Math.floor(val);
    int min=(int)((val-hour)*60.0);
    return hour+":"+(min<10?"0"+min:""+min);
  }
}
