package c20322553;

import processing.core.PApplet;

public class wave extends PApplet{
    Orderer or;
    float mapped;
    public wave(Orderer or){
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
            or.line(mapped, y, mapped, y + y * or.getAudioBuffer().get(i) / 3);
            or.line(mapped, y, mapped, y - y * or.getAudioBuffer().get(i) / 3);
        }
        or.popMatrix();
    }
}
