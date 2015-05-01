package me.ouchxp.monads;

import static me.ouchxp.monads.Either.asLeft;
import static me.ouchxp.monads.Either.asRight;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

//TODO: rewrite comment here
/** Represents a value of one of two possible types (a disjoint union.)
 *  Instances of Either are either an instance of [[scala.util.Left]] or [[scala.util.Right]].
 *
 *  A common use of Either is as an alternative to [[scala.Option]] for dealing
 *  with possible missing values.  In this usage, [[scala.None]] is replaced
 *  with a [[scala.util.Left]] which can contain useful information.
 *  [[scala.util.Right]] takes the place of [[scala.Some]].  Convention dictates
 *  that Left is used for failure and Right is used for success.
 *
 *  For example, you could use `Either[String, Int]` to detect whether a
 *  received input is a String or an Int.
 *
 *  {{{
 *  val in = Console.readLine("Type Either a string or an Int: ")
 *  val result: Either[String,Int] = try {
 *      Right(in.toInt)
 *    } catch {
 *      case e: Exception =>
 *        Left(in)
 *  }
 *
 *  println( result match {
 *    case Right(x) => "You passed me the Int: " + x + ", which I will increment. " + x + " + 1 = " + (x+1)
 *    case Left(x) => "You passed me the String: " + x
 *  })
 *  }}}
 *
 *  A ''projection'' can be used to selectively operate on a value of type Either,
 *  depending on whether it is of type Left or Right. For example, to transform an
 *  Either using a function, in the case where it's a Left, one can first apply
 *  the `left` projection and invoke `map` on that projected Either. If a `right`
 *  projection is applied to that Left, the original Left is returned, unmodified.
 *
 *  {{{
 *  val l: Either[String, Int] = Left("flower")
 *  val r: Either[String, Int] = Right(12)
 *  l.left.map(_.size): Either[Int, Int] // Left(6)
 *  r.left.map(_.size): Either[Int, Int] // Right(12)
 *  l.right.map(_.toDouble): Either[String, Double] // Left("flower")
 *  r.right.map(_.toDouble): Either[String, Double] // Right(12.0)
 *  }}}
 *
 *  Like with other types which define a `map` method, the same can be achieved
 *  using a for-comprehension:
 *  {{{
 *  for (s <- l.left) yield s.size // Left(6)
 *  }}}
 *
 *  To support multiple projections as generators in for-comprehensions, the Either
 *  type also defines a `flatMap` method.
 *
 *  @author <a href="mailto:research@workingmouse.com">Tony Morris</a>, Workingmouse
 *  @version 1.0, 11/10/2008
 *  @since 2.7
 */

public abstract class Either<L, R> {
	protected Either() {
	}
	
	/**
	 * If the condition is satisfied, return the given `B` in `Right`, otherwise, return the given `A` in `Left`.
	 * 
	 * @param test
	 * @param supplyerRight
	 * @param supplyerLeft
	 * @return Either<A, B>
	 */
	public static <A, B> Either<A, B> cond(boolean test, Supplier<? extends B> supplyerRight, Supplier<? extends A> supplyerLeft) {
		return test ? new Right<A, B>(supplyerRight.get()) : new Left<A, B>(supplyerLeft.get());
	}
	
	/**
	 * Create a Left
	 * 
	 * @param value
	 * @return
	 */
	public static <K, U> Left<K, U> asLeft(K value) {
		return new Left<K, U>(value);
	}
	
	/**
	 * Create a Right
	 * 
	 * @param value
	 * @return
	 */
	public static <K, U> Right<K, U> asRight(U value) {
		return new Right<K, U>(value);
	}
	
	/**
	 * Create a new either instance that flip the left and right
	 * 
	 * @return
	 */
	public abstract Either<R, L> swap();
	
	//TODO: fold
	//TODO: joinRight
	//TODO: joinLeft
	
	public abstract boolean isLeft();
	
