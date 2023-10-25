package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchMonitoredInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLSwitchMonitoredInject extends TypeQLMainQuery<TypeQLSwitchMonitoredInjectMatch>{
	public TypeQLSwitchMonitoredInject(TypeQLDriver driver) {
		super(RailwayQuery.SWITCHMONITORED_INJECT, driver);
	}

	public Map<String, Object> switchMonitoredInject() throws Exception{
		//String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\SwitchMonitoredInject.tql";
		//byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		Map<String, Object> matchMap = new HashMap<>();

		driver.transaction(t -> {
			//String query = new String(fileBytes, StandardCharsets.UTF_8);
			String query = "match"
			+ "$switch isa Switch, has id $switchID;"
			+ "get"
			+	"$switchID;";

			//System.out.println("Executing TypeQL Query: SwitchMonitoredInject");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SW , result.get("switchID").asAttribute().getValue().asLong());
				}
			);
		}, "READ");
		return matchMap;
	}

	@Override
	public Collection<TypeQLSwitchMonitoredInjectMatch> evaluate() throws Exception {
		final Collection<TypeQLSwitchMonitoredInjectMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = switchMonitoredInject();
		matches.add(new TypeQLSwitchMonitoredInjectMatch(matchMap));
		return matches;
	}
}
