package designpatterns.decorator.decoratordefine;

public class Client {
	public static void main(String[] args) {
		Component component = new ConcreteDecorator(new ConcreteComponent());
		component.operate();
		component = new ConcreteDecorator2(new ConcreteComponent());
		component.operate();
	}
}