	public abstract boolean isRight();
	
	public LeftProjection<L, R> left() {
		return new LeftProjection<L, R>(this);
	}
	
	public RightProjection<L, R> right() {
		return new RightProjection<L, R>(this);
	}
	
}

final class Left<L, R> extends Either<L, R> {
	final L value;
	
	protected Left(L value) {
		this.value = value;
	}
	
	@Override
	public Either<R, L> swap() {
		return new Right<R, L>(value);
	}
	
	@Override
	public boolean isLeft() {
		return true;
	}
	
	@Override
	public boolean isRight() {
		return false;
	}
	
	@Override
	public String toString() {
		return "Left[" + value + "]";
	}
}

final class Right<L, R> extends Either<L, R> {
	final R value;
	
	protected Right(R value) {
		this.value = value;
	}
	
	@Override
	public Either<R, L> swap() {
		return new Left<R, L>(value);
	}
	
	@Override
	public boolean isLeft() {
		return false;
	}
	
	@Override
	public boolean isRight() {
		return true;
	}
	
	@Override
	public String toString() {
		return "Right[" + value + "]";
	}
}

final class LeftProjection<L, R> {
	private final Either<? extends L, ? extends R> e;
	
	protected LeftProjection(Either<? extends L, ? extends R> e) {
		this.e = e;
	}
	
	@SuppressWarnings("unchecked")
	private L value() {
		return ((Left<L, R>) e).value;
	}
	
	/**
	 * Returns the value from this `Left` or throws `java.util.NoSuchElementException` if this is a `Right`.
	 * 
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().get() // 12
	 * Either.asRight(12).left().get() // NoSuchElementException
	 * </code>
	 * </pre>
	 * 
	 * @throws java.util.NoSuchElementException if the projection is RightProjection
	 */
	public L get() {
		if (e.isLeft())
			return value();
		throw new NoSuchElementException("Either.right.value on Left");
	}
	
	/**
	 * Executes the given side-effecting function if this is a `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().forEach(System.out::println)  // prints "12"
	 * Either.asRight(12).left().forEach(System.out::println) // doesn't print
	 * </code>
	 * </pre>
	 * 
	 * @param f The side-effecting function to execute.
	 */
	public void forEach(Consumer<? super L> f) {
		if (e.isLeft())
			f.accept(value());
	}
	
	/**
	 * Returns the value from this `Left` or the given argument if this is a `Right`.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().orElseGet(() -> 17)  // 12
	 * Either.asRight(12).left().orElseGet(() -> 17) // 17
	 * </code>
	 * </pre>
	 *
	 */
	public L orElseGet(Supplier<? extends L> f) {
		return e.isLeft() ? value() : f.get();
	}
	
	/**
	 * Returns the value from this `Left` or the given argument if this is a `Right`.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().orElse(17)  // 12
	 * Either.asRight(12).left().orElse(17) // 17
	 * </code>
	 * </pre>
	 *
	 */
	public L orElse(L defaultValue) {
		return e.isLeft() ? value() : defaultValue;
	}
	
	/**
	 * Returns `true` if `Right` or returns the result of the application of the given function to the `Left` value.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().forall(x -> x > 10)  // true
	 * Either.asLeft(7).left().forall(x -> x > 10)   // false
	 * Either.asRight(12).left().forall(x -> x > 10) // true
	 * </code>
	 * </pre>
	 *
	 */
	public boolean forall(Predicate<? super L> p) {
		return e.isLeft() ? p.test(value()) : true;
	}
	
	/**
	 * Returns `false` if `Right` or returns the result of the application of the given function to the `Left` value.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().exists(x -> x > 10)  // true
	 * Either.asLeft(7).left().exists(x -> x > 10)   // false
	 * Either.asRight(12).left().exists(x -> x > 10) // false
	 * </code>
	 * </pre>
	 *
	 */
	public boolean exists(Predicate<? super L> p) {
		return e.isLeft() ? p.test(value()) : false;
	}
	
