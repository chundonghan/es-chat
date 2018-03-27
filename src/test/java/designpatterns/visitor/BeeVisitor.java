package designpatterns.visitor;

public class BeeVisitor implements Visitor{
	private int totalChrysanthemumHoney = 0;
	private int totalOsmanthusHoney = 0;
	@Override
	public void visit(Chrysanthemum c) {
		// TODO Auto-generated method stub
		System.out.println("A bee is gathering "+c.getClass().getSimpleName()+"'s honey!");
		totalChrysanthemumHoney+=5;
	}

	@Override
	public void visit(Osmanthus o) {
		// TODO Auto-generated method stub
		System.out.println("A bee is gathering "+o.getClass().getSimpleName()+"'s honey!");
		totalOsmanthusHoney+=3;
	}
	
	public void totalHoney(){
		System.out.println("bees gathered ChrysanthemumHoney:"+totalChrysanthemumHoney+" OsmanthusHoney:"+totalOsmanthusHoney);
	}
	
}
