package util;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

public class ListOperatorPool<E> {
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
			return new ListOperator<>(data, ListOperator.Operator.ADD);
		operator.data = data;
		operator.operator = ListOperator.Operator.ADD;
		return operator;
	}

	public synchronized ListOperator<E> remove(E data) {
		ListOperator operator;
		if ((operator = listOperators.poll()) == null)
			return new ListOperator<>(data, ListOperator.Operator.REMOVE);
		operator.data = data;
		operator.operator = ListOperator.Operator.REMOVE;
		return operator;
	}

	public synchronized ListOperator<E> addAll(Collection<? extends E> data) {
		ListOperator operator;
		if ((operator = listOperators.poll()) == null)
			return new ListOperator<>(data, ListOperator.Operator.ADD_ALL);
		operator.data = data;
		operator.operator = ListOperator.Operator.ADD_ALL;
		return operator;
	}

	public synchronized ListOperator<E> removeAll(Collection<? extends E> data) {
		ListOperator operator;
		if ((operator = listOperators.poll()) == null)
			return new ListOperator<>(data, ListOperator.Operator.REMOVE_ALL);
		operator.data = data;
		operator.operator = ListOperator.Operator.REMOVE_ALL;
		return operator;
	}
}
