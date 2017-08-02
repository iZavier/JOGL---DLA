
import java.util.Random;

/**
 *
 * @author Shivan Patel
 */
public class Particle {
    public int x;
    public int y;
    public boolean sticky = false;
    private boolean[][] latticeStruct;
    private int fHeight;
    private int fWidth;
    private int connectivity;
    private int sRad = 80;

    public Particle(boolean[][] lattice, int width, int height, int con) {
        latticeStruct = lattice;
        fHeight = height;
        fWidth = width;
        connectivity = con;
        //System.out.println(fWidth+" "+fHeight);
        createParticles();
    }
    
    public void createParticles() {
        
        Random random = new Random();
        x = random.nextInt(fWidth);
        y = random.nextInt(fHeight);

        
        while (checkPosition() && isIn(x, y, sRad)) {
            x = random.nextInt(fWidth);
            y = random.nextInt(fHeight);  
        }
        
        //System.out.println(latticeStruct[600][600]);

        
    }
    private boolean checkPosition() {

        if (latticeStruct[x][y]) {// 2d array lattice pls
            return true;
        } else {
            return false;
        }
        
    }
    public void updateParticles() {
        Random random = new Random();
        //System.out.println("X: "+x+" Y: "+y);
        
        while (!sticky) {
            if (connectivity == 8) {
                x += random.nextInt(3) - 1; //move around range of -1 to 1 pixels // 8con
                y += random.nextInt(3) - 1; //move around range of -1 to 1 pixels
            } else {
                //System.out.println(connectivity);
                int pos = random.nextInt(4) + 1; //NESW
                //System.out.println(pos);
                if (pos == 1) {
                    y += 1;
                } else if (pos == 2){
                    x += 1;
                } else if (pos == 3){
                    y -= 1;
                } else if (pos == 4){
                    x -= 1;
                }
            }
            //System.out.println("X: "+x+" Y: "+y);
            if (x >= fWidth || x < 0 || y >= fHeight || y < 0) { // if particles have moved off the screen, recreate them
                createParticles();
            }
            
            if (checkSticky()) {
                sticky = true;
                latticeStruct[x][y] = true;
            }
        }
        
    }

    public boolean checkSticky() {
        int dx = x;
        int dy = y;
        if (dx <= 0 || dx >= fWidth || dx - 1 <= 0 || dx - 1 >= fWidth || dx + 1 <= 0 || dx + 1 >= fWidth || dy <= 0 || dy >= fHeight || dy - 1 <= 0 || dy - 1 >= fHeight || dy + 1 <= 0 || dy + 1 >= fWidth) {
            return false;
        }
        
        if (connectivity == 4) {
                if (latticeStruct[dx][dy+1] || latticeStruct[dx+1][dy] || latticeStruct[dx][dy-1] || latticeStruct[dx-1][dy]) { //N E S W
                    return true;
                }
        } else {
                if (latticeStruct[dx][dy+1] || latticeStruct[dx+1][dy] || latticeStruct[dx][dy-1] || latticeStruct[dx-1][dy] || latticeStruct[dx+1][dy+1] || latticeStruct[dx+1][dy-1] || latticeStruct[dx-1][dy-1] || latticeStruct[dx-1][dy+1]) { //N E S W
                    return true; 
                }
        }
        return false;
    }
    public boolean[][] getLattice() {
        return latticeStruct;
    }
    public double getColor(int x1, int y1, int x2, int y2) {
       int dx = x2 - x1;
       int dy = y2 - y1;
       double dist = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
       double diff = fWidth - (fWidth / 2) - dist; // 300-600
       double normalized = (diff - (fWidth/2)) / (fWidth - (fWidth / 2));
       normalized = 1 + normalized;
       return normalized; 
    }
    boolean getSticky() {
        return sticky;
    }
    private boolean isIn(int x, int y, int rad) {
        double x2 = Math.pow((300 - x),2);
        double y2 = Math.pow((300 - y),2);
        double res = x2 + y2;
        double sqres= Math.sqrt(res);
        return sqres <= rad;
    }

}
