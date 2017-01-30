package nullEngine.object;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class ListOperator<E> {
	private Object data;
	private Operator operator;

	public interface OperatorCallback<F> {
		void add(F obj);

		void remove(F obj);
	}

	private ListOperator(E data, Operator operator) {
		this.data = data;
		this.operator = operator;
	}

	private ListOperator(Collection<? extends E> data, Operator operator) {
		this.data = data;
		this.operator = operator;
	}

	public enum Operator {
		ADD {
			@Override
			public <T> boolean run(List<T> list, Object data, OperatorCallback<T> callback) {
				if (list == null) {
					callback.add((T) data);
					return true;
				}
				if (callback != null) {
					callback.add((T) data);
				}
				return list.add((T) data);
			}
		},
		REMOVE {
			@Override
			public <T> boolean run(List<T> list, Object data, OperatorCallback<T> callback) {
				if (list == null) {
					callback.remove((T) data);
					return true;
				}
				if (callback != null) {
					callback.remove((T) data);
				}
				return list.remove(data);
			}
		},
		ADD_ALL {
			@Override
			public <T> boolean run(List<T> list, Object data, OperatorCallback<T> callback) {
				if (list == null) {
					for (T t : ((Collection<T>) data))
						callback.add(t);
					return true;
				}
				if (callback != null) {
					boolean modified = false;
					for (T t : ((Collection<T>) data))
						if (list.add(t)) {
							modified = true;
							callback.add(t);
						}
					return modified;
				}
				return list.addAll((Collection<T>) data);
			}
		},
		REMOVE_ALL {
			@Override
			public <T> boolean run(List<T> list, Object data, OperatorCallback<T> callback) {
				if (list == null) {
					for (T t : ((Collection<T>) data))
						callback.remove(t);
					return true;
				}
				if (callback != null) {
					boolean modified = false;
					for (T t : ((Collection<T>) data))
						if (list.remove(t)) {
							modified = true;
							callback.remove(t);
						}
					return modified;
				}
				return list.removeAll((Collection<T>) data);
			}
		};

		public abstract <T> boolean run(List<T> list, Object data, OperatorCallback<T> callback);
	}

	public static <T> ListOperator<T> add(T data) {
		return new ListOperator<>(data, Operator.ADD);
	}

	public static <T> ListOperator<T> remove(T data) {
		return new ListOperator<>(data, Operator.REMOVE);
	}

	public static <T> ListOperator<T> addAll(Collection<T> data) {
		return new ListOperator<>(data, Operator.ADD_ALL);
	}

	public static <T> ListOperator<T> removeAll(Collection<T> data) {
		return new ListOperator<>(data, Operator.REMOVE_ALL);
	}

	public boolean run(List<E> list, OperatorCallback<E> callback) {
		return operator.run(list, data, callback);
	}

	public Object getData() {
		return data;
	}

	void setData(Object data) {
		this.data = data;
	}

	public Operator getOperator() {
		return operator;
	}

	void setOperator(Operator operator) {
		this.operator = operator;
	}

	public static class ListOperatorPool<E> {
		private int maxListOperators;
		private final Queue<ListOperator<E>> listOperators;

		public synchronized int getMaxListOperators() {
			return maxListOperators;
		}

		public synchronized void setMaxListOperators(int maxListOperators) {
			this.maxListOperators = maxListOperators;
		}

		public ListOperatorPool(int maxListOperators) {
			this.maxListOperators = maxListOperators;
			listOperators = new ArrayDeque<>(maxListOperators);
		}

		public ListOperatorPool() {
			this(1000);
		}

		public synchronized void delete(ListOperator<E> operator) {
			if (listOperators.size() < maxListOperators)
				listOperators.add(operator);
		}

		public synchronized ListOperator<E> add(E data) {
			ListOperator operator;
			if ((operator = listOperators.poll()) == null)
				return ListOperator.add(data);
			operator.setData(data);
			operator.setOperator(Operator.ADD);
			return operator;
		}

		public synchronized ListOperator<E> remove(E data) {
			ListOperator operator;
			if ((operator = listOperators.poll()) == null)
				return ListOperator.remove(data);
			operator.setData(data);
			operator.setOperator(Operator.REMOVE);
			return operator;
		}

		public synchronized ListOperator<E> addAll(Collection<E> data) {
			ListOperator operator;
			if ((operator = listOperators.poll()) == null)
				return ListOperator.addAll(data);
			operator.setData(data);
			operator.setOperator(Operator.ADD_ALL);
			return operator;
		}

		public synchronized ListOperator<E> removeAll(Collection<E> data) {
			ListOperator operator;
			if ((operator = listOperators.poll()) == null)
				return ListOperator.removeAll(data);
			operator.setData(data);
			operator.setOperator(Operator.REMOVE_ALL);
			return operator;
		}
	}

	public static class ListOperatorQueue<E> {
		private ListOperatorPool<E> pool;
		private Queue<ListOperator<E>> queue = new ArrayDeque<>();

		public ListOperatorQueue(ListOperatorPool<E> pool) {
			this.pool = pool;
		}

		public ListOperatorQueue() {
			this(new ListOperatorPool<>());
		}

		public void add(E data) {
			queue.add(pool.add(data));
		}

		public void remove(E data) {
			queue.add(pool.remove(data));
		}

		public void addAll(Collection<E> data) {
			queue.add(pool.addAll(data));
		}

		public void removeAll(Collection<E> data) {
			queue.add(pool.removeAll(data));
		}

		public boolean run(List<E> list, OperatorCallback<E> callback) {
			boolean modified = false;
			ListOperator<E> operator;
			while ((operator = queue.poll()) != null) {
				if (operator.run(list, callback)) {
					modified = true;
				}
				pool.delete(operator);
			}
			return modified;
		}
	}
}
