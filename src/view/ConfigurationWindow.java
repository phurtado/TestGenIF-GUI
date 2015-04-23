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
import javax.swing.SwingConstants;

public class ConfigurationWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtWorkingPath;
	private JTextField txtBasePath;
	private JTextField txtIfPath;

	public String getWorkingPath(){
		return txtWorkingPath.getText();
	}

	public String getTestgenIfBasePath(){
		return txtBasePath.getText();
	}
	
	public String getIfPath(){
		return txtIfPath.getText();
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
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblBasePath = new JLabel("TestGen-IF base path");
			lblBasePath.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_lblBasePath = new GridBagConstraints();
			gbc_lblBasePath.insets = new Insets(0, 0, 5, 0);
			gbc_lblBasePath.anchor = GridBagConstraints.WEST;
			gbc_lblBasePath.gridx = 0;
			gbc_lblBasePath.gridy = 0;
			contentPanel.add(lblBasePath, gbc_lblBasePath);
		}
		{
			txtBasePath = new JTextField();
			txtBasePath.setText(Project.getInstance().getTestgenIfBasePath());
			
			GridBagConstraints gbc_txtBasePath = new GridBagConstraints();
			gbc_txtBasePath.insets = new Insets(0, 0, 5, 0);
			gbc_txtBasePath.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtBasePath.gridx = 0;
			gbc_txtBasePath.gridy = 1;
			contentPanel.add(txtBasePath, gbc_txtBasePath);
			txtBasePath.setColumns(10);
		}
		{
			JLabel lblWorkingPath = new JLabel("TestGen-IF working folder path (default is base path + /working)");
			GridBagConstraints gbc_lblWorkingPath = new GridBagConstraints();
			gbc_lblWorkingPath.insets = new Insets(0, 0, 5, 0);
			gbc_lblWorkingPath.anchor = GridBagConstraints.WEST;
			gbc_lblWorkingPath.gridx = 0;
			gbc_lblWorkingPath.gridy = 2;
			contentPanel.add(lblWorkingPath, gbc_lblWorkingPath);
		}
		{
			txtWorkingPath = new JTextField();
			txtWorkingPath.setText(Project.getInstance().getIfWorkingPath());
			
			GridBagConstraints gbc_txtWorkingPath = new GridBagConstraints();
			gbc_txtWorkingPath.insets = new Insets(0, 0, 5, 0);
			gbc_txtWorkingPath.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWorkingPath.gridx = 0;
			gbc_txtWorkingPath.gridy = 3;
			contentPanel.add(txtWorkingPath, gbc_txtWorkingPath);
			txtWorkingPath.setColumns(10);
		}
		{
			JLabel lblIfPath = new JLabel("IF-2.0 path");
			GridBagConstraints gbc_lblIfPath = new GridBagConstraints();
			gbc_lblIfPath.anchor = GridBagConstraints.WEST;
			gbc_lblIfPath.insets = new Insets(0, 0, 5, 0);
			gbc_lblIfPath.gridx = 0;
			gbc_lblIfPath.gridy = 4;
			contentPanel.add(lblIfPath, gbc_lblIfPath);
		}
		{
			txtIfPath = new JTextField();
			txtIfPath.setText(Project.getInstance().getIfPath());
			
			GridBagConstraints gbc_txtIfPath = new GridBagConstraints();
			gbc_txtIfPath.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtIfPath.gridx = 0;
			gbc_txtIfPath.gridy = 5;
			contentPanel.add(txtIfPath, gbc_txtIfPath);
			txtIfPath.setColumns(10);
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
						Project.getInstance().setTestgenIfBasePath((getTestgenIfBasePath().trim()));
						Project.getInstance().setIfPath(getIfPath().trim());
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
