package designpatterns.singleton;

public class SingletonFactory {
	private SingletonFactory() {

	}

	private static SingletonFactory sf;

	private int i;

	public static SingletonFactory getSingletonFactory() {
		synchronized (SingletonFactory.class) {
			if (sf == null) {
				sf = new SingletonFactory();
			}
			return sf;
		}
	}

	public void increaseI() {
		i++;
	}

	public int getI() {
		return i;
	}

	public static void main(String[] args) {
		SingletonFactory sf = SingletonFactory.getSingletonFactory();
		sf.increaseI();
		System.out.println(sf.getI());
		SingletonFactory sf1 = SingletonFactory.getSingletonFactory();
		sf1.increaseI();
		System.out.println(sf1.getI());
		System.out.println(sf==sf1);
	}
}
