package designpatterns.templatemethod;

public class TempTest {
	public static void main(String[] args) {
		AbstractTemp one = new PersonOne();
		one.business();
		
		AbstractTemp two = new PersonTwo();
		two.business();
	}
}
