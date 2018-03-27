package designpatterns.strategy;

public class ConcreteStrategy implements Strategy{

	@Override
	public void doSomething() {
		System.out.println("strategy 1 do");	
	}

}
