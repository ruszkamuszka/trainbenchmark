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
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\ConnectedSegmentsInject.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		Map<String, Object> matchMap = new HashMap<>();

		driver.transaction(t -> {
			String query = new String(fileBytes, StandardCharsets.UTF_8);

			System.out.println("Executing TypeQL Query: ConnectedSegmentsInject");
			var result = t.query().match(TypeQL.parseQuery(query).asMatch()).collect(Collectors.toList());
			for (var element: result) {
				matchMap.put(QueryConstants.VAR_SENSOR , element.get("sensorID").asAttribute().asLong().getValue());
				matchMap.put(QueryConstants.VAR_SEGMENT1 , element.get("segment1ID").asAttribute().asLong().getValue());
				matchMap.put(QueryConstants.VAR_SEGMENT3 , element.get("segment3ID").asAttribute().asLong().getValue());
			}
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
