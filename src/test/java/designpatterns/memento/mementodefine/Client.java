package designpatterns.memento.mementodefine;

public class Client {
	public static void main(String[] args) {
		Originator originator = new Originator();
		Caretaker caretaker = new Caretaker();
		originator.setState("on");
		//保存状态
		caretaker.setMemento(originator.createMemento());
		//改变状态
		originator.setState("off");
		System.out.println(originator.getState());
		//恢复状态
		originator.restoreMemento(caretaker.getMemento());
		System.out.println(originator.getState());
	}
}
