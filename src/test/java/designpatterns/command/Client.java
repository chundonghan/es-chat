package designpatterns.command;

public class Client {
	public static void main(String[] args) {
		Receiver receiver = new Receiver();
		
		Command command = new NewCommand(receiver);
		//封闭Receiver
		Command command1 = new NewCommand();
		
		Invoker invoker = new Invoker();
		invoker.setCommand(command1);
		
		invoker.execCommand();
		invoker.undoCommand();
	}
	//Invoker下达命令Command给Receiver
}
