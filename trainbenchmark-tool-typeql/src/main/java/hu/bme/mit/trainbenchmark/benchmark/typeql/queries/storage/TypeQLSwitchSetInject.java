package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLSwitchSetInject extends TypeQLMainQuery<TypeQLSwitchSetInjectMatch>{
	public TypeQLSwitchSetInject(TypeQLDriver driver) {
		super(RailwayQuery.SWITCHSET_INJECT, driver);
	}

	public Map<String, Object> switchSetInject() throws Exception{
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\SwitchSetInject.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		Map<String, Object> matchMap = new HashMap<>();

		driver.transaction(t -> {
			String query = new String(fileBytes, StandardCharsets.UTF_8);

			System.out.println("Executing TypeQL Query: SwitchSetInject");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SW , result.get("switchID").asAttribute().asLong().getValue());
					matchMap.put(QueryConstants.VAR_CURRENTPOSITION , result.get("currentposition").asAttribute().asString().getValue());
				}
			);
		}, "READ");
		System.out.println("SwitchSetInject size: " +matchMap.size());
		return matchMap;
	}

	@Override
	public Collection<TypeQLSwitchSetInjectMatch> evaluate() throws Exception {
		final Collection<TypeQLSwitchSetInjectMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = switchSetInject();
		matches.add(new TypeQLSwitchSetInjectMatch(matchMap));
		return matches;
	}
}
