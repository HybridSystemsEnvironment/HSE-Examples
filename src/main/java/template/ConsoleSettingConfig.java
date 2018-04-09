package template;

import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.file.HSEFile;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.logging.ConsoleSettings;

/**
 * This is an example of a class that can select the console settings a number
 * of different ways. Running this class as a java application will open a
 * browser to choose a path for a new XML file that can be configured
 * externally, allowing modified settings without changing the code
 * 
 * @author beshort
 *
 */
public class ConsoleSettingConfig
{

	/**
	 * Get selected console settings
	 * 
	 * @return true is configuration is successfull
	 */
	public static ConsoleSettings getConsoleSettings() throws Exception
	{
		/* uncomment for config A */
		return getConsoleSettingsA();

		/* uncomment for config A */
		//return getConsoleSettingsB();

		/* uncomment to load console settings from file using browser */
		//return HSEFile.loadObjectFromFile(FileBrowser.load(), ConsoleSettings.class);

		/* uncomment to load console settings from file using path */
		//return HSEFile.loadObjectFromFile(new File("path"), ConsoleSettings.class);
	}

	/**
	 * Initializes console settings with configuration A
	 * 
	 * @return consoleSettings
	 */
	public static ConsoleSettings getConsoleSettingsA()
	{
		ConsoleSettings console = new ConsoleSettings();
		console.printStatusInterval = 10.0;
		console.printProgressIncrement = 10;
		console.printIntegratorExceptions = false;
		console.printInfo = true;
		console.printDebug = true;
		console.printWarning = true;
		console.printError = true;
		console.printLogToFile = true;
		console.terminateAtInput = true;
		return console;
	}

	/**
	 * Initializes console settings with configuration B
	 * 
	 * @return consoleSettings
	 */
	public static ConsoleSettings getConsoleSettingsB()
	{
		ConsoleSettings consoleSettings = new ConsoleSettings();
		consoleSettings.printStatusInterval = 2.0;
		consoleSettings.printProgressIncrement = 20;
		consoleSettings.printIntegratorExceptions = true;
		consoleSettings.printInfo = true;
		consoleSettings.printDebug = true;
		consoleSettings.printWarning = true;
		consoleSettings.printError = true;
		consoleSettings.printLogToFile = true;
		consoleSettings.terminateAtInput = true;
		return consoleSettings;
	}

	/**
	 * Application to create a new console settings file for external
	 * configuration
	 */
	public static void main(String args[])
	{
		createNewConsoleSettingsFile();
	}

	///////// Internal Methods ///////// 

	/**
	 * Create a new console setting file
	 */
	public static void createNewConsoleSettingsFile()
	{
		ConsoleSettings console = new ConsoleSettings();
		HSEFile file = new HSEFile(console);
		file.saveToFile(FileBrowser.save(), DataFormat.XML);
	}

	/**
	 * Attempt to configure console
	 */
	public static void configureConsole()
	{
		ConsoleSettings consoleSettings = Console.getSettings(); // fetch default
		try
		{
			consoleSettings = getConsoleSettings();
		} catch (Exception getFail)
		{
			Console.error("Unable to get console settings");
		}

		Console.setSettings(consoleSettings); // store specified settings

	}

}
