package model;

public class IFState {
	private String name;
	
	public IFState(String name){
		this.name = name;
	}
	
	public IFState(){
		this.name = "";
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return getName();
	}
}
