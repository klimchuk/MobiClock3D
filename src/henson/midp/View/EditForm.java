package henson.midp.View;

import javax.microedition.lcdui.*;
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

public class EditForm extends Form implements CommandListener, IUpdate
{
  TextField tf=null;
  TextField tf3=null;
  TextField tf4=null;
  TextField tf2=null;
  Place place=null;
  //
  public EditForm(String name, Place place)
  {
    super(name);
    // Set up this Displayable to listen to command events
    setCommandListener(this);
    // add the Exit command
    addCommand(CommandManager.findCommand);
    addCommand(CommandManager.okCommand);
    addCommand(CommandManager.cancelCommand);
    //
    tf=new TextField("Name", place.Name, 20, TextField.ANY);
    this.append(tf);
    tf3=new TextField("Latitude", ""+place.Latitude, 20, TextField.DECIMAL);
    this.append(tf3);
    tf4=new TextField("Longitude", ""+place.Longitude, 20, TextField.DECIMAL);
    this.append(tf4);
    tf2=new TextField("TimeZone", ""+place.TimeZone, 20, TextField.DECIMAL);
    this.append(tf2);
    //
    this.place=place;
  }

  public void commandAction(Command command, Displayable displayable)
  {
    if(command==CommandManager.okCommand)
    {
      if(place!=null)
      {
        place.Name=tf.getString();
        place.Latitude=Float.parseFloat(tf3.getString());
        place.Longitude=Float.parseFloat(tf4.getString());
        place.TimeZone=Float.parseFloat(tf2.getString());
        place.weatherStation="";
      }
    }
    else
    if(command==CommandManager.findCommand)
    {
      // Список городов
      ListForm lf=new ListForm("City", ListForm.TYPE_REGION);
      lf.updateData(null);
      MIDlet1.screenManager.pushScreen(lf);
      //
      return;
    }
    //
    MIDlet1.screenManager.popScreen();
  }

  public void Update(Object data)
  {
    if(data!=null)
    {
      Place p = (Place) data;
      tf.setString(p.Name);
      tf3.setString("" + p.Latitude);
      tf4.setString("" + p.Longitude);
      tf2.setString("" + (p.TimeZone / 60.0f));
    }
  }
}
