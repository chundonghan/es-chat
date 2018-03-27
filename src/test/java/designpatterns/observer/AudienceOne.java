package designpatterns.observer;

public class AudienceOne implements  Watcher{

	@Override
	public void update() {
		System.out.println(this.getClass().getSimpleName()+" is roaring!");
		
	}

}
