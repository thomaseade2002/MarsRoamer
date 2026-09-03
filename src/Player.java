import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject{
    public Torso torso;
    public Legs legs;

    public Player(float x, float y, ID id, KeyInput input){
        super(x, y, id);
        this.torso = new Torso(x, y, ID.Torso, input);
        this.legs = new Legs(x, y + 32, ID.Legs, input);
    }

    @Override
    public void tick() {
        legs.tick();
        torso.legsX = legs.getX();
        torso.legsY = legs.getY();
        torso.tick();
        x = torso.getX();
        y = torso.getY();
    }

    @Override
    public void render(Graphics g) {
        legs.render(g);
        torso.render(g);
    }
}
