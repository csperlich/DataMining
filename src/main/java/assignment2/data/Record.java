package assignment2.data;

public class Record {
	private double[] inputs;
	private double[] outputs;

	public Record(double[] inputs, double[] outputs) {
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public double[] getInputs() {
		return this.inputs;
	}

	public double[] getOutputs() {
		return this.outputs;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("INPUTS: [");

		if (this.inputs.length > 1) {
			for (int i = 0; i < this.inputs.length - 1; i++) {
				sb.append(this.inputs[i] + ", ");
			}
		}

		sb.append(this.inputs[this.inputs.length - 1] + "] OUTPUTS: [");
		if (this.outputs != null) {
			if (this.outputs.length > 1) {
				for (int i = 0; i < this.outputs.length - 1; i++) {
					sb.append(this.outputs[i] + ", ");
				}
			}
			sb.append(this.outputs[this.outputs.length - 1]);
		}
		sb.append("]\n");
		return sb.toString();
	}

	public void setOutput(double[] outputs) {
		this.outputs = outputs;

	}

}
