package gameClient.Audio;

import javazoom.jl.player.*;
import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimplePlayer implements Runnable
{ 
    private String path;
    private Player playMP3;
	public SimplePlayer(String path)
	{
    	this.path = path;
    }
    public SimplePlayer()
    {

    }
    public void play()
    {
        try
        {
             FileInputStream fis = new FileInputStream(path);
             playMP3 = new Player(fis);
             playMP3.play();
        }  
        catch(Exception e)
        {
        	System.out.println(e);
        }
    }



	@Override
	public void run() 
	{
	    play();
	}


}