
package netproperties;

import com.be3short.data.cloning.ObjectCloner;

import control.Signal;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;
import edu.ucsc.cross.hse.core.network.Connection;
import edu.ucsc.cross.hse.core.network.Network;
import edu.ucsc.cross.hse.core.variable.RandomVariable;
import timer.TimerState;

public class TransmittedSignal<V> extends HybridSystem<TimerState> implements Signal<V> {

	public Signal<V> source;

	public V heldVal;

	public Latency latency;

	public Network<?> connection;

	public TransmittedSignal(Signal<V> input, Network<?> connection) {

		super(new TimerState(0.0));
		this.heldVal = ObjectCloner.deepInstanceClone(input.get());
		this.connection = connection;
		this.latency = null;
		this.source = (input);
	}

	public Latency getLatency() {

		if (latency == null) {
			for (Connection<?> conn : connection.getTopology().edgeSet()) {
				if (conn.getProperties().contains(this)) {
					Latency lat = conn.getProperty(Latency.class);
					latency = lat;
				}
			}
			if (latency == null) {

				latency = new Latency(0.0, 0.0);

			}

		}
		return latency;
	}

	@Override
	public V get() {

		if (getLatency().maxLatency == 0.0) {
			return source.get();
		} else {
			return heldVal;
		}
	}

	/**
	 * @return the input
	 */
	public Signal<V> getInput() {

		return source;
	}

	@Override
	public boolean C(TimerState arg0) {

		return true && (getLatency().maxLatency > 0.0);
	}

	@Override
	public boolean D(TimerState arg0) {

		return arg0.value <= 0.0 && (getLatency().maxLatency > 0.0);
	}

	@Override
	public void F(TimerState x, TimerState x_dot) {

		x_dot.value = -1.0;
	}

	@Override
	public void G(TimerState x, TimerState x_plus) {

		heldVal = ObjectCloner.deepInstanceClone(source.get());
		x_plus.value = RandomVariable.generate(getLatency().minLatency, getLatency().maxLatency);
	}

}