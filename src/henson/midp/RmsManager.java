package henson.midp;

import javax.microedition.rms.*;
import java.io.*;
import java.util.*;
import henson.midp.Model.*;

public class RmsManager
{
  final static String RMS_NAME="S21";
  // Места
  public Vector places=null;
  // Текущее место
  public int placeIndex=0;
  // Агенты (Смит)
  public Vector agents=null;

  public RmsManager()
  {
    places=new Vector();
    for(int i=0; i<10; i++)
      places.addElement(new Place("<Empty>", 0.0f, 0.0f, i-5));
    //
    agents=new Vector();
  }

  public boolean isAvailable()
  {
    try
    {
      RecordStore rs = RecordStore.openRecordStore(RMS_NAME, false);
      rs.closeRecordStore();
    }
    catch (RecordStoreException ex) {
      return false;
    }
    return true;
  }

  public boolean loadRMS()
  {
    try
    {
      RecordStore rs = RecordStore.openRecordStore(RMS_NAME, false);
      RecordEnumeration re = rs.enumerateRecords(null, null, false);
      if (re.hasNextElement())
      {
        byte[] data = re.nextRecord();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        try
        {
          int version = dis.readInt();
          //
          if(version>3)
          {
            places.removeAllElements();
            int count = dis.readInt();
            for (int i = 0; i < count; i++) {
              Place p = new Place();
              p.Load(dis);
              places.addElement(p);
            }
            placeIndex = dis.readInt();
            //
            agents.removeAllElements();
            count = dis.readInt();
            for (int i = 0; i < count; i++) {
              Agent a = new Agent();
              a.Load(dis);
              agents.addElement(a);
            }
          }
          //
          dis.close();
          bais.close();
        }
        catch (IOException ex)
        {
        }
      }
      //
      rs.closeRecordStore();
    }
    catch (RecordStoreException ex)
    {
      try
      {
        RecordStore.deleteRecordStore(RMS_NAME);
      }
      catch (RecordStoreException e)
      {
      }
      return false;
    }
    //
    return true;
  }

  public Place getCurrentPlace()
  {
    if(places==null || places.size()<=placeIndex)
      return null;
    else
      return (Place)places.elementAt(placeIndex);
  }

  public Place getHomePlace()
  {
    if(places==null || places.size()<1)
      return null;
    else
      return (Place)places.elementAt(0);
  }

  public boolean saveRMS()
  {
    try {
      RecordStore.deleteRecordStore(RMS_NAME);
    }
    catch (RecordStoreException ex) {
    }
    try {
      RecordStore rs = RecordStore.openRecordStore(RMS_NAME, true);
      //
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      try
      {
        dos.writeInt(4);
        //
        dos.writeInt(places.size());
        for(int i=0; i<places.size(); i++)
        {
          Place p=(Place)places.elementAt(i);
          p.Save(dos);
        }
        //
        dos.writeInt(placeIndex);
        //
        dos.writeInt(agents.size());
        for(int i=0; i<agents.size(); i++)
        {
          Agent a=(Agent)agents.elementAt(i);
          a.Save(dos);
        }
        //
        dos.writeInt(placeIndex);
        //
        dos.close();
        baos.close();
      }
      catch (IOException ex) {
      }
      //
      rs.addRecord(baos.toByteArray(), 0, baos.toByteArray().length);
      //
      rs.closeRecordStore();
    }
    catch (RecordStoreException ex)
    {
      return false;
    }
    //
    return true;
  }
}
