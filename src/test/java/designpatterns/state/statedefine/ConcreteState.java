package designpatterns.state.statedefine;

public class ConcreteState extends State {

	@Override
	public void handle1() {
		
	}

	@Override
	public void handle2() {
		super.context.setCurrentState(Context.STATE2);
		super.context.handle2();
	}

}
