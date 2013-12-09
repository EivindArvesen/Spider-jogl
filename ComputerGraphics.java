package no.hiof.eivindaa;

/**
 *
 * @author Eivind
 */

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class ComputerGraphics implements GLEventListener {
  
  private float rotateX = 0.0f;
  private float rotateY = 0.0f;
  private float rotationIncrement = 15f;
  private float zoom = 0f;
  private float zoomIncrement = 0.4f;
  private boolean doAutoRotation = false;
  private float autoRotation = 0f;

  public static void main(String[] args)
  {
    final ComputerGraphics app = new ComputerGraphics();
      
    Frame frame = new Frame("Assignment 1 - Computer Graphics");
    GLCanvas canvas = new GLCanvas();
    canvas.addGLEventListener(app);
    frame.add(canvas);
    frame.setSize(640, 480);
    final Animator animator = new Animator(canvas);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
          
        // Run this on another thread than the AWT event queue to
        // make sure the call to Animator.stop() completes before
        // exiting
        new Thread(new Runnable() {
          public void run() {
            animator.stop();
            System.exit(0);
          }
        }).start();

      }
    });
    
    frame.addKeyListener(new KeyListener() {
      
      @Override
      public void keyPressed(KeyEvent ke) { app.handleKeyPress(ke); }
      @Override
      public void keyReleased(KeyEvent ke) { }
      @Override
      public void keyTyped(KeyEvent ke) { }

    });

    // Center frame
    frame.setLocationRelativeTo(null);
    
    frame.setVisible(true);    
    animator.start();
  }
  
  private void handleKeyPress(KeyEvent ke) {
    // handle rotation
    if (ke.getKeyCode() == KeyEvent.VK_W) {
      rotateX += rotationIncrement;
    }
    else if (ke.getKeyCode() == KeyEvent.VK_S) {
      rotateX -= rotationIncrement;
    }
    else if (ke.getKeyCode() == KeyEvent.VK_D)
      rotateY -= rotationIncrement;
    else if (ke.getKeyCode() == KeyEvent.VK_A)
      rotateY += rotationIncrement;
    else if (ke.getKeyCode() == KeyEvent.VK_D)
      rotateY -= rotationIncrement;
    // handle zoom
    else if (ke.getKeyCode() == KeyEvent.VK_UP)
      zoom += zoomIncrement;
    else if (ke.getKeyCode() == KeyEvent.VK_DOWN)
      zoom -= zoomIncrement;
    // handle looped rotation
    else if ((ke.getKeyCode() == KeyEvent.VK_LEFT) || (ke.getKeyCode() == KeyEvent.VK_RIGHT))
      if(doAutoRotation==true){doAutoRotation=false;}
      else{doAutoRotation=true;}
    }
    
  public void init(GLAutoDrawable drawable)
  {
    GL gl = drawable.getGL();
    // Set background color (white) and shading mode
    gl.glShadeModel(GL.GL_SMOOTH);
    gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    // Set light
    float ambient[] = {1.0f,1.0f,1.0f,1.0f };
    float diffuse[] = {1.0f,1.0f,1.0f,1.0f };
    float position[] = {20.0f,30.0f,20.0f,0.0f };
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT,ambient,0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse,0);
    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position,0);
    
    gl.glEnable(GL.GL_LIGHTING);
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);
    gl.glEnable(GL.GL_NORMALIZE);
  }

  public void reshape(GLAutoDrawable drawable, 
                      int x, int y, int width, int height)
  {
    GL gl = drawable.getGL();
    GLU glu = new GLU();
    if (height <= 0) // no divide by zero
      height = 1;
    // keep ratio
    final float h = (float) width / (float) height;
    gl.glViewport(0, 0, width, height);
    gl.glMatrixMode(GL.GL_PROJECTION);
    gl.glLoadIdentity();
    glu.gluPerspective(60.0f, h, 1.0, 20.0);
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();
  }

  public void display(GLAutoDrawable drawable)
  {
    GL gl = drawable.getGL();
    GLU glu=new GLU();
    GLUquadric q=glu.gluNewQuadric();
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    gl.glLoadIdentity();
    
    // position camera
    gl.glTranslatef(0f, 0f, -7+zoom);
    gl.glRotatef(25f, 1f, 0f, 0f);
    
    // rotate camera
    gl.glRotatef(rotateX, 1f, 0f, 0f);
    gl.glRotatef(rotateY, 0f, 1f, 0f);
    // auto-rotation
    gl.glRotatef(autoRotation, 0f, 1f, 0f);
    if(doAutoRotation){
        autoRotation -= 0.6f;
    }
    
    
    // draw lower body
    Materials.brown(gl);
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.4f, -1.5f);
    gl.glRotatef(15f, 1f, 0f, 0f);
    Render.drawSuperEllipsoid(gl, 1.4, 1.2, 1.7, 90, 2.0, 1.7);
    gl.glPopMatrix();
    
    // draw upper body
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.0f, 0.6f);
    gl.glRotatef(-10f, 1f, 0f, 0f);
    Render.drawSuperEllipsoid(gl, 0.8, 0.6, 1.4, 70, 2.0, 1.5);
    
    // draw head
    gl.glPushMatrix();
    gl.glTranslatef(0.0f, 0.15f, 1.1f);
    gl.glRotatef(7f, 1f, 0f, 0f);
    Render.drawSuperEllipsoid(gl, 0.45, 0.4, 0.4, 50, 1.5, 2.0);
    
    // DRAW LEFT EYES
    gl.glPushMatrix();
    Materials.black(gl);
    gl.glTranslatef(-0.1f, 0.22f, 0.23f);
    glu.gluSphere(q, 0.1f, 20, 20);
    
    gl.glTranslatef(0.05f, -0.07f, 0.13f);
    glu.gluSphere(q, 0.05f, 5, 5);
    
    gl.glTranslatef(-0.11f, -0.04f, -0.04f);
    glu.gluSphere(q, 0.05f, 5, 5);
    
    gl.glTranslatef(-0.03f, 0.15f, -0.2f);
    glu.gluSphere(q, 0.07f, 5, 5);
    
    // DRAW RIGHT EYES
    gl.glPopMatrix();
    gl.glTranslatef(0.1f, 0.22f, 0.23f);
    glu.gluSphere(q, 0.1f, 20, 20);
    
    gl.glTranslatef(-0.05f, -0.07f, 0.13f);
    glu.gluSphere(q, 0.05f, 5, 5);
    
    gl.glTranslatef(0.11f, -0.04f, -0.04f);
    glu.gluSphere(q, 0.05f, 5, 5);
    
    gl.glTranslatef(0.03f, 0.15f, -0.2f);
    glu.gluSphere(q, 0.07f, 5, 5);
    
    gl.glPopMatrix();
    gl.glPushMatrix();
   
    
    // DRAW FANGS
    gl.glPushMatrix();
    gl.glTranslatef(-0.1f, 0.05f, 1.4f);
    Render.drawFang(gl, glu);
    
    gl.glPopMatrix();
    gl.glTranslatef(0.1f, 0.05f, 1.4f);
    Render.drawFang(gl, glu);
    
    gl.glPopMatrix();
    
    // DRAW "ARMS"
    Materials.brown(gl);
    gl.glPushMatrix();
    gl.glTranslatef(-0.25f, 0.02f, 1.3f);
    gl.glRotatef(-20f, 0f, 1f, 0f);
    Render.drawArm(gl, glu, q, Render.left_arm);
    gl.glPopMatrix();
    
    gl.glTranslatef(0.25f, 0.02f, 1.3f);
    gl.glRotatef(20f, 0f, 1f, 0f);
    Render.drawArm(gl, glu, q, Render.right_arm);
    
    gl.glPopMatrix();
    
    // DRAW EIGHT LEGS
    
    gl.glPushMatrix();
    // draw left leg 1
    gl.glTranslatef(0.35f, 0.25f, 1.3f);
    gl.glRotatef(45f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.left_leg_one);
    
    // draw left leg 2
    gl.glTranslatef(0.5f, 0.0f, -0.1f);
    gl.glRotatef(20f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.left_leg_two);
    
    // draw left leg 3
    gl.glTranslatef(0.4f, 0.0f, -0.1f);
    gl.glRotatef(40f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.left_leg_three);
    
    // draw left leg 4
    gl.glTranslatef(0.5f, 0.0f, -0.1f);
    gl.glRotatef(20f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.left_leg_four);
    
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    // draw right leg 1
    gl.glTranslatef(-0.35f, 0.25f, 1.3f);
    gl.glRotatef(-45f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.right_leg_one);
    
    // draw right leg 2
    gl.glTranslatef(-0.5f, 0.0f, -0.1f);
    gl.glRotatef(-20f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.right_leg_two);
    
    // draw right leg 3
    gl.glTranslatef(-0.4f, 0.0f, -0.1f);
    gl.glRotatef(-40f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.right_leg_three);
    
    // draw right leg 4
    gl.glTranslatef(-0.5f, 0.0f, -0.1f);
    gl.glRotatef(-20f, 0f, 1f, 0f);
    Render.drawAleg(gl,glu,q,Render.right_leg_four);
    
    gl.glPopMatrix();
    
    
    
    gl.glFlush();
    
    Render.updateAngles();
  }

  public void displayChanged(GLAutoDrawable drawable, 
                            boolean modeChanged, boolean deviceChanged) { }

}
