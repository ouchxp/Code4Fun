package me.ouchxp.monads;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Monadic Try type. Represents a result type that could have succeeded with type T or failed with a Throwable. Originally was Exception but due to seeing
 * issues with eg play with checked Throwable, And also seeing that Scala deals with throwable, I made the decision to change it to use Throwable.
 *
 * @param <T>
 */

public abstract class Try<T> {
	
	protected Try() {
	}
	
	public static <U> Try<U> ofFailable(TrySupplier<U> f) {
		Objects.requireNonNull(f);
		
		try {
			U y = f.get();
			return Try.successful(y);
		} catch (Throwable t) {
			return Try.failure(t);
		}
	}
	
	/**
	 * Transform success or pass on failure. Takes an optional type parameter of the new type. You need to be specific about the new type if changing type
	 * <p>
	 * Try.ofFailable(() -> "1").<Integer>map((x) -> Integer.valueOf(x))
	 *
	 * @param f function to apply to successful value.
	 * @param <U> new type (optional)
	 * @return Success<U> or Failure<U>
	 */
	
	public abstract <U> Try<U> map(TryMapFunction<? super T, ? extends U> f);
	
	/**
	 * Transform success or pass on failure, taking a Try<U> as the result. Takes an optional type parameter of the new type. You need to be specific about the
	 * new type if changing type.
	 * <p>
	 * Try.ofFailable(() -> "1").<Integer>flatMap((x) -> Try.ofFailable(() ->Integer.valueOf(x))) returns Integer(1)
	 *
	 * @param f function to apply to successful value.
	 * @param <U> new type (optional)
	 * @return Success<U> or Failure<U>
	 */
	public abstract <U> Try<U> flatMap(TryMapFunction<? super T, Try<U>> f);
	
	/**
	 * Specifies a result to use in case of failure. Gives access to the exception which can be pattern matched on.
	 * <p>
	 * Try.ofFailable(() -> "not a number") .<Integer>flatMap((x) -> Try.ofFailable(() ->Integer.valueOf(x))) .recover((t) -> 1) returns Integer(1)
	 *
	 * @param f
	 * @return
	 */
	
	public abstract T recover(Function<? super Throwable, T> f);
	
	/**
	 * Try applying f(t) on the case of failure.
	 * 
	 * @param f
	 * @return a new Try in the case of failure, or the current Success.
	 */
	public abstract Try<T> recoverWith(TryMapFunction<? super Throwable, Try<T>> f);
	
	/**
	 * Return a value in the case of a failure. This is similar to recover but does not expose the exception type.
	 *
	 * @param value
	 * @return
	 */
	public abstract T orElse(T value);
	
	/**
	 * Return another try in the case of failure. Like recoverWith but without exposing the exception.
	 *
	 * @param f
	 * @return
	 */
	public abstract Try<T> orElseTry(TrySupplier<T> f);
	
	/**
	 * Gets the value on Success or throws the cause of the failure.
	 *
	 * @return
	 * @throws Throwable
	 */
	public abstract T checkedGet() throws Throwable;
	
	/**
	 * Gets the value on Success or throws the cause of the failure wrapped in IllegalStateException
	 * If the original exception need to be thrown, use checkedGet().
	 * 
	 * @return
	 * @throws IllegalStateException
	 */
	public T get() {
		try {
			return checkedGet();
		} catch (Throwable t) {
			throw new IllegalStateException(t);
		}
	}
	
	public abstract boolean isSuccess();
	
	public abstract Try<T> onFailure(Consumer<Throwable> f);
	
	public abstract Throwable failed();
	
	public abstract Optional<T> toOptional();
	
	public <U> Try<U> transform(TryMapFunction<? super T, Try<U>> s, TryMapFunction<? super Throwable, Try<U>> f) {
		try {
			return isSuccess() ? s.apply(checkedGet()) : f.apply(failed());
		} catch (Throwable e) {
			return Try.failure(e);
		}
	}
	
	/**
	 * Factory method for failure.
	 *
	 * @param e
	 * @param <U>
	 * @return a new Failure
	 */
	
	public static <U> Try<U> failure(Throwable e) {
		return new Failure<>(e);
	}
	
	/**
	 * Factory method for success.
	 *
	 * @param x
	 * @param <U>
	 * @return a new Success
	 */
	public static <U> Try<U> successful(U x) {
		return new Success<U>(x);
	}
	
}

class Success<T> extends Try<T> {
	private final T value;
	
	public Success(T value) {
		this.value = value;
	}
	
	@Override
	public <U> Try<U> flatMap(TryMapFunction<? super T, Try<U>> f) {
		Objects.requireNonNull(f);
		try {
			return f.apply(value);
		} catch (Throwable t) {
			return Try.failure(t);
		}
	}
	
	@Override
	public T recover(Function<? super Throwable, T> f) {
		Objects.requireNonNull(f);
		return value;
	}
	
	@Override
	public Try<T> recoverWith(TryMapFunction<? super Throwable, Try<T>> f) {
		Objects.requireNonNull(f);
		return this;
	}
	
	@Override
	public T orElse(T value) {
		return this.value;
	}
	
	@Override
	public Try<T> orElseTry(TrySupplier<T> f) {
		Objects.requireNonNull(f);
		return this;
	}
	
	@Override
	public T checkedGet() throws Throwable {
		return value;
	}
	
	@Override
	public <U> Try<U> map(TryMapFunction<? super T, ? extends U> f) {
		Objects.requireNonNull(f);
		try {
			return new Success<U>(f.apply(value));
		} catch (Throwable t) {
			return Try.failure(t);
		}
	}
	
	@Override
	public boolean isSuccess() {
		return true;
	}
	
	@Override
	public Try<T> onFailure(Consumer<Throwable> f) {
		return this;
	}
	
	@Override
	public Throwable failed() {
		throw new java.lang.UnsupportedOperationException("Success.failed()");
	}
	
	@Override
	public Optional<T> toOptional() {
		return Optional.ofNullable(value);
	}
	
}

class Failure<T> extends Try<T> {
	private final Throwable e;
	
	Failure(Throwable e) {
		this.e = e;
	}
	
	@Override
	public <U> Try<U> map(TryMapFunction<? super T, ? extends U> f) {
		Objects.requireNonNull(f);
		return Try.failure(e);
	}
	
	@Override
	public <U> Try<U> flatMap(TryMapFunction<? super T, Try<U>> f) {
		Objects.requireNonNull(f);
		return Try.<U> failure(e);
	}
	
	@Override
	public T recover(Function<? super Throwable, T> f) {
		Objects.requireNonNull(f);
		return f.apply(e);
	}
	
	@Override
	public Try<T> recoverWith(TryMapFunction<? super Throwable, Try<T>> f) {
		Objects.requireNonNull(f);
		try {
			return f.apply(e);
		} catch (Throwable t) {
			return Try.failure(t);
		}
	}
	
	@Override
	public T orElse(T value) {
		return value;
	}
	
	@Override
	public Try<T> orElseTry(TrySupplier<T> f) {
		Objects.requireNonNull(f);
		return Try.ofFailable(f);
	}
	
	@Override
	public T checkedGet() throws Throwable {
		throw e;
	}
	
	@Override
	public boolean isSuccess() {
		return false;
	}
	
	@Override
	public Try<T> onFailure(Consumer<Throwable> f) {
		f.accept(e);
		return this;
	}
	
	@Override
	public Throwable failed() {
		return e;
	}
	
	@Override
	public Optional<T> toOptional() {
		return Optional.empty();
	}
}
