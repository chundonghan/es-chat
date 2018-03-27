package designpatterns.observer;

public class ObserverTest {
	public static void main(String[] args) {
		Watched singer = new Singer();
		AudienceOne a1 = new AudienceOne();
		AudienceTwo a2 = new AudienceTwo();
		AudienceThree a3 = new AudienceThree();
		singer.addWatcher(a1);
		singer.addWatcher(a2);
		singer.addWatcher(a3);
		singer.removeWatcher(a2);
		singer.notifyWatcher();
	}
}
