package view;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import xml.Project;
import model.IFModel;
import model.TestObjective;

import javax.swing.JSeparator;

public class Main {

	private JFrame frmTestgenif;
	private IFModel ifModel;

	private List<TestObjective> testObjectives;
	private JComboBox<TestObjective> cbTestPurpose;
	private JTextArea textAreaDescription;
	private JTextArea textAreaFormalDefinition;
	private String cFilePath;
	private JButton btnSelectImage;
	private JScrollPane scrollPane_Image;
	private JButton btnNewTestPurpose;
	private JButton btnEditDescription;
	private JButton btnEditFormalDefinition;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmTestgenif.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		this.ifModel = null;
		
		initialize();
	}
	
	public Frame getFrame(){
		return frmTestgenif;
	}
	
	public void loadProjectData(){
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTestgenif = new JFrame();
		frmTestgenif.setTitle("TestGen-IF");
		frmTestgenif.setBounds(100, 100, 1000, 750);
		frmTestgenif.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmTestgenif.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		final JMenuItem mntmViewIfModel = new JMenuItem("View IF model");
		mntmViewIfModel.setEnabled(false);
		mntmViewIfModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev){
				TextDialog td = new TextDialog(ifModel.getText());
				td.setVisible(true);
			}
		});
		
		final JMenuItem mntmOpen = new JMenuItem("Open IF model");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("IF file","if"));
				fc.setAcceptAllFileFilterUsed(false);
				int ret = fc.showOpenDialog(null);
				if (ret==JFileChooser.APPROVE_OPTION){
					try {
						ifModel = new IFModel(fc.getSelectedFile().getAbsolutePath());
						Project.getInstance().setIfModel(ifModel);
						mntmViewIfModel.setEnabled(true);
						btnNewTestPurpose.setEnabled(true);
					}
					catch (Exception ex){
						JOptionPane.showMessageDialog(frmTestgenif,
								ex.getMessage(),"Cannot open file",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		final JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmOpenProject = new JMenuItem("Open project");
		mntmOpenProject.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String path = "";
				final JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("XML file","xml"));
				fc.setAcceptAllFileFilterUsed(false);
				int ret = fc.showOpenDialog(null);
				if (ret==JFileChooser.APPROVE_OPTION){
					try {
						path = fc.getSelectedFile().getAbsolutePath();
						
					}
					catch (Exception ex){
						JOptionPane.showMessageDialog(frmTestgenif,
								ex.getMessage(),"Cannot open file",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					try {
						Project.parseProjectFile(path);
						Main.this.testObjectives = Project.getInstance().getTestPurposes();
						DefaultComboBoxModel<TestObjective> dc = new DefaultComboBoxModel<TestObjective>(
								testObjectives.toArray(new TestObjective[testObjectives.size()])
								);
						cbTestPurpose.setModel(dc);
						if(cbTestPurpose.getModel().getSize() > 0){ //Workaround for initial selection issue
							cbTestPurpose.setSelectedIndex(-1);
							cbTestPurpose.setSelectedIndex(0);
						}
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frmTestgenif,
								e.getMessage(),"Could not read project file.",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
					try{
						ifModel = new IFModel(Project.getInstance().getIfModelPath());
						btnNewTestPurpose.setEnabled(true);
						mntmViewIfModel.setEnabled(true);
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(frmTestgenif,
								e.getMessage(),"Could not read IF model file.",
								JOptionPane.ERROR_MESSAGE);
					}
					try{
						BufferedImage myPicture = ImageIO.read(new File(Project.getInstance().getImagePath()));
						JLabel picLabel = new JLabel(new ImageIcon(myPicture));
						scrollPane_Image.setViewportView(picLabel);			
					}
					catch(Exception e){
						JOptionPane.showMessageDialog(frmTestgenif,
								e.getMessage(),"Could not read image file.",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}
		});
		mnFile.add(mntmOpenProject);
		
		JMenuItem mntmSaveProject = new JMenuItem("Save project");
		mntmSaveProject.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					final JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("XML file","xml"));
					fc.setAcceptAllFileFilterUsed(false);
					int ret = fc.showSaveDialog(null);
					if (ret==JFileChooser.APPROVE_OPTION){
						String filename = fc.getSelectedFile().getAbsolutePath();
						if (!filename.endsWith(".xml"))
						    filename += ".xml";
						Project.writeProjectFile(filename);
					}
				} catch (XMLStreamException | IOException
						| TransformerException e1) {
					JOptionPane.showMessageDialog(frmTestgenif,
							e1.getMessage(),"Could not save project",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		mnFile.add(mntmSaveProject);
		
		JMenuItem mntmConfiguration = new JMenuItem("Configuration");
		mntmConfiguration.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigurationWindow cfw = new ConfigurationWindow();
				cfw.setModal(true);
				cfw.setVisible(true);
				
			}
		});
		
		mnFile.add(mntmOpen);
		mnFile.add(mntmViewIfModel);
		mnFile.add(mntmConfiguration);
		mnFile.add(mntmQuit);
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		frmTestgenif.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel leftPanel = new JPanel();
		frmTestgenif.getContentPane().add(leftPanel);
		leftPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		JLabel lblSelectTestPurpose = new JLabel("Select test purpose");
		leftPanel.add(lblSelectTestPurpose, "2, 2");
		
		cbTestPurpose = new JComboBox<TestObjective>();
		
		
		//cbTestPurpose.addItem("this is a test");
		
		cbTestPurpose.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				TestObjective tp = (TestObjective)e.getItem();
				textAreaDescription.setText(tp.getDescription());
				textAreaFormalDefinition.setText(tp.getFormalDefinition());
				cFilePath = tp.getCFilePath();
				btnEditDescription.setEnabled(true);
				btnEditFormalDefinition.setEnabled(true);
				
			}
		});
		cbTestPurpose.setSelectedIndex(-1);
		leftPanel.add(cbTestPurpose, "2, 4, left, default");
		
		btnNewTestPurpose = new JButton("New test purpose...");
		btnNewTestPurpose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewTestPurposeWindow ntpw = new NewTestPurposeWindow(Main.this.getFrame(), Main.this.ifModel);
				ntpw.setModal(true);
				ntpw.setVisible(true);
				
				if("OK".equals(ntpw.getCloseCondition())){
					TestObjective to = new TestObjective(ntpw.getTestTitle(),"","",ntpw.getSavedFileName());
					testObjectives.add(to); //TODO:should update model automatically...
					DefaultComboBoxModel<TestObjective> dc = new DefaultComboBoxModel<TestObjective>(
							testObjectives.toArray(new TestObjective[testObjectives.size()])
							);
					cbTestPurpose.setModel(dc);
				}
			}
		});
		btnNewTestPurpose.setEnabled(false);
		leftPanel.add(btnNewTestPurpose, "2, 6, left, default");
		
		JLabel lblDescription = new JLabel("Description");
		leftPanel.add(lblDescription, "2, 10");
		
		JScrollPane scrollPane_Description = new JScrollPane();
		leftPanel.add(scrollPane_Description, "2, 12, fill, fill");
		
		textAreaDescription = new JTextArea();
		textAreaDescription.setEditable(false);
		scrollPane_Description.setViewportView(textAreaDescription);
		
		btnEditDescription = new JButton("Edit description");
		btnEditDescription.setEnabled(false);
		
		btnEditDescription.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textAreaDescription.isEditable()){
					textAreaDescription.setEditable(true);
					((JButton)(e.getSource())).setText("Save description");
				}
				else{
					((TestObjective)(cbTestPurpose.getSelectedItem())).setDescription(textAreaDescription.getText());
					textAreaDescription.setEditable(false);
					((JButton)(e.getSource())).setText("Edit description");
				}
				
			}
		});
		
		leftPanel.add(btnEditDescription, "2, 14, left, default");
		
		JLabel lblFormalDefinition = new JLabel("Formal definition");
		leftPanel.add(lblFormalDefinition, "2, 16");
		
		JScrollPane scrollPane_FormalDefinition = new JScrollPane();
		leftPanel.add(scrollPane_FormalDefinition, "2, 18, fill, fill");
		
		textAreaFormalDefinition = new JTextArea();
		textAreaFormalDefinition.setEditable(false);
		scrollPane_FormalDefinition.setViewportView(textAreaFormalDefinition);
		
		btnEditFormalDefinition = new JButton("Edit formal definition");
		btnEditFormalDefinition.setEnabled(false);
		
		btnEditFormalDefinition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!textAreaFormalDefinition.isEditable()){
					textAreaFormalDefinition.setEditable(true);
					((JButton)(e.getSource())).setText("Save definition");
				}
				else{
					((TestObjective)(cbTestPurpose.getSelectedItem())).setFormalDefinition(textAreaFormalDefinition.getText());
					textAreaDescription.setEditable(false);
					((JButton)(e.getSource())).setText("Edit definition");
				}				
			}
		});
		
		leftPanel.add(btnEditFormalDefinition, "2, 20, left, default");
		
		JSeparator separator = new JSeparator();
		leftPanel.add(separator, "2, 22");
		
		JButton btnGenerateTestCases = new JButton("Generate Test Cases");
		//btnGenerateTestCases.setEnabled(false);
		
		btnGenerateTestCases.addActionListener(new ActionListener() {
			
			private void ex(/*final StyledText st*/) throws IOException{
				Thread t = new Thread(){
					
				    String[] mapToEnv(Map<String, String> map) {
				        final String[] envp = new String[map.size()];
				        int i = 0;
				        for (Map.Entry<String, String> e : map.entrySet()) {
				            envp[i] = e.getKey() + '=' + e.getValue();
				            i++;
				        }
				        return envp;
				    }
					
					@Override
					public void run() {
						super.run();
						Process proc = null;
						String iffile = "";
						String iffilename = "";String iffilenamenoext = "";
						String depth = "20";
						String search = "bfs";
						String purposes = "";
						String outfolder = Project.getInstance().getIfWorkingPath() + "/tmp/tcs";
						
					    CopyOption[] options = new CopyOption[]{
					    	      StandardCopyOption.REPLACE_EXISTING,
					    	      StandardCopyOption.COPY_ATTRIBUTES
					    	    }; 
						try {
							iffile = Project.getInstance().getIfModelPath();
							iffilename = Paths.get(iffile).getFileName().toString();							
							iffilenamenoext = iffilename.substring(0, iffilename.lastIndexOf('.'));
							Files.copy(Paths.get(iffile), Paths.get(Project.getInstance().getIfWorkingPath() + "/" + iffilename),options);
							if(!Files.exists(Paths.get(Project.getInstance().getIfWorkingPath() + "/tmp/"))){
								Files.createDirectory(Paths.get(Project.getInstance().getIfWorkingPath() + "/tmp/"));
							}
							if(!Files.exists(Paths.get(Project.getInstance().getIfWorkingPath() + "/tmp/tps/"))){
								Files.createDirectory(Paths.get(Project.getInstance().getIfWorkingPath() + "/tmp/tps/"));
							}
							if(!Files.exists(Paths.get(Project.getInstance().getIfWorkingPath() + "/tmp/tcs/"))){
								Files.createDirectory(Paths.get(Project.getInstance().getIfWorkingPath() + "/tmp/tcs/"));
							}
							for(TestObjective s:Project.getInstance().getTestPurposes()){
								String cFileName = Paths.get(s.getCFilePath()).getFileName().toString();
								Files.copy(Paths.get(s.getCFilePath()), Paths.get(Project.getInstance().getIfWorkingPath() + "/tmp/tps/" + cFileName),options);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						String[] cmd = { Project.getInstance().getIfWorkingPath() + "/start-generation.sh","-f",iffilenamenoext,"-d",depth,"-s",search,"-p","tmp/tps/","-c",outfolder};
						try {
							final HashMap<String, String> env = new HashMap<String, String>(System.getenv());
					        //String pathstr = "/home/pani/TestGen_IF/TestGen-IF/testgen-if/lib" + ":" + "/home/pani/TestGen_IF/TestGen-IF/IF-2.0/src/simulator" + ":" + "/home/pani/TestGen_IF/TestGen-IF/IF-2.0/bin/iX86" + ":" + "/home/pani/TestGen_IF/TestGen-IF/IF-2.0/com" + ":" + env.get("PATH");
							String pathstr = Project.getInstance().getTestgenIfLibPath() + ":" 
											+ Project.getInstance().getIfSimulatorPath() + ":" 
											+ Project.getInstance().getIfBinArchPath() + ":" 
											+ Project.getInstance().getTestgenIfComPath() + ":" 
											+ env.get("PATH");
							env.put("PATH", pathstr);
					        env.put("IF", Project.getInstance().getIfPath());
					        env.put("TestGenIF",Project.getInstance().getTestgenIfBasePath());

					        final String[] envp=mapToEnv(env);
							proc = Runtime.getRuntime().exec(cmd,envp,new File(Project.getInstance().getIfWorkingPath()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						InputStream inputStream = proc.getInputStream();
						InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

						String line;
						try {
							while ((line = bufferedReader.readLine()) != null)
							{
								System.out.println(line);
							   // st.setText(st.getText() + "\n" + line);
							   // st.update();
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 
					}
				};
				//Display.getDefault().asyncExec(t);
				t.start();
			}

			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(!Project.getInstance().getIfPath().isEmpty() && !Project.getInstance().getTestgenIfBasePath().isEmpty()){
						ex();	
					}
					else{
						JOptionPane.showMessageDialog(frmTestgenif,
								"Please set up paths for IF-2.0 and TestGen-IF in the configuration dialog.","Invalid configuration",
								JOptionPane.ERROR_MESSAGE);		
					}					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		leftPanel.add(btnGenerateTestCases, "2, 24, left, default");
		
		
		JLabel lblTestCases = new JLabel("Test cases");
		leftPanel.add(lblTestCases, "2, 26, left, default");
		
		JScrollPane scrollPane_TestCases = new JScrollPane();
		leftPanel.add(scrollPane_TestCases, "2, 28, fill, fill");
		
		JTextArea textAreaTestCases = new JTextArea();
		textAreaTestCases.setEditable(false);
		scrollPane_TestCases.setViewportView(textAreaTestCases);
		
		JPanel rightPanel = new JPanel();
		frmTestgenif.getContentPane().add(rightPanel);
		rightPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		scrollPane_Image = new JScrollPane();
		rightPanel.add(scrollPane_Image);
		
		btnSelectImage = new JButton("Select Image");
		scrollPane_Image.setColumnHeaderView(btnSelectImage);
		
		btnSelectImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				final JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("Image file","png","jpg","bmp"));
				fc.setAcceptAllFileFilterUsed(false);
				int ret = fc.showOpenDialog(null);
				if (ret==JFileChooser.APPROVE_OPTION){
					try {
						BufferedImage myPicture = ImageIO.read(fc.getSelectedFile());
						JLabel picLabel = new JLabel(new ImageIcon(myPicture));
						scrollPane_Image.setViewportView(picLabel);
						//((JButton)e.getSource()).setVisible(false);
						Project.getInstance().setImagePath(fc.getSelectedFile().getAbsolutePath());
					}
					catch (Exception ex){
						JOptionPane.showMessageDialog(frmTestgenif,
								ex.getMessage(),"Cannot open file",
								JOptionPane.ERROR_MESSAGE);
					}
				}				
			}
		});
		
	}

}
