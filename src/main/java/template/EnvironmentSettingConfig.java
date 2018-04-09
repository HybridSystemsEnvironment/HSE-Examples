package template;

import edu.ucsc.cross.hse.core.environment.EnvironmentSettings;
import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.file.HSEFile;
import edu.ucsc.cross.hse.core.logging.Console;
import edu.ucsc.cross.hse.core.specification.DomainPriority;
import edu.ucsc.cross.hse.core.specification.IntegratorType;

/**
 * This is an example of a class that can select the environment settings a
 * number of different ways. Running this class as a java application will open
 * a browser to choose a path for a new XML file that can be configured
 * externally, allowing configurations without changing the code
 * 
 * @author beshort
 *
 */
public class EnvironmentSettingConfig
{

	/**
	 * Select a environment settings to use
	 * 
	 * @return EnvironmentSettings selected set
	 */
	private static EnvironmentSettings getSelectedEnvironmentSettings() throws Exception
	{
		/* uncomment for config A */
		return getEnvironmentSettingsA();

		/* uncomment for config A */
		//return getEnvironmentSettingsB();

		/* uncomment to load environment settings from file using browser */
		//return HSEFile.loadObjectFromFile(FileBrowser.load(), EnvironmentSettings.class);

		/* uncomment to load environment settings from file using path */
		//return HSEFile.loadObjectFromFile(new File("path"), EnvironmentSettings.class);
	}

	/**
	 * Initializes environment settings with configuration A
	 * 
	 * @return EnvironmentSettings
	 */
	public static EnvironmentSettings getEnvironmentSettingsA()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = .5E-8;
		settings.odeMaximumStepSize = .5E-4;
		settings.odeSolverAbsoluteTolerance = 1.0e-6;
		settings.odeRelativeTolerance = 1.0e-10;
		settings.eventHandlerMaximumCheckInterval = .1e-10;
		settings.eventHandlerConvergenceThreshold = .1e-5;
		settings.maxEventHandlerIterations = 25;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	/**
	 * Initializes environment settings with configuration B
	 * 
	 * @return EnvironmentSettings
	 */
	public static EnvironmentSettings getEnvironmentSettingsB()
	{
		EnvironmentSettings settings = new EnvironmentSettings();
		settings.odeMinimumStepSize = .5E-9;
		settings.odeMaximumStepSize = .5E-5;
		settings.odeSolverAbsoluteTolerance = 1.0e-7;
		settings.odeRelativeTolerance = 1.0e-12;
		settings.eventHandlerMaximumCheckInterval = .1e-16;
		settings.eventHandlerConvergenceThreshold = .1e-8;
		settings.maxEventHandlerIterations = 50;
		settings.integratorType = IntegratorType.DORMAND_PRINCE_853;
		settings.domainPriority = DomainPriority.JUMP;
		settings.storeNonPrimativeData = false;
		return settings;
	}

	/**
	 * Application to create a new environment settings file for external
	 * configuration
	 */
	public static void main(String args[])
	{
		createNewEnvironmentSettingsFile();
	}

	/**
	 * Create a new console setting file
	 */
	public static void createNewEnvironmentSettingsFile()
	{
		EnvironmentSettings console = new EnvironmentSettings();
		HSEFile file = new HSEFile(console);
		file.saveToFile(FileBrowser.save(), DataFormat.XML);
	}

	/**
	 * Attempt to configure console
	 * 
	 * @return true is configuration is successfull
	 */
	public static EnvironmentSettings getEnvironmentSettings()
	{
		EnvironmentSettings EnvironmentSettings = new EnvironmentSettings(); // fetch default
		try
		{
			EnvironmentSettings = getSelectedEnvironmentSettings();
			return EnvironmentSettings;
		} catch (Exception getFail)
		{
			Console.error("Unable to get environment settings", getFail);
		}

		return new EnvironmentSettings(); // return initialized flag
	}

}
