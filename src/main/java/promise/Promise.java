package promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import promise.Callbacks.Action;
import promise.Callbacks.Failure;
import promise.Callbacks.Function;

public class Promise<T> {
	static Logger log = LoggerFactory.getLogger(Promise.class);

	private Callable<T> task;
	private ExecutorService executor;

	private Promise(final Callable<T> task, ExecutorService exeuctor) {
		this.task = task;
		this.executor = exeuctor;
	}

	public static <U> Promise<U> of(final Callable<U> task, ExecutorService exeuctor) {
		return new Promise<U>(task, exeuctor);
	}

	public <U> Promise<U> then(final Function<T, U> success, final Failure failure) {
		final Future<U> submit = executor.submit(new FunctionWrapper<T, U>(task, success, failure));

		return Promise.of(new Callable<U>() {
			public U call() throws Exception {
				return submit.get();
			}
		}, executor);
	}

	public void then(final Action<T> success, final Failure failure) {
		executor.submit(new ActionWrapper<T>(task, success, failure));
	}

	public <O> Promise<O> then(final Function<T, O> success) {
		return then(success, null);
	}

	public void then(final Action<T> success) {
		then(success, null);
	}

	public void then(final Failure failure) {
		then((Action<T>) null, failure);
	}
}
