package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLPosLength extends TypeQLMainQuery<TypeQLPosLengthMatch>{
	public TypeQLPosLength(final TypeQLDriver driver) {
		super(RailwayQuery.POSLENGTH, driver);
	}

	public Stream<ConceptMap> posLength() throws Exception {
		driver.read("...");

		TypeQLMatch.Filtered getQuery = TypeQL.match(
			var("s").isa("Segment").has("id", var("segmentID")).has("length", var("len")),
			var("len").gt(0)
		).get("segmentID", "len");


		Stream<ConceptMap> results = driver.getTransaction().query().match(getQuery);
		results.forEach(result -> System.out.println(result.get("s").asThing().getIID()));
		driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLPosLengthMatch> evaluate() throws Exception {
		return posLength().map(conceptMap -> {
			Object segmentID = conceptMap.get("segmentID").asAttribute().getValue();
			Object len = conceptMap.get("len").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SENSOR, segmentID);
			matchMap.put(QueryConstants.VAR_LENGTH, len);
			return new TypeQLPosLengthMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
