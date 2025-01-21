package henson.midp.Model;

import java.io.*;
import java.util.*;
import henson.midp.*;

public class Place
{
  public String Name="<Empty>";
  public float Latitude=0.0f;
  public float Longitude=0.0f;
  public float TimeZone=0.0f;
  public String weatherStation="";
  // Погода
  public Weather weather=null;
  // Места (знать надо)
  public Vector locs=new Vector();
  // События
  public Vector events=new Vector();

  public Place()
  {
  }

  public Place(String Name, float Latitude, float Longitude, float TimeZone)
  {
    this.Name=Name;
    this.Latitude=Latitude;
    this.Longitude=Longitude;
    this.TimeZone=TimeZone;
    this.weatherStation="";
  }

  public String toString()
  {
    return Name + " GMT" + (TimeZone < 0 ? "-" : "+") + Math.abs(TimeZone);
  }

  public void Load(DataInputStream dis) throws IOException
  {
    Name = dis.readUTF();
    Latitude=dis.readFloat();
    Longitude=dis.readFloat();
    TimeZone=dis.readFloat();
    weatherStation=dis.readUTF();
    //
    /*
    locs.removeAllElements();
    int size=dis.readInt();
    for(int i=0; i<size; i++)
    {
      Location l=new Location();
      l.Load(dis);
      locs.addElement(l);
    }
    //
    events.removeAllElements();
    size=dis.readInt();
    for(int i=0; i<size; i++)
    {
      Event e=new Event();
      e.Load(dis);
      events.addElement(e);
    }*/
  }

  public void Save(DataOutputStream dos) throws IOException
  {
    dos.writeUTF(Name);
    dos.writeFloat(Latitude);
    dos.writeFloat(Longitude);
    dos.writeFloat(TimeZone);
    dos.writeUTF(weatherStation);
    //
    /*
    dos.writeInt(locs.size());
    for(int i=0; i<locs.size(); i++)
    {
      Location l=(Location)locs.elementAt(i);
      l.Save(dos);
    }
    //
    dos.writeInt(events.size());
    for(int i=0; i<events.size(); i++)
    {
      Event e=(Event)events.elementAt(i);
      e.Save(dos);
    }*/
  }

  public void getWeather()
  {
    if(weather==null)
      weather=new Weather();
    //
    if(weatherStation==null || weatherStation.length()<4)
    {
      WeatherStation ws = Util.getNearestStation("/xws.dat");
      weatherStation=ws.name;
    }
    //
    if(weatherStation!=null || weatherStation.length()==4)
      weather.getWeather(weatherStation);
  }
}
