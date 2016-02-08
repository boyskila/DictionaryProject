package project_trie.desktop;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import project_trie.trie.FileManager;

public class DescriptionFormPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea description;
	private JButton submit;
	private JTextField word;

	public DescriptionFormPanel() {
		setBackground(Color.ORANGE);
		description = new JTextArea();
		submit = new JButton("submit");
		word = new JTextField();
		createForm();
	}

	public void createForm() {
		setLayout(null);
		submit.setToolTipText("save changes");
		getWord().setBounds(10, 20, 200, 32);
		add(getWord());
		description.setBounds(10, 60, 700, 300);
		description.setText("Enter description here...");
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		add(getDescription());
		submit.setBounds(620, 370, 90, 30);
		add(getSubmit());
		fireSubmit();
	}

	public void fireSubmit() {
		new ButtonAction(submit, description);
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!word.getText().isEmpty()) {
					String value = word.getText();
					if (FileManager.dataBase.has(value)) {
						new MessageDialog("", "Dictionary already contains "
								+ '"' + value.toUpperCase() + '"');
					}else {
					FileManager.dataBase.add(value, description.getText());
					FileManager.saveChanges();
					new MessageDialog("", value
							+ " has been added successfully");
					word.setText("");
					description.setText("Enter description here..");
					MainPanel.cl.first(MainPanel.bottom);
					Autocomplete.updateAutocomplete(FileManager.dataBase.list());
					}
				}
			}
		});
	}

	public JButton getSubmit() {
		return submit;
	}

	public JTextArea getDescription() {
		return description;
	}

	public JTextField getWord() {
		return word;
	}
}
