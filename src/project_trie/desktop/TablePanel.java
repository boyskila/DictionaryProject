package project_trie.desktop;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import project_trie.trie.FileManager;

public class TablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private int height;
	private Table table;
	private JButton edit;
	private JButton remove;
	private JButton viewDescription;
	private DescriptionLable label;
	private EditPanel editPanel;
	private JScrollPane sp;

	public TablePanel() {
		setLayout(null);
//		setBackground(Color.ORANGE);
		edit = new JButton("edit");
		viewDescription = new JButton("view");
		//setBackground(Color.WHITE);
		remove = new JButton("remove");
		height = 500;
	}

	public void addTable(Table table, boolean setHeight) {
		setBackground(Color.ORANGE);
		this.table = table;
		if (setHeight) {
			height = (int) getTable().getPreferredSize().getHeight();
		}
		if (height > 500) {
			height = 500;
		}
		sp = new JScrollPane(this.table);
		sp.getViewport().setBackground(Color.ORANGE);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.setBounds(5, 8, 527, height + 22);
		add(sp);
		edit.setBounds(430, height + 40, 100, 30);
		add(edit);
		viewDescription.setBounds(210, height + 40, 100, 30);
		add(viewDescription);
		remove.setBounds(320, height + 40, 100, 30);
		add(remove);
		fireEdit();
		fireView();
		fireRemove();
	}

	public void fireRemove() {
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = table.getColumnValue(1);
				if (new MessageDialog()
						.isMessageAnswerPositive("Are you sure you want to delete "
								+ key)) {
					table.removeRow();
					FileManager.dataBase.remove(key);
					FileManager.saveChanges();
					Autocomplete.updateAutocomplete(FileManager.dataBase.list());
					table.resetFirstColumn();
					if (table.getRowCount() < 10) {
						String letter = String.valueOf(key.charAt(0));
						//MainPanel.tableHolder.remove(MainPanel.tableHolder.getNext());
						MainPanel.bottom.remove(MainPanel.tableHolder);
						MainPanel.tableHolder = new TablePanelHolder(FileManager.dataBase.list(letter));
						MainPanel.bottom.add(MainPanel.tableHolder,"tpt");
						MainPanel.cl.show(MainPanel.bottom, "tpt");
						revalidate();
						repaint();
					}
					if (table.getRowCount() == 0) {
						try {
							removeAll();
							TablePanelHolder.cl
									.previous(TablePanelHolder.bottom);
						} catch (Exception e2) {
							removeAll();
							revalidate();
							repaint();
						}
					}
				}
			}
		});
	}

	public void fireEdit() {
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeComponent(label);
				removeComponent(editPanel);
				if (table.isRowSelected()) {
					editPanel = new EditPanel(table.getColumnValue(1), table
							.getColumnValue(2));
					add(editPanel);
					fireSave();
					revalidate();
					repaint();
				}
			}
		});
	}

	public void fireSave() {
		new ButtonAction(editPanel.getSaveButton(), editPanel.getEditArea());
		editPanel.getSaveButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String wordFromTable = table.getColumnValue(1);
				String wordFromEditPAnel = editPanel.getWordField().getText();
				String value = editPanel.getEditArea().getText();
				if (value.length() < 1100) {
					removeComponent(editPanel);
					revalidate();
					repaint();
					if (wordFromTable != null) {
						if (wordFromEditPAnel.equals(wordFromTable)) {
							FileManager.dataBase.update(wordFromTable, value);
							table.setColumnValue(value, 2);
						} else {
							FileManager.dataBase.remove(wordFromTable);
							FileManager.dataBase.add(wordFromEditPAnel, value);
							table.setColumnValue(wordFromEditPAnel, 1);
							table.setColumnValue(value, 2);
							Autocomplete
									.updateAutocomplete(FileManager.dataBase
											.list());
						}
						table.setColumnValue(false, 3);
						FileManager.saveChanges();
					} else {
						new MessageDialog("", "reduce text description with "
								+ (value.length() - 1100) + " symbols");
					}
				}
			}
		});
	}

	public void fireView() {
		viewDescription.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (table.isRowSelected()) {
						removeComponent(label);
						removeComponent(editPanel);
						label = new DescriptionLable(table.getColumnValue(2),
								table.getColumnValue(1));
						add(label);
						revalidate();
						repaint();
					}
				} catch (Exception e2) {
					new MessageDialog("", "Please select row");
				}
			}
		});
	}

	public void removeComponent(Component component) {
		if (component != null) {
			remove(component);
		}
	}

	public DescriptionLable getLabel() {
		return label;
	}

	public EditPanel getEditPanel() {
		return editPanel;
	}

	public Table getTable() {
		return table;
	}
}