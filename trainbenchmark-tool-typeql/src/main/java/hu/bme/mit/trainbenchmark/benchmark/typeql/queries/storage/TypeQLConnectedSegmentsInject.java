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
import java.util.stream.Collectors;

public class TypeQLConnectedSegmentsInject extends TypeQLMainQuery<TypeQLConnectedSegmentsInjectMatch>{
	public TypeQLConnectedSegmentsInject(TypeQLDriver driver) {
		super(RailwayQuery.CONNECTEDSEGMENTS_INJECT, driver);
	}

	public Map<String, Object> connectedSegmentsInject() throws Exception {
		//String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\ConnectedSegmentsInject.tql";
		//byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		Map<String, Object> matchMap = new HashMap<>();

		driver.transaction(t -> {
			String query = "match"
			+ "$sensor isa Sensor, has id $sensorID;"
			+ "$segment1 isa Segment, has id $segment1ID;"
			+ "$segment3 isa Segment, has id $segment3ID;"
			+ "not { $segment1 is $segment3; };"
			+ "$monitoredBy1(TrackElement: $segment1, Sensor: $sensor) isa monitoredBy;"
			+ "$monitoredBy2(TrackElement: $segment3, Sensor: $sensor) isa monitoredBy;"
			+ "$connectsTo1(TrackElement: $segment1, TrackElement: $segment3) isa connectsTo;"
			+ "get"
				+ "$sensorID, $segment1ID, $segment3ID;";

			//System.out.println("Executing TypeQL Query: ConnectedSegmentsInject");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SENSOR, result.get("sensorID").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_SEGMENT1, result.get("segment1ID").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_SEGMENT3, result.get("segment3ID").asAttribute().getValue().asLong());
				}
			);
		}, "READ");
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
