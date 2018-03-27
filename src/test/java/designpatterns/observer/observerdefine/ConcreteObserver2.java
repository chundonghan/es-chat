package designpatterns.observer.observerdefine;

public class ConcreteObserver2 implements Observer{

	@Override
	public void update() {
		System.out.println(this.getClass().getSimpleName()+" catch subject do something");
	}

}
