import java.util.concurrent.Semaphore;

public abstract class Lock {
	public final static Semaphore SEMAPHORE = new Semaphore(1);
}
