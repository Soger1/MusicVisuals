package c20322553;

import ie.tudublin.Visual;
import ie.tudublin.*;
import java.util.*;

import ddf.minim.AudioBuffer;

import java.time.*;



public class Orderer extends Visual {

    ArrayList<Shape> Renderlist = new ArrayList<>();
    
    float angle = 0;
    int stage = 0;
    private int time = 0;
    float sphereSize = 0;
    float split = 0;
    float rotor = 0;
    LocalTime present = LocalTime.now();
    int Secnow = present.getSecond();
    int Seccheck = present.getSecond();
    int backdrop = 240;
    int time_changed = 0;
    private int sweep_num = 0;
    public float sweep_place = -100;
    wave waveform = new wave(this);
    synthWave synthform = new synthWave(this);
    float bx;
    float by;
    float sum;
    float average;

    public int getTime() {
        return this.time;
    }

    public int getsweepnum() {
        return this.sweep_num;
    }
    public int getbackdrop() {
        return this.backdrop;
    }

    public int getstage() {
        return this.stage;
    }

    public void synthwave()
    {
        //backdrop
        stroke(30, 11, 69);
        fill(30, 11, 69);
        rect(0, 0, width, height/2);


        //draw the sun
        stroke(255, 0, 0);
        fill(255,0,0);
        circle(width/2, height/4, getSmoothedAmplitude()*2000);
        stroke(255);
        
        //draw lines for the ground
        for (int i = 0; i <= 13; i++){
            bx = map(i, 0,13, 0-width/2, width+width/2);

            if(bx < 0){
                by = -bx;
                bx = 0;
                if(height - by < height/2){
                    by = height/2;
                }
            }
            else if(bx > width){
                by = bx - width;
                bx = width;
                if(height - by < height/2){
                    by = height/2;
                }
            }
            else{
                by = 0;
            }

            line(bx,height-by,width/2,height/2-1);

        }

        stroke(255);
        fill(0);

        //render horizontal lines to make ground look like its moving
        if(frameCount % 20 == 0){
            Renderlist.add(new hLine(0, height/2, 0, this));
        }
        synthform.wave_render(height/2);//call function to add waveform
        render();
    }

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
            Renderlist.add(new Star(rx,ry,(int) bands[i] / 10, -300,this));
            waveform.wave_render(height/2);

    }
    }
    
    public void creSweeper(){
        if(frameCount % 60 == 0){
            sweep_num ++;
            Renderlist.add(new sweeper(0,sweep_num,5, stage,this, 0));
            if (frameCount % 120 == 0)
            {
                int poi = (int) (50 * getSmoothedAmplitude());
                if (poi >= 3 )
                {
                    poi = poi + 2;
                }
            }
        }
    }

    public void render(){ // renders all shapes in render list
        for (Shape shape: Renderlist){
            shape.render();
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
            Renderlist.add(new Polygon(0,0,1, poi, this));
        }

        pushMatrix();
        camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
        translate(0, 0, -200);

        rotateX(angle);
        rotateZ(angle);       
        sphereSize = (400 * getSmoothedAmplitude()); 
        if(getstage() == 5)
        {
            stroke(0);
        }

        sphere(sphereSize);   
        popMatrix();
        angle += 0.01f;
    }

    public void funkybase(){
        render();

        if (backdrop > 1 && backdrop != 0){
            Renderlist.add(new v_wall(width/2,0,width/15,this));
            Renderlist.add(new h_wall(0,height - 2*(height/backdrop),height,this));
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


            Renderlist.add(new bubble(xposition,yposition,1,poi,80,this,2,height,width));
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
            synthwave();
            break;
            
            case 2: // Chorus 1
            if (time_changed == 1){
                Renderlist.clear();
                time_changed = 2;
            }
            sunrays();
            chorusbase();
            break;

            case 3: //Verse 2
            if (time_changed == 2){
                Renderlist.clear();
                time_changed = 3;
            }
            synthwave();
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
        if (time <= 13){
            stage = 0;
        }
        if (time > 13 & time <= 61){
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
        if(time > 159 & time <= 164){
            fill(255);
            textSize(width/12);
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
        text("Time:" + frameCount, 30, 45);
        text("Time_Debug:" + Secnow, 30, 60);

        for(int i = 0 ; i < bands.length ; i++)
        {
            text("Band_" + i + ":" + bands[i], 150, 30 + 15 * i);
        }
        noFill();
    }

}

