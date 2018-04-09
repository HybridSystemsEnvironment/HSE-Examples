package template;

import edu.ucsc.cross.hse.core.environment.ExecutionParameters;
import edu.ucsc.cross.hse.core.file.DataFormat;
import edu.ucsc.cross.hse.core.file.FileBrowser;
import edu.ucsc.cross.hse.core.file.HSEFile;
import edu.ucsc.cross.hse.core.logging.Console;

/**
 * This is an example of a class that can select the execution parameters a
 * number of different ways. Running this class as a java application will open
 * a browser to choose a path for a new XML file that can be configured
 * externally, allowing configurations without changing the code
 * 
 * @author beshort
 *
 */
public class ExecutionParametersConfig
{

	/**
	 * Select a execution parameters to use
	 * 
	 * @return ExecutionParameters selected set
	 */
	private static ExecutionParameters getSelectedExecutionParameters() throws Exception
	{
		/* uncomment for config A */
		return getExecutionParametersA();

		/* uncomment for config A */
		//return getExecutionParametersB();

		/* uncomment to load execution parameters from file using browser */
		//return HSEFile.loadObjectFromFile(FileBrowser.load(), ExecutionParameters.class);

		/* uncomment to load execution parameters from file using path */
		//return HSEFile.loadObjectFromFile(new File("path"), ExecutionParameters.class);
	}

	/**
	 * Initializes execution parameters with configuration A
	 * 
	 * @return ExecutionParameters
	 */
	public static ExecutionParameters getExecutionParametersA()
	{
		ExecutionParameters parameters = new ExecutionParameters();
		parameters.maximumJumps = 20;
		parameters.maximumTime = 20.0;
		parameters.dataPointInterval = .05;
		return parameters;
	}

	/**
	 * Initializes execution parameters with configuration B
	 * 
	 * @return ExecutionParameters
	 */
	public static ExecutionParameters getExecutionParametersB()
	{
		ExecutionParameters parameters = new ExecutionParameters();
		parameters.maximumJumps = 100;
		parameters.maximumTime = 50.0;
		parameters.dataPointInterval = .1;
		return parameters;
	}

	/**
	 * Application to create a new execution parameters file for external
	 * configuration
	 */
	public static void main(String args[])
	{
		createNewExecutionParametersFile();
	}

	///////// Internal Methods ///////// 

	/**
	 * Create a new console setting file
	 */
	public static void createNewExecutionParametersFile()
	{
		ExecutionParameters console = new ExecutionParameters();
		HSEFile file = new HSEFile(console);
		file.saveToFile(FileBrowser.save(), DataFormat.XML);
	}

	/**
	 * Attempt to configure console
	 * 
	 * @return true is configuration is successfull
	 */
	public static ExecutionParameters getExecutionParameters()
	{
		ExecutionParameters ExecutionParameters = new ExecutionParameters(); // fetch default
		try
		{
			ExecutionParameters = getSelectedExecutionParameters();
			return ExecutionParameters;
		} catch (Exception getFail)
		{
			Console.error("Unable to get execution parameters", getFail);
		}

		return new ExecutionParameters(); // return initialized flag
	}

}
