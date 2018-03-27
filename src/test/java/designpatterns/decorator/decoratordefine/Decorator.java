package designpatterns.decorator.decoratordefine;

public abstract class Decorator extends Component{
	private Component component;
	public Decorator(Component component){
		this.component = component;
	}
	@Override
	public void operate() {
		// TODO Auto-generated method stub
		component.operate();
	}

}
