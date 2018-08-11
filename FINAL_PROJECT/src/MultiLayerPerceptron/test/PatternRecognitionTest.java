package MultiLayerPerceptron.test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import MultiLayerPerceptron.MultiLayerPerceptron;
import MultiLayerPerceptron.transferfunctions.SigmoidalTransfer;
import MultiLayerPerceptron.utility.ImageProcessingBW;

public class PatternRecognitionTest {
	public static void main(String[] args) 
	{	
		TestPrecision(50);
		
		/*int i;
		for(i=0; i <= 200;)
		{
			if(i < 20)
				i++;
			else if(i < 50)
				i+=2;
			else if(i >= 50)
				i+=5;
			
			System.out.println(i+","+TestPrecision(i));
		}*/		
	}
	
	public static double TestPrecision(int maxit)
	{
		int size = 32;
		double error = 0.0;
		double accouracy = 0.01;
		int nimagesxpatt = 89;
		int npatt = 36;
		int dir = 1;
		int cpatt = 1;
		
		int[] layers = new int[]{ size*size, size, npatt };
		
		MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.6, new SigmoidalTransfer());
		
		/* Learning */
		for(int i = 0; i < maxit; i++)
		{
			for(int k = 1; k < nimagesxpatt; k++)
			{
				for(int j = 1; j < npatt+1; j++)
				{		
					String pattern = "C:/Users/Naaz/eclipse-workspace/FINAL_PROJECT/img/patterns/"+j+"/"+k+".png";
					double[] inputs = ImageProcessingBW.loadImage(pattern, size, size);
					
					if(inputs == null)
					{
						System.out.println("Cant find "+pattern);
						continue;
						
					}
					double[] output = new double[npatt];

					for(int l = 0; l < npatt; l++)
						output[l] = 0.0;
					
					output[j-1] = 1.0;
					
					
					// Training
					error = net.backPropagate(inputs, output);
					System.out.println("Error is "+error+" ("+i+" "+j+" "+k+" )");					
				}
			}
		}
		
	//	System.out.println("Learning completed!");
		
		/* Test */
		int correct = 0;
		
		
		double[] inputs = ImageProcessingBW.loadImage("C:/Users/Naaz/eclipse-workspace/FINAL_PROJECT/img/test/4.png", size, size);
		double[] output = net.execute(inputs);

		int max = 0;
		for(int i = 0; i < npatt; i++)
			if(output[i] > output[max])
			{
				max = i;
			}
		
		System.out.println(output[max]+" pattern "+(max+1));
		
	
		return (double) ((double) (npatt*nimagesxpatt) - (double) correct) / (double) (npatt*nimagesxpatt);
	}
}
