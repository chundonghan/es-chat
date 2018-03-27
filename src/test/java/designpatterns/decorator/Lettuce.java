package designpatterns.decorator;

public class Lettuce extends Condiment {
	Humburger humburger;
	public Lettuce(Humburger humburger) {
		this.humburger = humburger;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return humburger.getName() + "  add  " +getClass().getSimpleName().toLowerCase();
	}

	@Override
	public double getPrice() {
		// TODO Auto-generated method stub
		return humburger.getPrice() + 1.5;
	}

}
