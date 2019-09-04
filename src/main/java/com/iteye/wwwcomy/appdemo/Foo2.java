package com.iteye.wwwcomy.appdemo;

public class Foo2 {

	static {
		System.out.print("a");
	}

	static class Bar implements Runnable {

		static {
			System.out.print("b");
		}

		Bar() {
			System.out.print("c");
		}

		@Override
		public void run() {
			System.out.print("d");
		}

	}

	public static void main(String[] args) throws Exception {
		System.out.print("e");
		Thread t1 = new Thread(new Bar());
		Thread t2 = new Thread(new Bar());
		t1.run();
		t2.start();
		System.out.print("f");
	}
}
