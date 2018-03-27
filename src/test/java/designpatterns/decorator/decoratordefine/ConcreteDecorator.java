package designpatterns.decorator.decoratordefine;

public class ConcreteDecorator extends Decorator{

	public ConcreteDecorator(Component component) {
		super(component);
		// TODO Auto-generated constructor stub
	}
	private void m1(){
		System.out.println(" m1");
	}
	@Override
	public void operate() {
		super.operate();
		m1();
	}
}
