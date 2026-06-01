package lunasoft.applications.videos_duration.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import lunasoft.applications.videos_duration.core.HelpUtil;
import lunasoft.applications.videos_duration.core.PropertyManagerImpl;
import lunasoft.applications.videos_duration.core.S;
import lunasoft.applications.videos_duration.core.VideoInfo;
import lunasoft.common.data.mt.MT;
import lunasoft.common.data.mt.VectorMT;
import lunasoft.common.util.ImageManager;
import lunasoft.common.view.dialogs.FileChooserDialog;
import lunasoft.common.view.dialogs.OptionsDialog;
import lunasoft.common.view.dialogs.ScanVideosDialog;

@SuppressWarnings("serial")
public class MainFrameVideosDuration extends JFrame
{
	private static MainFrameVideosDuration INSTANCE = new MainFrameVideosDuration();

	/** Отступ. */
	public static final int INDENT = 5;
	/** Ширина 1-го столбца таблицы (с порядковым номером файлов). */
	public static final int COLUMN_0_WIDTH = 50;
	/** Ширина 2-го столбца таблицы. */
	public static final int COLUMN_1_WIDTH = 20;
	/** Ширина 4-го столбца таблицы. */
	public static final int COLUMN_3_WIDTH = 100;

	public static final long TIME_SECOND = 1000;
	public static final	long TIME_MINUTE = 60 * TIME_SECOND;
	public static final	long TIME_HOUR = 60 * TIME_MINUTE;

	// GUI.
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItemFileExit;
	private JMenu menuOptions;
	private JMenuItem menuItemOptionsSettings;
	private JMenu menuHelp;
	private JMenuItem menuItemHelpHelpTopics;
	private JMenuItem menuItemHelpAbout;

	// Panels.
	private JPanel panelContent;
	private JPanel panelPathToVideos;
	private JPanel panelVideosTable;
	private JPanel panelTotal;

	// panelPathToVideos.
	private JTextField textFieldPathToVideos;
	private JButton buttonBrowse;

	// panelVideosTable.
	private JButton buttonSelectAll;
	private JButton buttonDeselectAll;
	private JTable tableVideos;
	private TableModel tableModel;

	// panelTotal.
	private JTextField textFieldTotal;

	private VectorMT videos = new VectorMT(MT.TYPE_9_OBJECT, 1000);

	public MainFrameVideosDuration()
	{
	}

	public static void main(String args[])
	{
		if (INSTANCE.isVisible())
		{
			INSTANCE.requestFocus();
		}
		else
		{
			INSTANCE.init();
		}
	}

