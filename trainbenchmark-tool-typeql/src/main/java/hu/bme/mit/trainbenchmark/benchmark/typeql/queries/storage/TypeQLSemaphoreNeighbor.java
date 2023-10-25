package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSemaphoreNeighborMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLSemaphoreNeighbor extends TypeQLMainQuery<TypeQLSemaphoreNeighborMatch>{
	public TypeQLSemaphoreNeighbor(final TypeQLDriver driver) {
		super(RailwayQuery.SEMAPHORENEIGHBOR, driver);
	}

	boolean found = false;
	public Map<String, Object> semaphoreNeighbor() throws Exception{
		//String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\SemaphoreNeighbor.tql";
		//byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		//String query = new String(fileBytes, StandardCharsets.UTF_8);
		String query = "match"
		+ "$semaphore isa Semaphore, has id $semaphoreID;"
		+ "$sensor1 isa Sensor, has id $sensor01;"
		+ "$sensor2 isa Sensor, has id $sensor02;"
		+ "not { $sensor1 is $sensor2; };"
		+ "$te1 isa Segment, has id $te01;"
		+ "$te2 isa Segment, has id $te02;"
		+ "not { $te1 is $te2; };"
		+ "$route1 isa Route, has exit $exit, has id $route01;"
		+ "$route2 isa Route, has entry $entry, has id $route02;"
		+ "not { $route1 is $route2; };"
		+ "$exit = $semaphoreID;"
		+ "$entry != $semaphoreID;"
		+ "$semaphores($te1, $semaphore) isa semaphores;"
		+ "$requires1($route1, $sensor1) isa requires;"
		+ "$requires2($route2, $sensor2) isa requires;"
		+ "$monitoredBy1($te1, $sensor1) isa monitoredBy;"
		+ "$monitoredBy2($te2, $sensor2) isa monitoredBy;"
		+ "$connectedTo($te1, $te2) isa connectsTo;"
		+ "get $exit, $route01, $route02, $sensor01, $sensor02, $te01, $te02;"	;

		Map<String, Object> matchMap = new HashMap<>();
		driver.transaction(t -> {
			//System.out.println("Executing TypeQL Query: SemaphoreNeighbor");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SEMAPHORE, result.get("exit").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_ROUTE1, result.get("route01").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_ROUTE2, result.get("route02").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_SENSOR1, result.get("sensor01").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_SENSOR2, result.get("sensor02").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_TE1, result.get("te01").asAttribute().getValue().asLong());
					matchMap.put(QueryConstants.VAR_TE2, result.get("te02").asAttribute().getValue().asLong());
				}
			);
		}, "READ");
		found = matchMap.size() == 0 ? false : true;
		return matchMap;
	}

	@Override
	public Collection<TypeQLSemaphoreNeighborMatch> evaluate() throws Exception {
		final Collection<TypeQLSemaphoreNeighborMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = semaphoreNeighbor();
		if(found){
			matches.add(new TypeQLSemaphoreNeighborMatch(matchMap));
		}
		//System.out.println("SemaphoreNeighbor size: " +matches.size());
		return matches;
	}
}
