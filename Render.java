/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hiof.eivindaa;

/**
 *
 * @author Eivind
 */
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Render {
    // static dimensions
    public static final float LEG_UPPER_LENGTH=0.9f;
    public static final float LEG_MIDDLE_LENGTH=1.1f;
    public static final float LEG_LOWER_LENGTH=1.1f;
    public static final float LEG_FOOT_LENGTH=0.5f;
    public static final float UPPER_LEG_RADIUS=0.11f;
    public static final float MIDDLE_LEG_RADIUS=0.08f;
    public static final float LOWER_LEG_RADIUS=0.07f;
    public static final float FOOT_LEG_RADIUS=0.05f;
    public static final float END_LEG_RADIUS=0.03f;
    public static final float ARM_LENGTH=0.2f;
    public static final float ARM_RADIUS=0.6f;
    private static final int SLICES=10;
    private static final int STACKS=10;

    // eating angles
    private static final int[] ARM_TOP_V =
    {0, 5, 10, 15, 20 ,25, 20, 15 , 10, 5, 0,
     -5, -10, -15, -20, -25, -20, -15, -10, -5};
    private static final int[] ARM_BOTTOM_V =
    {0, 0, 10, 2, 0, 10 ,0 ,0 , 0, 0, 0,
    0, 0, 0, 10, 2, 0, 10 ,0 ,0};
    // walking angles
    private static final int[] LIMB_ONE_V =
    {0, 1, 2, 4, 6, 8, 6, 4 ,2 ,1 , 0,
     -1, -2, -4, -6, -8, -6, -4, -2, -1 };
    private static final int[] LIMB_TWO_V =
    {0, -3, -6, -9, -12, -15, -12, -9 ,-6 ,-3 , 0,
     3, 6, 9, 12, 15, 12, 9, 6, 3 };
    private static final int[] LIMB_THREE_V =
    {0, 2, 4, 6, 8, 10, 8, 6 ,4 ,2 , 0,
     -2, -4, -6, -8, -10, -8, -6, -4, -2 };
    private static final int[] LIMB_FOUR_V =
    {0, -3, -6, -9, -12, -15, -12, -9 ,-6 ,-3 , 0,
     3, 6, 9, 12, 15, 12, 9, 6, 3 };
    // when we walk:
    public static int movePos=0;
    public static int[]left_arm={0,0,0,0};
    public static int[]right_arm={0,0,0,0};
    public static int[]left_leg_one={0,0,0,0};
    public static int[]left_leg_two={0,0,0,0};
    public static int[]left_leg_three={0,0,0,0};
    public static int[]left_leg_four={0,0,0,0};
    public static int[]right_leg_one={0,0,0,0};
    public static int[]right_leg_two={0,0,0,0};
    public static int[]right_leg_three={0,0,0,0};
    public static int[]right_leg_four={0,0,0,0};

    public static void drawSuperEllipsoid(GL gl, double rx, double ry, double rz, int n, 
                                     double e1, double e2) {
      
    double pi = Math.PI;
    double dv = 2 * pi / n;
    double dw = pi / (n / 2);

    double exp1 = 2.0 / e1;
    double exp2 = 2.0 / e2;

    for (double v = 0; v < 2 * pi; v += dv) {
      gl.glBegin(GL.GL_TRIANGLE_STRIP);

      gl.glVertex3d(0.0, 0.0, rz);

      for (double w = dw; w < pi; w += dw) {

        double cosv = Math.cos(v);
        double cosw = Math.cos(w);
        double cosvdv = Math.cos(v + dv);
        double sinv = Math.sin(v);
        double sinw = Math.sin(w);
        double sinvdv = Math.sin(v + dv);

        double acosv = Math.abs(cosv);
        double acosw = Math.abs(cosw);
        double acosvdv = Math.abs(cosvdv);
        double asinv = Math.abs(sinv);
        double asinw = Math.abs(sinw);
        double asinvdv = Math.abs(sinvdv);

        double scosv = Math.signum(cosv);
        double scosw = Math.signum(cosw);
        double scosvdv = Math.signum(cosvdv);
        double ssinv = Math.signum(sinv);
        double ssinw = Math.signum(sinw);
        double ssinvdv = Math.signum(sinvdv);

        gl.glNormal3d((1 / rx) * scosv * Math.pow(acosv, 2.0 - exp1) * ssinw * Math.pow(asinw, 2.0 - exp2),
                      (1 / ry) * ssinv * Math.pow(asinv, 2.0 - exp1) * ssinw * Math.pow(asinw, 2.0 - exp2),
                      (1 / rz) * scosw * Math.pow(acosw, 2.0 - exp2));
        gl.glVertex3d(rx * scosv * Math.pow(acosv, exp1) * ssinw * Math.pow(asinw, exp2),
                      ry * ssinv * Math.pow(asinv, exp1) * ssinw * Math.pow(asinw, exp2),
                      rz * scosw * Math.pow(acosw, exp2));

        gl.glNormal3d((1 / rx) * scosvdv * Math.pow(acosvdv, 2.0 - exp1) * ssinw * Math.pow(asinw, 2.0 - exp2),
                      (1 / ry) * ssinvdv * Math.pow(asinvdv, 2.0 - exp1) * ssinw * Math.pow(asinw, 2.0 - exp2),
                      (1 / rz) * scosw * Math.pow(acosw, 2.0 - exp2));
        gl.glVertex3d(rx * scosvdv * Math.pow(acosvdv, exp1) * ssinw * Math.pow(asinw, exp2),
                      ry * ssinvdv * Math.pow(asinvdv, exp1) * ssinw * Math.pow(asinw, exp2),
                      rz * scosw * Math.pow(acosw, exp2));

      }

      gl.glVertex3d(0.0, 0.0, -rz);

      gl.glEnd();
    } 
  }
  
  public static void drawFang(GL gl, GLU glu)
  {
      gl.glPushMatrix();
      Materials.brown(gl);
      drawSuperEllipsoid(gl, 0.16, 0.16, 0.15, 30, 2.0, 2.0);
              
      gl.glTranslatef(0.0f, -0.17f, 0.11f);
      Materials.black(gl);
      drawSuperEllipsoid(gl, 0.12, 0.26, 0.06, 30, 1.5, 2.0);
      gl.glPopMatrix();
  }
  
  public static void drawClosedCylinder(GL gl,GLU glu,GLUquadric q,
            float r1,float r2,float l)
    {
        gl.glPushMatrix();
        glu.gluDisk(q, 0.0f, r1, SLICES, STACKS);
        glu.gluCylinder(q, r1, r2, l, SLICES, STACKS);
        gl.glTranslatef(0.0f,0.0f,l);
        glu.gluDisk(q, 0.0f, r2, SLICES, STACKS);
        gl.glPopMatrix();
    }
  
  public static void drawArm(GL gl, GLU glu, GLUquadric q, int[] v)
  {
      gl.glPushMatrix();
        gl.glRotatef(30.0f+v[0], 0.0f, 0.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,UPPER_LEG_RADIUS*ARM_RADIUS,
                MIDDLE_LEG_RADIUS*ARM_RADIUS, LEG_UPPER_LENGTH*ARM_LENGTH);
        gl.glTranslatef(0.0f, 0.0f, LEG_UPPER_LENGTH*ARM_LENGTH);
        glu.gluSphere(q, 0.07f*ARM_RADIUS, 10, 10);
        gl.glRotatef(60f, 1f, 0f, 0f);
        gl.glRotatef(v[1], 1.0f, 1.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,MIDDLE_LEG_RADIUS*ARM_RADIUS,
                LOWER_LEG_RADIUS*ARM_RADIUS, LEG_MIDDLE_LENGTH*ARM_LENGTH);
        gl.glTranslatef(0.0f, 0.0f, LEG_MIDDLE_LENGTH*ARM_LENGTH);
        glu.gluSphere(q, 0.07f*ARM_RADIUS, 10, 10);
        gl.glRotatef(60f, 1f, 0f, 0f);
        gl.glRotatef(v[0], 0.0f, 1.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,LOWER_LEG_RADIUS*ARM_RADIUS,
                FOOT_LEG_RADIUS*ARM_RADIUS, LEG_LOWER_LENGTH*ARM_LENGTH);
        gl.glTranslatef(0.0f, 0.0f, LEG_LOWER_LENGTH*ARM_LENGTH);
        glu.gluSphere(q, 0.04f*ARM_RADIUS, 10, 10);
        gl.glRotatef(60f, 1f, 0f, 0f);
        gl.glRotatef(v[1], 0.0f, 1.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,FOOT_LEG_RADIUS*ARM_RADIUS,
                END_LEG_RADIUS*ARM_RADIUS, LEG_FOOT_LENGTH*ARM_LENGTH);
        gl.glPopMatrix();
  }
  
  public static void drawAleg(GL gl,GLU glu,GLUquadric q,int[] v)
    {
        gl.glPushMatrix();
        glu.gluSphere(q, 0.11f, 10, 10);
        gl.glRotatef(-60.0f+v[0], 0.0f, 0.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,UPPER_LEG_RADIUS,
                MIDDLE_LEG_RADIUS, LEG_UPPER_LENGTH);
        gl.glTranslatef(0.0f, 0.0f, LEG_UPPER_LENGTH);
        glu.gluSphere(q, 0.08f, 10, 10);
        gl.glRotatef(60f, 1f, 0f, 0f);
        gl.glRotatef(v[1], 1.0f, 1.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,MIDDLE_LEG_RADIUS,
                LOWER_LEG_RADIUS, LEG_MIDDLE_LENGTH);
        gl.glTranslatef(0.0f, 0.0f, LEG_MIDDLE_LENGTH);
        glu.gluSphere(q, 0.07f, 10, 10);
        gl.glRotatef(50f, 1f, 0f, 0f);
        gl.glRotatef(v[0], 0.0f, 1.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,LOWER_LEG_RADIUS,
                FOOT_LEG_RADIUS, LEG_LOWER_LENGTH);
        gl.glTranslatef(0.0f, 0.0f, LEG_LOWER_LENGTH);
        glu.gluSphere(q, 0.05f, 10, 10);
        gl.glRotatef(-30f, 1f, 0f, 0f);
        gl.glRotatef(v[1], 0.0f, 1.0f, 0.0f);
        drawClosedCylinder(gl,glu,q,FOOT_LEG_RADIUS,
                END_LEG_RADIUS, LEG_FOOT_LENGTH);
        gl.glPopMatrix();
    }
  
  public static void updateAngles()
    {
        movePos=(movePos+1)%LIMB_ONE_V.length;
        int movePos1=movePos;
        int movePos2=(movePos+LIMB_TWO_V.length/2)%LIMB_TWO_V.length;
        
        left_arm[0]=ARM_TOP_V[movePos1];
        left_arm[1]=ARM_BOTTOM_V[movePos1];
        right_arm[0]=ARM_TOP_V[movePos2];
        right_arm[1]=ARM_BOTTOM_V[movePos2];
        
        left_leg_one[0]=LIMB_ONE_V[movePos1];
        left_leg_one[1]=LIMB_TWO_V[movePos1];
        left_leg_one[2]=LIMB_THREE_V[movePos1];
        left_leg_one[3]=LIMB_FOUR_V[movePos1];
        left_leg_two[0]=LIMB_ONE_V[movePos2];
        left_leg_two[1]=LIMB_TWO_V[movePos2];
        left_leg_two[2]=LIMB_THREE_V[movePos2];
        left_leg_two[3]=LIMB_FOUR_V[movePos2];
        left_leg_three[0]=LIMB_ONE_V[movePos1];
        left_leg_three[1]=LIMB_TWO_V[movePos1];
        left_leg_three[2]=LIMB_THREE_V[movePos1];
        left_leg_three[3]=LIMB_FOUR_V[movePos1];
        left_leg_four[0]=LIMB_ONE_V[movePos2];
        left_leg_four[1]=LIMB_TWO_V[movePos2];
        left_leg_four[2]=LIMB_THREE_V[movePos2];
        left_leg_four[3]=LIMB_FOUR_V[movePos2];
        
        right_leg_one[0]=LIMB_ONE_V[movePos2];
        right_leg_one[1]=LIMB_TWO_V[movePos2];
        right_leg_one[2]=LIMB_THREE_V[movePos2];
        right_leg_one[3]=LIMB_FOUR_V[movePos2];
        right_leg_two[0]=LIMB_ONE_V[movePos1];
        right_leg_two[1]=LIMB_TWO_V[movePos1];
        right_leg_two[2]=LIMB_THREE_V[movePos1];
        right_leg_two[3]=LIMB_FOUR_V[movePos1];
        right_leg_three[0]=LIMB_ONE_V[movePos2];
        right_leg_three[1]=LIMB_TWO_V[movePos2];
        right_leg_three[2]=LIMB_THREE_V[movePos2];
        right_leg_three[3]=LIMB_FOUR_V[movePos2];
        right_leg_four[0]=LIMB_ONE_V[movePos1];
        right_leg_four[1]=LIMB_TWO_V[movePos1];
        right_leg_four[2]=LIMB_THREE_V[movePos1];
        right_leg_four[3]=LIMB_FOUR_V[movePos1];
        
        
    }
  
}