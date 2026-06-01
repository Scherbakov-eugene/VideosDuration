package lunasoft.common.view.dialogs;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import lunasoft.applications.videos_duration.core.PropertyManagerImpl;
import lunasoft.applications.videos_duration.core.S;
import lunasoft.common.data.mt.MT;
import lunasoft.common.data.mt.VectorMT;

@SuppressWarnings("serial")
public class FavoritesDialog extends JDialog
{
	private static FavoritesDialog INSTANCE;

	/** Отступ. */
	public static final int INDENT = 5;
	/** Ширина 1-го столбца таблицы (с порядковым номером файлов). */
	public static final int COLUMN_0_WIDTH = 50;

	// Panels.
	private JPanel contentPane;
	private JPanel panelFavorites;
	private JPanel panelButtonsRight;
	private JPanel panelButtonsBottom;

	// panelFavorites.
	private JTable tableFavorites;
	private TableModel tableModel;
	private VectorMT favorites = new VectorMT(MT.TYPE_9_OBJECT, 10);

	// panelButtons1.
	private JButton buttonAdd;
	private JButton buttonChange;
	private JButton buttonDelete;
	private JButton buttonUp;
	private JButton buttonDown;

	// panelButtons2.
	private JButton buttonOk;
	private JButton buttonCancel;

	private String selectedFavorite;

	public FavoritesDialog(Dialog parent)
	{
		super(parent, S.propertyManager.getMessage(PropertyManagerImpl.MSG_FAVORITES), true);
		INSTANCE = this;

		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);

		loadFavorites();
		prepareGUI();

