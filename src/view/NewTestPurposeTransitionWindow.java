package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import model.IFModel;
import model.IFState;

public class NewTestPurposeTransitionWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	private JComboBox<IFState> cbSource;
	private JComboBox<IFState> cbTarget;
	
	private JTextField txtInput;
	private JTextField txtOutput;
	private JTextField txtProcess;
	
	private IFModel model;
	
	private String closeCondition = "none";
	

	
	
	/**
	 * Create the dialog.
	 */
	public NewTestPurposeTransitionWindow(IFModel model) {
		this.model = model;
		setTitle("New transition");
		setBounds(100, 100, 450, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblSourceState = new JLabel("Source state");
			GridBagConstraints gbc_lblSourceState = new GridBagConstraints();
			gbc_lblSourceState.anchor = GridBagConstraints.WEST;
			gbc_lblSourceState.insets = new Insets(0, 0, 5, 0);
			gbc_lblSourceState.gridx = 0;
			gbc_lblSourceState.gridy = 0;
			contentPanel.add(lblSourceState, gbc_lblSourceState);
		}
		{
			this.cbSource = new JComboBox<IFState>();
			GridBagConstraints gbc_cbSource = new GridBagConstraints();
			gbc_cbSource.insets = new Insets(0, 0, 5, 0);
			gbc_cbSource.fill = GridBagConstraints.HORIZONTAL;
			gbc_cbSource.gridx = 0;
			gbc_cbSource.gridy = 1;
			
			DefaultComboBoxModel<IFState> dc = new DefaultComboBoxModel<IFState>(
					this.model.getIfStates().toArray(
							new IFState[this.model.getIfStates().size()]
					)
			);
			cbSource.setModel(dc);
			
			
			contentPanel.add(cbSource, gbc_cbSource);
		}
		{
			JLabel lblTargetState = new JLabel("Target state");
			GridBagConstraints gbc_lblTargetState = new GridBagConstraints();
			gbc_lblTargetState.anchor = GridBagConstraints.WEST;
			gbc_lblTargetState.insets = new Insets(0, 0, 5, 0);
			gbc_lblTargetState.gridx = 0;
			gbc_lblTargetState.gridy = 2;
			contentPanel.add(lblTargetState, gbc_lblTargetState);
		}
		{
			this.cbTarget = new JComboBox<IFState>();
			GridBagConstraints gbc_cbTarget = new GridBagConstraints();
			gbc_cbTarget.insets = new Insets(0, 0, 5, 0);
			gbc_cbTarget.fill = GridBagConstraints.HORIZONTAL;
			gbc_cbTarget.gridx = 0;
			gbc_cbTarget.gridy = 3;
			
			DefaultComboBoxModel<IFState> dc = new DefaultComboBoxModel<IFState>(
					this.model.getIfStates().toArray(
							new IFState[this.model.getIfStates().size()]
					)
			);
			cbTarget.setModel(dc);
			
			
			contentPanel.add(cbTarget, gbc_cbTarget);
		}
		{
			JLabel lblProcess = new JLabel("Process");
			GridBagConstraints gbc_lblProcess = new GridBagConstraints();
			gbc_lblProcess.anchor = GridBagConstraints.WEST;
			gbc_lblProcess.insets = new Insets(0, 0, 5, 0);
			gbc_lblProcess.gridx = 0;
			gbc_lblProcess.gridy = 4;
			contentPanel.add(lblProcess, gbc_lblProcess);
		}
		{
			txtProcess = new JTextField();
			GridBagConstraints gbc_txtProcess = new GridBagConstraints();
			gbc_txtProcess.insets = new Insets(0, 0, 5, 0);
			gbc_txtProcess.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtProcess.gridx = 0;
			gbc_txtProcess.gridy = 5;
			contentPanel.add(txtProcess, gbc_txtProcess);
			txtProcess.setColumns(10);
		}
		{
			JLabel lblInputSignals = new JLabel("Input signal");
			GridBagConstraints gbc_lblInputSignals = new GridBagConstraints();
			gbc_lblInputSignals.anchor = GridBagConstraints.WEST;
			gbc_lblInputSignals.insets = new Insets(0, 0, 5, 0);
			gbc_lblInputSignals.gridx = 0;
			gbc_lblInputSignals.gridy = 6;
			contentPanel.add(lblInputSignals, gbc_lblInputSignals);
		}
		{
			txtInput = new JTextField();
			GridBagConstraints gbc_txtInput = new GridBagConstraints();
			gbc_txtInput.insets = new Insets(0, 0, 5, 0);
			gbc_txtInput.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtInput.gridx = 0;
			gbc_txtInput.gridy = 7;
			contentPanel.add(txtInput, gbc_txtInput);
			txtInput.setColumns(10);
		}
		{
			JLabel lblOutputSignal = new JLabel("Output signal");
			GridBagConstraints gbc_lblOutputSignal = new GridBagConstraints();
			gbc_lblOutputSignal.anchor = GridBagConstraints.WEST;
			gbc_lblOutputSignal.insets = new Insets(0, 0, 5, 0);
			gbc_lblOutputSignal.gridx = 0;
			gbc_lblOutputSignal.gridy = 8;
			contentPanel.add(lblOutputSignal, gbc_lblOutputSignal);
		}
		{
			txtOutput = new JTextField();
			GridBagConstraints gbc_txtOutput = new GridBagConstraints();
			gbc_txtOutput.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtOutput.gridx = 0;
			gbc_txtOutput.gridy = 9;
			contentPanel.add(txtOutput, gbc_txtOutput);
			txtOutput.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						NewTestPurposeTransitionWindow.this.setVisible(false);	
						NewTestPurposeTransitionWindow.this.closeCondition = "OK";
					}
				});
				
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						NewTestPurposeTransitionWindow.this.setVisible(false);		
						NewTestPurposeTransitionWindow.this.closeCondition = "Cancel";
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public String getCloseCondition(){
		return closeCondition;
	}
	
	public IFState getSelectedSourceState(){
		return (IFState) cbSource.getSelectedItem();
	}

	public IFState getSelectedTargetState(){
		return (IFState) cbTarget.getSelectedItem();
	}
	
	public String getInputSignal(){
		return txtInput.getText();
	}
	public String getOutputSignal(){
		return txtOutput.getText();
	}
	
	public String getProcess(){
		return txtProcess.getText();
	}
	
}
