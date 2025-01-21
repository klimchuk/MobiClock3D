package henson.midp.Model;

import java.io.*;
import java.util.*;

public class Event
{
  public Date date;
  public int duration;
  //
  public String location;
  //
  public Vector agents=null;
  //
  public String name;
  public String description;

  public Event()
  {
    date=new Date();
    agents=new Vector();
  }

  public boolean Save(DataOutputStream dos)
  {
    try
    {
      dos.writeLong(date.getTime());
      dos.writeInt(duration);
      //
      dos.writeUTF(location);
      //
      dos.writeInt(agents.size());
      for(int i=0; i<agents.size(); i++)
        dos.writeUTF((String)agents.elementAt(i));
      //
      dos.writeUTF(name);
      dos.writeUTF(description);
    }
    catch (IOException ex)
    {
      return false;
    }
    return true;
  }

  public boolean Load(DataInputStream dis)
  {
    try
    {
      date.setTime(dis.readLong());
      duration=dis.readInt();
      //
      location=dis.readUTF();
      //
      agents.removeAllElements();
      int size=dis.readInt();
      for(int i=0; i<size; i++)
        agents.addElement(dis.readUTF());
      //
      name=dis.readUTF();
      description=dis.readUTF();
    }
    catch (IOException ex)
    {
      return false;
    }
    return true;
  }
}
