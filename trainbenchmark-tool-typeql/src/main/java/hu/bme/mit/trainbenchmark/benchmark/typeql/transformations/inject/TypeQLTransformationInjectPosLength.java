package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLPosLengthInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationInjectPosLength <TTypeQLDriver extends TypeQLDriver> extends TypeQLTransformation<TypeQLPosLengthInjectMatch, TTypeQLDriver> {
	public TypeQLTransformationInjectPosLength(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLPosLengthInjectMatch> matches) throws Exception {
		for(final TypeQLPosLengthInjectMatch match : matches){
			driver.transaction(t -> {
				String query = "match" +
					"    $segment isa Segment, has id "+ match.getSegment() + ", has length $length;" +
					"delete" +
					"    $segment has $length;";

				System.out.println("Executing TypeQL Delete: PosLengthInjectDelete");
				t.query().delete(TypeQL.parseQuery(query).asDelete());

				query = "match" +
					"    $segment isa Segment, has id "+ match.getSegment() + ";" +
					"insert" +
					"    $segment has length 0;";

				System.out.println("Executing TypeQL Insert: PosLengthInjectInsert");
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
	}
}
