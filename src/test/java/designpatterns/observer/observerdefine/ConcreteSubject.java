package designpatterns.observer.observerdefine;

public class ConcreteSubject extends Subject {
	public void doSomething() {
		System.out.println("do something");
		super.notifyObservers();
	}
}
