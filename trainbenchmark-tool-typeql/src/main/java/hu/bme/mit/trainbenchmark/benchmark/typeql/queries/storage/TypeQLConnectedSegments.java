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

	public Map<String, Object> connectedSegments() throws Exception {
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\ConnectedSegments.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));


		String query = new String(fileBytes, StandardCharsets.UTF_8);
		Map<String, Object> matchMap = new HashMap<>();
		transaction(t -> {
			System.out.println("Executing TypeQL Query: " + query);
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
					{
					 	matchMap.put(QueryConstants.VAR_SENSOR , result.get("sensorID").asAttribute().asLong().getValue());
						matchMap.put(QueryConstants.VAR_SEGMENT1 , result.get("segment1ID").asAttribute().asLong().getValue());
						matchMap.put(QueryConstants.VAR_SEGMENT2 , result.get("segment2ID").asAttribute().asLong().getValue());
						matchMap.put(QueryConstants.VAR_SEGMENT3 , result.get("segment3ID").asAttribute().asLong().getValue());
						matchMap.put(QueryConstants.VAR_SEGMENT4 , result.get("segment4ID").asAttribute().asLong().getValue());
						matchMap.put(QueryConstants.VAR_SEGMENT5 , result.get("segment5ID").asAttribute().asLong().getValue());
						matchMap.put(QueryConstants.VAR_SEGMENT6 , result.get("segment6ID").asAttribute().asLong().getValue());
					}
			);
		}, "READ");
		System.out.println("Match size: " +matchMap.size());
		return matchMap;
	}

	@Override
	public Collection<TypeQLConnectedSegmentsMatch> evaluate() throws Exception {
			final Collection<TypeQLConnectedSegmentsMatch> matches = new ArrayList<>();
			Map<String, Object> matchMap = connectedSegments();
			matches.add(new TypeQLConnectedSegmentsMatch(matchMap));
		for (TypeQLConnectedSegmentsMatch match : matches) {
			System.out.println("SensorID: "+match.getSensor()); // Print each match element
		}
			return matches;
	}

}
