package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLPosLength extends TypeQLMainQuery<TypeQLRouteSensorMatch>{
	public TypeQLPosLength(final TypeQLDriver driver) {
		super(RailwayQuery.POSLENGTH, driver);
	}

	public Stream<ConceptMap> posLength() throws Exception {
		driver.read("...");

		TypeQLMatch getQuery = TypeQL.match(
			var("s").isa("Segment").has("length", TypeQL.gt(0))
		).get("s");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		results.forEach(result -> System.out.println(result.get("s").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLRouteSensorMatch> evaluate() throws Exception {
		return null;
	}
}
