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
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import lunasoft.applications.videos_duration.core.PropertyManagerImpl;
import lunasoft.applications.videos_duration.core.S;
import lunasoft.applications.videos_duration.core.VideoInfo;
import lunasoft.common.data.mt.MT;
import lunasoft.common.data.mt.VectorMT;

@SuppressWarnings("serial")
public class ScanVideosDialog extends JDialog
{
	/** Отступ. */
	public static final int INDENT = 5;
	/** FPS обновления GUI. */
	public static final long FPS = 30;

	// Panels.
	private JPanel contentPane;
	private JPanel panelProgress;
	private JPanel panelButtons;

	// panelProgress.
	private JLabel labelCurrentFile;
	private JTextField textFieldCurrentFile;
	private JLabel labelFilesProcessed;
	private JTextField textFieldFilesProcessed;

	// panelButtons.
	private JButton buttonCancel;

	/** Путь, который надо сканировать. */
	private String path;
	/** Список видосов отсканированных. VectorMT хранит тип VideoInfo. */
	private VectorMT videos = new VectorMT(MT.TYPE_9_OBJECT, 1000);
	/** Признак, что сканирование завершено. */
	private boolean ok;
	/** Признак работы потока сканирования. */
	private boolean run;

	public ScanVideosDialog(Frame parent, String path)
	{
		super(parent, S.propertyManager.getMessage(PropertyManagerImpl.MSG_SCANNING), true);
		this.path = path;

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

		scan();
	}

	private void prepareGUI()
	{
		GridBagConstraints gbc;

		panelProgress = new JPanel(new GridBagLayout());
		panelProgress.setBorder(new TitledBorder(S.propertyManager.getMessage(PropertyManagerImpl.MSG_PROGRESS) + ":"));
		fillPanelProgress();
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		contentPane.add(panelProgress, gbc);

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

	private void fillPanelProgress()
	{
		GridBagConstraints gbc;

		labelCurrentFile = new JLabel(S.propertyManager.getMessage(PropertyManagerImpl.MSG_CURRENT_FILE) + ":");
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 0;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.anchor  = GridBagConstraints.EAST;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelProgress.add(labelCurrentFile, gbc);

		textFieldCurrentFile = new JTextField();
		textFieldCurrentFile.setEditable(false);
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.anchor  = GridBagConstraints.EAST;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelProgress.add(textFieldCurrentFile, gbc);

		labelFilesProcessed = new JLabel(S.propertyManager.getMessage(PropertyManagerImpl.MSG_FILES_PROCESSED) + ":");
		gbc = new GridBagConstraints();
		gbc.gridx   = 0;
		gbc.gridy   = 1;
		gbc.weightx = 0.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.NONE;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelProgress.add(labelFilesProcessed, gbc);

		textFieldFilesProcessed = new JTextField();
		textFieldFilesProcessed.setEditable(false);
		gbc = new GridBagConstraints();
		gbc.gridx   = 1;
		gbc.gridy   = 1;
		gbc.weightx = 1.0;
		gbc.weighty = 0.0;
		gbc.fill    = GridBagConstraints.HORIZONTAL;
		gbc.insets  = new Insets(0, INDENT, INDENT, INDENT); // top, left, bottom, right.
		panelProgress.add(textFieldFilesProcessed, gbc);
	}

	private void fillPanelButtons()
	{
		GridBagConstraints gbc;

		buttonCancel = new JButton(S.propertyManager.getMessage(PropertyManagerImpl.MSG_CANCEL));
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

	private void scan()
	{
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				run = true;

				// Сканируем все вложенные файлы.
				videos.clear();
				File file = new File(path);
				File[] files = file.listFiles();
				for (File file2 : files)
				{
					VideoInfo videoInfo = new VideoInfo();
					videoInfo.file = file2;
					videoInfo.path = file2.getAbsolutePath();
					videoInfo.selected = true;
					videos.put(videoInfo);
				}

				// Раскрываем все подкаталоги. Получаем только файлы.
				boolean repeat = true;
				while (repeat)
				{
					repeat = false;

					for (int i = 0; i < videos.sizeCurrent; i++)
					{
						VideoInfo videoInfo = (VideoInfo) videos.getValuesObject()[i];
						if (videoInfo.file.isDirectory())
						{
							File[] files2 = videoInfo.file.listFiles();
							for (File file2 : files2)
							{
								VideoInfo videoInfo2 = new VideoInfo();
								videoInfo2.file = file2;
								videoInfo2.path = file2.getAbsolutePath();
								videoInfo2.selected = true;
								videos.put(videoInfo2);
							}

							videos.removeIndex(i);
							repeat = true;
							break;
						}
					}
				}

				// Сканирование завершено. Сортируем список.
				Arrays.sort(videos.getValuesObject(), 0, videos.sizeCurrent);

				// Обновление GUI.
				long interval = 1000 / FPS;
				long timeLastUpdate = System.currentTimeMillis() - interval;

				// Для каждого файла запускаем МедиаИнфо.
				for (int i = 0; i < videos.sizeCurrent; i++)
				{
					VideoInfo videoInfo = (VideoInfo) videos.getValuesObject()[i];
					
					List<String> runCommand = new ArrayList<String>();
					runCommand.add(S.propertyManager.getProperty(PropertyManagerImpl.PROPERTY_PATH_TO_MEDIA_INFO));
					runCommand.add("--Inform=\"General;%Duration/String3%\"");
					runCommand.add(videoInfo.path);

					try
					{
						ProcessBuilder processBuilder = new ProcessBuilder(runCommand);

						// Set working directory.
						File workingDirectory = new File(runCommand.get(0)).getParentFile();
						processBuilder.directory(workingDirectory);

						// Merge stdout and stderr.
						processBuilder.redirectErrorStream(true);

						// Start process.
						Process process = processBuilder.start();

						// Сразу закрываем поток вывода, чтобы процесс не дожидался ввода с клавиатуры.
						process.getOutputStream().close();

						BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
						String str = br.readLine();
						System.out.println(videoInfo.path);
						System.out.println(str);

						videoInfo.duration = str;
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

					// Обновляем GUI.
					long timeCurrent = System.currentTimeMillis();
					if (timeCurrent - timeLastUpdate > interval)
					{
						timeLastUpdate = System.currentTimeMillis();
						updateGUI(videoInfo.path, i, videos.sizeCurrent);
					}

					// Если надо, прерываем поток, выходим.
					if (!run)
					{
						break;
					}
				}

				onOk();
			}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	private void updateGUI(String fileCurrent, int fileCurrentIndex, int fileTotal)
	{
		Thread thread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				textFieldCurrentFile.setText(fileCurrent);
				textFieldFilesProcessed.setText((fileCurrentIndex + 1) + " / " + fileTotal);
			}
		});
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.start();
	}

	public boolean isOk()
	{
		return ok;
	}

	public VectorMT getVideos()
	{
		return videos;
	}

	private void onOk()
	{
		ok = true;
		dispose();
	}

	private void onCancel()
	{
		ok = false;
		run = false;
		dispose();
	}
}
