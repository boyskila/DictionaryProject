package project_trie.desktop;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class EditPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextArea editArea;
	private JScrollPane scrollPane;
	private JButton save;
	private Font font = new Font("Serif", Font.ROMAN_BASELINE, 15);
	private JTextField wordField;

	public EditPanel(String word, String text) {
		setLayout(null);
		setBounds(540, 10, 700, 540);
		setBackground(Color.ORANGE);
		wordField = new JTextField(word);
		wordField.setBounds(0, 0, 200, 30);
		add(wordField);
		editArea = new JTextArea();
		editArea.setText(text);
		editArea.setLineWrap(true);
		editArea.setWrapStyleWord(true);
		editArea.setFont(font);
		scrollPane = new JScrollPane(editArea);
		scrollPane.setBounds(0, 50, getWidth(), getHeight() - 129);
		save = new JButton("save");
		save.setBounds(0, getHeight() - 71, 100, 30);
		add(scrollPane);
		add(save);
	}

	public JButton getSaveButton() {
		return save;
	}

	public JTextArea getEditArea() {
		return editArea;
	}

	public JTextField getWordField() {
		return wordField;
	}
}
