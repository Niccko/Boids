import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

public class Main extends PApplet {
    ArrayList<Boid> boids = new ArrayList<>();

    @Override
    public void settings() {
        size(800,800);
    }

    @Override
    public void setup() {
        for (int i = 0; i <200 ; i++) {
            boids.add(new Boid(this));
        }
    }

    @Override
    public void draw() {
        background(50);
        for (Boid b:boids) {
            b.update();
            b.show();
            b.move(boids);
        }
    }





    public static void main(String[] args) {
        String[] arg = {"Main"};
        Main main = new Main();
        PApplet.runSketch(arg,main);
    }
}
