package designpatterns.flyweight;

import java.util.HashMap;

public class FlyweightFatory {
	//池
	private static HashMap<String,Flyweight> pool = new HashMap<>();
	//享元工厂
	public static Flyweight getFlyweight(String extrinsic){
		Flyweight flyweight = null;
		if(pool.containsKey(extrinsic)){
			flyweight = pool.get(extrinsic);
		}else{
			flyweight = new ConcreteFlyweight(extrinsic);
			pool.put(extrinsic, flyweight);
		}
		return flyweight;
	}
}
