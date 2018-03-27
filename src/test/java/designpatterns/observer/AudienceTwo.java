package designpatterns.observer;

public class AudienceTwo implements  Watcher{

	@Override
	public void update() {
		System.out.println(this.getClass().getSimpleName()+" is screaming!");
		
	}

}
