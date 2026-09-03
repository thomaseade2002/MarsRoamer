import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.Serial;

public class Game extends Canvas implements Runnable{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    public static int WIDTH = 1920, HEIGHT = 1080;
    public float groundHeight = 680;
    public String title = "Mars Roamer";

    Thread thread;
    private boolean isRunning = false;

    private Handler handler;
    private KeyInput input;

    public Game() {
        new Window(WIDTH, HEIGHT, title, this);
        start();

        init();
    }

    public void init() {
        input = new KeyInput();

        this.addKeyListener(input);

        this.handler = new Handler(groundHeight, input);
    }

    private synchronized void start() {
        if (isRunning) return;

        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    private synchronized void stop() {
        if (!isRunning) return;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isRunning = false;
    }

    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 120.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                try {
                    tick();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    public void tick() throws IOException {
        if(handler != null) {
            handler.tick();
        }
    }

    private void render() {
        //renders the game
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        if(handler != null){
            handler.render(g);
        }

        bs.show();
        g.dispose();
    }

    public static void main(String[] args) {
        new Game();
    }

}
