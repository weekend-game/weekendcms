package game.weekend.weekendcms;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import game.weekend.weekendcms.SiteDescriptor.PageDescriptor;

public class ListEditor {

	public ListEditor() {
		panel = new JPanel();
		GBL gbl = new GBL(panel, true);

		gbl.addFixL(new JLabel(Loc.get("list_of_pages") + ":"), 1);
		gbl.newLine();

		listModel = new DefaultListModel<PageDescriptor>();
		list = new JList<PageDescriptor>(listModel);

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				PageDescriptor sv = list.getSelectedValue();
				if (sv == null) {
					cboLang.setSelectedItem("EN");
					fldFileName.setText("");
					fldName.setText("");
				} else {
					cboLang.setSelectedItem(sv.getLang());
					fldFileName.setText(sv.getFileName());
					fldName.setText(sv.getName());
				}
			}
		});

		gbl.addExtB(list, 9, 1);

		JPanel shiftPanel = new JPanel();
		shiftPanel.setLayout(new BorderLayout());

		JButton btnUp = new JButton(Loc.get("shift_up"));
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.isSelectionEmpty())
					return;

				int pos = list.getSelectedIndex();
				if (pos == 0)
					return;

				PageDescriptor pdHigher = listModel.elementAt(pos - 1);
				PageDescriptor pdCurrent = listModel.elementAt(pos);
				listModel.set(pos - 1, pdCurrent);
				listModel.set(pos, pdHigher);
				list.setSelectedIndex(pos - 1);
			}
		});
		shiftPanel.add(btnUp, BorderLayout.NORTH);

		shiftPanel.add(new JLabel(" "), BorderLayout.CENTER);

		JButton btnDown = new JButton(Loc.get("shift_down"));
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.isSelectionEmpty())
					return;

				int pos = list.getSelectedIndex();
				if (pos == listModel.size() - 1)
					return;

				PageDescriptor pdLower = listModel.elementAt(pos + 1);
				PageDescriptor pdCurrent = listModel.elementAt(pos);
				listModel.set(pos + 1, pdCurrent);
				listModel.set(pos, pdLower);
				list.setSelectedIndex(pos + 1);
			}
		});
		shiftPanel.add(btnDown, BorderLayout.SOUTH);

		gbl.addFixR(shiftPanel, 1);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("language") + ":"), 1);
		cboLang.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					saveLang();
				}
			}
		});
		gbl.addFixL(cboLang, 1);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("name") + ":"), 1);
		fldName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				saveName();
			}
		});
		fldName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveName();
			}
		});
		gbl.addExtX(fldName, 8);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("file_name") + ":"), 1);
		fldFileName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				saveFileName();
			}
		});
		fldFileName.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveFileName();
			}
		});
		gbl.addExtX(fldFileName, 8);
		gbl.newLine();

		JButton btnAdd = new JButton(Loc.get("new"));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int pos = -1;
				if (!list.isSelectionEmpty())
					pos = list.getSelectedIndex();
				listModel.add(++pos, new PageDescriptor("file_name.htm", "", "EN"));
				list.setSelectedIndex(pos);
			}
		});
		gbl.addFixL(btnAdd, 1);

		JButton btnDel = new JButton(Loc.get("delete"));
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.isSelectionEmpty())
					return;

				int pos = list.getSelectedIndex();
				listModel.remove(pos);

				if (pos == listModel.size())
					--pos;
				if (pos >= 0)
					list.setSelectedIndex(pos);
			}
		});
		gbl.addFixL(btnDel, 1);
	}

	public void setPages(List<PageDescriptor> pages) {
		listModel.addAll(pages);
		if (listModel.getSize() > 0)
			list.setSelectedIndex(0);
	}

	public DefaultListModel<PageDescriptor> getPages() {
		return listModel;
	}

	public JPanel getPanel() {
		return panel;
	}

	private void saveName() {
		PageDescriptor sv = list.getSelectedValue();
		if (sv != null) {
			sv.setName(fldName.getText());
			list.repaint();
		}
	}

	private void saveFileName() {
		PageDescriptor sv = list.getSelectedValue();
		if (sv != null) {
			sv.setFileName(fldFileName.getText());
			list.repaint();
		}
	}

	private void saveLang() {
		PageDescriptor sv = list.getSelectedValue();
		if (sv != null) {
			sv.setLang((String) cboLang.getSelectedItem());
			list.repaint();
		}
	}

	private JPanel panel;

	private DefaultListModel<PageDescriptor> listModel;
	private JList<PageDescriptor> list;

	private JComboBox<String> cboLang = new JComboBox<String>(new String[] { "EN", "RU" });
	private JTextField fldName = new JTextField(64);
	private JTextField fldFileName = new JTextField(64);
}
