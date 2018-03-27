package designpatterns.interpreter;

import java.util.HashMap;
import java.util.Map;

//上下文（环境）角色，使用HashMap来存储变量对应的数值 

class Context {
	private Map valueMap = new HashMap();

	public void addValue(Variable x, int y) {
		Integer yi = new Integer(y);
		valueMap.put(x, yi);
	}

	public int LookupValue(Variable x) {
		int i = ((Integer) valueMap.get(x)).intValue();
		return i;
	}
}

// 抽象表达式角色，也可以用接口来实现

abstract class Expression {
	public abstract int interpret(Context con);
}

// 终结符表达式角色
class Constant extends Expression {
	private int i;

	public Constant(int i) {
		this.i = i;
	}

	public int interpret(Context con) {
		return i;
	}

	@Override
	public String toString() {
		return new Integer(i).toString();
	}
}

class Variable extends Expression {
	private String name;
	public Variable(String name){
		this.name = name;
	}
	public int interpret(Context con) {
		// this为调用interpret方法的Variable对象
		return con.LookupValue(this);
	}
	@Override
	public String toString() {
		return name;
	}
}

// 非终结符表达式角色
class Add extends Expression {
	private Expression left, right;

	public Add(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public int interpret(Context con) {
		return left.interpret(con) + right.interpret(con);
	}

	@Override
	public String toString() {
		return "(" + left.toString() + " + " + right.toString() + ")";
	}
}

class Subtract extends Expression {
	private Expression left, right;

	public Subtract(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public int interpret(Context con) {
		return left.interpret(con) - right.interpret(con);
	}
	@Override
	public String toString() {
		return "(" + left.toString() + " - " + right.toString() + ")";
	}
}

class Multiply extends Expression {
	private Expression left, right;

	public Multiply(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public int interpret(Context con) {
		return left.interpret(con) * right.interpret(con);
	}
	@Override
	public String toString() {
		return "(" + left.toString() + " * " + right.toString() + ")";
	}
}

class Division extends Expression {
	private Expression left, right;

	public Division(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	public int interpret(Context con) {
		try {
			return left.interpret(con) / right.interpret(con);
		} catch (ArithmeticException ae) {
			System.out.println("被除数为0！");
			return -11111;
		}
	}
	@Override
	public String toString() {
		return "(" + left.toString() + " / " + right.toString() + ")";
	}
}

// 测试程序，计算 (a*b)/(a-b+2)
public class InterpreterTest {
	private static Expression ex;
	private static Context con;

	public static void main(String[] args) {
		con = new Context();
		// 设置变量、常量
		Variable a = new Variable("a");
		Variable b = new Variable("b");
		Constant c = new Constant(2);
		// 为变量赋值
		con.addValue(a, 4);
		con.addValue(b, 7);
		// 运算，对句子的结构由我们自己来分析，构造
		ex = new Division(new Multiply(a, b), new Add(new Subtract(a, b), c));
		System.out.println(ex.toString() + ",运算结果为：" + ex.interpret(con));

	}

}
