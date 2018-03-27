package designpatterns.strategy;

public class Context {
	private Strategy strategy;
	public Context(Strategy strategy) {
		this.strategy = strategy;
	}
	//封装策略方法
	public void somethingToDo(){
		strategy.doSomething();
	}
}
