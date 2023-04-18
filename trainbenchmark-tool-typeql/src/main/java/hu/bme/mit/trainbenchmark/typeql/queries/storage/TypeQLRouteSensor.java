package hu.bme.mit.trainbenchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;
import hu.bme.mit.trainbenchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.typeql.matches.TypeQLRouteSensorMatch;

import java.util.Collection;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLRouteSensor extends TypeQLMainQuery<TypeQLRouteSensorMatch>{

	public TypeQLRouteSensor(final TypeQLDriver driver) {
		super(RailwayQuery.ROUTESENSOR, driver);
	}

	public Stream<ConceptMap> routeSensor() throws Exception {
		driver.read("...");

		TypeQLMatch getQuery = TypeQL.match(
			var("rq").rel("r").rel("s").isa("requires"),
			var("r").isa("Route").has("id", var("r-id")),
			var("s").isa("Sensor").has("id", var("s-id"))
		).get("rq");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		results.forEach(result -> System.out.println(result.get("rq").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLRouteSensorMatch> evaluate() throws Exception {
		return null;
	}
}
