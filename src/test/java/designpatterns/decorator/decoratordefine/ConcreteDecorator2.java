package designpatterns.decorator.decoratordefine;

public class ConcreteDecorator2 extends Decorator{

	public ConcreteDecorator2(Component component) {
		super(component);
		// TODO Auto-generated constructor stub
	}
	private void m2(){
		System.out.println(" m2");
	}
	@Override
	public void operate() {
		super.operate();
		m2();
	}
}
