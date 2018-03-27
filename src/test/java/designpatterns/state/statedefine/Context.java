package designpatterns.state.statedefine;

public class Context {
	public final static State STATE1 = new ConcreteState();
	public final static State STATE2 = new ConcreteState2();

	private State currentState;

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
		this.currentState.setContext(this);
	}

	// 行为委托
	public void handle1() {
		System.out.println(1);
		this.currentState.handle1();
	}

	public void handle2() {
		System.out.println(2);
		this.currentState.handle2();
	}
}
