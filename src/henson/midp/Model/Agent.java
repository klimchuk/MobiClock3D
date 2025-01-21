package henson.midp.Model;

import java.io.*;

public class Agent
{
  public String name;
  public String description;
  //
  public Agent()
  {
  }

  public boolean Save(DataOutputStream dos)
  {
    try
    {
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
