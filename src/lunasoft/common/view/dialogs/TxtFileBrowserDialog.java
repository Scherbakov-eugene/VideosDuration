package lunasoft.common.view.dialogs;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import lunasoft.applications.videos_duration.core.S;

@SuppressWarnings("serial")
public class TxtFileBrowserDialog extends JDialog
{
	public static final Font FONT_MONOSPACED_12 = new Font("Monospaced", Font.TRUETYPE_FONT, 12);

	private JPanel contentPane;
	private JTextArea textArea;

	public TxtFileBrowserDialog(Frame parent, String title, String text, int width, int height)
	{
		super(parent, title, true);

		GridBagConstraints gbc;

		contentPane = new JPanel();
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);

		{
			gbc = new GridBagConstraints(
					0, 1, // grid (x, y).
					1, 1, // grid (w, h).
					1.0, 1.0, // weight (x, y).
					GridBagConstraints.WEST, // anchor.
					GridBagConstraints.BOTH, // fill.
					new Insets(10, 10, 10, 10), // insets (top, left, bottom, right).
					0, 0); // ipad (x, y).
			textArea = new JTextArea();
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			JScrollPane scrollPane = new JScrollPane(textArea);
			contentPane.add(scrollPane, gbc);
		}

		textArea.setFont(FONT_MONOSPACED_12);
		textArea.setText(text);
		textArea.setCaretPosition(0);

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

		setSize(width, height);
		S.setCenteredRelativelyParent(parent, this);
	}

	private void onCancel()
	{
		dispose();
	}
}
