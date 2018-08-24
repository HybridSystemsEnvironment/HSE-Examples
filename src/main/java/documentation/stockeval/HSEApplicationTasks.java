
package documentation.stockeval;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;

public interface HSEApplicationTasks {

	public void configureSettings(HSEnvironment environment);

	public void loadContents(HSEnvironment environment);

	public void executeSimulation(HSEnvironment environment);

	public void processData(HSEnvironment environment);
}
