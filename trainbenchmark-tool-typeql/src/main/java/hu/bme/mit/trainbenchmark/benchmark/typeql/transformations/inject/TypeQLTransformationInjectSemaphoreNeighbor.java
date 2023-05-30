package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSemaphoreNeighborInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationInjectSemaphoreNeighbor <TTypeQLDriver extends TypeQLDriver> extends TypeQLTransformation<TypeQLSemaphoreNeighborInjectMatch, TTypeQLDriver> {
	public TypeQLTransformationInjectSemaphoreNeighbor(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLSemaphoreNeighborInjectMatch> matches) throws Exception {
		for(final TypeQLSemaphoreNeighborInjectMatch match : matches){
			driver.transaction(t -> {
				String query = "match" +
					"    $semaphore isa Semaphore, has id " + match.getSemaphore() + ";" +
					"    $route isa Route, has id " + match.getRoute() + ", has entry $entry;" +
					"delete" +
					"    $route has $entry;";

				System.out.println("Executing TypeQL Delete: SemaphoreNeighborInjectDelete");
				t.query().delete(TypeQL.parseQuery(query).asDelete());
				//TODO Is this necessary?
				query = "match" +
					"    $route isa Route, has id " + match.getRoute() + ", has entry $entry;" +
					"insert" +
					"    $route has entry 0;";

				System.out.println("Executing TypeQL Insert: SemaphoreNeighborInjectInsert");
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
	}
}
