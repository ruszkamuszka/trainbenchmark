package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLConnectedSegmentsInject extends TypeQLMainQuery<TypeQLConnectedSegmentsInjectMatch>{
	public TypeQLConnectedSegmentsInject(TypeQLDriver driver) {
		super(RailwayQuery.CONNECTEDSEGMENTS_INJECT, driver);
	}

	public Map<String, Object> connectedSegmentsInject() throws Exception {
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\ConnectedSegmentsInject.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		Map<String, Object> matchMap = new HashMap<>();

		transaction(t -> {
			String query = new String(fileBytes, StandardCharsets.UTF_8);

			System.out.println("Executing TypeQL Query: " + query);
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SENSOR , result.get("sensorID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SEGMENT1 , result.get("segment1ID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SEGMENT3 , result.get("segment3ID").asAttribute().asLong().getValue());
				}
			);
		}, "READ");


		transaction(t -> {
			String query = "match" +
				"    $segment1 isa Segment, has id "+ matchMap.get(QueryConstants.VAR_SEGMENT1) + ";" +
				"    $segment3 isa Segment, has id "+ matchMap.get(QueryConstants.VAR_SEGMENT3) + ";" +
				"    $connectsTo1($segment1, $segment3) isa connectsTo;"+
				"delete" +
				"    $connectsTo1 isa connectsTo;";

			System.out.println("Executing TypeQL Query: " + query);
			t.query().delete(TypeQL.parseQuery(query).asDelete());

			query = "match" +
				"    $sensor isa Sensor, has id "+ matchMap.get(QueryConstants.VAR_SENSOR) + "$sensorID;" +
				"    $segment1 isa Segment, has id "+ matchMap.get(QueryConstants.VAR_SEGMENT1) + ";" +
				"    $segment3 isa Segment, has id "+ matchMap.get(QueryConstants.VAR_SEGMENT3) + ";" +
				"insert " +
				"    $segment2 isa Segment, has id 555555;" +
				"    $connectsTo2($segment1, $segment2) isa connectsTo;" +
				"    $connectsTo3($segment2, $segment3) isa connectsTo;" +
				"    $monitoredBy3($segment2, $sensor) isa monitoredBy;";

			System.out.println("Executing TypeQL Query: " + query);
			t.query().insert(TypeQL.parseQuery(query).asInsert());
		}, "WRITE");
		System.out.println("Match size: " +matchMap.size());
		return matchMap;
	}

	@Override
	public Collection<TypeQLConnectedSegmentsInjectMatch> evaluate() throws Exception {
		final Collection<TypeQLConnectedSegmentsInjectMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = connectedSegmentsInject();
		matches.add(new TypeQLConnectedSegmentsInjectMatch(matchMap));
		return matches;
	}
}
