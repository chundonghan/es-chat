package designpatterns.mediator;

public class MainBoard implements Mediator {
	private CDDriver cdDriver = null;
	private CPU cpu = null;
	private VideoCard videoCard = null;
	private AudioCard audioCard = null;

	public void setCDDriver(CDDriver cdDriver) {
		this.cdDriver = cdDriver;
	}

	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	public void setVideoCard(VideoCard videoCard) {
		this.videoCard = videoCard;
	}

	public void setAudioCard(AudioCard audioCard) {
		this.audioCard = audioCard;
	}

	@Override
	public void changed(Colleague c) {
		if (c instanceof CDDriver) {
			this.operCDDriverReadData((CDDriver) c);
		} else if (c instanceof CPU) {
			this.operCPU((CPU) c);
		}
	}
	private void operCDDriverReadData(CDDriver cd){
		String data = cd.getData();
		cpu.executeData(data);
	}
	private void operCPU(CPU cpu){
		String videoData = cpu.getVideoData();
		String audioData = cpu.getAudioData();
		videoCard.showData(videoData);
		audioCard.showData(audioData);
	}
}
