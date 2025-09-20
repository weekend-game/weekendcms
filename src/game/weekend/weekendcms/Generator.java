package game.weekend.weekendcms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class Generator {

	private final static String LF = System.lineSeparator();
	public static final Charset CHARSET = Charset.forName("UTF-8");
	private static PrintWriter out = null;
	private static SiteDescriptor sd = null;

	public static void main(String[] args) {
		Proper.read("WeekendCMS");

		SiteDescriptor siteDescriptor = new SiteDescriptor();
		siteDescriptor.readData();

		doIt(siteDescriptor);

		siteDescriptor.saveData();
		Proper.save();
		System.out.println("Done!");
	}

	public static void doIt(SiteDescriptor sd) {
		Generator.sd = sd;

		File homeFile = getDstFile("index.htm");
		makeHomePage(homeFile);

		for (SiteDescriptor.PageDescriptor page : sd.getPages()) {
			File file = getDstFile(page.getFileName());
			makeGeneralPage(file, page.getName(), page.getLang());
		}
	}

	public static File getDstFile(String fileName) {
		return new File(sd.getDstFolder() + "/" + fileName);
	}

	public static File getSrcFile(String fileName) {
		return new File(sd.getSrcFolder() + "/" + fileName);
	}

	public static void write(String s) {
		out.write(s);
	}

	public static void writeln(String s) {
		write(s + LF);
	}

	public static void writeInfo(String fileName) {
		try (BufferedReader reader = new BufferedReader(new FileReader(getSrcFile(fileName), Generator.CHARSET))) {
			String s;
			while ((s = reader.readLine()) != null) {
				writeln(s);
			}
		} catch (IOException ignored) {
		}
	}

	public static void makeHomePage(File file) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file, Generator.CHARSET))) {
			out = pw;

			start();

			writeln("");
			writeln("	<br>");
			writeln("	<table width=\"80%\" align=center border=0 cellpadding=0 cellspacing=0>");
			writeln("		<tr>");
			writeln("			<td align=right class=\"microtext\">");

			writeln("See my projects at <a href=\"" + sd.getGitSiteEN() + "\">" + sd.getGitSiteEN() + "</a>.<br>");
			writeln("I welcome any questions, comments and suggestions you may have<br>");
			writeln("regarding the " + sd.getProjectName() + ". Please send them directly to me at ");
			writeln("<a href=\"mailto:" + sd.getMail() + "\">" + sd.getMail() + "</a>.");

			writeln("			</td>");
			writeln("		</tr>");
			writeln("	</table>");
			writeln("");
			writeln("	<br><br><br><br><br><br>");
			writeln("");
			writeln("	<table width=\"80%\" align=center border=0 cellpadding=0 cellspacing=0>");
			makeMenu("", "EN", true);
			writeln("	</table>");
			writeln("");
			writeln("	<br><br>");
			writeln("");
			writeln("	<table width=\"80%\" align=center border=0 cellpadding=0 cellspacing=0>");
			writeln("		<tr>");
			writeln("			<td align=center class=\"title1\">");
			writeln("				" + sd.getProjectName());
			writeln("			</td>");
			writeln("		</tr>");
			writeln("	</table>");
			writeln("");
			writeln("	<br><br>");
			writeln("");
			writeln("	<table width=\"80%\" align=center border=0 cellpadding=0 cellspacing=0>");
			makeMenu("", "RU", true);
			writeln("	</table>");
			writeln("");
			writeln("	<br><br><br><br><br><br>");
			writeln("");
			writeln("	<table width=\"80%\" align=center border=0 cellpadding=0 cellspacing=0>");
			writeln("		<tr>");
			writeln("			<td class=\"microtext\">");

			writeln("Смотрите мои проекты на <a href=\"" + sd.getGitSiteRU() + "\">");
			writeln(sd.getGitSiteRU() + "</a>. Приветствую любые ваши<br>");
			writeln("вопросы, комментарии и предложения относительно " + sd.getProjectName() + ".<br>");
			writeln("Пишите мне по адресу <a href=\"mailto:" + sd.getMail() + "\">" + sd.getMail() + "</a>.");

			writeln("			</td>");
			writeln("		</tr>");
			writeln("	</table>");
			writeln("");
			writeln("</body>");
			writeln("");
			writeln("</html>");
			writeln("");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void makeGeneralPage(File file, String name, String lang) {
		try (PrintWriter pw = new PrintWriter(new FileWriter(file, Generator.CHARSET))) {
			out = pw;

			start();
			top(name, lang);
			info(file);
			bottom(name, lang);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void start() {
		writeln("<!DOCTYPE html>");
		writeln("<html>");
		writeln("");
		writeln("<head>");
		writeln("	<meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"utf-8\">");
		writeln("	<title>");
		writeln("		" + sd.getProjectName());
		writeln("	</title>");
		writeln("	<link rel=\"stylesheet\" type=\"text/css\" href=styles.css>");
		writeln("</head>");
		writeln("");
		writeln("<body>");
	}

	public static void top(String name, String lang) {
		writeln("	<table width=\"80%\" align=center border=0 cellpadding=0 cellspacing=0>");
		writeln("		<tr>");
		writeln("			<td align=center class=\"title1\">" + name);
		writeln("			</td>");
		writeln("		</tr>");
		writeln("		<tr>");
		writeln("			<td align=center class=\"menu\">");
		writeln("				<br>");
		writeln("			</td>");
		writeln("		</tr>");
		writeln("");
		makeMenu(name, lang, false);
		writeln("");
		writeln("		<tr>");
		writeln("			<td align=center class=\"menu\">");
		writeln("				<br>");
		writeln("				<hr size=1 noshade>");
		writeln("			</td>");
		writeln("		</tr>");
		writeln("	</table>");
		writeln("");
	}

	public static void bottom(String name, String lang) {
		writeln("	<table width=\"80%\" align=center border=0 cellpadding=0 cellspacing=0>");
		writeln("		<tr>");
		writeln("			<td align=center class=\"menu\">");
		writeln("				<hr size=1 noshade>");
		writeln("				<br>");
		writeln("			</td>");
		writeln("		</tr>");
		writeln("");
		makeMenu(name, lang, false);
		writeln("");
		writeln("		<tr>");
		writeln("			<td align=center class=\"menu\">");
		writeln("				<br>");
		if (lang.equalsIgnoreCase("RU")) {
			writeln("Смотрите мои проекты на <a href=\"" + sd.getGitSiteEN() + "\">" + sd.getGitSiteEN() + "</a>");
			writeln("(EN) или <a href=\"" + sd.getGitSiteRU() + "\">" + sd.getGitSiteRU() + "</a> (RU).<br>");
			writeln("Пишите мне по адресу <a href=\"mailto:" + sd.getMail() + "\">" + sd.getMail() + "</a><br>");
		} else {
			writeln("See my projects at <a href=\"" + sd.getGitSiteEN() + "\">" + sd.getGitSiteEN() + "</a>");
			writeln("(EN) or <a href=\"" + sd.getGitSiteRU() + "\">" + sd.getGitSiteRU() + "</a> (RU).<br>");
			writeln("Please write to me at <a href=\"mailto:" + sd.getMail() + "\">" + sd.getMail() + "</a><br>");
		}
		writeln("			</td>");
		writeln("		</tr>");
		writeln("	</table>");
		writeln("	<br>");
		writeln("");
		writeln("</body>");
		writeln("");
		writeln("</html>");
	}

	public static void info(File file) {
		writeInfo(file.getName());
	}

	public static void makeMenu(String name, String lang, boolean homeFree) {
		writeln("		<tr>");
		writeln("			<td align=center class=\"menu\">");

		if (!homeFree)
			if (lang.equalsIgnoreCase("RU"))
				writeln("				<a href=\"index.htm\">Главная страница</a>");
			else
				writeln("				<a href=\"index.htm\">Home</a>");

		int i = 1;
		for (SiteDescriptor.PageDescriptor page : sd.getPages()) {
			if (page.getLang().equalsIgnoreCase(lang)) {
				if (!homeFree || i++ != 1)
					writeln("				|");

				if (page.getName().equalsIgnoreCase(name))
					writeln("				" + page.getName());
				else
					writeln("				<a href=\"" + page.getFileName() + "\">" + page.getName() + "</a>");
			}
		}

		writeln("			</td>");
		writeln("		</tr>");
	}
}
