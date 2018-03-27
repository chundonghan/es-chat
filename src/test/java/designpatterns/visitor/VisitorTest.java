package designpatterns.visitor;

import java.util.ArrayList;
import java.util.List;

public class VisitorTest {
	public static void main(String[] args) {
		List<Element> flowers = new ArrayList<>();
		for (int i = 0; i < 10; i++){
			flowers.add(FlowerGenerator.newFlower());
		}
		BeeVisitor visitor = new BeeVisitor();
		for (Element flower : flowers) {
			flower.accept(visitor);
		}
		visitor.totalHoney();
	}
}
