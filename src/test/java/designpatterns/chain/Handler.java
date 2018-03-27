package designpatterns.chain;

public abstract class Handler {
	/**
	 * 上级链
	 */
	protected Handler successor;

	/**
	 * 处理请求
	 */
	public abstract void handleRequest();

	public Handler getSuccessor() {
		return successor;
	}

	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}

}
