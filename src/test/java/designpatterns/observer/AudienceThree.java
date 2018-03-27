package designpatterns.observer;

public class AudienceThree implements  Watcher{

	@Override
	public void update() {
		System.out.println(this.getClass().getSimpleName()+" is barking!");
		
	}

}
