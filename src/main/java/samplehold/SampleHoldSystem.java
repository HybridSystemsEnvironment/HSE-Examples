
package samplehold;

import com.be3short.data.cloning.ObjectCloner;

import control.Signal;
import edu.ucsc.cross.hse.core.modeling.HybridSystem;

public class SampleHoldSystem<X> extends HybridSystem<SampleHoldState<X>> implements Signal<X> {

	public Signal<X> input;

	public SampleHoldSystem(SampleHoldState<X> state, Signal<X> input) {

		super(state);

		this.input = input;
	}

	@Override
	public boolean C(SampleHoldState<X> x) {

		return x.timerValue >= 0.0;
	}

	@Override
	public boolean D(SampleHoldState<X> x) {

		return x.timerValue <= 0.0;
	}

	@Override
	public void F(SampleHoldState<X> x, SampleHoldState<X> x_dot) {

		x_dot.timerValue = -1.0;
	}

	@Override
	public void G(SampleHoldState<X> x, SampleHoldState<X> x_plus) {

		x_plus.timerValue = .4;
		x_plus.holdValue.value = (ObjectCloner.deepInstanceClone(input.get()));
	}

	@Override
	public X get() {

		return this.getComponents().getState().holdValue.getValue();// holdValue;
	}

}
