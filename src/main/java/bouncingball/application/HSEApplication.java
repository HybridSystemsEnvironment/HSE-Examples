
package bouncingball.application;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;

/**
 * An application that prepares, runs, and processes an environment. This class
 * does not need to be changed.
 */
public class HSEApplication {

	/**
	 * Method that will be executed when application is run
	 */
	public static void main(String args[]) {

		// load console settings
		ConfigurationLoader.loadConsoleSettings();
		// initialize environment
		HSEnvironment environment = new HSEnvironment();
		// load environment settings
		ConfigurationLoader.loadEnvironmentSettings(environment);
		// load environment content
		ContentLoader.loadEnvironmentContent(environment);
		// run environment
		environment.run();
		// process results
		PostProcessor.processEnvironmentData(environment);

	}

}