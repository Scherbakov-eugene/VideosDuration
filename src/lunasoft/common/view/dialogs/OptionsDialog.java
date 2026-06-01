package lunasoft.common.view.dialogs;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import lunasoft.applications.videos_duration.core.PropertyManagerImpl;
import lunasoft.applications.videos_duration.core.S;

@SuppressWarnings("serial")
public class OptionsDialog extends JDialog
{
	private static OptionsDialog INSTANCE;

	/** Отступ. */
	public static final int INDENT = 5;

	// Panels.
	private JPanel contentPane;
	private JPanel panelPathToMediaInfo;
	private JPanel panelButtons;

	// panelPathToMediaInfo.
	private JLabel labelPathToMediaInfo;
	private JTextField textFieldPathToMediaInfo;
	private JButton buttonBrowse;

	// panelButtons.
	private JButton buttonCancel;

	public OptionsDialog(Frame parent)
	{
		super(parent, S.propertyManager.getMessage(PropertyManagerImpl.MSG_OPTIONS), true);
		INSTANCE = this;

		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);

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

		setSize(1000, 200);
		S.setCenteredRelativelyParent(parent, this);
	}

	private void prepareGUI()
	{
		GridBagConstraints gbc;

		panelPathToMediaInfo = new JPanel(new GridBagLayout());
		panelPathToMediaInfo.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_SETTINGS) + ":"));
		fillPanelPathToMediaInfo();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelPathToMediaInfo, gbc);

		Component glue = Box.createGlue();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(glue, gbc);

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

	private void fillPanelPathToMediaInfo()
	{
		GridBagConstraints gbc;

		labelPathToMediaInfo = new JLabel(S.propertyManager.getMessage(PropertyManagerImpl.MSG_PATH_TO_MEDIA_INFO) + ":");
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelPathToMediaInfo.add(labelPathToMediaInfo, gbc);

		textFieldPathToMediaInfo = new JTextField();
		textFieldPathToMediaInfo.setEditable(false);
		String propertyPathToMediaInfo = S.propertyManager.getProperty(PropertyManagerImpl.PROPERTY_PATH_TO_MEDIA_INFO);
		if (propertyPathToMediaInfo != null)
		{
			textFieldPathToMediaInfo.setText(propertyPathToMediaInfo);
		}
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelPathToMediaInfo.add(textFieldPathToMediaInfo, gbc);

		buttonBrowse = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_BROWSE));
		gbc = new GridBagConstraints();
		gbc.gridx   = 2;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelPathToMediaInfo.add(buttonBrowse, gbc);
		buttonBrowse.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setDialogTitle(S.propertyManager.getMessage(PropertyManagerImpl.MSG_PATH_TO_MEDIA_INFO));

				// В диалоге подсвечивается последний выбранный файл.
				String propertyPathToMediaInfo = S.propertyManager.getProperty(PropertyManagerImpl.PROPERTY_PATH_TO_MEDIA_INFO);
				if (propertyPathToMediaInfo != null)
				{
					File selectedFile = new File(propertyPathToMediaInfo);
					if (selectedFile.exists())
					{
						fileChooser.setSelectedFile(selectedFile);
					}
				}

				// Если выбор подтверждён.
				if (fileChooser.showOpenDialog(INSTANCE) == JFileChooser.APPROVE_OPTION)
				{
					File file = fileChooser.getSelectedFile();

					String pathToMediaInfo = file.getPath();

					S.propertyManager.setProperty(PropertyManagerImpl.PROPERTY_PATH_TO_MEDIA_INFO, pathToMediaInfo);
					textFieldPathToMediaInfo.setText(pathToMediaInfo);
				}
			}
		});

		Component glue = Box.createGlue();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill    = GridBagConstraints.BOTH;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelPathToMediaInfo.add(glue, gbc);
	}

	private void fillPanelButtons()
	{
		GridBagConstraints gbc;

		buttonCancel = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_CLOSE));
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
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

	private void onCancel()
	{
		dispose();
	}
}
