package designpatterns.command;

public class Receiver {
	public void action(){
		System.out.println("action");
	}
	public void cancelAction(){
		System.out.println("cancel action");
	}
}
