package MultiLayerPerceptron.test;

import MultiLayerPerceptron.MultiLayerPerceptron;
import MultiLayerPerceptron.transferfunctions.*;

public class AndTest {
	public static void main(String[] args)
	{
		int[] layers=new int[] {2,2,1};
		
		MultiLayerPerceptron net = new MultiLayerPerceptron(layers, 0.6, new SigmoidalTransfer());
		
		/* Learning */
		for(int i = 0; i < 10; i++)
		{
			double[] inputs = new double[]{Math.round(Math.random()), Math.round(Math.random())};
			double[] output = new double[1];
			double error;
			
			
			if((inputs[0] == inputs[1]) && (inputs[0] == 1))
				output[0] = 1.0;
			else
				output[0] = 0.0;
			

			System.out.println(inputs[0]+" and "+inputs[1]+" = "+output[0]);
			
						
			error = net.backPropagate(inputs, output);
			
			
			System.out.println("Error at step "+i+" is "+error);
		}
		
		System.out.println("Learning completed!");
		
		/* Test */
		double[] inputs = new double[]{1.0, 1.0};
		double[] output = net.execute(inputs);

		System.out.println(inputs[0]+" and "+inputs[1]+" = "+Math.round(output[0])+" ("+output[0]+")");
		
	}
}
