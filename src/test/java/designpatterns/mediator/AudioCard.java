package designpatterns.mediator;

public class AudioCard extends Colleague {

	public AudioCard(Mediator mediator) {
		super(mediator);
	}
	public void showData(String data){
		System.out.println("the audio is "+ data);
	}

}
