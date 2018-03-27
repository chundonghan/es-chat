package designpatterns.mediator;

public class CDDriver extends Colleague{
	private String data = "";//读取数据
	public CDDriver(Mediator mediator) {
		super(mediator);
	}
	public String getData(){
		return data;
	}
	public void readCD(){
		this.data = "ポケモン,ゲットだぜーッ!";
		getMediator().changed(this);
	}
}
