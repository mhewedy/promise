package promise;

public interface Callbacks {

	public static interface Success {
	}

	public static interface Function<T, U> extends Success {
		public U onSuccess(T value);
	}

	public static interface Action<T> extends Success {
		public void onSuccess(T value);
	}

	public static interface Failure {
		public void onFailure(Throwable exception);
	}
}
