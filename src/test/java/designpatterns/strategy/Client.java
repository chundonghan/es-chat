package designpatterns.strategy;

public class Client {
	public static void main(String[] args) {
		Strategy s1 = new ConcreteStrategy();
		Context c =new Context(s1);
		c.somethingToDo();
		Strategy s2 = new ConcreteStrategy2();
		c =new Context(s2);
		c.somethingToDo();
	}
}
