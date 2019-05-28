package Immutable_Queue;
public interface Queue<T> {

	public Queue<T> enQueue(T t);

	public Queue<T> deQueue() throws Exception;

	public T head() throws Exception;

	public boolean isEmpty();
}