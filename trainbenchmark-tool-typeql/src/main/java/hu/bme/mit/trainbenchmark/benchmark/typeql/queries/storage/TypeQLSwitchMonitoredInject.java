package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchMonitoredInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLSwitchMonitoredInject extends TypeQLMainQuery<TypeQLSwitchMonitoredInjectMatch>{
	public TypeQLSwitchMonitoredInject(TypeQLDriver driver) {
		super(RailwayQuery.SWITCHMONITORED_INJECT, driver);
	}

	public Stream<ConceptMap> switchMonitoredInject() throws Exception{
		driver.read("...");

		TypeQLMatch.Filtered query = TypeQL.match(
			var("switch").isa("Switch").has("id", var("switchID"))
		).get("switchID");

		Stream<ConceptMap> results = driver.getTransaction().query().match(query);
		//results.forEach(result -> System.out.println(result.get("sid").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLSwitchMonitoredInjectMatch> evaluate() throws Exception {
		return switchMonitoredInject().map(conceptMap -> {
			Object switchID = conceptMap.get("switchID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SW, switchID);
			return new TypeQLSwitchMonitoredInjectMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
