package template;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;

public class EnvironmentConfig
{

	public static void main(String args[])
	{
		HSEnvironment environment = getEnvironment();
		operateEnvironment(environment);
		processResults(environment);
	}

	public static void operateEnvironment(HSEnvironment environment)
	{
		environment.run();
	}

	public static void processResults(HSEnvironment environment)
	{
		environment.saveToFile(FileBrowser.save(), DataFormat.HSE);
	}

	/**
	 * Setup the environment
	 * 
	 * @return environment
	 */
	public static HSEnvironment getEnvironment()
	{
		HSEnvironment environment = new HSEnvironment();

		//* uncomment a line to select a configuration *\\

		// create new environment from selected configurations
		environment = HSEnvironment.create(SystemSetConfig.getSystemSet(),
		ExecutionParametersConfig.getExecutionParameters(), EnvironmentSettingConfig.getEnvironmentSettings());

		// load environment from file using file browser
		//environment = HSEnvironment.loadFromFile(FileBrowser.load());

		// load environment from file from specified path
		//environment = HSEnvironment.loadFromFile(new File("path"));

		return environment;
	}
}
