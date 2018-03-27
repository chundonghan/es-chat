package designpatterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxy {
	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h){
		return (T) Proxy.newProxyInstance(loader, interfaces, h);
	}
}
