package henson.midp.Model;

import javax.microedition.m3g.*;
import javax.microedition.lcdui.*;
import java.util.*;
import java.io.*;

public class Object3D
{
  public VertexBuffer iVb;
  public IndexBuffer iIb;
  public Appearance iAppearance;
  private Image iImage;
  static private Hashtable images=new Hashtable();
  static private PolygonMode pm=new PolygonMode();

  public Object3D()
  {
    pm.setPerspectiveCorrectionEnable(true);
    pm.setShading(PolygonMode.SHADE_SMOOTH);
    pm.setTwoSidedLightingEnable(true);
  }

  public boolean load(int diffuse, int specular, short[] vert, byte[] norm)
  {
     VertexArray vertArray=new VertexArray(vert.length/3, 3, 2);
     vertArray.set(0, vert.length/3, vert);
     //
     VertexArray normArray=new VertexArray(norm.length/3, 3, 1);
     normArray.set(0, norm.length/3, norm);
     //
     //VertexArray texArray=new VertexArray(tex.length/2, 2, 2);
     //texArray.set(0, tex.length/2, tex);
     //
     int[] stripLen=new int[vert.length/12];
     for(int i=0; i<stripLen.length; i++)
       stripLen[i]=4;
     //
     VertexBuffer vb=iVb=new VertexBuffer();
     vb.setPositions(vertArray, 1.0f, null);
     vb.setNormals(normArray);
     vb.setDefaultColor(0x00ffffff);
     //vb.setTexCoords(0, texArray, tex_scale, null);
     //
     iIb=new TriangleStripArray(0, stripLen);
     //
     iAppearance=new Appearance();
     Material iMaterial=new Material();
     iAppearance.setMaterial(iMaterial);
     iMaterial.setColor(Material.DIFFUSE, diffuse);
     iMaterial.setColor(Material.SPECULAR, specular);
     iMaterial.setShininess(0.0f);
     //
     return true;
  }

  public boolean load2(int diffuse, int specular, short[] vert, byte[] norm)
  {
     VertexArray vertArray=new VertexArray(vert.length/3, 3, 2);
     vertArray.set(0, vert.length/3, vert);
     //
     VertexArray normArray=new VertexArray(norm.length/3, 3, 1);
     normArray.set(0, norm.length/3, norm);
     //
     int[] stripLen={ norm.length/3 };
     //
     VertexBuffer vb=iVb=new VertexBuffer();
     vb.setPositions(vertArray, 1.0f, null);
     vb.setNormals(normArray);
     vb.setDefaultColor(0x00ffffff);
     //vb.setTexCoords(0, texArray, tex_scale, null);
     //
     iIb=new TriangleStripArray(0, stripLen);
     //
     iAppearance=new Appearance();
     Material iMaterial=new Material();
     pm.setWinding(PolygonMode.WINDING_CW);
     iAppearance.setPolygonMode(pm);
     iAppearance.setMaterial(iMaterial);
     iMaterial.setColor(Material.DIFFUSE, diffuse);
     iMaterial.setColor(Material.SPECULAR, specular);
     iMaterial.setShininess(50.0f);
     //
     return true;
  }

  public boolean load(String name, short[] vert, byte[] norm, short[] tex, float tex_scale, int color)
  {
     VertexArray vertArray=new VertexArray(vert.length/3, 3, 2);
     vertArray.set(0, vert.length/3, vert);
     //
     VertexArray normArray=new VertexArray(norm.length/3, 3, 1);
     normArray.set(0, norm.length/3, norm);
     //
     VertexArray texArray=new VertexArray(tex.length/2, 2, 2);
     texArray.set(0, tex.length/2, tex);
     //
     int[] stripLen=new int[vert.length/12];
     for(int i=0; i<stripLen.length; i++)
       stripLen[i]=4;
     //
     VertexBuffer vb=iVb=new VertexBuffer();
     vb.setPositions(vertArray, 1.0f, null);
     vb.setNormals(normArray);
     vb.setTexCoords(0, texArray, tex_scale, null);
     //
     iIb=new TriangleStripArray(0, stripLen);
     //
     Texture2D texture=null;
     if(images.containsKey(name))
       texture=(Texture2D)images.get(name);
     else
     {
       try
       {
         iImage = Image.createImage(name);
         Image2D image2D=new Image2D(Image2D.RGBA, iImage);
         texture=new Texture2D(image2D);
         texture.setFiltering(Texture2D.FILTER_LINEAR, Texture2D.FILTER_LINEAR);
         texture.setWrapping(Texture2D.WRAP_REPEAT, Texture2D.WRAP_REPEAT);
         texture.setBlending(Texture2D.FUNC_MODULATE);
         images.put(name, texture);
       }
       catch (IOException ex) {
         System.out.println(ex);
         return false;
       }
     }
     //
     iAppearance=new Appearance();
     iAppearance.setTexture(0, texture);
     iAppearance.setPolygonMode(pm);
     //
     Material iMaterial=new Material();
     iAppearance.setMaterial(iMaterial);
     iMaterial.setColor(Material.DIFFUSE, color);
     iMaterial.setColor(Material.SPECULAR, color);
     iMaterial.setShininess(100.0f);
     //
     return true;
  }

  public boolean load2(String name, short[] vert, byte[] norm, short[] tex, float tex_scale)
  {
     VertexArray vertArray=new VertexArray(vert.length/3, 3, 2);
     vertArray.set(0, vert.length/3, vert);
     //
     VertexArray normArray=new VertexArray(norm.length/3, 3, 1);
     normArray.set(0, norm.length/3, norm);
     //
     VertexArray texArray=new VertexArray(tex.length/2, 2, 2);
     texArray.set(0, tex.length/2, tex);
     //
     int[] stripLen={ norm.length/3 };
     //
     VertexBuffer vb=iVb=new VertexBuffer();
     vb.setPositions(vertArray, 1.0f, null);
     vb.setNormals(normArray);
     vb.setTexCoords(0, texArray, tex_scale, null);
     //
     iIb=new TriangleStripArray(0, stripLen);
     //
     iAppearance=new Appearance();
     iAppearance.setTexture(0, createTexture(name));
     pm.setWinding(PolygonMode.WINDING_CW);
     iAppearance.setPolygonMode(pm);
     //
     Material iMaterial=new Material();
     iAppearance.setMaterial(iMaterial);
     iMaterial.setColor(Material.DIFFUSE, 0x00ffffff);
     iMaterial.setColor(Material.SPECULAR, 0x00ffffff);
     iMaterial.setShininess(100.0f);
     //
     return true;
  }

  private Texture2D createTexture(String name)
  {
    Texture2D texture=null;
    if(images.containsKey(name))
      texture=(Texture2D)images.get(name);
    else
    {
      try
      {
        iImage = Image.createImage(name);
        Image2D image2D=new Image2D(Image2D.RGB, iImage);
        texture=new Texture2D(image2D);
        texture.setFiltering(Texture2D.FILTER_LINEAR, Texture2D.FILTER_LINEAR);
        texture.setWrapping(Texture2D.WRAP_REPEAT, Texture2D.WRAP_REPEAT);
        texture.setBlending(Texture2D.FUNC_MODULATE);
        images.put(name, texture);
      }
      catch (IOException ex)
      {
        System.out.println(ex);
      }
    }
    return texture;
  }

  public void render(Graphics3D iG3D, Transform transform)
  {
    iG3D.render(iVb, iIb, iAppearance, transform);
  }
}
