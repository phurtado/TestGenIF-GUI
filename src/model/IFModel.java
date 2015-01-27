package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class IFModel {
	private String ifFilePath;
	private Set<IFState> ifStates;
	
	
	public IFModel(String ifFilePath) throws Exception{
		if(ifFilePath.endsWith(".if"))
			this.setIfFilePath(ifFilePath);
		else throw new Exception("Invalid file type");
		
		ifStates = new HashSet<IFState>();
		String text = getText();
		Pattern p = Pattern.compile("\\bstate\\s+(\\w+)\\b", Pattern.CASE_INSENSITIVE);
	    Matcher m = p.matcher(text);
	    while (m.find()) {
	    	ifStates.add(new IFState(m.group(1)));
	    }
	}

	public String getIfFilePath() {
		return ifFilePath;
	}

	public void setIfFilePath(String ifFilePath) {
		this.ifFilePath = ifFilePath;
	}
	
	public String getText(){
	    String everything = "";
		try(BufferedReader bf = new BufferedReader(new FileReader(this.getIfFilePath()))) {
	        StringBuilder sb = new StringBuilder();
	        String line = bf.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = bf.readLine();
	        }
	        everything = sb.toString();
	    }
	    catch(Exception ex){
	    	everything = "Error: Could not retrieve the text from the file.";
	    }
        return everything;
	}
	
	public Set<IFState> getIfStates(){
		return ifStates;
	}
	
}
