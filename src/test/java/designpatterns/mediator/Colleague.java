package designpatterns.mediator;
/**
 * 
 * 抽象同事类
 */
public abstract class Colleague {
	private Mediator mediator;

	public Colleague(Mediator mediator) {
		this.mediator = mediator;
	}

	public Mediator getMediator() {
		return mediator;
	}
}
