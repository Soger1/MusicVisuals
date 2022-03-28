package ie.tudublin;
import ie.tudublin.Visual;
import java.util.ArrayList;
import java.util.Iterator;

public class sequencer extends Visual {

    public class Shape{ // shape objects def
        float x;
        float y;
        float size;
        float op;
        int points;
        float z;
        int type;

        public Shape(float x, float y, float size, int points, float z, int type){
            this.x = x;
            this.y = y;
            this.size = size;
            this.points = points;
            this.z = z;
            this.type = type;
        }
    }

    ArrayList<Shape> Renderlist = new ArrayList<>();

    public void createShape(int x, int y, int size, int points, int z, int type){ // adds shapes to the render list
        Renderlist.add(new Shape(x,y,size,points,z, type));
    }
    
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

    
    
    float angle = 0;
    int stage = 0;
    int time = 0;
    float boxSize = 0;
    float split = 0;
    float rotor = 0;

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

    public int getRandom (int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void stars()
    {
        float[] bands = getSmoothedBands();
        for(int i = 0 ; i < bands.length ; i++)
        {
            int rx = getRandom(0, width);
            int ry = getRandom(0, height);
            createShape(rx,ry,(int) bands[i] / 10,0,-300,1);
        }
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
                shape.z = shape.z + 3f;
                translate(shape.x, shape.y, shape.z);
                if (shape.size > 3){
                    shape.size = 1;
                }
                circle(shape.x, shape.y, shape.size);
                noFill();
                stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
                popMatrix();
                
            }
        }

    }


    public void chorusbase()
    {
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
            createShape(0,0,1,poi,0,0);
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
    
    public void settings()
    {
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
        fullScreen(P3D, 1);
    }

    public void setup(){
        colorMode(HSB);
        noCursor();

        
        setFrameSize(256);

        startMinim();
        loadAudio("source.mp3");
        getAudioPlayer().cue(0);
        getAudioPlayer().play();

    }

    public void draw()
    {
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
            case 0:
            chorusbase();
            stars();
            break;
            
            case 1:
            chorusbase();
            break;
            
            case 2: // Chorus 1
            chorusbase();
            sunrays();
            break;

            case 3:
            chorusbase();
            break;

            case 4: // chorus 2
            chorusbase();
            stars();
            break;
            
            case 5:
            chorusbase();
            break;

            case 6: // chorus 3
            chorusbase();
            sunrays();
            stars();
            break;
            
            case 7:
            chorusbase();
            break;
        }
        if (frameCount % 60 == 0){
            time++;
        }
        if (time <= 26){
            stage = 0;
        }
        if (time > 26 & time <= 64){
            stage = 1;
        }
        if (time > 64 & time <= 84){
            stage = 2;
        }
        if (time > 84 & time <= 131){
            stage = 3;
        }
        if (time > 131 & time <= 167){
            stage = 4;
        }
        if (time > 167 & time <= 206){
            stage = 5;
        }
        if (time > 206 & time <= 243){
            stage = 6;
        }
        if (time > 243){
            stage = 7;
        }
        if(time > 163 & time <= 167){
            textSize(height / 6);
            fill(255);
            text("IN THE SILENCE", width / 7, height / 3);
            if (time > 165)
            {
                text("IN THE SILENCE", width / 7, (height / 3) * 2);
            }
            noFill();
        }
        if ( Renderlist.size() > 200){
            Renderlist.remove(1);
            Renderlist.remove(2);
            Renderlist.remove(3);
            Renderlist.remove(4);
            Renderlist.remove(5);
        }
        
    }

}