package henson.midp.View;

import javax.microedition.lcdui.*;
import henson.midp.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class InfoForm extends Form implements CommandListener
{
  StringItem si=null;

  public InfoForm(Vector v)
  {
    super("Info");
    // Set up this Displayable to listen to command events
    setCommandListener(this);
    // add the Exit command
    addCommand(CommandManager.closeCommand);
    //
    String header=(String)v.elementAt(0);
    setTitle(header);
    //
    for(int i=0; i<(v.size()-1)/2; i++)
    {
      String title=(String)v.elementAt(i*2+1);
      String text=(String)v.elementAt(i*2+2);
      si = new StringItem(title, text, StringItem.PLAIN);
      this.append(si);
    }
  }

  public InfoForm(String title, String text)
  {
    super("Info");
    // Set up this Displayable to listen to command events
    setCommandListener(this);
    // add the Exit command
    addCommand(CommandManager.closeCommand);
    //
    si=new StringItem(title, text, StringItem.PLAIN);
    this.append(si);
  }

  public void commandAction(Command command, Displayable displayable)
  {
    if(command==CommandManager.closeCommand)
      MIDlet1.screenManager.popScreen();
  }
}
