package ie.tudublin;
import ie.tudublin.Visual;
import java.util.ArrayList;

public class sequencer extends Visual {

    public class Shape{ // shape objects def
        float x;
        float y;
        float size;
        float op;
        int points;

        public Shape(float x, float y, float size, int points){
            this.x = x;
            this.y = y;
            this.size = size;
            this.points = points;
        }
    }

    ArrayList<Shape> Renderlist = new ArrayList<>();

    public void createShape(int x, int y, int size, int points){ // adds shapes to the render list
        Renderlist.add(new Shape(x,y,size,points));
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

    public void render(){ // renders all shapes in render list
        for (Shape shape: Renderlist){
            pushMatrix();
            translate(width / 2,height / 2, -200);
            rotate( frameCount / 200.0f);
            polygon(shape.x, shape.y, shape.size, shape.points); // drawing polygons
            popMatrix();
            shape.size = shape.size + 16f;
            
        }
    }
    
    public void settings()
    {
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
        fullScreen(P3D, SPAN);
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

    int stage = 0;
    int time = 0;

    public void draw()
    {
        calculateAverageAmplitude();
        background(0);
        noFill();
        lights();
        stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        switch(stage){
            case 0:
            
            break;
            

            case 1:
            
            break;
            

            case 2: // Chorus 1
            
                strokeWeight(2);
                render();

                if (frameCount % 30 == 0){
                    int poi = (int) (50 * getSmoothedAmplitude());
                        if (poi >= 3 ){
                            poi = poi + 2;
                        }
                        createShape(0,0,1,poi);
                    }
                pushMatrix();
                camera(0, 0, 0, 0, 0, -1, 0, 1, 0);
                translate(0, 0, -200);

                rotateX(angle);
                rotateZ(angle);       
                float boxSize = (400 * getSmoothedAmplitude()); 
                sphere(boxSize);   
                popMatrix();
                angle += 0.01f;
                break;

            case 3:
            

            break;

            case 4: // chorus 2
            
            strokeWeight(2);

            render();

            if (frameCount % 30 == 0){
                int poi = (int) (50 * getSmoothedAmplitude());
                if (poi >= 3 ){
                    poi = poi + 2;
                }
                createShape(0,0,1,poi);
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
            break;
            
            

            case 5:
            break;

            case 6:
            
                strokeWeight(2);

                render();
        
                if (frameCount % 30 == 0){
                    int poi = (int) (50 * getSmoothedAmplitude());
                    if (poi >= 3 ){
                        poi = poi + 2;
                    }
                    createShape(0,0,1,poi);
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
                break;
            

            case 7:
            

            break;
        }
        if (frameCount % 60 == 0){
            time++;
        }
        if (time <= 26){
            stage = 0;
        }
        if (time > 26 & time <= 62){
            stage = 1;
        }
        if (time > 62 & time <= 82){
            stage = 2;
        }
        if (time > 82 & time <= 126){
            stage = 3;
        }
        if (time > 126 & time <= 162){
            stage = 4;
        }
        if (time > 162 & time <= 199 ){
            stage = 5;
        }
        if (time > 199 & time <= 223){
            stage = 6;
        }
        if (time > 233){
            stage = 7;
        }
    }

}
