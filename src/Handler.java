import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Handler {
    private int buffer = 0;
    private float newBlockProb;
    private float blockHeightProb;
    public LinkedList<GameObject> objects = new LinkedList<GameObject>();
    private Random rand;
    private GameObject tempObject;
    private Player player;
    private Block block;
    public boolean dead = false;
    private KeyInput input;
    private int alpha = 50;
    private Font font = new Font("SanSerif", Font.BOLD, 100);
    public int score = 0;
    private int highScore = 0;
    private File file;
    private Scanner reader;
    private FileWriter writer;
    private boolean newRecord = false;

    public Handler(float groundHeight, KeyInput input) {
        try {
            file = new File(Objects.requireNonNull(getClass().getResource("highScore.txt")).getFile());
            reader = new Scanner(file);
            highScore = Integer.parseInt(reader.nextLine());
            //highScore = Integer.parseInt(Files.readString(Paths.get("src/highScore.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.input = input;
        addObject(new Sky(0, 0, 1920, groundHeight, ID.Sky));
        addObject(new Ground(0, groundHeight, 1920, 400, ID.Ground));
        addObject(new Player(400, groundHeight - 64, ID.Player, input));
    }

    public void tick() throws IOException {
        if (!dead) {
            rand = new Random();
            newBlockProb = rand.nextFloat();
            blockHeightProb = rand.nextFloat();

            if (newBlockProb < 0.02 && buffer == 0) {
                buffer = 50;
                if (blockHeightProb < 0.5) {
                    addObject(new Block(1920, 624, ID.Block, 56, 56));
                } else {
                    addObject(new Block(1920, 591, ID.Block, 56, 56));
                }
            } else if (buffer > 0) {
                buffer--;
            }
            //System.out.println(objects);
            for (int i = 0; i < objects.size(); i++) {
                tempObject = objects.get(i);
                if (objects.get(i).getId() == ID.Block) {
                    player = (Player) objects.get(2);
                    block = (Block) tempObject;
                    if (player.torso.hitBox.getRegion().intersects(block.hitBox.getRegion()) || player.legs.hitBox.getRegion().intersects(block.hitBox.getRegion())) {
                        dead = true;
                        if(score / 120 > highScore) {
                            highScore = score / 120;
                            try (FileOutputStream hs_file_w = new FileOutputStream("src/highScore.txt")) {
                                hs_file_w.write(String.valueOf(highScore).getBytes());
                                //Files.write(Paths.get("src/highScore.txt"), String.valueOf(highScore).getBytes(), StandardOpenOption.CREATE);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            newRecord = true;
                        }
                    } else if (tempObject.getX() < -64) {
                        objects.remove(tempObject);
                        i--;
                    }
                }
                objects.get(i).tick();
                score++;
            }
        } else if (input.keys[4]) {
            newRecord = false;
            dead = false;
            alpha = 50;
            restart();
        }
    }

    public void render(Graphics g) {
        for (GameObject object : objects) {
            object.render(g);
        }
        g.setFont(font);
        g.setColor(new Color(166, 102, 44, 255));
        g.drawString(String.valueOf(score / 120), 50, 100);
        g.setFont(new Font("SanSerif", Font.BOLD, 35));
        g.setColor(new Color(166, 102, 44, 100));
        g.drawString(String.valueOf(highScore), 60, 135);

        if (dead) {
            g.setColor(new Color(255, 253, 141, alpha));
            g.fillRect(0, 0, 1920, 1080);

            g.setFont(font);
            g.setColor(new Color(166, 102, 44, alpha));
            g.drawString("YOU DIED!", 150, 200);
            g.setFont(new Font("SanSerif", Font.BOLD, 50));
            g.drawString("Distance travelled: " + String.valueOf(score / 120) + "m", 160, 300);
            g.setColor(new Color(166, 102, 44, alpha-50));
            g.drawString("Press 'R' to restart.", 160, 360);
            if (newRecord) {
                g.setFont(new Font("SanSerif", Font.BOLD, 18));
                g.setColor(new Color(150, 0, 0, alpha));
                g.drawString("New high score!!", 162, 255);
            }

            if (alpha < 255) {
                alpha++;
            }
        }
    }

    public void addObject(GameObject tempObject) {
        objects.add(tempObject);
    }

    public void removeObject(GameObject tempObject) {
        objects.remove(tempObject);
    }

    public void restart() {
        score = 0;
        for(int i = 0; i< objects.size();i++) {
            if(objects.get(i).getId() == ID.Block) {
                objects.remove(i);
                i--;
            } else if (objects.get(i).getId() == ID.Player) {
                objects.set(i, new Player(400, 616, ID.Player, input));
            }
        }
    }
}
