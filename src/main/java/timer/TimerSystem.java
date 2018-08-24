
package timer;

import edu.ucsc.cross.hse.core.modeling.HybridSystem;

public class TimerSystem extends HybridSystem<TimerState> {

	public TimerSystem(TimerState state) {

		super(state);
	}

	@Override
	public boolean C(TimerState x) {

		return x.value >= 0.0;
	}

	@Override
	public boolean D(TimerState x) {

		return x.value <= 0.0;
	}

	/**
	 * The flow map is a mapping that determines the continuous dynamics of the
	 * state. This function defines how to compute the derivatives of the state
	 * elements
	 * 
	 * @param x
	 *            the current state of the system
	 * 
	 * @param x_dot
	 *            a copy of the state where the computed derivatives are to be
	 *            stored.
	 */
	@Override
	public void F(TimerState x, TimerState x_dot) {

		x_dot.value = -1.0;
	}

	/**
	 * The jump map G sets the timer value to 1 when a jump occurs
	 * 
	 * @param x
	 *            current timer state
	 * 
	 * @param x_plus
	 *            timer state after the jump has occurred
	 */
	@Override
	public void G(TimerState x, TimerState x_plus) {

		x_plus.value = 1.0;
	}

}
