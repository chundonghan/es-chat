package designpatterns.factorymethod;

public abstract class Creator {
	public abstract <T> T newInstance(Class<T> c);
}
