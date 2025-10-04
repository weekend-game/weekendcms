## WeekendCMS

**Content management system**

The program helps you create HTML website pages in a specific style - the style of my [website](https://weekend-game.github.io/). If you need something different, you'll need to modify the program.

### How to run the program

Download the repository to your computer. Everything you need for the program is located in the app folder. Navigate to the app folder and run the program by double-clicking the WeekendCMS.jar file or, if the program doesn't start, double-click the WeekendCMS.bat file. If the program doesn't start, download and install Java 11 or later and repeat the steps above.

### How to open a project in Eclipse

In Eclipse, select "Import..." from the "File" menu. In the window that opens, select "Existing projects into workspace." Navigate to the folder with the downloaded repository and click "Finish." The project will open in Eclipse. In the Package Explorer (on the left side of the screen), double-click the WeekendCMS.java file. The file will open for editing (in the center of the screen). Run the program by pressing Ctrl+F11 or using your preferred method for running programs in Eclipse.

### How to use the program

The program helps you create HTML website pages in a specific style - the style of my website. If you need something different, you'll need to modify the program.

To better understand the explanations below, launch the program and open the website you created with it. We see that the website consists of two types of pages: the [home page](https://weekend-game.github.io/) and [project pages](https://weekend-game.github.io/weekendcms.htm) like this one. All project pages are essentially the same.

**Homepage (index.htm)**

The project name is displayed in large font in the center. In this case, it's my profile name - Weekend Game. If you run the program, you'll see that the window that appears contains several fields. The "Project Name" field controls what's displayed in the center of the home page. At the top of the page is a link to the website for projects and a prompt to send emails to my address. It's written in English. The bottom section contains similar information, but in Russian. The CMS uses the "Website for projects in English", "Website for projects in Russian" and "Email" fields to specify all of these attributes.

We can see that above and below the profile name are menu bars with links to the pages of each project (repository). The top section contains links to the English pages, and the bottom section contains links to the Russian pages.

**Project (repository) pages**

The project (repository) page list is defined in the CMS under the "List of pages" section. These are the project pages. Each page has a "Language" setting (EN or RU). This setting determines whether the page link will be located at the top or bottom of the homepage. Links will be generated in the order they appear in the page list. You can change the order using the "Shift up" and "Shift down" buttons located to the right of the list. The "Name" field defines the page title in natural language, and the "File name" field defines the page file name.

When generating pages, not only the homepage is generated but also pages for each row in the page list. Each project page will contain a title in large font, taken from the "Name" field. There will also be a menu with links to all pages in English or Russian, depending on the page attribute. The page will have two menu bars: one at the top and one at the bottom. At the very bottom, you will see suggestions for visiting project sites and email addresses. The page content will be taken from a file with the same name, extracted from the folder specified in the "Folder where text files are located" field. For example, to generate the page weekdayinterpreter.htm, you must not only specify it in the page list but also place the weekdayinterpreter description in a file with the same name (weekendinterpreter.htm) and place it in the folder specified in the "Folder where text files are located" field. If such a file is not found, the page weekdayinterpreter.htm will still be created, but with only a header and footer.

**Website generation**

To generate website pages, click the "Generate pages" button. All generated HTML files will be saved in the folder specified in the "Folder for generated pages" field. My CMS does not generate a stylesheet. You should create one manually and place it in a convenient location. You should also take care of the placement of image files yourself.

**Important!** Do not manually edit the generated HTML files located in the pages folder, as they will be overwritten the next time they are generated.
How the program is written

### How the program is written

Details of the program's implementation can be found on the [project page](https://weekend-game.github.io/weekendcms.htm#ProgDescr).

### Results

Creating even this small CMS has significantly simplified my website management.

### It would be nice...

The [diary](https://weekend-game.github.io/diary.htm) is growing rapidly. I need to archive old posts.

I need to create a filter in the [diary](https://weekend-game.github.io/diary.htm) that will allow me to filter posts by specific projects.
