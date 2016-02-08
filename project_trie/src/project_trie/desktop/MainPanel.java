package project_trie.desktop;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import project_trie.trie.FileManager;
import project_trie.trie.Trie;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static JPanel bottom;
	static CardLayout cl;
	private FileManager fm;
	private Trie dictionary;
	private DescriptionFormPanel descrPanel;
	private MenuPanel menu;
	private JPanel wellcomePanel;
	public static TablePanelHolder tableHolder;

	public MainPanel() {
		setLayout(null);
		bottom = new JPanel();
		cl = new CardLayout();
		fm = new FileManager();
		dictionary = fm.getDictionary();
		descrPanel = new DescriptionFormPanel();
		menu = new MenuPanel(dictionary.list());
		wellcomePanel = new WelcomePanel();
		setupPanel();
	}

	public void setupPanel() {
		bottom.setLayout(cl);
		add(menu);
		bottom.setBounds(0, 50, 1500, 800);
		bottom.add(wellcomePanel, "welcome");
		bottom.add(descrPanel, "descriptionForm");
		fireSearch();
		fireGetAllWordsButton();
		fireAddWordButton();
		add(bottom);
	}

	public void fireSearch() {
		menu.getSearchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = menu.getSearchField().getText();
				if (word.length() > 0 && !hasDigit(word)) {

					if (!dictionary.has(word)) {
						if (word.length() == 1) {
							List<String> words = dictionary.list(word);
							if (!words.isEmpty()) {
								tableHolder = new TablePanelHolder(words);
								bottom.add(tableHolder, "tpt");
								cl.show(bottom, "tpt");
							} else {
								new MessageDialog("", "      Invalid word");
							}
						} else {
							descrPanel.getWord().setText(word);
							cl.show(bottom, "descriptionForm");
						}
					} else {
						List<String> l = new ArrayList<>(1);
						l.add(word);
						TablePanel tab = new TablePanel();
						tab.addTable(new Table(l, 1), true);
						bottom.add(tab, "tab");
						cl.show(bottom, "tab");
					}
					menu.getSearchField().setText("");
				} else {
					new MessageDialog("", "Please enter valid word");
					menu.getSearchField().setText("");
				}
			}
		});
	}

	public void fireGetAllWordsButton() {
		menu.getAllWordsButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableHolder = new TablePanelHolder(dictionary.list());
				bottom.add(tableHolder, "tph");
				cl.show(bottom, "tph");
			}
		});
	}

	public void fireAddWordButton() {
		menu.getAddButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(bottom, "descriptionForm");
			}
		});

	}

	private boolean hasDigit(String word) {
		for (int i = 0; i < word.length(); i++) {
			if (Character.isDigit(word.charAt(i))) {
				return true;
			}

		}
		return false;
	}
}