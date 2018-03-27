package designpatterns.factorymethod;

public class ConcreteCreator extends Creator{

	@SuppressWarnings("unchecked")
	@Override
	public <T> T newInstance(Class<T> c) {
		T newInstance = null;
		try {
			 newInstance = (T) Class.forName(c.getName()).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newInstance;
	}

}
