import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Ground extends GameObject{

    private LinkedList<Dirt> dirtPatches = new LinkedList<Dirt>();
    private final float width;
    private final float height;
    private float spawnProb;
    private int spawnHeight;
    private int lastHeight;
    private Random rand;
    public Ground(float x, float y, float width, float height, ID id) {
        super(x, y, id);
        this.width = width;
        this.height = height;
    }

    public void tick(){
        rand = new Random();
        spawnProb = rand.nextFloat();
        if(spawnProb < 0.1) {
            while(spawnHeight >= lastHeight - 15 && spawnHeight <= lastHeight + 15) {
                spawnHeight = rand.nextInt(1, 401);
            }
            dirtPatches.add(new Dirt(1920, 1080 - spawnHeight, ID.Dirt));
        }
        lastHeight = spawnHeight;
        for(int i=0;i<dirtPatches.size();i++) {
            dirtPatches.get(i).tick();
            if(dirtPatches.get(i).getX() < -64) {
                dirtPatches.remove(i);
                i--;
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
        for(Dirt dirt : dirtPatches) {
            dirt.render(g);
        }
    }

}
