package xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import model.IFModel;
import model.TestObjective;

public class Project {
	private List<TestObjective> tpList;
	private String imgPath;
	private String ifModelPath;
	private String ifWorkingPath;
	
	private static Project instance = null;
	
	protected Project(){
		this.tpList = new ArrayList<TestObjective>();
		this.imgPath = "";
		this.ifModelPath = "";
		this.ifWorkingPath = "";
		
	}
	
	public static Project getInstance(){
		if(instance == null){
			instance = new Project();
		}
		return instance;
	}
	
	public List<TestObjective> getTestPurposes(){
		return this.tpList;
	}
	
	public String getImagePath(){
		return this.imgPath;
	}
	
	public String getIfModelPath(){
		return this.ifModelPath;
	}
	
	public String getIfWorkingPath(){
		return this.ifWorkingPath;
	}
	
	public static void parseProjectFile(String file) throws XMLStreamException, FactoryConfigurationError, FileNotFoundException{	
		Project p = new Project();
		XMLStreamReader xsr = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(file));
		while(xsr.hasNext()){
			int event = xsr.next();
			switch(event){
				case XMLStreamConstants.START_ELEMENT:
					String lcn = xsr.getLocalName();
					if(lcn.equals("image")) {
						p.imgPath = xsr.getAttributeValue(null,"src");
					}
					else if(lcn.equals("ifmodel")) {
						p.ifModelPath = xsr.getAttributeValue(null,"src");
					}
					else if(lcn.equals("tps")) {
						p.tpList = new ArrayList<TestObjective>();
						parseTpList(p, xsr);
					}
					else if(lcn.equals("ifworkingpath")) {
						p.ifWorkingPath = xsr.getAttributeValue(null,"src");
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					break;
				case XMLStreamConstants.END_ELEMENT:
					break;
				case XMLStreamConstants.START_DOCUMENT:
					break;
			}
		}
		instance = p;
	}

	private static void parseTpList(Project p, XMLStreamReader xsr)
			throws XMLStreamException {
		int level = 1;
		while(xsr.hasNext() && level > 0){
			int ev2 = xsr.next();
			switch(ev2){
				case XMLStreamConstants.START_ELEMENT:
					level++;
					String lcn2 = xsr.getLocalName();
					if(lcn2.equals("tp")) {
						parseTp(p, xsr);
						level--; //parseTp exits one level below
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					level--;
					break;
			}
		}
	}

	private static void parseTp(Project p, XMLStreamReader xsr)
			throws XMLStreamException {
		int level = 1;
		String title = xsr.getAttributeValue(null,"title");
		String desc = "";
		String fdef = "";
		String tpf = "";
		while(xsr.hasNext() && level > 0){
			int ev3 = xsr.next();
			switch(ev3){
				case XMLStreamConstants.START_ELEMENT:
					level++;
					String lcn3 = xsr.getLocalName();
					
					if(lcn3.equals("description")) {
						ev3 = xsr.next();
						if(ev3 == XMLStreamConstants.CHARACTERS) desc=xsr.getText().trim();
					}
					else if (lcn3.equals("formaldef")){
						ev3 = xsr.next();
						if(ev3 == XMLStreamConstants.CHARACTERS) fdef=xsr.getText().trim();
					}
					else if (lcn3.equals("tpfile")){
						tpf = xsr.getAttributeValue(null,"src");
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					level--;
					break;
			}
		}
		TestObjective to = new TestObjective(title, desc, fdef, tpf);
		p.tpList.add(to);
	}
	
	public static void writeProjectFile(String file) throws XMLStreamException, IOException, TransformerException {
		 StringWriter sw = new StringWriter(); 
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();

	     XMLStreamWriter writer = factory.createXMLStreamWriter(sw);

	     writer.writeStartDocument();
     		 writer.writeStartElement("project");
	  		 	writer.writeStartElement("ifworkingpath");
			 		writer.writeAttribute("src", instance.ifWorkingPath);
			 	writer.writeEndElement();
     		 	writer.writeStartElement("image");
     		 		writer.writeAttribute("src", instance.imgPath);
     		 	writer.writeEndElement();
     		 	writer.writeStartElement("ifmodel");
     		 		writer.writeAttribute("src", instance.ifModelPath);
 		 		writer.writeEndElement();
 		 		writer.writeStartElement("tps");
		 			for (TestObjective to : instance.tpList) {
						writer.writeStartElement("tp");
							writer.writeAttribute("title", to.getTitle());
							writer.writeStartElement("description");
								writer.writeCharacters(to.getDescription());
							writer.writeEndElement();
							writer.writeStartElement("formaldef");
								writer.writeCharacters(to.getFormalDefinition());
							writer.writeEndElement();							
							writer.writeStartElement("tpfile");
								writer.writeAttribute("src", to.getCFilePath());
							writer.writeEndElement();				
						writer.writeEndElement();
		 			}
 		 		writer.writeEndElement();
		     writer.writeEndElement();
	     writer.writeEndDocument();

	     writer.flush();
	     writer.close();
	     

	     FileWriter formattedFileWriter = new FileWriter(file);
	    	 
	     TransformerFactory tfactory = TransformerFactory.newInstance();

	     Transformer transformer = tfactory.newTransformer();
	     transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	     transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	     transformer.transform(new StreamSource(new StringReader(sw.toString())), new StreamResult(formattedFileWriter));


	}

	public void setImagePath(String path) {
		this.imgPath = path;
		
	}

	public void setIfModel(IFModel ifModel) {
		this.ifModelPath = ifModel.getIfFilePath();
		
	}
	
	public void setIfWorkingPath(String path) {
		this.ifWorkingPath = path;
		
	}
}
