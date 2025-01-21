package henson.midp;

public interface ITimeHandler
{
  public abstract void initTime();
  public abstract void stepTime(long delay);
}
