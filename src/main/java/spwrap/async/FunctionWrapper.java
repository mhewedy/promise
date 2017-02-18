package spwrap.async;

import java.util.concurrent.Callable;

import spwrap.async.Callbacks.Failure;
import spwrap.async.Callbacks.Function;

final class FunctionWrapper<T, U> implements Callable<U> {

	private Callable<T> wrapped;
	private Function<T, U> success;
	private Failure failure;

	public FunctionWrapper(Callable<T> wrapped, Function<T, U> success, Failure failure) {
		super();
		this.wrapped = wrapped;
		this.success = success;
		this.failure = failure;
	}

	public U call() {
		U successResult = null;
		try {
			T result = wrapped.call();
			if (success != null) {
				successResult = success.onSuccess(result);
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
		return successResult;
	}
}