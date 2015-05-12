package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import java.io.File;

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
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblBasePath = new JLabel("TestGen-IF base path");
			lblBasePath.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_lblBasePath = new GridBagConstraints();
			gbc_lblBasePath.insets = new Insets(0, 0, 5, 5);
			gbc_lblBasePath.anchor = GridBagConstraints.WEST;
			gbc_lblBasePath.gridx = 0;
			gbc_lblBasePath.gridy = 0;
			contentPanel.add(lblBasePath, gbc_lblBasePath);
		}
		{
			txtBasePath = new JTextField();
			txtBasePath.setText(Project.getInstance().getTestgenIfBasePath());
			
			GridBagConstraints gbc_txtBasePath = new GridBagConstraints();
			gbc_txtBasePath.insets = new Insets(0, 0, 5, 5);
			gbc_txtBasePath.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtBasePath.gridx = 0;
			gbc_txtBasePath.gridy = 1;
			contentPanel.add(txtBasePath, gbc_txtBasePath);
			txtBasePath.setColumns(10);
		}
		{
			JButton btnBrowseBasePath = new JButton("Browse...");
			btnBrowseBasePath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					final JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int val = fc.showOpenDialog(ConfigurationWindow.this);

			        if (val == JFileChooser.APPROVE_OPTION) {
			            String filePath = fc.getSelectedFile().getAbsolutePath();
			            Project.getInstance().setTestgenIfBasePath(filePath);
			            txtBasePath.setText(filePath);
			        }
				}
			});
			GridBagConstraints gbc_btnBrowseBasePath = new GridBagConstraints();
			gbc_btnBrowseBasePath.insets = new Insets(0, 0, 5, 0);
			gbc_btnBrowseBasePath.gridx = 1;
			gbc_btnBrowseBasePath.gridy = 1;
			contentPanel.add(btnBrowseBasePath, gbc_btnBrowseBasePath);
		}
		{
			JLabel lblWorkingPath = new JLabel("TestGen-IF working folder path (default is base path + /working)");
			GridBagConstraints gbc_lblWorkingPath = new GridBagConstraints();
			gbc_lblWorkingPath.insets = new Insets(0, 0, 5, 5);
			gbc_lblWorkingPath.anchor = GridBagConstraints.WEST;
			gbc_lblWorkingPath.gridx = 0;
			gbc_lblWorkingPath.gridy = 2;
			contentPanel.add(lblWorkingPath, gbc_lblWorkingPath);
		}
		{
			txtWorkingPath = new JTextField();
			txtWorkingPath.setText(Project.getInstance().getIfWorkingPath());
			
			GridBagConstraints gbc_txtWorkingPath = new GridBagConstraints();
			gbc_txtWorkingPath.insets = new Insets(0, 0, 5, 5);
			gbc_txtWorkingPath.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWorkingPath.gridx = 0;
			gbc_txtWorkingPath.gridy = 3;
			contentPanel.add(txtWorkingPath, gbc_txtWorkingPath);
			txtWorkingPath.setColumns(10);
		}
		{
			JButton btnBrowseWorkingPath = new JButton("Browse...");
			btnBrowseWorkingPath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int val = fc.showOpenDialog(ConfigurationWindow.this);

			        if (val == JFileChooser.APPROVE_OPTION) {
			            String filePath = fc.getSelectedFile().getAbsolutePath();
			            Project.getInstance().setIfWorkingPath(filePath);
			            txtWorkingPath.setText(filePath);
			        }
				}
			});
			GridBagConstraints gbc_btnBrowseWorkingPath = new GridBagConstraints();
			gbc_btnBrowseWorkingPath.insets = new Insets(0, 0, 5, 0);
			gbc_btnBrowseWorkingPath.gridx = 1;
			gbc_btnBrowseWorkingPath.gridy = 3;
			contentPanel.add(btnBrowseWorkingPath, gbc_btnBrowseWorkingPath);
		}
		{
			JLabel lblIfPath = new JLabel("IF-2.0 path");
			GridBagConstraints gbc_lblIfPath = new GridBagConstraints();
			gbc_lblIfPath.anchor = GridBagConstraints.WEST;
			gbc_lblIfPath.insets = new Insets(0, 0, 5, 5);
			gbc_lblIfPath.gridx = 0;
			gbc_lblIfPath.gridy = 4;
			contentPanel.add(lblIfPath, gbc_lblIfPath);
		}
		{
			txtIfPath = new JTextField();
			txtIfPath.setText(Project.getInstance().getIfPath());
			
			GridBagConstraints gbc_txtIfPath = new GridBagConstraints();
			gbc_txtIfPath.insets = new Insets(0, 0, 0, 5);
			gbc_txtIfPath.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtIfPath.gridx = 0;
			gbc_txtIfPath.gridy = 5;
			contentPanel.add(txtIfPath, gbc_txtIfPath);
			txtIfPath.setColumns(10);
		}
		{
			JButton btnBrowseIfPath = new JButton("Browse...");
			btnBrowseIfPath.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					final JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int val = fc.showOpenDialog(ConfigurationWindow.this);

			        if (val == JFileChooser.APPROVE_OPTION) {
			            String filePath = fc.getSelectedFile().getAbsolutePath();
			            Project.getInstance().setIfPath(filePath);
			            txtIfPath.setText(filePath);
			        }
				}
			});
			GridBagConstraints gbc_btnBrowseIfPath = new GridBagConstraints();
			gbc_btnBrowseIfPath.gridx = 1;
			gbc_btnBrowseIfPath.gridy = 5;
			contentPanel.add(btnBrowseIfPath, gbc_btnBrowseIfPath);
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
