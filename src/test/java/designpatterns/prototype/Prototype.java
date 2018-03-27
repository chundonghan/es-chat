package designpatterns.prototype;

public class Prototype implements Cloneable {

	@Override
	protected Prototype clone() throws CloneNotSupportedException {
		Prototype prototype = (Prototype) super.clone();
		return prototype;
	}
}
