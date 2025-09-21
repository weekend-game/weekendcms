package game.weekend.weekendcms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class WeekendCMS {

	/** Application name */
	public static final String APP_NAME = "WeekendCMS";

	/** Version */
	public static final String APP_VERSION = "01.10";

	/**
	 * Create an application. The application frame, objects required for operation,
	 * and frame controls are created.
	 */
	public WeekendCMS() {
		// Keeper of settings between application sessions
		Proper.read(APP_NAME);

		// Interface language
		Loc.setLanguage(Proper.getProperty("Language", "en"));

		// Site descriptor
		siteDescriptor = new SiteDescriptor();
		siteDescriptor.readData();

		// Application frame
		frame = new JFrame(APP_NAME + " (" + APP_VERSION + ") ");
		makeJFrame();

		// Messages
		messenger = new Messenger(frame);

		// Reading for editing from the site descriptor
		readFromSiteDescriptor();
	}

	/**
	 * Customizing the main application frame.
	 */
	private void makeJFrame() {
		// Do nothing when trying to close the window, but
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		// intercept this event
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// and call this method. It will save the settings
				close();
			}
		});

		JPanel panel = (JPanel) frame.getContentPane();
		GBL gbl = new GBL(panel, true);

		gbl.addFixL(new JLabel(Loc.get("project_name") + ":"), 1);
		gbl.addHor(7);

		ButtonGroup btgLan = new ButtonGroup();
		JRadioButton enButton = new JRadioButton("EN");
		enButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prevLanguage = Proper.getProperty("Language", "EN");
				Proper.setProperty("Language", "EN");
				if (!prevLanguage.equalsIgnoreCase("EN"))
					messenger.inf(Loc.get("restart_the_application"));
			}
		});
		gbl.addFixR(enButton, 1);
		enButton.setSelected(Loc.getLanguage().equalsIgnoreCase("EN"));
		btgLan.add(enButton);

		JRadioButton ruButton = new JRadioButton("RU");
		ruButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String prevLanguage = Proper.getProperty("Language", "EN");
				Proper.setProperty("Language", "RU");
				if (!prevLanguage.equalsIgnoreCase("RU"))
					messenger.inf(Loc.get("restart_the_application"));
			}
		});
		gbl.addFixR(ruButton, 1);
		ruButton.setSelected(Loc.getLanguage().equalsIgnoreCase("RU"));
		btgLan.add(ruButton);

		gbl.newLine();

		gbl.addExtX(fldProjectName, 6);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("website_for_projects_in_english") + ":"), 1);
		gbl.newLine();
		gbl.addExtX(fldGitSiteEN, 10);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("website_for_projects_in_russian") + ":"), 1);
		gbl.newLine();
		gbl.addExtX(fldGitSiteRU, 10);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("email") + ":"), 1);
		gbl.newLine();
		gbl.addExtX(fldMail, 10);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("folder_for_generated_pages") + ":"), 1);
		gbl.newLine();
		fldDstFolder.setEditable(false);
		gbl.addExtX(fldDstFolder, 9);
		JButton btnDst = new JButton("...");
		btnDst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File currentFile = new File(fldDstFolder.getText());
				fileChooser.setCurrentDirectory(new File((currentFile == null) ? "." : currentFile.getAbsolutePath()));
				int choice = fileChooser.showOpenDialog(frame);
				if (choice == JFileChooser.APPROVE_OPTION)
					fldDstFolder.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbl.addFixL(btnDst, 1);
		gbl.newLine();

		gbl.addFixL(new JLabel(Loc.get("folder_where_text_files_are_located") + ":"), 1);
		gbl.newLine();
		fldSrcFolder.setEditable(false);
		gbl.addExtX(fldSrcFolder, 9);
		JButton btnSrc = new JButton("...");
		btnSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				File currentFile = new File(fldSrcFolder.getText());
				fileChooser.setCurrentDirectory(new File((currentFile == null) ? "." : currentFile.getAbsolutePath()));
				int choice = fileChooser.showOpenDialog(frame);
				if (choice == JFileChooser.APPROVE_OPTION)
					fldSrcFolder.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		gbl.addFixL(btnSrc, 1);
		gbl.newLine();

		listEditor = new ListEditor();
		gbl.addExtB(listEditor.getPanel(), 10, 1);
		gbl.newLine();

		gbl.addHor(2);

		JButton makeButton = new JButton(Loc.get("generate_pages"));
		makeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeToSiteDescriptor();
				Generator.doIt(siteDescriptor);
				messenger.inf(Loc.get("the_pages_are_formed"));
			}
		});
		gbl.addFixL(makeButton, 1);

		gbl.addHor(3);

		JButton okButton = new JButton(Loc.get("ok"));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				writeToSiteDescriptor();
				siteDescriptor.saveData();
				close();
			}
		});
		gbl.addFixL(okButton, 2);

		JButton cancelButton = new JButton(Loc.get("cancel"));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		gbl.addFixL(cancelButton, 2);

		// Restore the position and size of the frame that it had in the previous work
		// session
		Proper.setBounds(frame);
	}

	/**
	 * Run application .
	 *
	 * @param args not used.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				WeekendCMS app = new WeekendCMS();
				app.getFrame().setVisible(true);
			}
		});
	}

	/**
	 * Close application.
	 * 
	 * Saves everything that needs to be saved for restoration on next startup
	 */
	private void close() {
		Proper.saveBounds(frame);
		Proper.save();

		frame.dispose();
	}

	private void readFromSiteDescriptor() {
		fldProjectName.setText(siteDescriptor.getProjectName());
		fldGitSiteEN.setText(siteDescriptor.getGitSiteEN());
		fldGitSiteRU.setText(siteDescriptor.getGitSiteRU());
		fldMail.setText(siteDescriptor.getMail());
		fldDstFolder.setText(siteDescriptor.getDstFolder());
		fldSrcFolder.setText(siteDescriptor.getSrcFolder());
		listEditor.setPages(siteDescriptor.getPages());
	}

	private void writeToSiteDescriptor() {
		siteDescriptor.setProjectName(fldProjectName.getText());
		siteDescriptor.setGitSiteEN(fldGitSiteEN.getText());
		siteDescriptor.setGitSiteRU(fldGitSiteRU.getText());
		siteDescriptor.setMail(fldMail.getText());
		siteDescriptor.setDstFolder(fldDstFolder.getText());
		siteDescriptor.setSrcFolder(fldSrcFolder.getText());
		siteDescriptor.setPages(listEditor.getPages());
	}

	/**
	 * Get the main application frame.
	 * 
	 * @return main application frame.
	 */
	public JFrame getFrame() {
		return frame;
	}

	private JFrame frame;
	private Messenger messenger;

	private SiteDescriptor siteDescriptor;

	private JTextField fldProjectName = new JTextField();
	private JTextField fldGitSiteEN = new JTextField();
	private JTextField fldGitSiteRU = new JTextField();
	private JTextField fldMail = new JTextField();
	private JTextField fldDstFolder = new JTextField();
	private JTextField fldSrcFolder = new JTextField();

	private ListEditor listEditor;
}
