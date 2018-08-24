
package documentation.stockeval;

import java.io.File;

import com.be3short.io.general.FileSystemInteractor;
import com.be3short.io.xml.XMLParser;

import edu.ucsc.cross.hse.core.environment.HSEnvironment;
import edu.ucsc.cross.hse.core.file.FileBrowser;

public abstract class HSEApplication implements HSEApplicationTasks {

	private HSEnvironment environment;

	/**
	 * @return the environment
	 */
	public HSEnvironment getEnvironment() {

		return environment;
	}

	/**
	 * @param environment
	 *            the environment to set
	 */
	public void setEnvironment(HSEnvironment environment) {

		this.environment = environment;
	}

	public HSEApplication() {

		environment = new HSEnvironment();
	}

	public void executeTasks() {

		this.configureSettings(environment);
		this.loadContents(environment);
		this.executeSimulation(environment);
		this.processData(environment);
	}

	public void exportPreparedApp() {

		this.configureSettings(environment);
		this.loadContents(environment);
		File output = FileBrowser.save();
		FileSystemInteractor.createOutputFile(output, XMLParser.serializeObject(this));
	}
}
