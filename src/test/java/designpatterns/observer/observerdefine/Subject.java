package designpatterns.observer.observerdefine;

import java.util.NoSuchElementException;
import java.util.Vector;

public abstract class Subject {
	private Vector<Observer> observers = new Vector<>();

	public void addObserver(Observer o) {
		this.observers.addElement(o);
	}

	public void delObserver(Observer o) {
		if (this.observers.contains(o)) {
			this.observers.removeElement(o);
		} else {
			throw new NoSuchElementException("无此观察者！");
		}
	}

	public void notifyObservers() {
		if (!this.observers.isEmpty()) {
			for (Observer o : this.observers) {
				o.update();
			}
		}
	}
	
}