	private void init()
	{
		S.propertyManager.loadMessages();
		S.propertyManager.loadProperties();

		setIconImage(S.getIconImage());
		setTitle(HelpUtil.NAME_VERSION);

		prepareGUI();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				fileExit();
			}
		});

		setSize(1280, 720);
		S.setCenteredRelativelyDesktop(this);
		setVisible(true);
	}

	private void prepareGUI()
	{
		GridBagConstraints gbc;

		panelContent = new JPanel(new GridBagLayout());
		setContentPane(panelContent);

		//
		// Menu bar.
		//

		menuBar = new JMenuBar();
		INSTANCE.setJMenuBar(menuBar);
		{
			menuFile = new JMenu();
			menuFile.setText(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_FILE));
			menuBar.add(menuFile);
			{
				menuItemFileExit = new JMenuItem();
				menuItemFileExit.setText(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_FILE_EXIT));
				menuItemFileExit.setIcon(ImageManager.readImageIconRelativePath(ImageManager.IMG_FILE_EXIT));
				menuItemFileExit.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						fileExit();
					}
				});
				menuFile.add(menuItemFileExit);
			}

			menuOptions = new JMenu();
			menuOptions.setText(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_OPTIONS));
			menuBar.add(menuOptions);
			{
				menuItemOptionsSettings = new JMenuItem();
				menuItemOptionsSettings.setText(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_OPTIONS_SETTINGS) + "...");
				menuItemOptionsSettings.setIcon(ImageManager.readImageIconRelativePath(ImageManager.IMG_OPTIONS_SETTINGS));
				menuItemOptionsSettings.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						OptionsDialog optionsDialog = new OptionsDialog(INSTANCE);
						optionsDialog.setVisible(true);
					}
				});
				menuOptions.add(menuItemOptionsSettings);
			}

			menuHelp = new JMenu();
			menuHelp.setText(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_HELP));
			menuBar.add(menuHelp);
			{
				menuItemHelpHelpTopics = new JMenuItem();
				menuItemHelpHelpTopics.setText(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_HELP_HELP_TOPICS));
				menuItemHelpHelpTopics.setIcon(ImageManager.readImageIconRelativePath(ImageManager.IMG_LINK_HELP_HELP_TOPICS));
				menuItemHelpHelpTopics.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						HelpUtil.showHelpTopics(INSTANCE);
					}
				});
				menuHelp.add(menuItemHelpHelpTopics);

				menuHelp.addSeparator();

				menuItemHelpAbout = new JMenuItem();
				menuItemHelpAbout.setText(S.propertyManager.getMessage(PropertyManagerImpl.MSG_MENU_HELP_ABOUT) + "...");
				menuItemHelpAbout.setIcon(ImageManager.readImageIconRelativePath(ImageManager.IMG_LINK_HELP_ABOUT));
				menuItemHelpAbout.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						HelpUtil.showAbout(INSTANCE);
					}
				});
				menuHelp.add(menuItemHelpAbout);
			}
		}

		panelPathToVideos = new JPanel(new GridBagLayout());
		panelPathToVideos.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_PATH_TO_VIDEOS) + ":"));
		fillPanelPathToVideos();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelContent.add(panelPathToVideos, gbc);

		panelVideosTable = new JPanel(new GridBagLayout());
		panelVideosTable.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_VIDEOS) + ":"));
		fillPanelVideosTable();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelContent.add(panelVideosTable, gbc);

		panelTotal = new JPanel(new GridBagLayout());
		panelTotal.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_TOTAL) + ":"));
		fillPanelTotal();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelContent.add(panelTotal, gbc);
	}

	private void fillPanelPathToVideos()
	{
		GridBagConstraints gbc;

		textFieldPathToVideos = new JTextField();
		textFieldPathToVideos.setEditable(false);
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelPathToVideos.add(textFieldPathToVideos, gbc);

		buttonBrowse = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_BROWSE) + "...");
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelPathToVideos.add(buttonBrowse, gbc);
		buttonBrowse.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				FileChooserDialog fileChooserDialog = new FileChooserDialog(INSTANCE);
				fileChooserDialog.setVisible(true);

				String selectedPath = fileChooserDialog.getSelectedPath();
				if (selectedPath != null && selectedPath.trim().length() > 0)
				{
					ScanVideosDialog scanVideosDialog = new ScanVideosDialog(INSTANCE, selectedPath);
					scanVideosDialog.setVisible(true);
					if (scanVideosDialog.isOk())
					{
						videos = scanVideosDialog.getVideos();
						textFieldPathToVideos.setText(selectedPath);
						updateTable();
					}
				}
			}
		});
	}

	private void fillPanelVideosTable()
	{
		GridBagConstraints gbc;

		buttonSelectAll = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_SELECT_ALL));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelVideosTable.add(buttonSelectAll, gbc);
		buttonSelectAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				for (int i = 0; i < videos.sizeCurrent; i++)
				{
					VideoInfo videoInfo = (VideoInfo) videos.getValuesObject()[i];
					videoInfo.selected = true;
				}

				updateTable();
			}
		});

		buttonDeselectAll = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_DESELECT_ALL));
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelVideosTable.add(buttonDeselectAll, gbc);
		buttonDeselectAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				for (int i = 0; i < videos.sizeCurrent; i++)
				{
					VideoInfo videoInfo = (VideoInfo) videos.getValuesObject()[i];
					videoInfo.selected = false;
				}

				updateTable();
			}
		});

		Component glue = Box.createHorizontalGlue();
		gbc = new GridBagConstraints();
		gbc.gridx   = 2;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelVideosTable.add(glue, gbc);

		tableVideos = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tableVideos);
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.gridwidth   = 3;
		gbc.gridheight   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelVideosTable.add(scrollPane, gbc);

		tableModel = new TableModel()
		{
			public int getRowCount()
			{
				if (videos == null)
				{
					return 0;
				}
				else
				{
					return videos.sizeCurrent;
				}
			}

			public int getColumnCount()
			{
				return 4;
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
					columnName = "+";
				}
				else if (columnIndex == 2)
				{
					columnName = S.propertyManager.getMessage(PropertyManagerImpl.MSG_PATH);
				}
				else if (columnIndex == 3)
				{
					columnName = S.propertyManager.getMessage(PropertyManagerImpl.MSG_DURATION);
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
					classObj = Boolean.class;
				}
				else if (columnIndex == 2)
				{
					classObj = String.class;
				}
				else if (columnIndex == 3)
				{
					classObj = String.class;
				}

				return classObj;
			}

			public boolean isCellEditable(int rowIndex, int columnIndex)
			{
				if (columnIndex == 1)
				{
					return true;
				}
				else
				{
					return false;
				}
			}

			public Object getValueAt(int rowIndex, int columnIndex)
			{
				Object value = "";

				if (videos != null)
				{
					if (columnIndex == 0)
					{
						value = Integer.valueOf(rowIndex + 1);
					}
					else if (columnIndex == 1)
					{
						value = new Boolean(((VideoInfo) videos.getValuesObject()[rowIndex]).selected);
					}
					else if (columnIndex == 2)
					{
						value = ((VideoInfo) videos.getValuesObject()[rowIndex]).path;
					}
					else if (columnIndex == 3)
					{
						value = ((VideoInfo) videos.getValuesObject()[rowIndex]).duration;
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
				if (columnIndex == 1)
				{
					((VideoInfo) videos.getValuesObject()[rowIndex]).selected = ((Boolean) aValue).booleanValue();
					updateTotal();
				}
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

		tableVideos.setModel(tableModel);
		tableVideos.getColumnModel().getColumn(0).setMinWidth(COLUMN_0_WIDTH);
		tableVideos.getColumnModel().getColumn(0).setMaxWidth(COLUMN_0_WIDTH);
		tableVideos.getColumnModel().getColumn(1).setMinWidth(COLUMN_1_WIDTH);
		tableVideos.getColumnModel().getColumn(1).setMaxWidth(COLUMN_1_WIDTH);
		tableVideos.getColumnModel().getColumn(3).setMinWidth(COLUMN_3_WIDTH);
		tableVideos.getColumnModel().getColumn(3).setMaxWidth(COLUMN_3_WIDTH);
		tableVideos.getTableHeader().setReorderingAllowed(false);
	}

	private void fillPanelTotal()
	{
		GridBagConstraints gbc;

		textFieldTotal = new JTextField();
		textFieldTotal.setEditable(false);
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelTotal.add(textFieldTotal, gbc);
	}

	private void updateTable()
	{
		tableVideos.createDefaultColumnsFromModel();
		tableVideos.getColumnModel().getColumn(0).setMinWidth(COLUMN_0_WIDTH);
		tableVideos.getColumnModel().getColumn(0).setMaxWidth(COLUMN_0_WIDTH);
		tableVideos.getColumnModel().getColumn(1).setMinWidth(COLUMN_1_WIDTH);
		tableVideos.getColumnModel().getColumn(1).setMaxWidth(COLUMN_1_WIDTH);
		tableVideos.getColumnModel().getColumn(3).setMinWidth(COLUMN_3_WIDTH);
		tableVideos.getColumnModel().getColumn(3).setMaxWidth(COLUMN_3_WIDTH);
		tableVideos.tableChanged(new TableModelEvent(tableModel));
		updateTotal();
	}

	private void updateTotal()
	{
		long timeTotal = 0;
		int filesSelected = 0;

		// Вычисляем длительность всех видосов.
		for (int i = 0; i < videos.sizeCurrent; i++)
		{
			VideoInfo videoInfo = (VideoInfo) videos.getValuesObject()[i];

			if (videoInfo.duration.trim().length() == 0)
			{
				continue;
			}

			int hourLabel = videoInfo.duration.indexOf(':');
			videoInfo.hours = Integer.parseInt(videoInfo.duration.substring(0, hourLabel));

			String temp = videoInfo.duration.substring(hourLabel + 1);
			int minuteLabel = temp.indexOf(':');
			videoInfo.minutes = Integer.parseInt(temp.substring(0, minuteLabel));

			temp = temp.substring(minuteLabel + 1);
			int secondLabel = temp.indexOf('.');
			videoInfo.seconds = Integer.parseInt(temp.substring(0, secondLabel));

			temp = temp.substring(secondLabel + 1);
			videoInfo.milliseconds = Integer.parseInt(temp);

			videoInfo.time = videoInfo.hours * TIME_HOUR
					+ videoInfo.minutes * TIME_MINUTE
					+ videoInfo.seconds * TIME_SECOND
					+ videoInfo.milliseconds;

			if (videoInfo.selected)
			{
				filesSelected++;
				timeTotal += videoInfo.time;
			}
		}

		// Подсчитываем итоговое время.
		long timeTotalMilliseconds = timeTotal % TIME_SECOND;

		timeTotal -= timeTotalMilliseconds;
		long timeTotalSeconds = (timeTotal % TIME_MINUTE) / TIME_SECOND;

		timeTotal -= timeTotalSeconds * TIME_SECOND;
		long timeTotalMinutes = (timeTotal % TIME_HOUR) / TIME_MINUTE;

		timeTotal -= timeTotalMinutes * TIME_MINUTE;
		long timeTotalHours = timeTotal / TIME_HOUR;

		String timeStr = "";
		timeStr += S.propertyManager.getMessage(PropertyManagerImpl.MSG_FILES_SELECTED) + " : " + filesSelected + " / " + videos.sizeCurrent;
		timeStr += ", " + S.propertyManager.getMessage(PropertyManagerImpl.MSG_DURATION) + " : "
				+ timeTotalHours + " ч " + timeTotalMinutes + " мин " + timeTotalSeconds + " сек " + timeTotalMilliseconds + " мс";
		textFieldTotal.setText(timeStr);
	}

	private void fileExit()
	{
		S.propertyManager.saveProperties();
		dispose();
	}
}
