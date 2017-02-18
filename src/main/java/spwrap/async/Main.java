package spwrap.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import spwrap.async.Callbacks.Failure;
import spwrap.async.Callbacks.Function;

public class Main {

	public static void main(String[] args) {
		ExecutorService exeuctor = Executors.newFixedThreadPool(10);

		/*
		Promise.of(() -> "10: " + Thread.currentThread(), exeuctor)
				.then((Action<String>) value -> System.out.println(value));
		*/
		Promise.of(() -> "10x", exeuctor)
				.then((Function<String, Integer>) value -> Integer.parseInt(value)
						, (Failure) e -> System.out.println(e.getMessage()))
				.then((Failure) e -> System.out.println(e.getMessage()))
//				.then((Function<Integer, Double>) value -> (double) value)
//				.then((Action<Double>) value -> System.out.println(value))
				;

		System.out.println("END OF MAIN: " + Thread.currentThread());

		exeuctor.shutdown();
	}

	static void sleep2s() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
