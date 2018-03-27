package designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

public class Singer implements Watched{
	private List<Watcher> audiences;
	public Singer(){
		audiences = new ArrayList<>();
	}
	@Override
	public void addWatcher(Watcher watcher) {
		audiences.add(watcher);
		
	}

	@Override
	public void removeWatcher(Watcher watcher) {
		if(audiences.contains(watcher)){
			audiences.remove(watcher);
		}
		
	}

	@Override
	public void notifyWatcher() {
		if(audiences.size()>0){
			for(Watcher watcher:audiences){
				watcher.update();
			}
		}
		
	}

}
