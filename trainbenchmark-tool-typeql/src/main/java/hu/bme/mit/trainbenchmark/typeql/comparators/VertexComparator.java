package hu.bme.mit.trainbenchmark.typeql.comparators;

import com.vaticle.typeql.lang.common.TypeQLToken;

import java.util.Comparator;

public class VertexComparator implements Comparator<TypeQLToken> {

	@Override
	public int compare(TypeQLToken o1, TypeQLToken o2) {
		final long id1 = getLongId(o1);
		final long id2 = getLongId(o2);
		return Long.compare(id1, id2);
	}

	private long getLongId(TypeQLToken o1) {
		Object o = o1.hashCode();
		if (o instanceof Long) {
			return (Long) o;
		}
		if (o instanceof Integer) {
			return Long.valueOf((Integer) o);
		}
		throw new IllegalStateException("ID should be int or long");
	}
}
