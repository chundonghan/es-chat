package designpatterns.state.statedefine;

public class Client {
	public static void main(String[] args) {
		Context context  = new Context();
		context.setCurrentState(new ConcreteState());
		context.handle1();
		context.handle2();
	}
}
