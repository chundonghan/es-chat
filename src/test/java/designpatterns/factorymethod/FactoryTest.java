package designpatterns.factorymethod;

public class FactoryTest {
	public static void main(String[] args) {
		Creator creator = new ConcreteCreator();
		Product p = creator.newInstance(ConcreteProduct.class);
		System.out.println(p);
		 p = creator.newInstance(ConcreteProduct.class);
		System.out.println(p);
	}
}
