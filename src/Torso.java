import java.awt.*;

public class Torso extends GameObject{
    public float legsX;
    public float legsY;
    private float _acc = 1f;
    private float _dcc = 0.5f;
    private float _grav = 0.5f;
    private float _jumpVel = -20f;
    private float startY;
    private boolean jumping = false;
    private boolean crouching = false;
    private KeyInput input;
    public HitBox hitBox;
    public Torso(float x, float y, ID id, KeyInput input) {
        super(x, y, id);
        this.input = input;
        this.hitBox = new HitBox(x, y, ID.HitBox, 33, 32);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
        hitBox.updateCoordinates(x, y);

        /*
         * Horizontal Movement:
         *  -> keys 0 = true right
         *  -> keys 1 = true left
         */
        if(input.keys[0]) velX += _acc;
        else if(input.keys[1]) velX -= _acc;
        else if(!input.keys[0] && !input.keys[1]) {
            if (velX > 0) velX -= _dcc;
            else if (velX < 0) velX += _dcc;
        }

        /*
         * Vertical Movement:
         * -> keys 2 = crouch
         * -> keys 3 = jump
         */
        if(input.keys[3] && !jumping){
            jumping = true;
            velY = _jumpVel;
            startY = y;
            return;
        }

        if(jumping && y < startY) {
            velY += _grav;
        } else if(jumping && y >= startY) {
            y = startY;
            jumping = false;
            velY = 0;
        }

        if(input.keys[2] && legsY - y > 0 && !jumping) {
            y += 4;
            crouching = true;
        } else if(!input.keys[2] && legsY - y < 32 && !jumping) {
            y -= 4;
        } else if(!(legsY - y == 0)) {
            crouching = false;
        }

        if(!crouching && legsY - y != 32) {
            y = legsY - 32;
        }

        if(x < 100) x = 100;
        else if (x > 1100) x = 1100;

        if (legsX != x) {
            x = legsX;
        }

        velX = clamp(velX, 5, -5);
        velY = clamp(velY, 30, -10);
    }

    private float clamp(float value, float max, float min) {
        if (value > max) value = max;
        else if(value <= min) value = min;

        return value;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int)x, (int)y, 33, 32);
        g.setColor(Color.black);
        g.fillRect((int)x+4, (int)y+5, 25, 12);
        g.fillRect((int)x+7, (int)y+15, 19, 5);
        //g.fillPolygon([(int)x+4], [], 3);
        g.fillRect((int)x+10, (int)y+22, 3, 4);
        g.setColor(Color.RED);
        g.fillRect((int)x+3, (int)y+22, 7, 4);
        g.setColor(Color.BLUE);
        g.fillRect((int)x+22, (int)y+22, 7, 4);
    }
}
