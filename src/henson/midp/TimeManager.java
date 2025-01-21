package henson.midp;

public class TimeManager implements Runnable
{
  ITimeHandler handler=null;
  boolean running=false;
  Thread thread=null;
  long lastStep=0;
  //
  public TimeManager(ITimeHandler handler)
  {
    this.handler=handler;
  }

  public void Start()
  {
    running=true;
    if(handler!=null)
      handler.initTime();
    lastStep=System.currentTimeMillis();
    thread=new Thread(this);
    thread.start();
  }

  public void Stop()
  {
    try
    {
      synchronized(this)
      {
        running = false;
      }
      //
      if (thread != null)
      {
        // Ждем завершения
        wait(1000);
        //thread.join();
        if(thread.isAlive())
        {
          System.out.println("ALIVE");
          thread.interrupt();
        }
        else
          System.out.println("DEAD");
      }
    }
    catch (Exception ex)
    {
      System.out.println("STOP => "+ex.getMessage());
    }
  }

  /**
   * run
   *
   * @todo Implement this java.lang.Runnable method
   */
  public void run()
  {
    while(true)
    {
      synchronized(this)
      {
        if (!running) break;
      }
      //
      long newStep=System.currentTimeMillis();
      if(handler!=null)
        handler.stepTime(newStep-lastStep);
      lastStep=newStep;
    }
  }
}
