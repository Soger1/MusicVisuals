package c20322553;
import c20322553.Orderer;

public class Main {

    public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Orderer());		
	}

	public static void main(String[] args)
	{
		Main main = new Main();
		main.startUI();			
	}
    
}
