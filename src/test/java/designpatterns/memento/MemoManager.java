package designpatterns.memento;
/**
 * 
 * 备忘录管理对象
 */
public class MemoManager {
	MemoBean memento;

	public MemoBean getMemento() {
		return memento;
	}

	public void setMemento(MemoBean memento) {
		this.memento = memento;
	}
	
}
