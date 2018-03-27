package designpatterns.decorator;

public class ChickenBurger extends Humburger {

	public ChickenBurger() {
		name = "鸡腿堡";
	}
	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return 10;
	}

}
