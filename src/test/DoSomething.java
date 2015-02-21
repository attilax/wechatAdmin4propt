package test;

public class DoSomething implements Runnable {
	private String name;

	public void run() {
		for (int i = 0; i < 6; i++) {
			System.out.println(name + ": " + i);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}