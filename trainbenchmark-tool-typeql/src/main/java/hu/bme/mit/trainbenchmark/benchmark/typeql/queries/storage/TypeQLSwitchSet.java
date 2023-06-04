package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLSwitchSet extends TypeQLMainQuery<TypeQLSwitchSetMatch>{
	public TypeQLSwitchSet(final TypeQLDriver driver) {
		super(RailwayQuery.SWITCHSET, driver);
	}

	boolean found = false;
	public Map<String, Object> switchSet() throws Exception {
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\SwitchSet.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		String query = new String(fileBytes, StandardCharsets.UTF_8);

		Map<String, Object> matchMap = new HashMap<>();
		driver.transaction(t -> {
			System.out.println("Executing TypeQL Query: SwitchSet");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SEMAPHORE, result.get("semaphoreID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_ROUTE, result.get("routeID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SWP, result.get("swpID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_SW, result.get("switchID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_POSITION, result.get("position").asAttribute().asString().getValue());
					matchMap.put(QueryConstants.VAR_CURRENTPOSITION, result.get("currentposition").asAttribute().asString().getValue());
				}
			);
		}, "READ");
		found = matchMap.size() == 0 ? false : true;
		return matchMap;
	}

	@Override
	public Collection<TypeQLSwitchSetMatch> evaluate() throws Exception {
		final Collection<TypeQLSwitchSetMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = switchSet();
		if(found){
			matches.add(new TypeQLSwitchSetMatch(matchMap));
		}
		System.out.println("SwitchSet size: " +matches.size());
		return matches;
	}
}
