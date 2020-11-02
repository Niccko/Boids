import processing.core.PApplet;
import processing.core.PVector;

import javax.crypto.interfaces.PBEKey;
import java.util.ArrayList;

public class Boid extends PApplet {
    PApplet sk;

    private int size = 5;
    private int percRadius = 100;
    PVector pos,vel,acc;

    public Boid(PApplet sketch){
        sk = sketch;
        pos = new PVector(random(sk.width),random(sk.height));
        vel = PVector.random2D().mult(random(6)+1);
        acc = new PVector(0,0);
    }

    PVector align(ArrayList<Boid> bd){
        PVector align = new PVector(0,0);
        int count = 0;
        for (Boid b:bd) {
            if(b!=this && PVector.dist(b.pos,pos)<percRadius){
                align.add(b.vel);
                count++;
            }
        }
        if(count>0){
            align.div(count);
            align.setMag(4);
            align.sub(vel);
            align.limit(0.2f);
        }
        return align;
    }

    PVector cohesion(ArrayList<Boid> bd){
        PVector coh = new PVector(0,0);
        int count = 0;
        for (Boid b:bd) {
            if(b!=this && PVector.dist(b.pos,pos)<percRadius){
                coh.add(b.pos);
                count++;
            }
        }
        if(count>0){
            coh.div(count);
            coh.sub(pos);
            coh.setMag(4);
            coh.sub(vel);
            coh.limit(0.2f);
        }
        return coh;
    }

    PVector separation(ArrayList<Boid> bd){
        PVector sep = new PVector(0,0);
        int count = 0;
        for (Boid b:bd) {
            float dist = PVector.dist(b.pos,pos);
            if(b!=this && dist<percRadius){
                PVector d = PVector.sub(pos,b.pos);
                sep.add(d.div(dist/10));
                count++;
            }
        }
        PVector ms = new PVector(mouseX,mouseY);
        float dist = PVector.dist(pos,ms);
        if(dist<percRadius){
            sep.add(PVector.sub(pos,ms).div(dist));
            count++;
        }
        count++;
        if(count>0){
            sep.div(count);
            sep.setMag(4);
            sep.sub(vel);
            sep.limit(0.2f);
        }
        return sep;
    }

    public void move(ArrayList<Boid> bd){
        PVector align = align(bd);
        acc.add(align);
        PVector cohesion = cohesion(bd);
        acc.add(cohesion);
        PVector separation = separation(bd);
        acc.add(separation);
    }

    private void checkEdges(){
        if(pos.x>sk.width) pos.x = 0;
        if(pos.x<0) pos.x = sk.width;
        if(pos.y>sk.height) pos.y = 0;
        if(pos.y<0) pos.y = sk.height;
    }

    public void show(){
        sk.color(255);
        sk.noStroke();
        sk.strokeWeight(2);
        sk.stroke(255);
        sk.line(pos.x,pos.y,pos.x+vel.x*size,pos.y+vel.y*size);
    }

    public void update(){
        vel.add(acc);
        pos.add(vel);
        checkEdges();
        acc.mult(0);
    }
}
