package ie.tudublin;
import ie.tudublin.Visual;
import java.util.ArrayList;

public class chorus extends Visual {

    public void settings()
    {
        size(800, 800, P3D);
        println("CWD: " + System.getProperty("user.dir"));
        fullScreen(P3D, SPAN);
    }

    public class Shape{
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

    public void createShape(int x, int y, int size, int points){
        Renderlist.add(new Shape(x,y,size,points));
    }
    
    void polygon(float x, float y, float radius, int npoints) {
        float angle = TWO_PI / npoints;
        beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
          float sx = x + cos(a) * radius;
          float sy = y + sin(a) * radius;
          vertex(sx, sy, 0);
        }
        endShape(CLOSE);
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
    float angle = 0;
    public void draw(){
        
        calculateAverageAmplitude();
        background(0);
        noFill();
        lights();
        stroke(map(getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
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

        calculateFrequencyBands();
        
    }

    public void render(){
        for (Shape shape: Renderlist){
            pushMatrix();
            translate(width / 2,height / 2, -200);
            rotate( frameCount / 200.0f);
            polygon(shape.x, shape.y, shape.size, shape.points);
            popMatrix();
            shape.size = shape.size + 16f;
            
        }
    }



}
