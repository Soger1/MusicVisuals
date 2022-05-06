package c20322553;

import processing.core.PApplet;

public class synthWave extends PApplet{
    Orderer or;
    float mapped;
    float y_superimportantvariable;
    public synthWave(Orderer or){
        this.or = or;
    }

    public void wave_render(int y)
    {
        or.colorMode(PApplet.RGB);
        or.pushMatrix();
        or.translate(0, 0);
        for (int i = 0; i < or.getAudioBuffer().size(); i++)
        {
            or.stroke(255);
            mapped = map(i,0,(or.getAudioBuffer().size()),0,(or.width));
            y_superimportantvariable = y + y*or.getAudioBuffer().get(i)/3;
            if(y_superimportantvariable <= or.height/2){
                or.line(mapped, y, mapped, y + y * or.getAudioBuffer().get(i) / 3);

            }
        }
        or.popMatrix();
    }
}
