package designpatterns.templatemethod;

public abstract class AbstractTemp {
	//抽象类定义整个流程骨架
	public final void business(){
		stepOne();
		stepTwo();
		stepThree();
	}
	protected abstract void stepOne();
	protected abstract void stepTwo();
	protected abstract void stepThree();
}
