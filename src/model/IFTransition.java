package model;

public class IFTransition {
	private IFState source;
	private IFState target;
	private String process;
	private String input;
	private String output;
	
	public IFTransition(IFState source,IFState target,String process,String input,String output){
		this.source = source;
		this.target = target;
		this.process = process;
		this.input = input;
		this.output = output;
	}

	public IFState getSource() {
		return source;
	}

	public void setSource(IFState source) {
		this.source = source;
	}

	public IFState getTarget() {
		return target;
	}

	public void setTarget(IFState target) {
		this.target = target;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	@Override
	public String toString(){
		return process.toString() + " :: " + source.toString() + "---" + input + "|" + output + "-->" + target.toString();
	}
}
