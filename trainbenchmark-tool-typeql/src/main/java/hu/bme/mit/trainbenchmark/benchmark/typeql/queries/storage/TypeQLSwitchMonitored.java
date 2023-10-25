package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchMonitoredMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLSwitchMonitored extends TypeQLMainQuery<TypeQLSwitchMonitoredMatch>{
	public TypeQLSwitchMonitored(final TypeQLDriver driver) {
		super(RailwayQuery.SWITCHMONITORED, driver);
	}

	boolean found = false;
	public Map<String, Object> switchMonitored() throws Exception{
		//String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\SwitchMonitored.tql";
		//byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		//String query = new String(fileBytes, StandardCharsets.UTF_8);
		String query = "match"
		+ "$switch isa Switch, has id $switchID;"
		+ "not{ (Sensor: $sensor, TrackElement: $switch) isa monitoredBy; };"
		+ "get" + "$switchID;";

		Map<String, Object> matchMap = new HashMap<>();
		driver.transaction(t -> {
			//System.out.println("Executing TypeQL Query: SwitchMonitored");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SW, result.get("switchID").asAttribute().getValue().asLong());
				}
			);
		}, "READ");
		found = matchMap.size() == 0 ? false : true;
		return matchMap;
	}

	@Override
	public Collection<TypeQLSwitchMonitoredMatch> evaluate() throws Exception {
		final Collection<TypeQLSwitchMonitoredMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = switchMonitored();
		if (found){
			matches.add(new TypeQLSwitchMonitoredMatch(matchMap));
		}
		//System.out.println("SwitchMonitored size: " +matches.size());
		return matches;
	}
}
