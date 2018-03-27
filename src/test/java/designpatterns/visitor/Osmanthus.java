package designpatterns.visitor;
/**
 * 桂花
 *
 */
public class Osmanthus implements Element{

	@Override
	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}

}
