package hu.bme.mit.trainbenchmark.typeql.comparators;

import com.vaticle.typeql.lang.common.TypeQLToken;
import hu.bme.mit.trainbenchmark.benchmark.matches.comparators.BaseMatchComparator;
import hu.bme.mit.trainbenchmark.typeql.matches.TypeQLMatch;

public class TypeQLMatchComparator extends BaseMatchComparator<TypeQLMatch, TypeQLToken> {
	public TypeQLMatchComparator() {
		super(new VertexComparator());
	}

	@Override
	public int compare(TypeQLMatch o1, TypeQLMatch o2) {
		return 0;
	}
}
