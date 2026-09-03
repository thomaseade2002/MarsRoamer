import java.awt.*;

public class Sky extends GameObject{

    private float width, height;

    public Sky(float x, float y, float width, float height, ID id) {
        super(x, y, id);
        this.width = width;
        this.height = height;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }
}