	/**
	 * Binds the given function across `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().flatMap(x -> Either.Left("scala"))  // Left[scala]
	 * Either.asRight(12).left().flatMap(x -> Either.Left("scala")) // Right(12)
	 * </code>
	 * </pre>
	 * 
	 * @param f The function to bind across `Left`.
	 */
	@SuppressWarnings("unchecked")
	public <LL> Either<LL, R> flatMap(Function<? super L, ? extends Either<? extends LL, ? extends R>> f) {
		return e.isLeft() ? (Either<LL, R>) f.apply(value()) : asRight(((Right<L, R>) e).value);
	}
	
	/**
	 * Maps the function argument through `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().map(x -> x + 2)                   // Left[14]
	 * Either.<Integer,Integer>asRight(12).left().map(x -> x + 2) // Right[12]
	 * </code>
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public <LL> Either<LL, R> map(Function<? super L, ? extends LL> f) {
		return e.isLeft() ? asLeft(f.apply(value())) : asRight(((Right<L, R>) e).value);
	}
	
	/**
	 * Returns `None` if this is a `Right` or if the given predicate `p` does not hold for the left value, otherwise, returns a `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asLeft(12).left().filter(x -> x > 10)  // Optional[Left(12)]
	 * Either.asLeft(7).left().filter(x -> x > 10)   // Optional.empty
	 * Either.asRight(12).left().filter(x -> x > 10) // Optional.empty
	 * </code>
	 * </pre>
	 */
	public Optional<Either<L, R>> filter(Predicate<? super L> p) {
		return e.isLeft() && p.test(value()) ? Optional.of(asLeft(value())) : Optional.empty();
	}
	
	/**
	 * Returns a immutable `List` containing the `Right` value if it exists or an empty immutable `List` if this is a `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().toList() // List(12)
	 * Either.asLeft(12).right().toList() // List()
	 * </code>
	 * </pre>
	 */
	public List<L> toList() {
		return e.isLeft() ? Collections.singletonList(value()) : Collections.emptyList();
	}
	
	/**
	 * Returns a `Some` containing the `Right` value if it exists or a `None` if this is a `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().toOptional()   // Optional[12]
	 * Either.asRight(null).right().toOptional() // Optional.empty
	 * Either.asLeft(12).right().toOptional()    // Optional.empty
	 * </code>
	 * </pre>
	 */
	public Optional<L> toOptional() {
		return e.isLeft() ? Optional.ofNullable(value()) : Optional.empty();
	}
}

final class RightProjection<L, R> {
	private final Either<? extends L, ? extends R> e;
	
	protected RightProjection(Either<? extends L, ? extends R> e) {
		this.e = e;
	}
	
	@SuppressWarnings("unchecked")
	private R value() {
		return ((Right<L, R>) e).value;
	}
	
	/**
	 * Returns the value from this `Right` or throws `java.util.NoSuchElementException` if this is a `Left`.
	 * 
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().get() // 12 }
	 * Either.asLeft(12).right().get() // NoSuchElementException }
	 * </code>
	 * </pre>
	 * 
	 * @return R
	 * @throws java.util.NoSuchElementException if the projection is `LeftProjection`.
	 */
	public R get() {
		if (e.isRight())
			return value();
		throw new NoSuchElementException("Either.left.value on Right");
	}
	
	/**
	 * Executes the given side-effecting function if this is a `Right`.
	 * 
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().forEach(System.out::println) // prints "12"
	 * Either.asLeft(12).right().forEach(System.out::println)  // doesn't print
	 * </code>
	 * </pre>
	 * 
	 * @param f
	 */
	public void forEach(Consumer<? super R> f) {
		if (e.isRight())
			f.accept(value());
	}
	
