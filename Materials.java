/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hiof.eivindaa;

import javax.media.opengl.GL;

/**
 * Class for materials
 * @author Eivind
 */
public class Materials {
    public static void debugGreen(GL gl)
  {
    float amb[]={0.1f,0.3f,0.2f,0.5f};
    float diff[]={0.5f,0.3f,0.3f,0.1f};
    float spec[]={0.58f,0.727811f,0.633f,1.0f};
    float shine=76.8f;
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,amb,0);
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,diff,0);
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR, spec, 0);
    gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);
  }
    
    public static void brown(GL gl)
  {

    float amb[]={0.02f,0.02f,0.02f,0.3f};
    float diff[]={0.26f,0.18f,0.1f,0.1f};
    float spec[]={0.38f,0.327811f,0.333f,0.1f};
    float shine=12.8f;
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_AMBIENT,amb,0);
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_DIFFUSE,diff,0);
    gl.glMaterialfv(GL.GL_FRONT_AND_BACK,GL.GL_SPECULAR, spec, 0);
    gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);
  }
    
  public static void black(GL gl)
  {
    float amb[]={0.0f, 0.0f, 0.0f, 1.0f};
    float diff[]={0.01f, 0.01f, 0.01f, 1.0f};
    float spec[]={0.75f, 0.75f, 0.75f, 1.0f};
    float shine=255f;
    gl.glMaterialfv(GL.GL_FRONT,GL.GL_AMBIENT,amb,0);
    gl.glMaterialfv(GL.GL_FRONT,GL.GL_DIFFUSE,diff,0);
    gl.glMaterialfv(GL.GL_FRONT,GL.GL_SPECULAR, spec, 0);
    gl.glMaterialf(GL.GL_FRONT_AND_BACK,GL.GL_SHININESS,shine);
  }
}
