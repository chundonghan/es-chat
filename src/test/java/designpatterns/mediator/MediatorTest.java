package designpatterns.mediator;

public class MediatorTest {

	public static void main(String[] args) {
		MainBoard mediator = new MainBoard();
		CDDriver cdDriver = new CDDriver(mediator);
		CPU cpu = new CPU(mediator);
		VideoCard videoCard = new VideoCard(mediator);
		AudioCard audioCard = new AudioCard(mediator);
		mediator.setCDDriver(cdDriver);
		mediator.setCpu(cpu);
		mediator.setVideoCard(videoCard);
		mediator.setAudioCard(audioCard);
		
		cdDriver.readCD();
	}

}
