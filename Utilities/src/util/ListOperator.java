package util;

import java.util.Collection;
import java.util.List;

public class ListOperator<E> {
	Object data;
	Operator operator;

	ListOperator(E data, Operator operator) {
		this.data = data;
		this.operator = operator;
	}

	ListOperator(Collection<?extends E> data, Operator operator) {
		this.data = data;
		this.operator = operator;
	}

	enum Operator {
		ADD {
			@Override
			public boolean run(List list, Object data) {
				return list.add(data);
			}
		},
		REMOVE {
			@Override
			public boolean run(List list, Object data) {
				return list.remove(data);
			}
		},
		ADD_ALL {
			@Override
			public boolean run(List list, Object data) {
				return list.addAll((Collection) data);
			}
		},
		REMOVE_ALL {
			@Override
			public boolean run(List list, Object data) {
				return list.removeAll((Collection<?>) data);
			}
		};

		public abstract boolean run(List list, Object data);
	}

	public static <T> ListOperator<T> add(T data) {
		return new ListOperator<>(data, Operator.ADD);
	}

	public static <T> ListOperator<T> remove(T data) {
		return new ListOperator<>(data, Operator.REMOVE);
	}

	public static <T> ListOperator<T> addAll(Collection<? extends T> data) {
		return new ListOperator<>(data, Operator.ADD_ALL);
	}

	public static <T> ListOperator<T> removeAll(Collection<? extends T> data) {
		return new ListOperator<>(data, Operator.REMOVE_ALL);
	}

	public boolean run(List<E> list) {
		return operator.run(list, data);
	}
}
