package designpatterns.mediator;

public class CPU extends Colleague {
	private String videoData = "";
	private String audioData = "";

	public CPU(Mediator mediator) {
		super(mediator);
	}
	public String getVideoData(){
		return videoData;
	}
	public String getAudioData(){
		return audioData;
	}
	public void executeData(String data){
		String[] array = data.split(",");
		this.videoData = array[0];
		this.audioData = array[1];
		getMediator().changed(this);
	}
}
