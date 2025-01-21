package henson.midp.Model;

public class Cylinder extends Object3D
{
  public Cylinder(int r, int w, int count)
  {
    // r - радиус
    // w - ширина по оси X
    // count - количество сегментов
    double angleStep=2*Math.PI/count;
    short[] vert=new short[(count+1)*2*3];
    byte[] norm=new byte[(count+1)*2*3];
    short[] tex=new short[(count+1)*2*2];
    //
    for(int i=0; i<count+1; i++)
    {
      double baseAngle=i*angleStep-angleStep/2;
      double x=Math.cos(baseAngle);
      double y=Math.sin(baseAngle);

      // первая точка X, Y, Z
      vert[i*6]=(short)Math.ceil(x*r);
      vert[i*6+1]=(short)Math.ceil(y*r);
      vert[i*6+2]=(short)Math.ceil(-w/2);
      // нормали первой точки X, Y, Z
      norm[i*6]=(byte)Math.ceil(x*127);
      norm[i*6+1]=(byte)Math.ceil(y*127);
      norm[i*6+2]=(byte)0;
      // текстура первой точки
      tex[i*4]=0;
      tex[i*4+1]=(short)i;

      // вторая точка X, Y, Z
      vert[i*6+3]=(short)Math.ceil(x*r);
      vert[i*6+4]=(short)Math.ceil(y*r);
      vert[i*6+5]=(short)Math.ceil(w/2);
      // нормали второй точка X, Y, Z
      norm[i*6+3]=(byte)Math.ceil(x*127);
      norm[i*6+4]=(byte)Math.ceil(y*127);
      norm[i*6+5]=0;
      // текстура второй точки
      tex[i*4+2]=10;
      tex[i*4+3]=(short)i;
    }
    //load2(0x0000ffff, 0x0000ffff, vert, norm);
    load2("/Ls.png", vert, norm, tex, 0.1f);
  }
}
