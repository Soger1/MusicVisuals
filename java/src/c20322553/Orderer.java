package c20322553;
import c20322553.Shape;

import ie.tudublin.Visual;
import ie.tudublin.*;
import java.util.*;
import java.time.*;



public class Orderer extends Visual {

    
    ArrayList<Shape> Renderlist = new ArrayList<>();

    void polygon(float x, float y, float radius, int npoints) { // draws polygons with x number of points
        float angle = TWO_PI / npoints;
        beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
          float sx = x + cos(a) * radius;
          float sy = y + sin(a) * radius;
          vertex(sx, sy, 0);
        }
        endShape(CLOSE);
    }

    public void createShape(int x, int y, int size, int points, int z, int type, int life){ // adds shapes to the render list
        Renderlist.add(new Shape(x,y,size,points,z,type,life));
    }
    
    float angle = 0;
    int stage = 0;
    int time = 0;
    float boxSize = 0;
    float split = 0;
    float rotor = 0;
    LocalTime present = LocalTime.now();
    int Secnow = present.getSecond();
    int Seccheck = present.getSecond();
    int backdrop = 240;
    int time_changed = 0;
    int sweep_num = 0;
    float sweep_place = -100;

    public void sunrays() //72 degress
    {
        float[] bands = getSmoothedBands();
        for(int i = 0 ; i < bands.length ; i++)
        {
            split = radians(360 / bands.length);
            float sunr = ((420 * getSmoothedAmplitude()) * TWO_PI);
            fill(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
            pushMatrix();
            beginShape();
            translate((width / 2) ,(height / 2) , -200);
            rotate((split * i) + (rotor / 1000));
            vertex(sunr ,0);
            vertex(sunr ,30);
            vertex(sunr + bands[i]*15 ,getSmoothedAmplitude() * 300);
            vertex(sunr + bands[i]*15 ,-getSmoothedAmplitude() * 300);
            vertex(sunr ,-30);
            vertex(sunr ,0);
            endShape(CLOSE);
            popMatrix();
            noFill();
            rotor++;
        }
        
    }

    public int Timer(int cursec)
    {
        int out;
        if (Seccheck != cursec)
        {
            out = 1;
            Seccheck = cursec; 
        }
        else
        {
            out = 0;
        }
        
        return out;
    }

    public int getRandom (int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void stars(){
        float[] bands = getSmoothedBands();
        for(int i = 0 ; i < bands.length ; i++)
        {
            int rx = getRandom(0, width);
            int ry = getRandom(0, height);
            createShape(rx,ry,(int) bands[i] / 10,0,-300,1, 0);
        }
    }

    int bubble(float x, float y, float size, int points, int life){
        float max = 0;
        
        if (time > 255){
            max = 500;
        }
        else{
            max = 1000 * getSmoothedAmplitude();
        }

        if (size > max || life <= 0){
            int sides = points * 2;
            float px = x;
            float py = y - size; 
            for(int i = 0 ; i <= sides ; i ++)
            {
                float r = (i % 2 == 0) ? size : size / 2; 
                // float r = radius;
                float theta = map(i, 0, sides, 0, TWO_PI);
                float newx = x + sin(theta) * r;
                float newy = y - cos(theta) * r;
                
                //circle(x, y, 20);
                line(px, py, newx, newy);
                px = newx;
                py = newy;
            }
            int decrease = (int)(8 - 50 * getSmoothedAmplitude());
            if (decrease < 1){
                decrease = 1;
            }
            return decrease;
        }
        else{
            circle(x, y, size);
            return 0;
        }
    }
    
    public void creSweeper(){
        if(frameCount % 360 == 0){
            sweep_num ++;
            createShape(0,0,0,0,0,5,sweep_num);
        }
    }

    public void drawSweeper(float x){
        float position = (x * width/ 360);
        line(position, 0, position, height);
    }
    
    public void vertrect(float x, float y, float size){
        rect(x, y, size, height);
    }

    public void horirect(float x, float y, float size){
        rect (x, y, width, size);
    }

    public void render(){ // renders all shapes in render list
        for (Shape shape: Renderlist){
            if (shape.type == 0){
            pushMatrix();
            translate(width / 2,height / 2, -200);
            rotate( frameCount / 200.0f);
            polygon(shape.x, shape.y, shape.size, shape.points); // drawing polygons
            popMatrix();
            shape.size = shape.size + 16f;
            }
            if (shape.type == 1){
                pushMatrix();
                fill(255);
                noStroke();
                shape.z = shape.z + 30f;
                translate(shape.x, shape.y, shape.z);
                if (shape.size > 3){
                    shape.size = 1;
                }
                circle(shape.x, shape.y, shape.size);
                noFill();
                stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
                popMatrix(); 
            }
            if (shape.type == 2){
                setColour(shape.type);
                strokeWeight(2);
                pushMatrix();
                if(shape.x - shape.size <= ((sweep_place) * width/360) && shape.x + shape.size >= (sweep_place * width/ 360)){
                    shape.life = 0;
                }
                if(shape.life > 0){
                    shape.life = shape.life - bubble(shape.x, shape.y, shape.size, shape.points, shape.life);
                    if((shape.y - shape.size/2) >= (height - height/backdrop)){
                        shape.size = shape.size + 1;
                    }
                    else{
                        shape.life = 0;
                    }
                }
                else if(stage == 7){
                    setColour(shape.type);
                    fill(0,0,0);
                    pushMatrix();
                    bubble(shape.x, shape.y, shape.size, shape.points, shape.life);
                    popMatrix();
                    noFill();
                }
                popMatrix();
            }
            if (shape.type == 3){
                setColour(shape.type);
                pushMatrix();
                vertrect(shape.x, shape.y, shape.size);
                popMatrix();
                noFill();
            }
            if (shape.type == 4){
                setColour(shape.type);
                pushMatrix();
                horirect(shape.x, shape.y, shape.size);
                popMatrix();
                noFill();
            }
            if (shape.type == 5){
                setColour(shape.type);
                pushMatrix();
                drawSweeper(shape.x);
                popMatrix();
                strokeWeight(2);
                shape.x ++;
                if(shape.life == sweep_num){
                    sweep_place = (shape.x - 2);
                }
            }
        }
    }

    public void setColour(int type){
        if(type == 2){
            switch(stage){
                case 0:
                    stroke(255,255,0);
                    break;
                case 5:
                    stroke(255,0,0);
                    break;
                case 7:
                    stroke(0,0,0);
                    break;
            }
        }
        if(type == 3 || type == 4){
            switch(stage){
                case 0:
                    fill(255,165,0);
                    stroke(255,165,0);
                    break;
                case 5:
                    stroke(191,64,191);
                    fill(191,64,191);
                    break;
                case 7:
                    stroke(0,252,0);
                    fill(0,252,0);
                    break;
            }
        }
        if(type == 5){
            stroke(255,255,255);
            strokeWeight(15);
        }
    }

    public void chorusbase(){
        stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        strokeWeight(2);
        render();

        if (frameCount % 30 == 0)
        {
            int poi = (int) (50 * getSmoothedAmplitude());
            if (poi >= 3 )
            {
                poi = poi + 2;
            }
            createShape(0,0,1,poi,0,0, 0);
        }

        pushMatrix();
        camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
        translate(0, 0, -200);

        rotateX(angle);
        rotateZ(angle);       
        boxSize = (400 * getSmoothedAmplitude()); 
        sphere(boxSize);   
        popMatrix();
        angle += 0.01f;
    }

    public void funkybase(){
        render();

        if (backdrop > 1){
            createShape(width/2, 0, width/15, 0, 0, 3, 0);
            createShape(0, height - 2*(height/backdrop), height, 0, 0, 4, 0);
            backdrop = backdrop - 1;
        }
        if (frameCount % 5 == 0){
            int xposition = getRandom(0, width);
            int yposition = getRandom(height - height/backdrop, height);

            int poi = (int) (100 * getSmoothedAmplitude());
            if (poi < 5){
                poi = 5;
            }
            else if (poi > 15){
                poi = 15;
            }

            createShape(xposition, yposition, 1, poi, 0, 2, 8);
        }
    }

    
    public void settings(){
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
        fullScreen(P3D, 1);
    }

    public void setup(){
        colorMode(RGB);
        noCursor();
        textSize(height / 6);
        
        setFrameSize(256);

        startMinim();
        loadAudio("source.mp3");

        getAudioPlayer().cue(0);
        getAudioPlayer().play();

    }

    public void draw()
    {
        float[] bands = getSmoothedBands();
        calculateAverageAmplitude();
        try
        {
            calculateFFT();
        }
        catch(VisualException e)
        {
            e.printStackTrace();
        }
        calculateFrequencyBands();
        background(0);
        noFill();
        lights();
        switch(stage){
            case 0: //Intro
            funkybase();
            break;
            
            case 1: //Verse 1
            if (time_changed == 0){
                Renderlist.clear();
                time_changed = 1;
                backdrop = 240;
            }
            chorusbase();
            break;
            
            case 2: // Chorus 1
            if (time_changed == 1){
                Renderlist.clear();
                time_changed = 2;
            }
            chorusbase();
            break;

            case 3: //Verse 2
            if (time_changed == 2){
                Renderlist.clear();
                time_changed = 3;
            }
            chorusbase();
            break;

            case 4: //chorus 2
            if (time_changed == 3){
                Renderlist.clear();
                time_changed = 4;
            }
            chorusbase();
            stars();
            break;
            
            case 5: //Funky bit
            if (time_changed == 4){
                Renderlist.clear();
                time_changed = 5;
            }
            funkybase();
            creSweeper();
            break;

            case 6: // chorus 3
            if (time_changed == 5){
                Renderlist.clear();
                time_changed = 6;
                backdrop = 240;
            }
            chorusbase();
            sunrays();
            stars();
            break;
            
            case 7: //Outro
            if (time_changed == 6){
                Renderlist.clear();
                time_changed = 7;
            }
            funkybase();
            break;
        }
        present = LocalTime.now();
        Secnow = present.getSecond();
        time = time + Timer(Secnow);
        if (time <= 27){
            stage = 0;
        }
        if (time > 27 & time <= 61){
            stage = 1;
        }
        if (time > 61 & time <= 82){
            stage = 2;
        }
        if (time > 82 & time <= 129){
            stage = 3;
        }
        if (time > 128 & time <= 164){
            stage = 4;
        }
        if (time > 164 & time <= 201){
            stage = 5;
        }
        if (time > 201 & time <= 238){
            stage = 6;
        }
        if (time > 238){
            stage = 7;
        }
        if(time > 159 & time <= 163){
            fill(255);
            text("IN THE SILENCE", width / 7, height / 3);
            if (time > 162)
            {
                text("IN THE SILENCE", width / 7, (height / 3) * 2);
            }
            noFill();
        }
        
        for (int i = 0; i < Renderlist.size(); i++){
            if (i > 900)
            {
                Renderlist.remove(i - 900);
            }
        }


        //Debug Stuff
        textSize(12);
        fill(255);
        text("Renderlist size:" + Renderlist.size(), 30, 30);
        text("Time:" + time, 30, 45);
        text("Time_Debug:" + Secnow, 30, 60);

        for(int i = 0 ; i < bands.length ; i++)
        {
            text("Band_" + i + ":" + bands[i], 150, 30 + 15 * i);
        }
        noFill();
    }

}

