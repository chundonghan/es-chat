package designpatterns.observer.observerdefine;

public class Client {
	public static void main(String[] args) {
		ConcreteSubject subject = new ConcreteSubject();
		Observer o1 = new ConcreteObserver();
		Observer o2 = new ConcreteObserver2();
		subject.addObserver(o1);
		subject.delObserver(o2);
		subject.addObserver(o2);
		subject.doSomething();

		
	}
}
