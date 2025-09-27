package game.weekend.weekendcms;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class SiteDescriptor {

	public static final int MAX_PAGE = 20;

	public void readData() {
		setProjectName(Proper.getProperty("projectName", "Weekend game"));
		setGitSiteEN(Proper.getProperty("gitSiteEN", "https://github.com/weekend-game"));
		setGitSiteRU(Proper.getProperty("gitSiteRU", "https://gitflic.ru/user/weekend-game"));
		setMail(Proper.getProperty("mail", "weekend_game@mail.ru"));
		setDstFolder(Proper.getProperty("dstFolder", "./destination"));
		setSrcFolder(Proper.getProperty("srcFolder", "./source"));

		pages.clear();
		for (int i = 0; i < MAX_PAGE; ++i) {
			String fileName = Proper.getProperty("fileName" + i, "");
			String name = Proper.getProperty("name" + i, "");
			String lang = Proper.getProperty("lang" + i, "");
			if (fileName.length() > 0 || name.length() > 0 || lang.length() > 0)
				pages.add(new PageDescriptor(fileName, name, lang));
		}
	}

	public void saveData() {
		Proper.setProperty("projectName", projectName);
		Proper.setProperty("gitSiteEN", gitSiteEN);
		Proper.setProperty("gitSiteRU", gitSiteRU);
		Proper.setProperty("mail", mail);
		Proper.setProperty("dstFolder", dstFolder);
		Proper.setProperty("srcFolder", srcFolder);

		int i = 0;
		for (PageDescriptor d : pages) {
			Proper.setProperty("fileName" + i, d.fileName);
			Proper.setProperty("name" + i, d.name);
			Proper.setProperty("lang" + i, d.lang);
			++i;
		}
		while (i < MAX_PAGE) {
			Proper.setProperty("fileName" + i, "");
			Proper.setProperty("name" + i, "");
			Proper.setProperty("lang" + i, "");
			++i;
		}
	}

	@Override
	public boolean equals(Object o) {
		SiteDescriptor sd = (SiteDescriptor) o;

		if (!getProjectName().equals(sd.getProjectName()) || !getGitSiteEN().equals(sd.getGitSiteEN())
				|| !getGitSiteRU().equals(sd.getGitSiteRU()) || !getMail().equals(sd.getMail())
				|| !getDstFolder().equals(sd.getDstFolder()) || !getSrcFolder().equals(sd.getSrcFolder()))
			return false;

		return (getPages().equals(sd.getPages()));
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getGitSiteEN() {
		return gitSiteEN;
	}

	public void setGitSiteEN(String gitSiteEN) {
		this.gitSiteEN = gitSiteEN;
	}

	public String getGitSiteRU() {
		return gitSiteRU;
	}

	public void setGitSiteRU(String gitSiteRU) {
		this.gitSiteRU = gitSiteRU;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getDstFolder() {
		return dstFolder;
	}

	public void setDstFolder(String dstFolder) {
		this.dstFolder = dstFolder;
	}

	public String getSrcFolder() {
		return srcFolder;
	}

	public void setSrcFolder(String srcFolder) {
		this.srcFolder = srcFolder;
	}

	public ArrayList<PageDescriptor> getPages() {
		return pages;
	}

	public void setPages(DefaultListModel<PageDescriptor> pages) {
		this.pages.clear();
		for (int i = 0; i < pages.size(); ++i)
			this.pages.add(pages.get(i));
	}

	public static class PageDescriptor {

		public PageDescriptor(String fileName, String name, String lang) {
			this.fileName = fileName;
			this.name = name;
			this.lang = lang;
		}

		@Override
		public boolean equals(Object o) {
			PageDescriptor pd = (PageDescriptor) o;

			return getFileName().equals(pd.getFileName()) && getName().equals(pd.getName())
					&& getLang().equals(pd.getLang());
		}

		@Override
		public String toString() {
			return lang + " " + name.trim() + " (" + fileName.trim() + ")";
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLang() {
			return lang;
		}

		public void setLang(String lang) {
			this.lang = lang;
		}

		private String fileName;
		private String name;
		private String lang;
	}

	private String projectName;
	private String gitSiteEN;
	private String gitSiteRU;
	private String mail;
	private String dstFolder;
	private String srcFolder;
	private ArrayList<PageDescriptor> pages = new ArrayList<PageDescriptor>();
}
