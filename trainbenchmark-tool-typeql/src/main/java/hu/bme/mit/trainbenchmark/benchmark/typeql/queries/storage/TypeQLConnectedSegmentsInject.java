package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLConnectedSegmentsInject extends TypeQLMainQuery<TypeQLConnectedSegmentsInjectMatch>{
	public TypeQLConnectedSegmentsInject(TypeQLDriver driver) {
		super(RailwayQuery.CONNECTEDSEGMENTS_INJECT, driver);
	}

	public Stream<ConceptMap> connectedSegmentsInject() throws Exception {
		driver.read("...");

		TypeQLMatch.Filtered getQuery = TypeQL.match(
			var("sensor").isa("Sensor").has("id", var("sensorID")),
			var("switch").isa("Switch"),
			var("switchOther").isa("Switch"),
			var().rel("Sensor", var("sensor")).rel("Switch", var("switch")).isa("monitoredBy"),
			var().rel("Sensor", var("sensor")).rel("SwitchOther", var("switch")).isa("monitoredBy"),
			var("segment1").isa("Segment").has("id",var("segment1ID")),
			var("segment2").isa("Segment").has("id",var("segment2ID")),
			var().rel("Segment", var("segment1")).rel("Switch", var("switch")).isa("connectsTo"),
			var().rel("Segment", var("segment2")).rel("Switch", var("switchOther")).isa("connectsTo")
		).get("sensorID", "segment1ID", "segment2ID");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		//results.forEach(result -> System.out.println(result.get("s").asThing().getIID()));
		//driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLConnectedSegmentsInjectMatch> evaluate() throws Exception {
		return connectedSegmentsInject().map(conceptMap -> {
			Object sensorID = conceptMap.get("sensorID").asAttribute().getValue();
			Object segment1ID = conceptMap.get("segment1ID").asAttribute().getValue();
			Object segment2ID = conceptMap.get("segment2ID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SENSOR, sensorID);
			matchMap.put(QueryConstants.VAR_SEGMENT1, segment1ID);
			matchMap.put(QueryConstants.VAR_SEGMENT2, segment2ID);
			return new TypeQLConnectedSegmentsInjectMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
