package view;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import javax.swing.JTextPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TextDialog extends JDialog {

	/**
	 * Create the dialog.
	 * @param text Text to show in the dialog 
	 */
	public TextDialog(String text) {
		setBounds(100, 100, 1600, 900);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setEditable(false);
		textPane.setText(text);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		getContentPane().add(btnClose, BorderLayout.SOUTH);

	}

}
