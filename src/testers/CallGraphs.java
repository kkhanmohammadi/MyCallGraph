package testers;

public class CallGraphs
{
	public static void main(String[] args) {
		doStuff();
	}
	
	public static void doStuff() {
		System.out.println("In foo...");
		int i=0;
		i++;
		new A().foo();
	}
}

class A
{
	public void foo() {

		bar();
	}
	
	public void bar() {
		System.out.println("In foo...");
	}
}