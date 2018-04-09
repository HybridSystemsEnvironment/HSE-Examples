package template;

import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.file.HSEFile;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.modeling.SystemSet;

/**
 * This is an example of a class that can select the system set a number of
 * different ways. Running this class as a java application will open a browser
 * to choose a path for a new XML file that can be configured externally,
 * allowing configurations without changing the code
 * 
 * @author beshort
 *
 */
public class SystemSetConfig
{

	/**
	 * Select a system set to use
	 * 
	 * @return SystemSet selected set
	 */
	public static SystemSet getSelectedSystemSet() throws Exception
	{
		/* uncomment for config A */
		return getSystemSetA();

		/* uncomment for config A */
		//return getSystemSetB();

		/* uncomment to load system set from file using browser */
		//return HSEFile.loadObjectFromFile(FileBrowser.load(), SystemSet.class);

		/* uncomment to load system set from file using path */
		//return HSEFile.loadObjectFromFile(new File("path"), SystemSet.class);
	}

	/**
	 * Initializes system set with configuration A
	 * 
	 * @return SystemSet
	 */
	public static SystemSet getSystemSetA()
	{
		SystemSet console = new SystemSet();

		return console;
	}

	/**
	 * Initializes system set with configuration B
	 * 
	 * @return SystemSet
	 */
	public static SystemSet getSystemSetB()
	{
		SystemSet SystemSet = new SystemSet();

		return SystemSet;
	}

	/**
	 * Application to create a new system set file for external configuration
	 */
	public static void main(String args[])
	{
		createNewSystemSetFile();
	}

	///////// Internal Methods ///////// 

	/**
	 * Create a new console setting file
	 */
	public static void createNewSystemSetFile()
	{
		SystemSet console = new SystemSet();
		HSEFile file = new HSEFile(console);
		file.saveToFile(FileBrowser.save(), DataFormat.XML);
	}

	/**
	 * Attempt to configure console
	 * 
	 * @return true is configuration is successfull
	 */
	public static SystemSet getSystemSet()
	{
		SystemSet systemSet = new SystemSet(); // fetch default
		try
		{
			systemSet = getSelectedSystemSet();
			return systemSet;
		} catch (Exception getFail)
		{
			Console.error("Unable to get system set", getFail);
		}

		return new SystemSet(); // return initialized flag
	}

}
