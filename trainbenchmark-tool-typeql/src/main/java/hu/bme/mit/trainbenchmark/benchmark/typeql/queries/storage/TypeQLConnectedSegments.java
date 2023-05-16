package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLConnectedSegments extends TypeQLMainQuery<TypeQLConnectedSegmentsMatch>{
	public TypeQLConnectedSegments(final TypeQLDriver driver) {
		super(RailwayQuery.CONNECTEDSEGMENTS, driver);
	}

	public Stream<ConceptMap> connectedSegments() throws Exception {
		driver.read("...");

		TypeQLMatch.Filtered getQuery = TypeQL.match(
			var("sensor").isa("Sensor").has("id", var("sensorID")),
			var("switch").isa("Switch"),
			var().rel("Sensor", var("sensor")).rel("Switch", var("switch")).isa("monitoredBy"),
			var("segment1").isa("Segment").has("id",var("segment1ID")),
			var("segment2").isa("Segment").has("id",var("segment2ID")),
			var("segment3").isa("Segment").has("id",var("segment3ID")),
			var("segment4").isa("Segment").has("id",var("segment4ID")),
			var("segment5").isa("Segment").has("id",var("segment5ID")),
			var("segment6").isa("Segment").has("id",var("segment6ID")),
			var().rel("Segment", var("segment1")).rel("Switch", var("switch1")).isa("connectsTo"),
			var().rel("Segment", var("segment2")).rel("Switch", var("switch2")).isa("connectsTo"),
			var().rel("Segment", var("segment3")).rel("Switch", var("switch3")).isa("connectsTo"),
			var().rel("Segment", var("segment4")).rel("Switch", var("switch4")).isa("connectsTo"),
			var().rel("Segment", var("segment5")).rel("Switch", var("switch5")).isa("connectsTo"),
			var().rel("Segment", var("segment6")).rel("Switch", var("switch6")).isa("connectsTo")
			).get("sensorID", "segment1ID", "segment2ID", "segment3ID", "segment4ID", "segment5ID", "segment6ID");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		//results.forEach(result -> System.out.println(result.get("s").asThing().getIID()));
		//driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLConnectedSegmentsMatch> evaluate() throws Exception {
		return connectedSegments().map(conceptMap -> {
			Object sensorID = conceptMap.get("sensorID").asAttribute().getValue();
			Object segment1ID = conceptMap.get("segment1ID").asAttribute().getValue();
			Object segment2ID = conceptMap.get("segment2ID").asAttribute().getValue();
			Object segment3ID = conceptMap.get("segment3ID").asAttribute().getValue();
			Object segment4ID = conceptMap.get("segment4ID").asAttribute().getValue();
			Object segment5ID = conceptMap.get("segment5ID").asAttribute().getValue();
			Object segment6ID = conceptMap.get("segment6ID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SENSOR, sensorID);
			matchMap.put(QueryConstants.VAR_SEGMENT1, segment1ID);
			matchMap.put(QueryConstants.VAR_SEGMENT2, segment2ID);
			matchMap.put(QueryConstants.VAR_SEGMENT3, segment3ID);
			matchMap.put(QueryConstants.VAR_SEGMENT4, segment4ID);
			matchMap.put(QueryConstants.VAR_SEGMENT5, segment5ID);
			matchMap.put(QueryConstants.VAR_SEGMENT6, segment6ID);
			return new TypeQLConnectedSegmentsMatch(matchMap);
		}).collect(Collectors.toList());
	}

}
