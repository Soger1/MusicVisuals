package c20322553;



public class Shape extends Orderer {

    public void setColour(int type, Orderer or){
        if(type == 2){
            switch(getstage()){
                case 0:
                    or.stroke(255,255,0);
                    break;
                case 5:
                    or.stroke(255,0,0);
                    break;
                case 7:
                    or.stroke(0,0,0);
                    break;
            }
        }
        if(type == 3 || type == 4){
            switch(getstage()){
                case 0:
                    or.fill(255,165,0);
                    or.stroke(255,165,0);
                    break;
                case 5:
                    or.stroke(191,64,191);
                    or.fill(191,64,191);
                    break;
                case 7:
                    or.stroke(0,252,0);
                    or.fill(0,252,0);
                    break;
            }
        }
        if(type == 5){
            or.stroke(255,255,255);
            or.strokeWeight(15);
        }
    }
}

class Polygon extends Shape
{
    float x;
    float y;
    float size;
    float op;
    int points;
    Orderer or;


    public Polygon(float x, float y, float size, int points, Orderer or){
        this.x = x;
        this.y = y;
        this.size = size;
        this.points = points;
        this.or = or;
    }

    public void render(){
        
        or.pushMatrix();
        or.translate(or.width / 2,or.height / 2, -200);
        or.rotate( or.frameCount / 200.0f);
        polygon(x, y, size, points); // drawing polygons
        or.popMatrix();
        size = size + 16f;
    }

    public void polygon(float x, float y, float radius, int npoints) { // draws polygons with x number of points
        float angle = TWO_PI / npoints;
        or.beginShape();
        for (float a = 0; a < TWO_PI; a += angle) {
            float sx = x + cos(a) * radius;
            float sy = y + sin(a) * radius;
            or.vertex(sx, sy, 0);
        }
        or.endShape(CLOSE);
    }
}

class Star extends Shape
{
    float x;
    float y;
    float size;
    float op;
    float z;
    Orderer or;

    public Star(float x, float y, float size, float z, Orderer or){
        this.x = x;
        this.y = y;
        this.size = size;
        this.z = z;
        this.or = or;
    }

    public void render(){
        or.pushMatrix();
        or.fill(255);
        or.noStroke();
        z = z + 30f;
        or.translate(x, y, z);
        if (size > 3)
        {
            size = 1;
        }
        or.circle(x, y, size);
        or.noFill();
        or.stroke(map(or.getSmoothedAmplitude(), 0, 1, 0, 255), 255, 255);
        or.popMatrix(); 
    }
}

class bubble extends Shape
{
    float x;
    float y;
    float size;
    float op;
    int points;
    int life;
    int type;
    Orderer or;
    int h;
    int w;

    public bubble(float x, float y, float size, int points, int life, Orderer or, int type,int h, int w){
        this.x = x;
        this.y = y;
        this.size = size;
        this.points = points;
        this.life = life;
        this.or = or;
        this.type = type;
        this.h = h;
        this.w = w;
    }

    public void render(){
        or.stroke(255,255,0);
        or.strokeWeight(2);
        or.pushMatrix();
        if(x - size <= ((sweep_place) * w/360) && +size >= (sweep_place * w/ 360)){
           life = 0;
        }
        if(life > 0){
            life = life - bubbles(x, y, size, points, life);
            
            if((y - size/2) >= (h - h/getbackdrop())){
                life = 0;
            }
            else
            {
                size++;
            }
        }
        else if( getstage() == 7){
            or.stroke(255,255,0);
            or.fill(0,0,0);
            or.pushMatrix();
            bubbles(x, y, size, points, life);
            or.popMatrix();
            or.noFill();
        }
        or.popMatrix();
    }

    public int bubbles(float x, float y, float size, int points, int life){
        float max = 0;
        
        if (getTime() > 255){
            max = 500;
        }
        else{
            max = 1000 * or.getSmoothedAmplitude();
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
                or.line(px, py, newx, newy);
                px = newx;
                py = newy;
            }
            int decrease = (int)(8 - 50 * or.getSmoothedAmplitude());
            if (decrease < 1){
                decrease = 1;
            }
            return decrease;
        }
        else{
            or.circle(x, y, size);
            return 0;
        }
    }
}

class v_wall extends Shape
{
    float x;
    float y;
    float size;
    Orderer or;

    public v_wall(float x, float y, float size, Orderer or){
        this.x = x;
        this.y = y;
        this.size = size;
        this.or = or;
    }

    public void render(){
        if (or.stage != 5){
            or.fill(255,165,0);
            or.stroke(255,165,0);
            vertrect(x, y, size);
            or.noFill();
        }
    }

    public void vertrect(float x, float y, float size){
        or.rect(x, y, size, or.height);
    }
}

class h_wall extends Shape
{
    float x;
    float y;
    float size;
    Orderer or;

    public h_wall(float x, float y, float size, Orderer or){
        this.x = x;
        this.y = y;
        this.size = size;
        this.or = or;
    }

    public void render(){
        if (or.stage != 5){
            or.fill(255,165,0);
            or.stroke(255,165,0);
            horirect(x, y, size);
            or.noFill();
        }
    }

    public void horirect(float x, float y, float size){
        or.rect(x, y, or.width, size);
    }
}

class sweeper extends Shape
{
    float x;
    float life;
    int type;
    int stage;
    Orderer or;
    int dir;

    public sweeper(float x, float life, int type, int stage, Orderer or, int dir)
    {
        this.x = x;
        this.life = life;
        this.type = type;
        this.or = or;
        this.dir = dir;
    }

    public void render(){
        setColour(type, or);
        or.pushMatrix();
        drawSweeper(x);
        or.popMatrix();
        or.strokeWeight(or.width/20);
        x ++;
        if(life == getsweepnum()){
            sweep_place = (x - 2);
        }
    }

    public void drawSweeper(float x){
        if (this.dir == 0 && x <= or.width + 20)
        {
        float position = (x * or.width/ 360);
        or.line(position, 0, position, or.height);
        }
    }
}

class hLine extends Shape{
    float gain;
    float x;
    float y;
    Orderer or;

    public hLine(float x, float y, float gain, Orderer or){
        this.x = x;
        this.y = y;
        this.gain = gain;
        this.or = or;
    }

    public void render(){
        or.line(0, y, or.width, y);
        gain = gain + (float)1.618/10;
        y = y + gain;
    }
}