		// Call onCancel() when cross is clicked.
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				onCancel();
			}
		});

		// Call onCancel() on ESCAPE.
		contentPane.registerKeyboardAction(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		setSize(700, 500);
		S.setCenteredRelativelyParent(parent, this);
	}

	private void prepareGUI()
	{
		GridBagConstraints gbc;

		panelFavorites = new JPanel(new GridBagLayout());
		panelFavorites.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_FAVORITES) + ":"));
		fillPanelFavorites();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelFavorites, gbc);

		panelButtonsRight = new JPanel(new GridBagLayout());
		fillPanelButtonsRight();
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelButtonsRight, gbc);

		panelButtonsBottom = new JPanel(new GridBagLayout());
		fillPanelButtonsBottom();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelButtonsBottom, gbc);
	}

	private void fillPanelFavorites()
	{
		GridBagConstraints gbc;

		tableFavorites = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tableFavorites);
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.gridwidth   = 3;
		gbc.gridheight   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelFavorites.add(scrollPane, gbc);

		tableFavorites.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				// LMB double click.
				if ((e.getButton() == 1) && (e.getClickCount() == 2))
				{
					onOk();
				}
				// RMB single click.
				else if ((e.getButton() == 3) && (e.getClickCount() == 1))
				{
				}
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
			
			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}
		});

		tableModel = new TableModel()
		{
			public int getRowCount()
			{
				if (favorites == null)
				{
					return 0;
				}
				else
				{
					return favorites.sizeCurrent;
				}
			}

			public int getColumnCount()
			{
				return 2;
			}

			public String getColumnName(int columnIndex)
			{
				String columnName = "N/A";

				if (columnIndex == 0)
				{
					columnName = "#";
				}
				else if (columnIndex == 1)
				{
					columnName = S.propertyManager.getMessage(PropertyManagerImpl.MSG_PATH);
				}

				return columnName;
			}

			public Class<?> getColumnClass(int columnIndex)
			{
				Class<?> classObj = Object.class;

				if (columnIndex == 0)
				{
					classObj = Integer.class;
				}
				else if (columnIndex == 1)
				{
					classObj = String.class;
				}

				return classObj;
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				return false;
			}

			public Object getValueAt(int rowIndex, int columnIndex)
			{
				Object value = "";

				if (favorites != null)
				{
					if (columnIndex == 0)
					{
						value = Integer.valueOf(rowIndex + 1);
					}
					else if (columnIndex == 1)
					{
						value = (String) favorites.getValuesObject()[rowIndex];
					}
				}
				else
				{
					value = "N/A";
				}

				return value;
			}

			public void setValueAt(Object aValue, int rowIndex, int columnIndex)
			{
				throw new UnsupportedOperationException("Not supported yet.");
			}

			public void addTableModelListener(TableModelListener l)
			{
				//throw new UnsupportedOperationException("Not supported yet.");
			}

			public void removeTableModelListener(TableModelListener l)
			{
				//throw new UnsupportedOperationException("Not supported yet.");
			}
		};

		tableFavorites.setModel(tableModel);
		tableFavorites.getColumnModel().getColumn(0).setMinWidth(COLUMN_0_WIDTH);
		tableFavorites.getColumnModel().getColumn(0).setMaxWidth(COLUMN_0_WIDTH);
		tableFavorites.getTableHeader().setReorderingAllowed(false);
	}

	private void fillPanelButtonsRight()
	{
		GridBagConstraints gbc;

		buttonAdd = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_ADD));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsRight.add(buttonAdd, gbc);
		buttonAdd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String res = JOptionPane.showInputDialog(INSTANCE,
						"Введите полный путь",
						S.propertyManager.getMessage(PropertyManagerImpl.MSG_ADD),
						JOptionPane.QUESTION_MESSAGE);

				if (res != null && res.trim().length() > 0)
				{
					favorites.put(res.trim());
					updateTable();
				}
			}
		});

		buttonChange = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_CHANGE));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsRight.add(buttonChange, gbc);
		buttonChange.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int[] rows = tableFavorites.getSelectedRows();
				if (rows.length == 1)
				{
					String favorite = (String) favorites.getValuesObject()[rows[0]];

					String res = (String) JOptionPane.showInputDialog(INSTANCE,
							"Введите полный путь",
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_CHANGE),
							JOptionPane.QUESTION_MESSAGE,
							null,
							null,
							favorite);

					if (res != null && res.trim().length() > 0)
					{
						favorites.getValuesObject()[rows[0]] = res.trim();
						updateTable();
						tableFavorites.setRowSelectionInterval(rows[0], rows[0]);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(INSTANCE,
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_SELECT_ONLY_ONE_ROW),
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_MOVE),
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		buttonDelete = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_DELETE));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsRight.add(buttonDelete, gbc);
		buttonDelete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int[] rows = tableFavorites.getSelectedRows();
				if (rows.length > 0)
				{
					int btn = JOptionPane.showConfirmDialog(INSTANCE,
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_DO_YOU_REALLY_WANT_TO_DELETE_SELECTED_RECORDS) + "?",
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_DELETE),
							JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (btn == JOptionPane.OK_OPTION)
					{
						for (int index : rows)
						{
							favorites.getValuesObject()[index] = null;
						}

						VectorMT vector = new VectorMT(MT.TYPE_9_OBJECT, favorites.sizeCurrent);
						for (int i = 0; i < favorites.sizeCurrent; i++)
						{
							if (favorites.getValuesObject()[i] != null)
							{
								vector.put(favorites.getValuesObject()[i]);
							}
						}
						favorites.clear();
						favorites = vector;
						updateTable();
					}
				}
			}
		});

		buttonUp = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MOVE_UP));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 3;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsRight.add(buttonUp, gbc);
		buttonUp.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int[] rows = tableFavorites.getSelectedRows();
				if (rows.length == 1)
				{
					if (rows[0] > 0)
					{
						Object temp = favorites.getValuesObject()[rows[0] - 1];
						favorites.getValuesObject()[rows[0] - 1] = favorites.getValuesObject()[rows[0]];
						favorites.getValuesObject()[rows[0]] = temp;
						updateTable();
						tableFavorites.setRowSelectionInterval(rows[0] - 1, rows[0] - 1);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(INSTANCE,
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_SELECT_ONLY_ONE_ROW),
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_MOVE),
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		buttonDown = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MOVE_DOWN));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 4;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsRight.add(buttonDown, gbc);
		buttonDown.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int[] rows = tableFavorites.getSelectedRows();
				if (rows.length == 1)
				{
					if (rows[0] < favorites.sizeCurrent - 1)
					{
						Object temp = favorites.getValuesObject()[rows[0] + 1];
						favorites.getValuesObject()[rows[0] + 1] = favorites.getValuesObject()[rows[0]];
						favorites.getValuesObject()[rows[0]] = temp;
						updateTable();
						tableFavorites.setRowSelectionInterval(rows[0] + 1, rows[0] + 1);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(INSTANCE,
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_SELECT_ONLY_ONE_ROW),
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_MOVE),
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	private void fillPanelButtonsBottom()
	{
		GridBagConstraints gbc;
		
		Component glue = Box.createHorizontalGlue();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsBottom.add(glue, gbc);

		buttonOk = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_OK));
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsBottom.add(buttonOk, gbc);
		buttonOk.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onOk();
			}
		});

		buttonCancel = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_CANCEL));
		gbc = new GridBagConstraints();
		gbc.gridx   = 2;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtonsBottom.add(buttonCancel, gbc);
		buttonCancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				onCancel();
			}
		});
	}

	private void updateTable()
	{
		tableFavorites.createDefaultColumnsFromModel();
		tableFavorites.getColumnModel().getColumn(0).setMinWidth(COLUMN_0_WIDTH);
		tableFavorites.getColumnModel().getColumn(0).setMaxWidth(COLUMN_0_WIDTH);
		tableFavorites.tableChanged(new TableModelEvent(tableModel));
	}

	public String getSelectedFavorite()
	{
		return selectedFavorite;
	}

	private void saveFavorites()
	{
		S.propertyManager.setProperty(PropertyManagerImpl.PROPERTY_FAVORITES_COUNT, Integer.toString(favorites.sizeCurrent));
		for (int i = 0; i < favorites.sizeCurrent; i++)
		{
			S.propertyManager.setProperty(PropertyManagerImpl.PROPERTY_FAVORITE_PATH + i, (String) favorites.getValuesObject()[i]);
		}
	}

	private void loadFavorites()
	{
		int count;
		try
		{
			count = Integer.parseInt(S.propertyManager.getProperty(PropertyManagerImpl.PROPERTY_FAVORITES_COUNT));
		}
		catch (Exception e)
		{
			count = 0;
		}

		favorites.clear();
		for (int i = 0; i < count; i++)
		{
			favorites.put(S.propertyManager.getProperty(PropertyManagerImpl.PROPERTY_FAVORITE_PATH + i));
		}
	}

	private void onOk()
	{
		saveFavorites();

		int[] selectedRows = tableFavorites.getSelectedRows();

		// Если выбрана только одна строка.
		if (selectedRows.length == 1)
		{
			selectedFavorite = ((String) favorites.getValuesObject()[selectedRows[0]]);
			dispose();
		}
		else
		{
			selectedFavorite = null;

			JOptionPane.showMessageDialog(INSTANCE,
					S.propertyManager.getMessage(PropertyManagerImpl.MSG_SELECT_ONLY_ONE_ROW),
					S.propertyManager.getMessage(PropertyManagerImpl.MSG_FAVORITES),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void onCancel()
	{
		saveFavorites();
		selectedFavorite = null;
		dispose();
	}
}
