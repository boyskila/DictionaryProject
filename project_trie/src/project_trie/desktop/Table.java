package project_trie.desktop;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import project_trie.trie.FileManager;

public class Table extends JTable {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;

	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	public Table(List<String> allWords, int size) {
		setFocusable(true);
		tableModel = new DefaultTableModel(0, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0 || column == 1 || column == 2 ? false : true;
			}

			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				default:
					return Boolean.class;
				}
			}
		};
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				for (int i = 0; i < getRowCount(); i++) {
					int key = e.getKeyCode();
					if (key == KeyEvent.VK_ENTER) {
						setValueAt(true, i, 3);
					} else {
						setValueAt(false, i, 3);
					}
				}
			}
		});
		getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		String header[] = new String[] { "No", "Word", "Description", "" };
		tableModel.setColumnIdentifiers(header);
		setModel(tableModel);
		getColumnModel().getColumn(0).setPreferredWidth(40);
		for (int i = 0; i < allWords.size(); i++) {
			tableModel.addRow(new Object[] { size++, allWords.get(i),
					FileManager.dataBase.get(allWords.get(i)), false });
			setRowHeight(i, 40);
		}
		// setToolTipText((String) getValueAt(0, 1));

		getColumnModel().getColumn(1).setPreferredWidth(200);
		getColumnModel().getColumn(2).setPreferredWidth(250);
		setPreferredScrollableViewportSize(getPreferredSize());
		new BoxChecker(this);
	}

	public void removeRow() {
		tableModel.removeRow(getSelectedRow());
	}

	public boolean isRowSelected() {
		if (getColumnValue(3) == (Boolean) false) {
			new MessageDialog("", "Please select word");
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public <T> T getColumnValue(int column) {
		return (T) getValueAt(getSelectedRow(), column);
	}

	public <T> void setColumnValue(T value, int column) {
		setValueAt(value, getSelectedRow(), column);
	}

	public void resetFirstColumn() {
		for (int i = 0; i < getRowCount(); i++) {
			setValueAt(i + 1, i, 0);
		}
	}
}