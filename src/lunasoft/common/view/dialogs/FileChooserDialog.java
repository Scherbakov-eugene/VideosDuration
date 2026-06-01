package lunasoft.common.view.dialogs;

import java.awt.Component;
import java.awt.Frame;
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
import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
public class FileChooserDialog extends JDialog
{
	private static FileChooserDialog INSTANCE;

	/** Отступ. */
	public static final int INDENT = 5;
	/** Ширина 1-го столбца таблицы (с порядковым номером файлов). */
	public static final int COLUMN_0_WIDTH = 50;
	/** Ширина 3-го столбца таблицы (с датой). */
	public static final int COLUMN_2_WIDTH = 220;

	// Panels.
	private JPanel contentPane;
	private JPanel panelPath;
	private JPanel panelCatalogs;
	private JPanel panelButtons;

	// panelPath.
	private JTextField textFieldPath;

	// panelCatalogs.
	private JButton buttonFavorites;
	private JButton buttonUp;
	private JTable tableCatalogs;
	private TableModel tableModel;
	private VectorMT files;

	// panelButtons.
	private JButton buttonOk;
	private JButton buttonCancel;

	private String selectedPath;

	public FileChooserDialog(Frame parent)
	{
		super(parent, S.propertyManager.getMessage(PropertyManagerImpl.MSG_PATH_TO_VIDEOS), true);
		INSTANCE = this;

		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);

		prepareGUI();

		String lastOpenedDirectory = S.propertyManager.getProperty(PropertyManagerImpl.PROPERTY_LAST_OPENED_DIRECTORY);
		if (lastOpenedDirectory != null)
		{
			textFieldPath.setText(lastOpenedDirectory);
			textFieldPathEnter();
		}

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

