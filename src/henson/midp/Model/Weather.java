package henson.midp.Model;

import org.kobjects.serialization.*;
import org.ksoap.*;
import org.ksoap.transport.*;
import henson.midp.*;
import henson.midp.View.*;
import java.util.*;
//import org.ksoap2.*;
//import org.ksoap2.transport.*;
//import org.ksoap2.serialization.*;

public class Weather implements Runnable
{
  String name="";
  String result="";
  Object weatherInfo=null;

  public Weather()
  {
  }

  public Vector getDescription()
  {
    Vector v=new Vector();
    v.addElement(name);
    //
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return v;
    //
    v.addElement(new String("Timestamp"));
    v.addElement(this.getObjectText("timestamp"));
    //
    v.addElement(new String("Station"));
    v.addElement(this.getObjectText("station"));
    //
    v.addElement(new String("Phenomena"));
    if(!this.appendArrayText("phenomena", v))
      v.addElement("No data");
    //
    v.addElement(new String("Precipitation"));
    if (!this.appendArrayText("precipitation", v))
      v.addElement("No data");
    //
    v.addElement(new String("Extremes"));
    if (!this.appendArrayText("extremes", v))
      v.addElement("No data");
    //
    v.addElement(new String("Pressure"));
    v.addElement(this.getObjectText("pressure"));
    //
    v.addElement(new String("Sky"));
    v.addElement(this.getObjectText("sky"));
    //
    v.addElement(new String("Temperature"));
    v.addElement(this.getObjectText("temperature"));
    //
    v.addElement(new String("Visibility"));
    v.addElement(this.getObjectText("visibility"));
    //
    v.addElement(new String("Wind"));
    v.addElement(this.getObjectText("wind"));
    //
    /*
    SoapObject resp=(SoapObject)weatherInfo;
    for(int i=0; i<resp.getPropertyCount(); i++)
    {
      PropertyInfo pi=new PropertyInfo();
      resp.getPropertyInfo(i, pi);
      //
      v.addElement(new String(pi.name));
      Object o=resp.getProperty(i);
      if(o instanceof SoapObject)
      {
        SoapObject ssss=(SoapObject)o;
        if(ssss.getPropertyCount()>0)
        {
          StringBuffer sb=new StringBuffer();
          for(int j=0; j<ssss.getPropertyCount(); j++)
          {
            Object ooo=ssss.getProperty(j);
            if(ooo!=null)
            {
              if(ooo instanceof SoapObject)
              {
                SoapObject kkkk=(SoapObject)ooo;
                if(kkkk.getPropertyCount()>0)
                {
                  for(int p=0; p<kkkk.getPropertyCount(); p++)
                  {
                    Object ttt=kkkk.getProperty(p);
                    if(ttt!=null)
                      sb.append(ttt.toString() + " ");
                  }
                }
              }
              else
                sb.append(ooo.toString() + " ");
            }
          }
          v.addElement(sb.toString());
        }
        else
          v.addElement("No data");
      }
      else
          v.addElement(resp.getProperty(i).toString());
    }*/
    //
    return v;
  }

  public double getWindSpeed()
  {
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return 0.0;
    //
    SoapObject resp=(SoapObject)weatherInfo;
    SoapObject so=(SoapObject)resp.getProperty("wind");
    SoapPrimitive sp=(SoapPrimitive)so.getProperty("prevailing_speed");
    return Double.parseDouble(sp.toString());
  }

  public int getWindDirection()
  {
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return 0;
    //
    SoapObject resp=(SoapObject)weatherInfo;
    SoapObject so=(SoapObject)resp.getProperty("wind");
    SoapObject so2=(SoapObject)so.getProperty("prevailing_direction");
    Integer iobj=(Integer)so2.getProperty("degrees");
    return iobj.intValue();
  }

  public double getTemperature()
  {
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return 0.0;
    //
    SoapObject resp=(SoapObject)weatherInfo;
    SoapObject so=(SoapObject)resp.getProperty("temperature");
    SoapPrimitive sp=(SoapPrimitive)so.getProperty("ambient");
    return Double.parseDouble(sp.toString());
  }

  public String getObjectText(String name)
  {
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return "No data";
    //
    SoapObject resp=(SoapObject)weatherInfo;
    Object o=resp.getProperty(name);
    if(o instanceof SoapObject)
    {
      SoapObject so=(SoapObject)o;
      return so.getProperty("string").toString();
    }
    else
      return o.toString();
  }


  public boolean appendArrayText(String name, Vector v)
  {
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return false;
    //
    SoapObject resp=(SoapObject)weatherInfo;
    Object o=(Object)resp.getProperty(name);
    if(o instanceof SoapObject)
    {
      StringBuffer sb=new StringBuffer();
      SoapObject so=(SoapObject)o;
      for(int i=0; i<so.getPropertyCount(); i++)
      {
        Object ob=(Object)so.getProperty(i);
        if(ob instanceof SoapObject)
        {
          SoapObject sob=(SoapObject)ob;
          sb.append(sob.getProperty("string").toString() + " ");
        }
        else
          sb.append(ob.toString()+" ");
      }
      v.addElement(sb.toString());
      return true;
    }
    //
    return false;
  }

  public double getVisibility()
  {
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return 0.0;
    //
    SoapObject resp=(SoapObject)weatherInfo;
    SoapObject so=(SoapObject)resp.getProperty("visibility");
    SoapPrimitive sp=(SoapPrimitive)so.getProperty("distance");
    return Double.parseDouble(sp.toString());
  }

  public String getVisibilityQualifier()
  {
    if(weatherInfo==null || !(weatherInfo instanceof SoapObject))
      return "";
    //
    SoapObject resp=(SoapObject)weatherInfo;
    SoapObject so=(SoapObject)resp.getProperty("visibility");
    return so.getProperty("qualifier").toString();
  }

  /*
  public void run()
  {
    try {
      // build request string
      SoapObject rpc = new SoapObject("capeconnect:GlobalWeather:GlobalWeather", "getWeatherReport");

      rpc.addProperty("code", name);

      SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

      envelope.bodyOut = rpc;

      result=name;

      HttpTransport ht = new HttpTransport("http://live.capescience.com:80/ccx/GlobalWeather");
      //ht.debug = true;

      ht.call("getWeatherReport", envelope);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result="Error:"+e.toString();
    }

  }
*/

  public void run()
  {
    Object obj=null;
    try {
      // build request string
      SoapObject rpc = new SoapObject("capeconnect:GlobalWeather:GlobalWeather", "getWeatherReport");
      rpc.addProperty("code", name);
      HttpTransport ht = new HttpTransport("http://live.capescience.com:80/ccx/GlobalWeather","capeconnect:GlobalWeather:GlobalWeather#getWeatherReport");
      weatherInfo=ht.call(rpc);
      //
      InfoForm info=new InfoForm(getDescription());
      MIDlet1.screenManager.pushScreen(info);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      result="Error:"+e.toString();
    }

  }

  public boolean getWeather(String name)
  {
    this.name=name;

    new Thread(this).start();
    //
    return true;
  }
}
