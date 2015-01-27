package model;

import java.util.Collection;

public class TestObjective {
	private String title;
	private String description;
	private String formalDefinition;
	private String cFilePath;
	
	public TestObjective(String title,String description,String formalDefinition, String cFilePath){
		this.title = title;
		this.description = description;
		this.formalDefinition = formalDefinition;
		this.cFilePath = cFilePath;
	}
	
	@Override
	public String toString() {
		return title;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getFormalDefinition() {
		return formalDefinition;
	}
	public void setFormalDefinition(String formalDefinition) {
		this.formalDefinition = formalDefinition;
	}

	public String getCFilePath() {
		return cFilePath;
	}

	public void setCFilePath(String filePath) {
		this.cFilePath = filePath;
	}
	
	public static String toCCode(Collection<IFTransition> transitions, boolean ordered){
		String npurpstr = ""; 				//number of purposes section of the code
		String purposesstr = "";			//purposes definition section of the code
		
		if(ordered) {
			npurpstr = "  	numOrdPurposes = "+ transitions.size() +";\r\n" + 
					   "  	numPurposes = 0;\r\n";
		}
		else {
			npurpstr = "  	numPurposes = "+ transitions.size() +";\r\n" + 
					   "  	numOrdPurposes = 0;\r\n";
		}
		
		int ipurp = 0;
		int nsignal = 0;
		for(IFTransition t : transitions){
			int isignal = 0;
			int numsignals = 0;
			String inpSignal = "";
			String outSignal = "";
			String inpParams = "";
			String outParams = "";
			if(!t.getInput().isEmpty()){
				numsignals++;
				String replaceParenthesis = t.getInput()
											 .replaceAll("\\(", "\\{")
											 .replaceAll("(?<=\\w)\\{", ",\\{")
											 .replaceAll("\\)", "\\}");
				int sepindex = replaceParenthesis.indexOf(',');
				if(sepindex != -1){
					inpSignal = replaceParenthesis.substring(0, sepindex);
					inpParams = replaceParenthesis.substring(sepindex+1, replaceParenthesis.length());
					if(inpParams.length() <= 2) inpParams = "NULL";
					else inpParams = "\""+inpParams+"\"";
				}
				else inpSignal = replaceParenthesis;
			}
			if(!t.getOutput().isEmpty()) {
				numsignals++;
				String replaceParenthesis = t.getOutput()
						 .replaceAll("\\(", "\\{")
						 .replaceAll("(?<=\\w)\\{", ",\\{")
						 .replaceAll("\\)", "\\}");
				int sepindex = replaceParenthesis.indexOf(',');
				if(sepindex != -1){
					outSignal = replaceParenthesis.substring(0, sepindex);
					outParams = replaceParenthesis.substring(sepindex+1, replaceParenthesis.length());
					if(outParams.length() <= 2) outParams = "NULL";
					else outParams = "\""+outParams+"\"";
				}
				else outSignal = replaceParenthesis;
				
			}
			purposesstr +=
				"   	purposes["+ipurp+"].numSignals = "+ numsignals +";        \r\n" + 
				"   	purposes["+ipurp+"].process = \""+t.getProcess()+"\";   	\r\n" +  //TODO PROCESS NAME "Patched"
				"   	purposes["+ipurp+"].source = \""+t.getSource()+"\";          \r\n" + 
				"   	purposes["+ipurp+"].target = \""+t.getTarget()+"\";       	\r\n";
			if(!t.getInput().isEmpty())	
				purposesstr += "		signalData signal"+ nsignal +" = {\""+inpSignal+"\",\"input\","+inpParams+"}; \r\n" + 
							   "   		purposes["+ipurp+"].signals["+ isignal++ +"] = signal"+ nsignal++ +"; \r\n";
			if(!t.getOutput().isEmpty())		
				purposesstr += "		signalData signal"+ nsignal +" = {\""+outSignal+"\",\"output\","+outParams+"}; \r\n" +
							   "   		purposes["+ipurp+"].signals["+ isignal++ +"] = signal"+ nsignal++ +"; \r\n"; 
				 
			ipurp++;
		}
		
		return "\r\n" + 
				npurpstr + 
				"\r\n" + 
				"	int i;\r\n" + 
				"   	for (i=0; i < numPurposes;i++) { \r\n" + 
				"      	purposes[i].status = false;      \r\n" + 
				"      	purposes[i].visited = false;     \r\n" + 
				"      	purposes[i].process = NULL;      \r\n" + 
				"		purposes[i].source = NULL;       \r\n" + 
				"		purposes[i].target = NULL;       \r\n" + 
				"      	purposes[i].numBoundClocks = 0;	 \r\n" + 
				"      	purposes[i].numActiveClocks = 0; \r\n" + 
				"      	purposes[i].numSignals = 0;      \r\n" + 
				"      	purposes[i].numVariables = 0;    \r\n" + 
				"      	purposes[i].depth = -1;          \r\n" + 
				"   	}\r\n" + 
				"\r\n" + 
				"   	for (i=0; i < numOrdPurposes;i++) {\r\n" + 
				"      	ordPurposes[i].status = false;     \r\n" + 
				"      	ordPurposes[i].visited = false;    \r\n" + 
				"      	ordPurposes[i].process = NULL;     \r\n" + 
				"		ordPurposes[i].source = NULL;      \r\n" + 
				"		ordPurposes[i].target = NULL;      \r\n" + 
				"      	ordPurposes[i].numBoundClocks = 0; \r\n" + 
				"      	ordPurposes[i].numActiveClocks = 0;\r\n" + 
				"      	ordPurposes[i].numSignals = 0;     \r\n" + 
				"      	ordPurposes[i].numVariables = 0;   \r\n" + 
				"      	ordPurposes[i].depth = -1;         \r\n" + 
				"   	}\r\n" + 
				"\r\n" +
				purposesstr;/* +
				"	purposes[0].numActiveClocks = 1;       \r\n" + 
				"   	purposes[0].numSignals = 2;        \r\n" + 
				"   	purposes[0].process = \"{PCEPChild}0\";   	\r\n" + 
				"   	purposes[0].source = \"KeepWait\";          \r\n" + 
				"   	purposes[0].target = \"KeepWait\";       	\r\n" + 
				"	\r\n" + 
				"\r\n" + 
				"	signalData signal1 = {\"TCP_Data_PCEP_Error_ind\",\"input\",\"{f,{_,{{1,4},}}}\"}; \r\n" + 
				"	signalData signal2 = {\"PCEP_Error_ind\",\"output\",NULL};	                       \r\n" + 
				"   	purposes[0].signals[0] = signal1;                                              \r\n" + 
				"   	purposes[0].signals[1] = signal2;                                              \r\n" + 
				"	\r\n" + 
				"	activeClockData activeClock1 = {\"pcepKeepWaitTimer\",true};                       \r\n" + 
				"   	purposes[0].active_clocks[0] = activeClock1;                                   \r\n" + 
				"";*/
	}

}