		setSize(800, 800);
		S.setCenteredRelativelyParent(parent, this);
	}

	private void prepareGUI()
	{
		GridBagConstraints gbc;

		panelPath = new JPanel(new GridBagLayout());
		panelPath.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_PATH_TO_VIDEOS) + ":"));
		fillPanelPath();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelPath, gbc);

		panelCatalogs = new JPanel(new GridBagLayout());
		panelCatalogs.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_CATALOGS) + ":"));
		fillPanelCatalogs();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelCatalogs, gbc);

		panelButtons = new JPanel(new GridBagLayout());
		fillPanelButtons();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelButtons, gbc);
	}

	private void fillPanelPath()
	{
		GridBagConstraints gbc;

		textFieldPath = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelPath.add(textFieldPath, gbc);
		textFieldPath.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				textFieldPathEnter();
			}
		});
	}

	private void fillPanelCatalogs()
	{
		GridBagConstraints gbc;

		buttonFavorites = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_FAVORITES));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelCatalogs.add(buttonFavorites, gbc);
		buttonFavorites.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				FavoritesDialog favoritesDialog = new FavoritesDialog(INSTANCE);
				favoritesDialog.setVisible(true);

				String selectedFavorite = favoritesDialog.getSelectedFavorite();
				if (selectedFavorite != null)
				{
					File file = new File(selectedFavorite);
					if (file.exists() && file.isDirectory())
					{
						textFieldPath.setText(selectedFavorite);
						textFieldPathEnter();
					}
					else
					{
						JOptionPane.showMessageDialog(INSTANCE,
								S.propertyManager.getMessage(PropertyManagerImpl.MSG_SELECTED_PATH_IS_NOT_A_DIRECTORY) +".",
								S.propertyManager.getMessage(PropertyManagerImpl.MSG_ERROR),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		buttonUp = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_UP));
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelCatalogs.add(buttonUp, gbc);
		buttonUp.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				File fileParent = new File(textFieldPath.getText()).getParentFile();
				if (fileParent != null && fileParent.exists() && fileParent.isDirectory())
				{
					textFieldPath.setText(fileParent.getAbsolutePath());
					textFieldPathEnter();
				}
				else
				{
					JOptionPane.showMessageDialog(INSTANCE,
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_PARENT_DIR_IS_NOT_A_DIRECTORY) + ".",
							S.propertyManager.getMessage(PropertyManagerImpl.MSG_ERROR),
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		Component glue = Box.createHorizontalGlue();
		gbc.gridx   = 2;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelCatalogs.add(glue, gbc);

		tableCatalogs = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tableCatalogs);
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.gridwidth   = 3;
		gbc.gridheight   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelCatalogs.add(scrollPane, gbc);

		tableCatalogs.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				// LMB double click.
				if ((e.getButton() == 1) && (e.getClickCount() == 2))
				{
					int[] selectedRows = tableCatalogs.getSelectedRows();

					// Если выбрана только одна строка.
					if (selectedRows.length == 1)
					{
						String path = ((File) files.getValuesObject()[selectedRows[0]]).getAbsolutePath();
						textFieldPath.setText(path);
						textFieldPathEnter();
					}
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
				if (files == null)
				{
					return 0;
				}
				else
				{
					return files.sizeCurrent;
				}
			}

			public int getColumnCount()
			{
				return 3;
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
				else if (columnIndex == 2)
				{
					columnName = S.propertyManager.getMessage(PropertyManagerImpl.MSG_DATE);
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
				else if (columnIndex == 2)
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

				if (files != null)
				{
					if (columnIndex == 0)
					{
						value = Integer.valueOf(rowIndex + 1);
					}
					else if (columnIndex == 1)
					{
						value = ((File) files.getValuesObject()[rowIndex]).getAbsolutePath();
					}
					else if (columnIndex == 2)
					{
						value = new Date(((File) files.getValuesObject()[rowIndex]).lastModified()).toString();
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

		tableCatalogs.setModel(tableModel);
		tableCatalogs.getColumnModel().getColumn(0).setMinWidth(COLUMN_0_WIDTH);
		tableCatalogs.getColumnModel().getColumn(0).setMaxWidth(COLUMN_0_WIDTH);
		tableCatalogs.getColumnModel().getColumn(2).setMinWidth(COLUMN_2_WIDTH);
		tableCatalogs.getColumnModel().getColumn(2).setMaxWidth(COLUMN_2_WIDTH);
		tableCatalogs.getTableHeader().setReorderingAllowed(false);
	}

	private void fillPanelButtons()
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
		panelButtons.add(glue, gbc);

		buttonOk = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_OK));
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelButtons.add(buttonOk, gbc);
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
		panelButtons.add(buttonCancel, gbc);
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
		tableCatalogs.createDefaultColumnsFromModel();
		tableCatalogs.getColumnModel().getColumn(0).setMinWidth(COLUMN_0_WIDTH);
		tableCatalogs.getColumnModel().getColumn(0).setMaxWidth(COLUMN_0_WIDTH);
		tableCatalogs.getColumnModel().getColumn(2).setMinWidth(COLUMN_2_WIDTH);
		tableCatalogs.getColumnModel().getColumn(2).setMaxWidth(COLUMN_2_WIDTH);
		tableCatalogs.tableChanged(new TableModelEvent(tableModel));
	}

	private void textFieldPathEnter()
	{
		File file = new File(textFieldPath.getText());
		if (file.exists() && file.isDirectory())
		{
			// Получаем список файлов.
			File[] files2 = file.listFiles();

			// Очищаем список файлов для обозревания.
			if (files == null)
			{
				files = new VectorMT(MT.TYPE_9_OBJECT, 100);
			}
			else
			{
				files.clear();
			}

			// Добавляем для обозревания только каталоги.
			for (File file2 : files2)
			{
				if (file2.isDirectory())
				{
					files.put(file2);
				}
			}

			// Сортируем список файлов.
			Arrays.sort(files.getValuesObject(), 0, files.sizeCurrent);

			// Обновляем таблицу.
			updateTable();
		}
		else
		{
			JOptionPane.showMessageDialog(INSTANCE,
					S.propertyManager.getMessage(PropertyManagerImpl.MSG_SELECTED_PATH_IS_NOT_A_DIRECTORY) + ".\n"
							+ "\"" + textFieldPath.getText() + "\"",
					S.propertyManager.getMessage(PropertyManagerImpl.MSG_ERROR),
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public String getSelectedPath()
	{
		return selectedPath;
	}

	private void onOk()
	{
		selectedPath = textFieldPath.getText();
		S.propertyManager.setProperty(PropertyManagerImpl.PROPERTY_LAST_OPENED_DIRECTORY, selectedPath);
		dispose();
	}

	private void onCancel()
	{
		selectedPath = null;
		dispose();
	}
}
