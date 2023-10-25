package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLPosLength extends TypeQLMainQuery<TypeQLPosLengthMatch>{
	public TypeQLPosLength(final TypeQLDriver driver) {
		super(RailwayQuery.POSLENGTH, driver);
	}

	boolean found = false;

	public Map<String, Object> posLength() throws Exception {
		//String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\PosLength.tql";
		//byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		//String query = new String(fileBytes, StandardCharsets.UTF_8);
		String query = "match"
		+ "$segment isa Segment, has id $segmentID, has length $length;"
		+ "$length <= 0;"
		+ "get" + "$segmentID;";

		Map<String, Object> matchMap = new HashMap<>();
		driver.transaction(t -> {
			//System.out.println("Executing TypeQL Query: PosLength");
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SEGMENT, result.get("segmentID").asAttribute().getValue().asLong());
				}
			);
		}, "READ");
		found = matchMap.size() == 0 ? false : true;
		return matchMap;
	}

	@Override
	public Collection<TypeQLPosLengthMatch> evaluate() throws Exception {
		final Collection<TypeQLPosLengthMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = posLength();
		if(found){
			matches.add(new TypeQLPosLengthMatch(matchMap));
		}
		//System.out.println("PosLength size: " +matches.size());
		return matches;
	}
}