	/**
	 * Returns the value from this `Right` or the given argument if this is a `Left`.
	 * 
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().orElseGet(() -> 17) // 12
	 * Either.asLeft(12).right().orElseGet(() -> 17)  // 17
	 * </code>
	 * </pre>
	 */
	public R orElseGet(Supplier<? extends R> f) {
		return e.isRight() ? value() : f.get();
	}
	
	/**
	 * Returns the value from this `Right` or the given argument if this is a `Left`.
	 * 
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().orElse(17) // 12
	 * Either.asLeft(12).right().orElse(17)  // 17
	 * </code>
	 * </pre>
	 */
	public R orElse(R defaultValue) {
		return e.isRight() ? value() : defaultValue;
	}
	
	/**
	 * Returns `true` if `Left` or returns the result of the application of the given function to the `Right` value.
	 *
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().forall(x -> x > 10) // true 
	 * Either.asRight(7).right().forall(x -> x > 10)  // false 
	 * Either.asLeft(12).right().forall(x -> x > 10)  // true 
	 * </code>
	 * </pre>
	 */
	public boolean forall(Predicate<? super R> p) {
		return e.isRight() ? p.test(value()) : true;
	}
	
	/**
	 * Returns `false` if `Left` or returns the result of the application of the given function to the `Right` value.
	 * 
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().exists(x -> x > 10) // true 
	 * Either.asRight(7).right().exists(x -> x > 10)  // false 
	 * Either.asLeft(12).right().exists(x -> x > 10)  // false 
	 * </code>
	 * </pre>
	 */
	public boolean exists(Predicate<? super R> p) {
		return e.isRight() ? p.test(value()) : false;
	}
	
	/**
	 * Binds the given function across `Right`.
	 *
	 * @param f The function to bind across `Right`.
	 */
	@SuppressWarnings("unchecked")
	public <RR> Either<L, RR> flatMap(Function<? super R, ? extends Either<? extends L, ? extends RR>> f) {
		return e.isRight() ? (Either<L, RR>) f.apply(value()) : asLeft(((Left<L, R>) e).value);
	}
	
	/**
	 * The given function is applied if this is a `Right`.
	 *
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().map(x -> "flower") // Right[flower]
	 * Either.asLeft(12).right().map(x -> "flower")  // Left[12]
	 * </code>
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public <RR> Either<L, RR> map(Function<? super R, ? extends RR> f) {
		return e.isRight() ? asRight(f.apply(value())) : asLeft(((Left<L, R>) e).value);
	}
	
	/**
	 * Returns `None` if this is a `Left` or if the given predicate `p` does not hold for the right value, otherwise, returns a `Right`.
	 *
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().filter(x -> x > 10) // Optional[12]
	 * Either.asRight(7).right().filter(x -> x > 10)  // Optional.empty
	 * Either.asLeft(12).right().filter(x -> x > 10)  // Optional.empty
	 * </code>
	 * </pre>
	 */
	public Optional<Either<L, R>> filter(Predicate<? super R> p) {
		return e.isRight() && p.test(value()) ? Optional.of(asRight(value())) : Optional.empty();
	}
	
	/**
	 * Returns a immutable `List` containing the `Right` value if it exists or an empty immutable `List` if this is a `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().toList() // List[12]
	 * Either.asLeft(12).right().toList() // []
	 * </code>
	 * </pre>
	 */
	public List<R> toList() {
		return e.isRight() ? Collections.singletonList(value()) : Collections.emptyList();
	}
	
	/**
	 * Returns a `Some` containing the `Right` value if it exists or a `None` if this is a `Left`.
	 *
	 * <pre>
	 * <code>
	 * Either.asRight(12).right().toOptional()   // Optional[12]
	 * Either.asRight(null).right().toOptional() // Optional.empty
	 * Either.asLeft(12).right().toOptional()    // Optional.empty
	 * </code>
	 * </pre>
	 */
	public Optional<R> toOptional() {
		return e.isRight() ? Optional.ofNullable(value()) : Optional.empty();
	}
	
}