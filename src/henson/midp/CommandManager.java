package henson.midp;

import javax.microedition.lcdui.*;

public class CommandManager
{
  public static Command exitCommand=new Command("Exit", Command.EXIT, 1);
  public static Command backCommand=new Command("Back", Command.BACK, 1);
  public static Command placesCommand=new Command("Locations", Command.ITEM, 1);
  public static Command editCommand=new Command("Edit", Command.ITEM, 1);
  public static Command okCommand=new Command("OK", Command.OK, 1);
  public static Command cancelCommand=new Command("Cancel", Command.CANCEL, 1);
  public static Command findCommand=new Command("Find", Command.SCREEN, 1);
  public static Command closeCommand=new Command("Close", Command.CANCEL, 1);
  public static Command refreshweatherCommand=new Command("Refresh weather", Command.ITEM, 1);
  public static Command reportweatherCommand=new Command("Report weather", Command.ITEM, 1);
  public static Command addeventCommand=new Command("Add event", Command.ITEM, 1);
  public static Command agentsCommand=new Command("Agents", Command.ITEM, 1);
  public static Command locsCommand=new Command("Places", Command.ITEM, 1);
}
