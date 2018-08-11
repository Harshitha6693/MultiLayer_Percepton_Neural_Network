package MultiLayerPerceptron;

public interface TransferFunction {
	public double evalute(double value);
	public double evaluteDerivate(double value);
}
