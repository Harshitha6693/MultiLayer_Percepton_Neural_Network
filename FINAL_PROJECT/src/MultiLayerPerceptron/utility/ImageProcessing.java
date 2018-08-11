package MultiLayerPerceptron.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

public class ImageProcessing {
	
	public static double[] loadImage(String path,int sizex, int sizey, boolean color)
	{
		File imgLoc=new File(path);
		
		BufferedImage bi;
		BufferedImage img;
		
		if(color)
			bi = new BufferedImage(sizex, sizey, BufferedImage.TYPE_INT_ARGB);
		else
			bi = new BufferedImage(sizex, sizey, BufferedImage.TYPE_BYTE_GRAY);
		
		try {
			img=ImageIO.read(imgLoc);
		}
		catch(IOException ex) {
			System.out.println(path+" not loaded");
			return null;
		}
		
		Graphics2D g=bi.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		double[] data=new double[sizex * sizey];
		
		for(int i=0; i<sizex; i++)
			for(int j=0; j<sizey; j++) {
				int[] d=new int[3];
				bi.getRaster().getPixel(i, j, d);
				
				data[i*sizex+j]=((double)d[0])/255.0;
				
			}
		return data;
	}

	public static boolean saveImage(String path, double[] data, int sizex, int sizey, boolean color)
	{
		BufferedImage bi;
		
		if(color)
			bi=new BufferedImage(sizex, sizey, BufferedImage.TYPE_INT_ARGB);
		
		else
			bi=new BufferedImage(sizex, sizey, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int i=0; i<sizex; i++)
			for(int j=0; j<sizey; j++)
			{
				int[] d= new int[3];
				d[0] = (int) (data[i*sizex + j] * 255);
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
