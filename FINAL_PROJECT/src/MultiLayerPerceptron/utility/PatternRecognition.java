package MultiLayerPerceptron.utility;

import MultiLayerPerceptron.MultiLayerPerceptron;
import MultiLayerPerceptron.TransferFunction;


public class PatternRecognition {
	protected MultiLayerPerceptron fNetwork;
	protected int fNPatterns;
	protected int fImageSize;
	
	public PatternRecognition(int imgSize,int nPatterns,double learningRate, TransferFunction transferFun) {
		
		int[] layers=new int[] {imgSize * imgSize, imgSize, nPatterns};
		fNPatterns=nPatterns;
		fImageSize=imgSize;
		
		fNetwork= new MultiLayerPerceptron(layers, learningRate, transferFun);
		
	}
	
	public int recognize(String imgPath)
	{
		double[] inputs = ImageProcessingBW.loadImage(imgPath, fImageSize, fImageSize);
		double[] output = fNetwork.execute(inputs);
		int max = 0;

		for(int i = 1; i < fNPatterns; i++)
		{
			if(output[i] > output[max])
			{
				max = i;
			}
		}
		
		return max;
	}
	
	public int recognize(boolean[][] bitMap, int sizeX, int sizeY)
	{
		// Riempio l'input con la bitmap
		double[] inputs = new double[fNetwork.getInputLayerSize()];
		
		int x = 0; 
		int y = 0;
		
		for(int i = 0; i < fNetwork.getInputLayerSize(); i++)
		{
			if(bitMap[y][x] == false)
				inputs[y*sizeX + x] = 0.0;
			else
				inputs[y*sizeX + x] = 1.0;
			
			x++;
			
			if(x >= sizeX)
			{
				x = 0;
				y++;
			}
		}
		
		
		double[] output = fNetwork.execute(inputs);
		int max = 0;

		for(int i = 1; i < fNPatterns; i++)
		{
			if(output[i] > output[max])
			{
				max = i;
			}
		}
		
		return max;
	}
	
	public double learningStep(double[] input, int output)
	{
		if(input == null)
		{
			return 0;
		}

		double[] outputs = new double[fNPatterns];

		for(int l = 0; l < fNPatterns; l++)
				outputs[l] = 0.0;
				
		outputs[output] = 1.0;

		return fNetwork.backPropagate(input, outputs);
	}
	
	public double learningStep(String dir, int imagesPerPattern)
	{
		double error = 0.0;
		for(int k = 1; k < imagesPerPattern; k++)
		{
			for(int j = 1; j < fNPatterns+1; j++)
			{		
				String pattern = dir+"/"+j+"/"+k+".png";
				double[] inputs = ImageProcessingBW.loadImage(pattern, fImageSize, fImageSize);
				
				if(inputs == null)
				{
					continue;	
				}
				double[] output = new double[fNPatterns];

				for(int l = 0; l < fNPatterns; l++)
					output[l] = 0.0;
				
				output[j-1] = 1.0;
				
				error += fNetwork.backPropagate(inputs, output);			
			}
		}
		return (error / (double) (fNPatterns * imagesPerPattern));
	}
}
