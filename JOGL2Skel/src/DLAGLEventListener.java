
import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_LINE_LOOP;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Font;
import java.util.Random;


/**
 *
 * @author cgray
 */
public class DLAGLEventListener implements com.jogamp.opengl.GLEventListener {
    private int pCount = 15000;
    private int aCount = 0;
    private boolean[][] lattice;
    private boolean[][] killZone;
    Particle[] particles = new Particle[pCount];
    public int con = 4;
    private int kRadius;
    private TextRenderer tRend;
    private int killed = 0;
    public DLAGLEventListener() {
    }
    
    protected void setup(GL2 gl2, int width, int height) {
        gl2.glMatrixMode(GL2.GL_PROJECTION);
        gl2.glLoadIdentity();

        // coordinate system origin at lower left with width and height same as the window
        GLU glu = new GLU();
        glu.gluOrtho2D(0.0f, width, 0.0f, height);

        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();

        gl2.glViewport(0, 0, width, height);
        
        
    }

    protected void render(GL2 gl2, int width, int height) {
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);

        gl2.glLoadIdentity();
        gl2.glBegin(GL2.GL_POINTS);
        gl2.glPointSize(1.0f);
        aCount = 0;
        pCount = 15000;
        lattice = new boolean[width][height]; //Create lattice
        killZone = new boolean[width][height]; // create kill Zone
        Random rand = new Random();
        kRadius = rand.nextInt((200 - 100) + 1) + 100;
        lattice[(width/2)][height/2] = true; //  the center of the lattice is now true seed
        tRend = new TextRenderer(new Font ("SansSerif", Font.BOLD, 12));
        createGrid(width, height);
        System.out.println("Particles Created");
        drawKillZone(gl2);
        placeParticles(gl2, width, height);
        gl2.glEnd();
        tRend.beginRendering(600, 600);
        tRend.setColor(0.2f, 0.8f, 0.2f, 0.8f);
        tRend.draw("Statistics", 460, 580);
        tRend.draw("Initital Particles: "+pCount, 460, 560);
        tRend.draw("Actual Particles: "+aCount, 460, 540);
        tRend.draw("Killed Particles: "+killed, 460, 520);
        tRend.draw("Killing Radius: "+kRadius, 460, 500);
        tRend.endRendering();

  
    }

    @Override
    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
        setup(glautodrawable.getGL().getGL2(), width, height);
    }

    @Override
    public void init(GLAutoDrawable glautodrawable) {
    }

    public void dispose(GLAutoDrawable glautodrawable) {
    }
    @Override
    public void display(GLAutoDrawable glautodrawable) {
        
        render(glautodrawable.getGL().getGL2(), glautodrawable.getSurfaceWidth(), glautodrawable.getSurfaceHeight());
        
    }
    public void setCon(int tcon) {
        con = tcon;

    }
    public void resetAll() {
        
    }
    public void drawKillZone(GL2 gl2) {
        for (int i = 0; i < 360; i++) {
           int x = (int) (Math.cos(Math.toRadians(i)) * kRadius);
           int y = (int) (Math.sin(Math.toRadians(i)) * kRadius);
           gl2.glColor3f(1, 0, 0);
           gl2.glVertex2f(x + 299, y + 299);
        }

    }
    private void createGrid(int width, int height) {
        for(int i=0; i < pCount; i++) {
            particles[i] = new Particle(lattice, width, height, con);
            particles[i].updateParticles();
            lattice = particles[i].getLattice();
        }
    }
    private void placeParticles(GL2 gl2, int width, int height) {
        for(int i=0; i < pCount; i++) {
            if (particles[i].getSticky() && (isIn(particles[i].x, particles[i].y))) {
                double c = particles[i].getColor(particles[i].x, particles[i].y, width / 2, height / 2);
                gl2.glColor3f(0, (float) c, 0);
                gl2.glVertex2f(particles[i].x, particles[i].y);
                aCount += 1;
                
            } else {
                if (!(isIn(particles[i].x, particles[i].y))) {
                    killed += 1;
                }
            }
            
        }
        
    }

    private boolean isIn(int x, int y) {
        double x2 = Math.pow((300 - x),2);
        double y2 = Math.pow((300 - y),2);
        double res = x2 + y2;
        double sqres= Math.sqrt(res);
        return sqres <= kRadius;
    }

}
