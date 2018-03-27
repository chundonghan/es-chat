package designpatterns.command;

public class NewCommand implements Command{
	private Receiver receiver;
	public NewCommand(Receiver receiver){
		this.receiver = receiver;
	}
	public NewCommand(){
		this.receiver = new Receiver();
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		receiver.action();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		receiver.cancelAction();
	}

}
