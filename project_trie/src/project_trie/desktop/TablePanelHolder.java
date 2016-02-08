package project_trie.desktop;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * this class is extension of TablePanel class			
 * The Idea is to remove scrollPane's and to
 * spread the words in separate tables and 
 * switch between them by pressing next and prev buttons
 */
public class TablePanelHolder extends JPanel {
	private static final long serialVersionUID = 1L;
	public static JPanel bottom;
	public static CardLayout cl;
	private JButton next;
	private JButton prev;
	private int counter = 1;//next end prev common variable
	private int pagesCount;

	public TablePanelHolder(List<String> l) {
		setLayout(null);
		bottom = new JPanel();
		cl = new CardLayout();
		next = new JButton("next");
		prev = new JButton("back");
		prev.setVisible(false);
		List<List<String>> list = splitList(l);
		pagesCount = list.size();
		bottom.setLayout(cl);
		setBackground(Color.ORANGE);
		bottom.setBounds(0, 50, 1500, 800);
		add(bottom);
		createPanels(list);
		firePrev();
		fireNext();
	}

	/*
	 * createPanels method use splitList method to create n number of panels
	 * using TablePanel class This is the main idea of this class
	 * 
	 * @param is list of list where inner lists are table pages
	 */
	TablePanel tablePanel;
	public void createPanels(List<List<String>> list) {
		int a = 1;
		for (int i = 0; i < list.size(); i++) {
			Table table = new Table(list.get(i), a);
			add(table);
			tablePanel = new TablePanel();
			tablePanel.addTable(table, true);
			tablePanel.setName(i + 1 + "");
			bottom.add(tablePanel, tablePanel.getName());
			a += list.get(i).size();
		}
		if (pagesCount > 1) {
			prev.setBounds(5, 20, 70, 23);
			next.setBounds(460, 20, 70, 23);
			add(next);
			add(prev);
		}
	}

	private void fireNext() {
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter++;
				if (counter == pagesCount) {
					next.setVisible(false);
				}
				if (counter > 1) {
					prev.setVisible(true);
				}
				cl.next(bottom);
			}
		});
	}

	private void firePrev() {
		prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				counter--;
				if (counter < pagesCount) {
					next.setVisible(true);
				}
				if (counter == 1) {
					prev.setVisible(false);
				}
				cl.previous(bottom);
			}
		});
	}

	/*
	 * splitList method split all words in dictionary in separated list's with
	 * capacity 10 words.Actually this is the size of the Table
	 */
	private List<List<String>> splitList(List<String> l) {
		int size = l.size();
		List<String> li = new ArrayList<>();
		List<List<String>> list = new ArrayList<>();
		for (int i = 0, a = 0; i < size; i++, a++) {
			li.add(l.get(i));
			if (a == 9) {
				list.add(li);
				li = new ArrayList<String>();
				a = -1;
			}
		}
		if (li.size() < 10 && li.size() > 0) {
			list.add(li);
		}
		return list;
	}

	public JButton getNext() {
		return next;
	}

	public JButton getPrev() {
		return prev;
	}
}
