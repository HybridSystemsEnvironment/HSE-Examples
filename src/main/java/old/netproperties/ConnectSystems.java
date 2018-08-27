
package old.netproperties;

import com.be3short.obj.access.FieldFinder;

import edu.ucsc.cross.hse.core.environment.SystemSet;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.core.network.Connection;
import edu.ucsc.cross.hse.core.network.Network;

public class ConnectSystems {

	public static SystemSet getConnectionSystems(Network<?> network) {

		SystemSet systems = new SystemSet();
		for (Connection<?> conn : network.getTopology().edgeSet()) {
			for (Object property : conn.getProperties()) {
				if (FieldFinder.containsSuper(property, HybridSystem.class)) {
					try {
						HybridSystem<?> hs = (HybridSystem<?>) property;
						hs.getComponents().getParameters().add(conn);
						systems.add(hs);
					} catch (Exception badSystem) {
						badSystem.printStackTrace();
					}
				}
			}
		}
		return systems;
	}
}
