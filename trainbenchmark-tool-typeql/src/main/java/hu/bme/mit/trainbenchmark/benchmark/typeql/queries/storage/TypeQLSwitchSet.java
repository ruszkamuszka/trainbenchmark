package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLSwitchSet extends TypeQLMainQuery<TypeQLSwitchSetMatch>{
	public TypeQLSwitchSet(final TypeQLDriver driver) {
		super(RailwayQuery.SWITCHSET, driver);
	}

	public Stream<ConceptMap> switchSet() throws Exception {
		driver.read("...");

		TypeQLMatch getQuery = TypeQL.match(
			var("semaphore").isa("Semaphore").has("id", var("semaphoreID")).has("signal", "GO"),
			var("signal").eq("GO"),
			var("route").isa("Route").has("id", var("routeID")).has("active", var("active")),
			var("switchPosition").isa("SwitchPosition").has("id", var("swP")).has("position", var("position")),
			var("switch").isa("Switch").has("id", var("sw")).has("currentPosition", var("currentPosition")),
			var().rel("Route", var("route")).rel("SwitchPosition", var("switchPosition")).isa("follows"),
			var("currentPosition").neq("position")
		).get("semaphore", "routeID", "swP", "sw", "position", "currentPosition");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		//results.forEach(result -> System.out.println(result.get("rq").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLSwitchSetMatch> evaluate() throws Exception {
		return switchSet().map(conceptMap -> {
			Object switchID = conceptMap.get("switchID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SW, switchID);
			return new TypeQLSwitchSetMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
