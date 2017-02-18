package spwrap.async;

import java.util.concurrent.Callable;

import spwrap.async.Callbacks.Action;
import spwrap.async.Callbacks.Failure;

final class ActionWrapper<T> implements Runnable {

	private Callable<T> wrapped;
	private Action<T> success;
	private Failure failure;

	public ActionWrapper(Callable<T> wrapped, Action<T> success, Failure failure) {
		super();
		this.wrapped = wrapped;
		this.success = success;
		this.failure = failure;
	}

	public void run() {
		try {
			T result = wrapped.call();
			if (success != null) {
				success.onSuccess(result);
			} else {
				Promise.log.info("no success callback was registered!");
			}
		} catch (Exception e) {
			if (failure != null) {
				failure.onFailure(e);
			} else {
				Promise.log.error("no failure callback was registered!: " + e.getMessage(), e);
			}
		}
	}
}