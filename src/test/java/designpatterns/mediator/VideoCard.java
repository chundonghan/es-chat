package designpatterns.mediator;

public class VideoCard extends Colleague {

	public VideoCard(Mediator mediator) {
		super(mediator);
	}

	public void showData(String data) {
		System.out.println("your are watching " + data);
	}
}
