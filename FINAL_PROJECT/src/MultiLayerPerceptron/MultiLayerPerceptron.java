package MultiLayerPerceptron;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MultiLayerPerceptron implements Cloneable
{
	protected double fLearningRate = 0.6;
	protected Layer[] fLayers;
	protected TransferFunction fTransferFunction;
	
	public MultiLayerPerceptron(int[] layers, double learningRate, TransferFunction fun)
	{
		fLearningRate = learningRate;
		fTransferFunction = fun;
		
		fLayers = new Layer[layers.length];
		
		for(int i = 0; i < layers.length; i++)
		{			
			if(i != 0)
			{
				fLayers[i] = new Layer(layers[i], layers[i - 1]);
			}
			else
			{
				fLayers[i] = new Layer(layers[i], 0);
			}
		}
	}
	
	public double[] execute(double[] input)
	{
		int i;
		int j;
		int k;
		double new_value;
		
		double output[] = new double[fLayers[fLayers.length - 1].length];
		
	
		//	System.out.println(fLayers[1].length+" outputs we want to see");
		
		
		// Put input
	    for(i = 0; i < fLayers[0].length; i++)
        {
            
            fLayers[0].neurons[i].value = input[i];
        }
        
      //  StringBuilder sb= new StringBuilder();
        
        // Execute - hiddens + output
        for(k = 1; k < fLayers.length; k++)
        {
            for(i = 0; i < fLayers[k].length; i++)
            {
                new_value = 0.0;
                for(j = 0; j < fLayers[k - 1].length; j++) {
                    new_value += fLayers[k].neurons[i].weights[j] * fLayers[k - 1].neurons[j].value;
//                    sb.append(k+"\t");
//                    sb.append(i+"\t");
//                    sb.append(fLayers[k].neurons[i].bias+"\t");
//                    sb.append(fLayers[k].neurons[i].weights[j]+"\n");
                }
                new_value += fLayers[k].neurons[i].bias;
                
                fLayers[k].neurons[i].value = fTransferFunction.evalute(new_value);
            }
        }
 //       System.out.println(sb.toString());
		
		// Get output
		for(i = 0; i < fLayers[fLayers.length - 1].length; i++)
		{
			output[i] = fLayers[fLayers.length - 1].neurons[i].value;
		}
		
		return output;
	}
	public double backPropagateMultiThread(double[] input, double[] output, int nthread)
	{
		return 0.0;
	}
	public double backPropagate(double[] input, double[] output)
	{
		double new_output[] = execute(input);
		double error;
		int i;
		int j;
		int k;
		for(i = 0; i < fLayers[fLayers.length - 1].length; i++)
		{
			error = output[i] - new_output[i];
			fLayers[fLayers.length - 1].neurons[i].delta = error * fTransferFunction.evaluteDerivate(new_output[i]);
		} 
	
		
		for(k = fLayers.length - 2; k >= 0; k--)
		{
			
			for(i = 0; i < fLayers[k].length; i++)
			{
				error = 0.0;
				for(j = 0; j < fLayers[k + 1].length; j++)
					error += fLayers[k + 1].neurons[j].delta * fLayers[k + 1].neurons[j].weights[i];
								
				fLayers[k].neurons[i].delta = error * fTransferFunction.evaluteDerivate(fLayers[k].neurons[i].value);				
			}
			
			
			for(i = 0; i < fLayers[k + 1].length; i++)
			{
				for(j = 0; j < fLayers[k].length; j++)
					fLayers[k + 1].neurons[i].weights[j] += fLearningRate * fLayers[k + 1].neurons[i].delta * 
							fLayers[k].neurons[j].value;
				fLayers[k + 1].neurons[i].bias += fLearningRate * fLayers[k + 1].neurons[i].delta;
			}
		}	
		
		 
		error = 0.0;
		
		for(i = 0; i < output.length; i++)
		{
			error += Math.abs(new_output[i] - output[i]);
			
			//System.out.println(output[i]+" "+new_output[i]);
		}

		error = error / output.length;
		return error;
	}
	
	public boolean save(String path)
	{
		try
		{
			FileOutputStream fout = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		}
		catch (Exception e) 
		{ 
			return false;
		}
		
		return true;
	}
	
	
	
	public static MultiLayerPerceptron load(String path)
	{
		try
		{
			MultiLayerPerceptron net;
			
			FileInputStream fin = new FileInputStream(path);
			ObjectInputStream oos = new ObjectInputStream(fin);
			net = (MultiLayerPerceptron) oos.readObject();
			oos.close();
			
			return net;
		}
		catch (Exception e) 
		{ 
			return null;
		}
	}
	
	

	
	public double getLearningRate()
	{
		return fLearningRate;
	}
	
	
	
	public void	setLearningRate(double rate)
	{
		fLearningRate = rate;
	}
	
	
	
	public void setTransferFunction(TransferFunction fun)
	{
		fTransferFunction = fun;
	}
	
	
	
	
	public int getInputLayerSize()
	{
		return fLayers[0].length;
	}
	
	
	
	public int getOutputLayerSize()
	{
		return fLayers[fLayers.length - 1].length;
	}
}
