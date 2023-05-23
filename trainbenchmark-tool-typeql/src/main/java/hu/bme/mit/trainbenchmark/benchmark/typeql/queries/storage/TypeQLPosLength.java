package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TypeQLPosLength extends TypeQLMainQuery<TypeQLPosLengthMatch>{
	public TypeQLPosLength(final TypeQLDriver driver) {
		super(RailwayQuery.POSLENGTH, driver);
	}

	public Map<String, Object> posLength() throws Exception {
		String filePath = "C:\\NewTrainBenchmark\\trainbenchmark\\trainbenchmark-tool-typeql\\src\\main\\resources\\PosLength.tql";
		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		String query = new String(fileBytes, StandardCharsets.UTF_8);

		Map<String, Object> matchMap = new HashMap<>();
		transaction(t -> {
			System.out.println("Executing TypeQL Query: " + query);
			t.query().match(TypeQL.parseQuery(query).asMatch()).forEach(result ->
				{
					matchMap.put(QueryConstants.VAR_SEGMENT, result.get("segmentID").asAttribute().asLong().getValue());
				}
			);
		}, "READ");
		System.out.println("Match size: " +matchMap.size());
		return matchMap;
	}

	@Override
	public Collection<TypeQLPosLengthMatch> evaluate() throws Exception {
		final Collection<TypeQLPosLengthMatch> matches = new ArrayList<>();
		Map<String, Object> matchMap = posLength();
		matches.add(new TypeQLPosLengthMatch(matchMap));
		for (TypeQLPosLengthMatch match : matches) {
			System.out.println("SegmentID: "+match.getSegment()); // Print each match element
		}
		return matches;
	}
}
