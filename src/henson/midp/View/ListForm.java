package henson.midp.View;

import javax.microedition.lcdui.*;
import henson.midp.*;
import henson.midp.Model.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ListForm extends List implements CommandListener, IUpdate
{
  public final static int TYPE_PLACES=0;
  public final static int TYPE_REGION=1;
  public final static int TYPE_CITIES=2;

  String[] regions={ "United States",
      "Canada",
      "Central America",
      "South America",
      "Northern Europe",
      "Western/Central Europe",
      "Southern Europe",
      "Eastern Europe",
      "Africa",
      "Middle East",
      "Indian Sub-continent",
      "South Asia",
      "East Asia",
      "Oceana"
  };

  String[] cities0={
      "61.22:-149.88:AST:-540:Anchorage, U.S.A."
      ,"33.75:-84.38:EST:-300:Atlanta, U.S.A."
      ,"42.33:-71.08:EST:-300:Boston, U.S.A."
      ,"40.03:-105.50:MST:-420:Boulder, U.S.A."
      ,"41.83:-87.75:CST:-360:Chicago, U.S.A."
      ,"37.35:-121.95:PST:-480:Cupertino, U.S.A."
      ,"39.73:-104.98:MST:-420:Denver, U.S.A."
      ,"42.38:-83.08:EST:-300:Detroit, U.S.A."
      ,"21.32:-157.87:HST:-600:Honolulu, U.S.A."
      ,"34.07:-118.25:PST:-480:Los Angeles, U.S.A."
      ,"25.77:-80.20:EST:-300:Miami, U.S.A."
      ,"44.98:-93.22:CST:-360:Minneapolis, U.S.A."
      ,"40.72:-74.02:EST:-300:New York City, U.S.A."
      ,"39.62:-75.12:EST:-300:Philadelphia, U.S.A."
      ,"33.30:-112.03:PNT:-420:Phoenix, U.S.A."
      ,"71.38:-156.3:AST:-540:Point Barrow, Alaska, U.S.A."
      ,"45.55:-122.60:PST:-480:Portland, U.S.A."
      ,"38.63:-90.18:CST:-360:Saint Louis, U.S.A."
      ,"40.77:-111.88:MST:-420:Salt Lake City, U.S.A."
      ,"32.72:-117.15:PST:-480:San Diego, U.S.A."
      ,"37.80:-122.40:PST:-480:San Francisco, U.S.A."
      ,"47.35:-122.20:PST:-480:Seattle, U.S.A."
      ,"38.63:-90.18:PST:-360:St. Louis, U.S.A."
      ,"38.92:-77.00:EST:-300:Washington, U.S.A."
  };

  String[] cities1={
      "51.05:-114.05:PST:-420:Calgary, Canada"
      ,"44.65:-63.58:PRT:-240:Halifax, Canada"
      ,"45.50:-73.60:EST:-300:Montr\u00e9al, Canada"
      ,"45.42:-75.70:EST:-300:Ottawa, Canada"
      ,"43.65:-79.33:EST:-300:Toronto, Canada"
      ,"49.27:-123.12:PST:-480:Vancouver, Canada"
      ,"49.90:-97.15:CST:-360:Winnipeg, Canada"
  };

  String[] cities2={
      "4.92:-52.30:AGT:-180:Cayenne, French Guiana"
,"19.40:-99.15:CST:-360:Ciudad de M\u00e9xico"
,"14.67:-90.37:CST:-360:Guatemala, Guatemala"
,"22.13:-82.37:EST:-300:Havana, Cuba"
,"10.50:-66.93:PRT:-240:Caracas, Venezuela"
,"12.10:-86.33:CST:-360:Managua, Nicaragua"
,"19.40:-99.15:CST:-360:Mexico City, Mexico"
,"9.00:-79.42:EST:-300:Panama City, Panama"
,"18.67:-72.33:EST:-300:Port-au-Prince, Haiti"
,"9.98:-84.07:CST:-360:San Jos\u00e9, Costa Rica"
,"13.67:-89.17:CST:-360:San Salvador"
,"18.50:-64.90:PRT:-240:Santo Domingo, Dominican Republic"
,"14.08:-87.23:CST:-360:Tegucigalpa, Honduras"

  };

  String[] cities3={
      "4.60:-74.08:CST:-300:Bogot\u00e1"
      ,"-15.78:-47.92:XXX:-180:Bras\u00edlia, Brazil"
      ,"-34.62:-58.40:XXX:-180:Buenos Aires, Argentina"
      ,"-16.50:-68.15:XXX:-240:La Paz, Bolivia"
      ,"-12.05:-77.05:XXX:-300:Lima, Peru"
      ,"-34.92:-56.17:XXX:-180:Montevideo"
      ,"-0.23:-78.50:XXX:-300:Quito, Ecuador"
      ,"-22.88:-43.28:XXX:-180:Rio de Janeiro"
      ,"-33.40:-70.67:XXX:-240:Santiago, Chile"
      ,"-23.55:-46.65:XXX:-180:S\u00e3o Paulo, Brazil"

  };

  String[] cities4={
      "55.72:12.57:XXX:60:K\u00f8benhavn, Denmark"
      ,"60.17:24.97:XXX:120:Helsinki, Finland"
      ,"60.3:19.13:XXX:120:Mariehamm, \u00c5land"
      ,"71.18:25.8:XXX:60:Nordkapp, Norway"
      ,"59.93:10.75:XXX:60:Oslo, Norway"
      ,"64.15:-21.95:XXX:0:Reykjav\u00edk, Iceland"
      ,"59.33:18.05:XXX:60:Stockholm, Sweden"

  };

  String[] cities5={
      "52.35:4.90:XXX:60:Amsterdam, Netherlands"
      ,"52.52:13.40:XXX:60:Berlin, Germany"
      ,"46.95:7.50:XXX:60:Bern. Switzerland"
      ,"50.73:7.10:XXX:60:Bonn, Germany"
      ,"50.83:4.35:XXX:60:Brussels, Belgium"
      ,"51.50:-3.22:XXX:0:Cardiff, Wales"
      ,"53.33:-6.25:XXX:0:Dublin, Ireland"
      ,"55.95:-3.22:XXX:0:Edinburgh, Scotland"
      ,"46.17:6.15:XXX:60:Geneva, Switzerland"
      ,"57.48:-4.20:XXX:0:Inverness, Scotland"
      ,"38.73:-9.13:XXX:0:Lisbon, Portugal"
      ,"51.50:-0.17:XXX:0:London, England"
      ,"40.42:-3.72:XXX:60:Madrid, Spain"
      ,"48.87:2.33:XXX:60:Paris, France"
      ,"48.22:16.37:XXX:60:Vienna, Austria"

  };

  String[] cities6={
      "39.92:32.83:XXX:120:Ankara, Turkey"
      ,"38.00:23.73:XXX:120:Athens, Greece"
      ,"41.03:28.98:XXX:120:Istanbul, Turkey"
      ,"41.88:12.50:XXX:60:Rome, Italy"

  };

  String[] cities7={
      "44.83:20.50:XXX:60:Belgrade, Serbia"
      ,"44.83:20.50:XXX:60:Beograd, Serbia"
      ,"44.45:26.17:XXX:120:Bucharest, Romania"
      ,"47.50:19.08:XXX:60:Budapest, Hungary"
      ,"50.50:30.47:XXX:120:Kiev, Ukraine"
      ,"55.75:37.58:XXX:180:Moscow, Russia"
      ,"55.03:83.05:XXX:420:Novosibirsk, Russia"
      ,"50.08:14.43:XXX:60:Prague, Czech Republic"
      ,"42.75:23.33:XXX:120:Sofia, Bulgaria"
      ,"59.92:30.25:XXX:180:St. Petersburg, Russia"
      ,"52.25:21.00:XXX:60:Warsaw, Poland"

  };

  String[] cities8={
      "5.58:0.10:XXX:0:Accra, Ghana"
      ,"9.05:38.70:XXX:180:Addis Ababa, Ethiopia"
      ,"12.77:45.02:XXX:180:Aden"
      ,"36.83:3.00:XXX:60:Algiers, Algeria"
      ,"-33.93:18.37:XXX:120:Cape Town, South Africa"
      ,"9.48:13.82:XXX:0:Conakry, Guinea"
      ,"14.57:17.48:XXX:0:Dakar, Senegal"
      ,"-6.83:39.20:XXX:180:Dar es Salaam, Tanzania"
      ,"11.50:43.08:XXX:180:Djibouti"
      ,"8.50:-13.28:XXX:0:Freetown, Sierra Leone"
      ,"-17.72:31.03:XXX:120:Harare"
      ,"0.33:32.50:XXX:180:Kampala, Uganda"
      ,"-4.33:15.25:XXX:60:Kinshasa"
      ,"6.45:3.38:XXX:60:Lagos, Nigeria"
      ,"-25.97:32.53:XXX:120:Maputo"
      ,"2.03:45.35:XXX:180:Mogadisho, Somalia"
      ,"6.30:10.78:XXX:0:Monrovia, Liberia"
      ,"-1.28:36.83:XXX:180:Nairobi, Kenya"
      ,"34.03:6.85:XXX:0:Rabat, Morocco"
      ,"15.45:44.20:XXX:180:Sanaa, Yemen"
      ,"32.88:13.20:XXX:120:Tripoli, Libya"
      ,"36.80:10.18:XXX:60:Tunis, Tunisia"

};

  String[] cities9={
      "24.47:54.42:XXX:240:Abu Dhabi, U.A.E."
      ,"31.95:35.93:XXX:120:Amman, Jordan"
      ,"33.33:44.43:XXX:180:Baghdad, Iraq"
      ,"33.87:35.50:XXX:120:Beirut, Lebanon"
      ,"30.05:31.25:XXX:120:Cairo, Egypt"
      ,"33.50:36.25:XXX:120:Damascus, Syria"
      ,"31.78:35.23:XXX:120:Jerusalem, Israel"
      ,"29.33:48.00:XXX:180:Kuwait"
      ,"26.20:50.58:XXX:180:Manama"
      ,"21.45:39.82:XXX:180:Mecca, Saudia Arabia"
      ,"23.48:58.55:XXX:240:Muscat"
      ,"24.65:46.72:XXX:180:Riyadh"
      ,"35.67:51.43:XXX:210:Tehran"

  };

  String[] cities10={
      "18.93:72.85:XXX:330:Bombay, India"
      ,"22.58:88.35:XXX:330:Calcutta, India"
      ,"6.93:79.97:XXX:330:Colombo, Sri Lanka"
      ,"23.70:90.37:XXX:360:Dhaka (Dacca), Bangladesh"
      ,"33.67:73.17:XXX:300:Islamabad, Pakistan"
      ,"34.50:69.20:XXX:270:Kabul, Afghanistan"
      ,"24.85:67.03:XXX:300:Karachi, Pakistan"
      ,"13.13:80.32:XXX:330:Madras, India"
      ,"28.62:77.22:XXX:330:New Delhi, India"

  };

  String[] cities11={
      "13.73:100.50:XXX:420:Bangkok, Thailand"
      ,"-6.13:106.75:XXX:420:Djakarta, Indonesia"
      ,"21.08:105.92:XXX:420:Hanoi, Vietnam"
      ,"10.97:106.67:XXX:480:Ho Chi Minh City, Vietnam"
      ,"3.15:101.68:XXX:480:Kuala Lumpur"
      ,"11.55:104.92:XXX:420:Phnom Penh, Cambodia"
      ,"16.75:96.33:XXX:390:Rangoon, Burma"
      ,"1.28:103.85:XXX:480:Singapore"

  };

  String[] cities12={
      "39.92:116.42:XXX:480:Beijing, China"
      ,"22.25:114.17:XXX:480:Hong Kong, China"
      ,"34.67:135.50:XXX:540:Osaka, Japan"
      ,"39.92:116.42:XXX:480:Peking, China"
      ,"39.00:125.50:XXX:540:Pyongyang, North Korea"
      ,"37.55:126.97:XXX:540:Seoul, Korea"
      ,"31.23:121.50:XXX:480:Shanghai, China"
      ,"25.07:121.48:XXX:480:Taipei"
      ,"41.33:69.17:XXX:300:Tashkent"
      ,"35.70:139.77:XXX:540:Tokyo, Japan"
      ,"47.92:106.88:XXX:480:Ulaanbaator, Mongolia"

  };

  String[] cities13={
      "-34.87:138.50:XXX:570:Adelaide, Australia"
      ,"-35.25:149.13:XXX:600:Canberra, Australia"
      ,"13.45:144.75:XXX:600:Guam"
      ,"14.60:120.98:XXX:480:Manila, Philippines"
      ,"-37.83:145.00:XXX:600:Melbourne, Australia"
      ,"-77.325:-167.09:XXX:720:Mt. Erebus, Antartica"
      ,"-31.93:115.83:XXX:480:Perth, Australia"
      ,"-33.87:151.22:XXX:600:Sydney, Australia"
      ,"-41.28:174.78:XXX:720:Wellington, New Zealand"
  };

  int type;
  String[] currentArray=null;

  public ListForm(String name, int type)
  {
    super(name, List.IMPLICIT);
    // Set up this Displayable to listen to command events
    setCommandListener(this);
    // add the Back command
    addCommand(CommandManager.backCommand);
    switch(type)
    {
    case TYPE_PLACES:
      addCommand(CommandManager.editCommand);
      break;
    }
    //
    this.type=type;
  }

  private Vector parseString(String s)
  {
    Vector v=new Vector();
    int pos=0;
    while(pos<s.length())
    {
      int index=s.indexOf(":", pos);
      if(index==-1)
      {
        v.addElement(new String(s.substring(pos)));
        break;
      }
      else
      {
        v.addElement(new String(s.substring(pos, index)));
        pos=index+1;
      }
    }
    return v;
  }

  private void fillList(String[] array)
  {
    currentArray=array;
    //
    for(int i=0; i<array.length; i++)
    {
      String s=array[i];
      if(s.indexOf(":")==-1)
        this.append(array[i], null);
      else
      {
        Vector v=parseString(s);
        if(v.size()>4)
          this.append((String)v.elementAt(4), null);
      }
    }
  }

  public void updateData(Object obj)
  {
    this.deleteAll();
    Font boldFont=Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
    //
    switch(type)
    {
    case TYPE_PLACES:
      for (int i = 0; i < MIDlet1.rmsManager.places.size(); i++)
      {
        Place p = (Place) MIDlet1.rmsManager.places.elementAt(i);
        this.append(i + " " + p, null);
      }
      this.setFont(0, boldFont);
      break;
    case TYPE_REGION:
      fillList(regions);
      break;
    case TYPE_CITIES:
      Integer iobj=(Integer)obj;
      switch(iobj.intValue())
      {
      case 0: fillList(cities0); break;
      case 1: fillList(cities1); break;
      case 2: fillList(cities2); break;
      case 3: fillList(cities3); break;
      case 4: fillList(cities4); break;
      case 5: fillList(cities5); break;
      case 6: fillList(cities6); break;
      case 7: fillList(cities7); break;
      case 8: fillList(cities8); break;
      case 9: fillList(cities9); break;
      case 10: fillList(cities10); break;
      case 11: fillList(cities11); break;
      case 12: fillList(cities12); break;
      case 13: fillList(cities13); break;
      }
      break;
    }
  }

  public void commandAction(Command command, Displayable displayable)
  {
    if(command==CommandManager.backCommand)
      MIDlet1.screenManager.popScreen();
    else
    if(command==List.SELECT_COMMAND)
    {
      int i=this.getSelectedIndex();
      // Выбор текущего места
      switch(type)
      {
      case TYPE_PLACES:
        MIDlet1.rmsManager.placeIndex = i;
        MIDlet1.rmsManager.saveRMS();
        MIDlet1.screenManager.popScreen();
        break;
      case TYPE_REGION:
        ListForm lf=new ListForm(this.getString(i), ListForm.TYPE_CITIES);
        lf.updateData(new Integer(i));
        MIDlet1.screenManager.pushScreen(lf);
        break;
      case TYPE_CITIES:
        String s=currentArray[i];
        Vector v=parseString(s);
        Place p=new Place((String)v.elementAt(4),
                          Float.parseFloat((String)v.elementAt(0)),
                          Float.parseFloat((String)v.elementAt(1)),
                          Float.parseFloat((String)v.elementAt(3)));
        MIDlet1.screenManager.popScreen(2, p);
        break;
      }

    }
    else
    if(command==CommandManager.editCommand)
    {
      int i=this.getSelectedIndex();
      Place p=(Place)MIDlet1.rmsManager.places.elementAt(i);
      // Изменить настройки места
      EditForm ef=new EditForm("Place", p);
      MIDlet1.screenManager.pushScreen(ef);
    }
  }

  public void Update(Object data)
  {
    int i=this.getSelectedIndex();
    switch(type)
    {
    case TYPE_PLACES:
      Place p = (Place) MIDlet1.rmsManager.places.elementAt(i);
      this.set(i ,i + " " + p, null);
      break;
    }
  }

  public void selectHome()
  {
    Place p=(Place)MIDlet1.rmsManager.places.elementAt(0);
    // Изменить настройки места
    EditForm ef=new EditForm("Place", p);
    MIDlet1.screenManager.pushScreen(ef);
    //
    Vector v=new Vector();
    v.addElement("Attention");
    v.addElement("HOME");
    v.addElement("Please edit your home");
    InfoForm iform=new InfoForm(v);
    MIDlet1.screenManager.pushScreen(iform);
  }
}
