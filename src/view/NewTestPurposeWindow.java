package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JList;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.xml.stream.XMLStreamException;

import model.IFModel;
import model.IFTransition;
import model.TestObjective;

public class NewTestPurposeWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitle;
	private IFModel model;
	private JList<IFTransition> list;
	private String savedFileName = null;
	private String closeCondition = "none";
	
	/**
	 * Create the dialog.
	 */
	public NewTestPurposeWindow(Frame parent, IFModel model) {
		super(parent);
		this.model = model;
		setTitle("New test purpose");
		setBounds(100, 100, 624, 436);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblTestObjectiveTitle = new JLabel("Test Objective title");
		GridBagConstraints gbc_lblTestObjectiveTitle = new GridBagConstraints();
		gbc_lblTestObjectiveTitle.anchor = GridBagConstraints.WEST;
		gbc_lblTestObjectiveTitle.gridwidth = 2;
		gbc_lblTestObjectiveTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTestObjectiveTitle.gridx = 0;
		gbc_lblTestObjectiveTitle.gridy = 0;
		contentPanel.add(lblTestObjectiveTitle, gbc_lblTestObjectiveTitle);
		
		txtTitle = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		contentPanel.add(txtTitle, gbc_textField);
		txtTitle.setColumns(10);
		
		JLabel lblTransitions = new JLabel("Transitions");
		GridBagConstraints gbc_lblTransitions = new GridBagConstraints();
		gbc_lblTransitions.anchor = GridBagConstraints.WEST;
		gbc_lblTransitions.insets = new Insets(0, 0, 5, 5);
		gbc_lblTransitions.gridx = 0;
		gbc_lblTransitions.gridy = 2;
		contentPanel.add(lblTransitions, gbc_lblTransitions);
		
		this.list = new JList<IFTransition>();
		this.list.setModel(new DefaultListModel<IFTransition>());
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 0);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 3;
		contentPanel.add(list, gbc_list);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		contentPanel.add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnAddTransition = new JButton("+");
		panel.add(btnAddTransition);
		
		JButton btnRemoveTransition = new JButton("-");
		panel.add(btnRemoveTransition);
		
		btnAddTransition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NewTestPurposeTransitionWindow ntptw = new NewTestPurposeTransitionWindow(NewTestPurposeWindow.this.model);
				ntptw.setModal(true);
				ntptw.setVisible(true);
				if("OK".equals(ntptw.getCloseCondition())){
					IFTransition t = new IFTransition(ntptw.getSelectedSourceState(),
							ntptw.getSelectedTargetState(),
							ntptw.getProcess(),
							ntptw.getInputSignal(),
							ntptw.getOutputSignal()
					);
					((DefaultListModel<IFTransition>)(list.getModel())).addElement(t);
				}
				
			}
		});
		
		btnRemoveTransition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedValue() != null){
					((DefaultListModel<IFTransition>)(list.getModel())).remove(list.getSelectedIndex());
				}
			}
		});
		
		Component verticalGlue = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.gridheight = 6;
		gbc_verticalGlue.insets = new Insets(0, 0, 5, 5);
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 6;
		contentPanel.add(verticalGlue, gbc_verticalGlue);
		
		JCheckBox chckbxOrdered = new JCheckBox("Ordered");
		GridBagConstraints gbc_chckbxOrdered = new GridBagConstraints();
		gbc_chckbxOrdered.anchor = GridBagConstraints.WEST;
		gbc_chckbxOrdered.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOrdered.gridx = 0;
		gbc_chckbxOrdered.gridy = 12;
		contentPanel.add(chckbxOrdered, gbc_chckbxOrdered);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.WEST;
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 13;
		contentPanel.add(panel_1, gbc_panel_1);
		
		JButton btnOk = new JButton("OK");
		panel_1.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeCondition = "Cancel";
				setVisible(false);
				
			}
		});
		panel_1.add(btnCancel);
		
		btnOk.addActionListener(new ActionListener() {
			
			private List<IFTransition> getListFromJList(JList<IFTransition> jl){
				List<IFTransition> l = new ArrayList<IFTransition>();
				for(int i = 0;i<jl.getModel().getSize();i++){
					l.add(jl.getModel().getElementAt(i));
				}
				return l;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String ccode = TestObjective.toCCode(getListFromJList(list), false);
				
				try {
					final JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("C file","C"));
					fc.setAcceptAllFileFilterUsed(false);
					int ret = fc.showSaveDialog(null);
					if (ret==JFileChooser.APPROVE_OPTION){
						String filename = fc.getSelectedFile().getAbsolutePath();
						if (!filename.endsWith(".C"))
						    filename += ".C";
						PrintWriter out = new PrintWriter(filename);
						out.print(ccode);
						out.close();
						savedFileName = filename;
						closeCondition = "OK";
						setVisible(false);
					}
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(contentPanel,
							e1.getMessage(),"Could not save project",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		/*{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}*/
	}
	
	public String getSavedFileName(){
		return savedFileName;
	}
	
	public String getTestTitle(){
		return txtTitle.getText();
	}
	
	public String getCloseCondition(){
		return closeCondition;
	}

}
