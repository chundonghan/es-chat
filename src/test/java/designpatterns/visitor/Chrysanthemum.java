package designpatterns.visitor;
/**
 * 菊花
 *
 */
public class Chrysanthemum implements Element{

	@Override
	public void accept(Visitor v) {
		// TODO Auto-generated method stub
		v.visit(this);
	}
	
	
}
