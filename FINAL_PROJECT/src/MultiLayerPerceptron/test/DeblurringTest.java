package MultiLayerPerceptron.test;

import MultiLayerPerceptron.MultiLayerPerceptron;
import MultiLayerPerceptron.transferfunctions.SigmoidalTransfer;
import MultiLayerPerceptron.utility.ImageProcessing;

public class DeblurringTest {
	public static void main(String[] args) 
	{
		int sizex = 32;
		int sizey = 32;
		int nimages = 9;
		
		int[] layers = new int[]{ sizex*sizey, sizex*sizey+sizex, sizex*sizey };
		
		MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.6, new SigmoidalTransfer());
		
		/* Learning */
		int i = 0;
		double error = 0.0;
		//double accouracy = 0.01;
		double maxit = 1000;
		
		while(/*(error > accouracy) && */(i < maxit))
		{
			
			double[] inputs = ImageProcessing.loadImage("C:/Users/Naaz/eclipse-workspace/FINAL_PROJECT/img/deblur/set2/"+(i%nimages)+"_blur.png", sizex, sizey, false);
			
			if(inputs == null)
			{
				i++;
				continue;
			}
			
			double[] output = ImageProcessing.loadImage("C:/Users/Naaz/eclipse-workspace/FINAL_PROJECT/img/deblur/set2/"+(i%nimages)+".png", sizex, sizey, false);
			
			if(output == null)
			{
				i++;
				continue;
			}
			
			// Training
			error = net.backPropagate(inputs, output);
			System.out.println("Error at step "+i+" is "+error);
			
			i++;
		}
		
		System.out.println("Learning completed!");
		
		/* Test */
		double[] inputs = ImageProcessing.loadImage("C:/Users/Naaz/eclipse-workspace/FINAL_PROJECT/img/deblur/set2/test_unblurred.png", sizex, sizey, false);
		double[] output = net.execute(inputs);

		ImageProcessing.saveImage("C:/Users/Naaz/eclipse-workspace/FINAL_PROJECT/img/deblur/set2/test_unblurred1.png", output, sizex, sizey, false);
		System.out.println("Test saved!");
	}
}
