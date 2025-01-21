package henson.midp;

import java.util.*;
import javax.microedition.lcdui.*;
import henson.midp.View.*;

public class ScreenManager extends Stack
{
  /** ���������� ����� ������� */
  private Display display=null;
  //
  public ScreenManager(Display display)
  {
    this.display=display;
  }

  public void pushScreen(Displayable disp)
  {
    // ������ �������
    display.setCurrent(disp);
    // ��������� � ����
    super.push(disp);
  }

  public void replaceScreen(Displayable disp)
  {
    // �������� ������� ����� �����
    display.setCurrent(disp);
    //
    super.pop();
    super.push(disp);
  }

  public Displayable getCurrent()
  {
    //return display.getCurrent();
    if(super.empty())
      return null;
    else
      return (Displayable)super.peek();
  }

  public void popScreen(int count, Object data)
  {
    Displayable disp = null;
    // ������� ������� �����
    for(int i=0; i<count; i++)
    {
      super.pop();
      //
      if (super.empty())
        // ����� �� ����������
        MIDlet1.quitApp();
      else
        // ������ �������
        disp = (Displayable)super.peek();
    }
    //
    if(disp!=null)
    {
      if (disp instanceof IUpdate)
      {
        IUpdate iupdate = (IUpdate) disp;
        iupdate.Update(data);
      }
      display.setCurrent(disp);
    }
  }

  public void popScreen()
  {
    popScreen(1, null);
  }

  public void flashLight(int duration)
  {
    if(display!=null)
      display.flashBacklight(duration);
  }
}
