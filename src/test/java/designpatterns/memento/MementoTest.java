package designpatterns.memento;

public class MementoTest {
	
	public static void main(String[] args) {
		//init
		MemoRole role = new MemoRole("发电机", 0, 1);
		//admin
		MemoManager manager = new MemoManager();
		//show 
		role.getCurrentState();
		
		//save 
		manager.setMemento(role.createMemo());
		//change
		role.setDeviceName("拖拉机");
		role.setStateLevel(2);
		role.setUseTime(200);
		//show 
		role.getCurrentState();
		
		//recover
		role.setMemento(manager.getMemento());
		//show 
		role.getCurrentState();
	}
}
