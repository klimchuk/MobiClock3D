package henson.midp.Model;

public class Cylinder extends Object3D
{
  public Cylinder(int r, int w, int count)
  {
    // r - ������
    // w - ������ �� ��� X
    // count - ���������� ���������
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

      // ������ ����� X, Y, Z
      vert[i*6]=(short)Math.ceil(x*r);
      vert[i*6+1]=(short)Math.ceil(y*r);
      vert[i*6+2]=(short)Math.ceil(-w/2);
      // ������� ������ ����� X, Y, Z
      norm[i*6]=(byte)Math.ceil(x*127);
      norm[i*6+1]=(byte)Math.ceil(y*127);
      norm[i*6+2]=(byte)0;
      // �������� ������ �����
      tex[i*4]=0;
      tex[i*4+1]=(short)i;

      // ������ ����� X, Y, Z
      vert[i*6+3]=(short)Math.ceil(x*r);
      vert[i*6+4]=(short)Math.ceil(y*r);
      vert[i*6+5]=(short)Math.ceil(w/2);
      // ������� ������ ����� X, Y, Z
      norm[i*6+3]=(byte)Math.ceil(x*127);
      norm[i*6+4]=(byte)Math.ceil(y*127);
      norm[i*6+5]=0;
      // �������� ������ �����
      tex[i*4+2]=10;
      tex[i*4+3]=(short)i;
    }
    //load2(0x0000ffff, 0x0000ffff, vert, norm);
    load2("/Ls.png", vert, norm, tex, 0.1f);
  }
}
