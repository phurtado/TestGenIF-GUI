package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import xml.Project;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigurationWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtWorkingPath;

	public String getWorkingPath(){
		return txtWorkingPath.getText();
	}

	/**
	 * Create the dialog.
	 */
	public ConfigurationWindow() {
		setTitle("Configuration");
		setModal(true);
		setBounds(100, 100, 551, 365);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblWorkingPath = new JLabel("TestGen-IF working folder path");
			GridBagConstraints gbc_lblWorkingPath = new GridBagConstraints();
			gbc_lblWorkingPath.anchor = GridBagConstraints.WEST;
			gbc_lblWorkingPath.insets = new Insets(0, 0, 5, 0);
			gbc_lblWorkingPath.gridx = 0;
			gbc_lblWorkingPath.gridy = 0;
			contentPanel.add(lblWorkingPath, gbc_lblWorkingPath);
		}
		{
			txtWorkingPath = new JTextField();
			txtWorkingPath.setText(Project.getInstance().getIfWorkingPath());
			GridBagConstraints gbc_txtWorkingPath = new GridBagConstraints();
			gbc_txtWorkingPath.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWorkingPath.gridx = 0;
			gbc_txtWorkingPath.gridy = 1;
			contentPanel.add(txtWorkingPath, gbc_txtWorkingPath);
			txtWorkingPath.setColumns(10);
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
						Project.getInstance().setIfWorkingPath(getWorkingPath().trim());
						setVisible(false);						
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
						setVisible(false);
						
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
