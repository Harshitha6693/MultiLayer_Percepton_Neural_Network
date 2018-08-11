package MultiLayerPerceptron.utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageProcessingBW {
	public static double[] loadImage(String path, int sizex, int sizey) {
		File imgLoc=new File(path);
		
		BufferedImage bi;
		BufferedImage img;
		
		bi = new BufferedImage(
	         	sizex, 
	            sizey,
	            BufferedImage.TYPE_BYTE_GRAY);
	
		try 
		{
			img = ImageIO.read(imgLoc);
		}
		catch (IOException ex) 
		{
			System.out.println(path+" not loaded");
			return null;
		}
   
	
	    Graphics2D g = bi.createGraphics();
	    g.drawImage(img, 0, 0, null);
	    g.dispose();
    
	    double[] data = new double[sizex*sizey];
	    
	    for(int i = 0; i < sizex; i++)
	    	for(int j = 0; j < sizey; j++)
	    	{
	    		int[] d = new int[3];
	    		bi.getRaster().getPixel(i, j, d);
	    		
	    		if(d[0] > 128)
	    			data[i*sizex + j] = 0.0;
	    		else
	    			data[i*sizex + j] = 1.0;
	    	}
	    		
		return data;		
	}


	public static boolean saveImage(String path, double[] data, int sizex, int sizey)
	{
		BufferedImage bi;
	
		bi = new BufferedImage(
	            sizex, 
	            sizey,
	            BufferedImage.TYPE_BYTE_GRAY);
			
		for(int i = 0; i < sizex; i++)
			for(int j = 0; j < sizey; j++)
		{				
			int[] d = new int[3];
			
			if(data[i*sizex + j] >= 0.5)
				d[0] = 0;
			else
				d[0] = 255;
			
			d[1] = 0;
			d[2] = 0;
    		bi.getRaster().setPixel(i, j, d);
    		
		}
	
		try 
		{
		    File outputfile = new File(path);
		    ImageIO.write(bi, "png", outputfile);
		    return true;
		} 
		catch (IOException e) 
		{
			return false;
		}
	}
}
