import java.awt.*;

public class Block extends GameObject {
    protected int width, height;
    private float velX = -5;
    public HitBox hitBox;
    public Block(float x, float y, ID id, int width, int height) {
        super(x, y, id);
        this.width = width;
        this.height = height;
        this.hitBox = new HitBox(x, y, ID.HitBox, width, height);
    }

    public void tick() {
        x += velX;
        hitBox.updateCoordinates(x, y);
    }

    public void render(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect((int)x, (int)y, width, height);
    }
}
