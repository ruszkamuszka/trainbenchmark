package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class TypeQLConnectedSegments extends TypeQLMainQuery<TypeQLConnectedSegmentsMatch>{
	public TypeQLConnectedSegments(final TypeQLDriver driver) {
		super(RailwayQuery.CONNECTEDSEGMENTS, driver);
	}
	boolean found = false;

	public Map<String, Object> connectedSegments() throws Exception {
		//String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\ConnectedSegments.tql";
		//byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		//String query = new String(fileBytes, StandardCharsets.UTF_8);
		String query = "match"
		+ "$sensor isa Sensor, has id $sensorID;"
		+ "$segment1 isa Segment, has id $segment1ID;"
		+ "$segment2 isa Segment, has id $segment2ID;"
		+ "$segment3 isa Segment, has id $segment3ID;"
		+ "$segment4 isa Segment, has id $segment4ID;"
		+ "$segment5 isa Segment, has id $segment5ID;"
		+ "$segment6 isa Segment, has id $segment6ID;"
		+ "not { $segment1 is $segment2; };"
		+ "not { $segment1 is $segment3; };"
		+ "not { $segment1 is $segment4; };"
		+ "not { $segment1 is $segment5; };"
		+ "not { $segment1 is $segment6; };"
		+ "not { $segment2 is $segment3; };"
		+ "not { $segment2 is $segment4; };"
		+ "not { $segment2 is $segment5; };"
		+ "not { $segment2 is $segment6; };"
		+ "not { $segment3 is $segment4; };"
		+ "not { $segment3 is $segment5; };"
		+ "not { $segment3 is $segment6; };"
		+ "not { $segment4 is $segment5; };"
		+ "not { $segment4 is $segment6; };"
		+ "not { $segment5 is $segment6; };"
		+ "($segment1, $sensor) isa monitoredBy;"
		+ "($segment2, $sensor) isa monitoredBy;"
		+ "($segment3, $sensor) isa monitoredBy;"
		+ "($segment4, $sensor) isa monitoredBy;"
		+ "($segment5, $sensor) isa monitoredBy;"
		+ "($segment6, $sensor) isa monitoredBy;"
		+ "($segment1, $segment2) isa connectsTo;"
		+ "($segment2, $segment3) isa connectsTo;"
		+ "($segment3, $segment4) isa connectsTo;"
		+ "($segment4, $segment5) isa connectsTo;"
		+ "($segment5, $segment6) isa connectsTo;"
		+ "get"
		+ "$sensorID, $segment1ID, $segment2ID, $segment3ID, $segment4ID, $segment5ID, $segment6ID;";

		Map<String, Object> matchMap = new HashMap<>();
		driver.transaction(t -> {
			//System.out.println("Executing TypeQL Query: ConnectedSegments");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
					{
					 	matchMap.put(QueryConstants.VAR_SENSOR , result.get("sensorID").asAttribute().getValue().asLong());
						matchMap.put(QueryConstants.VAR_SEGMENT1 , result.get("segment1ID").asAttribute().getValue().asLong());
						matchMap.put(QueryConstants.VAR_SEGMENT2 , result.get("segment2ID").asAttribute().getValue().asLong());
						matchMap.put(QueryConstants.VAR_SEGMENT3 , result.get("segment3ID").asAttribute().getValue().asLong());
						matchMap.put(QueryConstants.VAR_SEGMENT4 , result.get("segment4ID").asAttribute().getValue().asLong());
						matchMap.put(QueryConstants.VAR_SEGMENT5 , result.get("segment5ID").asAttribute().getValue().asLong());
						matchMap.put(QueryConstants.VAR_SEGMENT6 , result.get("segment6ID").asAttribute().getValue().asLong());
					}
			);
		}, "READ");
		found = matchMap.size() == 0 ? false : true;
		return matchMap;
	}

	@Override
	public Collection<TypeQLConnectedSegmentsMatch> evaluate() throws Exception {
			final Collection<TypeQLConnectedSegmentsMatch> matches = new ArrayList<>();
			Map<String, Object> matchMap = connectedSegments();
			if(found){
				matches.add(new TypeQLConnectedSegmentsMatch(matchMap));
			}
			//System.out.println("ConnectedSegments size: " +matches.size());
			return matches;
	}

}
