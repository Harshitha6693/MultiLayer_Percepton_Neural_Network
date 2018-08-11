package MultiLayerPerceptron;

public class Layer {
	public Neuron neurons[];
	public int length;
	
	
	public Layer(int len, int prev)
	{
		length = len;
		neurons = new Neuron[len];
		
		for(int j = 0; j < length; j++)
			neurons[j] = new Neuron(prev);
	}
}
