import java.awt.*;

public class HitBox {
    protected int width, height;
    protected float x, y;
    protected ID id;
    public HitBox(float x, float y, ID id, int width, int height) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public void updateCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getRegion() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public ID getId() {
        return id;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
