package designpatterns.chain;

public class ConcreteHandler extends Handler {

	@Override
	public void handleRequest() {
		/**
         * 判断是否有上级的责任对象
         * 如果有，就转发请求给上级的责任对象
         * 如果没有，则处理请求
         */
		if(getSuccessor()!=null){
			System.out.println("next level!");
			getSuccessor().handleRequest();
		}else{
			System.out.println("handle");
		}
	}
	//审批经费

}
