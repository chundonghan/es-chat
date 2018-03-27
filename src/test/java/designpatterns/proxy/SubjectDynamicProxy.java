package designpatterns.proxy;

import java.lang.reflect.InvocationHandler;

public class SubjectDynamicProxy extends DynamicProxy{
	public static <T> T newProxyInstance(Interface iface){
		ClassLoader loader = iface.getClass().getClassLoader();
		Class<?>[] interfaces = iface.getClass().getInterfaces();
		InvocationHandler handler = new DynamicProxyHandler(iface);
		return newProxyInstance(loader, interfaces, handler);

	}

}
