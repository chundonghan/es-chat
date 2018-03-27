package designpatterns.chain;

public class ChainTest {
	public static void main(String[] args) {
		Handler first = new ConcreteHandler();
		Handler second = new ConcreteHandler();
		Handler third = new ConcreteHandler();
		Handler fourth = new ConcreteHandler();
		//组装链
		first.setSuccessor(second);
		second.setSuccessor(third);
		third.setSuccessor(fourth);
		first.handleRequest();
	}
}
