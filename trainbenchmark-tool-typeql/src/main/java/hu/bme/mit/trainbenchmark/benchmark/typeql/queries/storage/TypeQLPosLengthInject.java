package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vaticle.typeql.lang.TypeQL.var;

public class TypeQLPosLengthInject extends TypeQLMainQuery<TypeQLPosLengthInjectMatch>{
	public TypeQLPosLengthInject(TypeQLDriver driver) {
		super(RailwayQuery.POSLENGTH_INJECT, driver);
	}

	public Stream<ConceptMap> posLengthInject() throws Exception{
		driver.read("...");

		TypeQLMatch.Filtered query = TypeQL.match(
			var("segment").isa("Segment").has("id", var("segmentID"))
		).get("segmentID");

		Stream<ConceptMap> results = driver.getTransaction().query().match(query);
		//results.forEach(result -> System.out.println(result.get("sid").asThing().getIID()));
		//driver.finishTransaction();
		return results;
	}

	@Override
	public Collection<TypeQLPosLengthInjectMatch> evaluate() throws Exception {
		return posLengthInject().map(conceptMap -> {
			Object segmentID = conceptMap.get("segmentID").asAttribute().getValue();
			Map<String, Object> matchMap = new HashMap<>();
			matchMap.put(QueryConstants.VAR_SEGMENT, segmentID);
			return new TypeQLPosLengthInjectMatch(matchMap);
		}).collect(Collectors.toList());
	}
}
