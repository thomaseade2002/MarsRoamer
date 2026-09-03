import java.awt.*;

public class Dirt extends GameObject {
    private float velX = -5;
    public Dirt(float x, float y, ID id) {
        super(x, y, id);
    }

    public void tick() {
        x += velX;
    }

    public void render(Graphics g) {
        g.setColor(new Color(255, 165, 0));
        g.fillRect((int)x, (int)y, 30, 10);
    }
}
