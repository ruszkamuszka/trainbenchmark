package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchMonitoredMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.not;
import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLSwitchMonitored extends TypeQLMainQuery<TypeQLSwitchMonitoredMatch>{
	public TypeQLSwitchMonitored(final TypeQLDriver driver) {
		super(RailwayQuery.SWITCHMONITORED, driver);
	}

	public Stream<ConceptMap> switchMonitored() throws Exception{
		driver.read("...");

		TypeQLMatch.Filtered query = TypeQL.match(
			var("switch").isa("Switch").has("id", var("switchID")),
			not(
				var()
					.rel("Sensor", var("sensor"))
					.rel("Switch", var("switch"))
					.isa("monitoredBy")
			)
		).get("switchID");

		Stream<ConceptMap> results = driver.getTransaction().query().match(query);
		//results.forEach(result -> System.out.println(result.get("sid").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLSwitchMonitoredMatch> evaluate() throws Exception {
		return switchMonitored().map(conceptMap -> {
			Object switchID = conceptMap.get("switchID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SW, switchID);
			return new TypeQLSwitchMonitoredMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
