package designpatterns.state;

public class OpenningState extends LiftState{
	@Override
	public void close() {
		// 状态修改
		super.context.setLiftState(Context.closeingState);
		// 动作委托为CloseState来执行
		super.context.getLiftState().close();
	}

	@Override
	public void open() {
		System.out.println("电梯门开启...");
	}

	@Override
	public void run() {
		// do nothing;
	}

	// 开门还不停止？
	public void stop() {
		// do nothing;
	}
}
