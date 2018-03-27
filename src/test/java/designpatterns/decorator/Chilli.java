package designpatterns.decorator;

public class Chilli extends Condiment {
	Humburger humburger;
	public Chilli(Humburger humburger) {
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
		return humburger.getPrice();
	}

}
