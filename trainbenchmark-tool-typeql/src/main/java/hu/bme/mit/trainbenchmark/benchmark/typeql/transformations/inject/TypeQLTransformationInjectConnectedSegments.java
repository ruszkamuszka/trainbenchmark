package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationInjectConnectedSegments<TTypeQLDriver extends TypeQLDriver> extends TypeQLTransformation<TypeQLConnectedSegmentsInjectMatch, TTypeQLDriver> {
	public TypeQLTransformationInjectConnectedSegments(final TTypeQLDriver driver) {
		super(driver);
	}
	long injectID = -1;
	@Override
	public void activate(final Collection<TypeQLConnectedSegmentsInjectMatch> matches) throws Exception {
		for(final TypeQLConnectedSegmentsInjectMatch match : matches){
			driver.transaction(t -> {
				String query = "match" +
					"    $segment1 isa Segment, has id "+ match.getSegment1() + ";" +
					"    $segment3 isa Segment, has id "+ match.getSegment3() + ";" +
					"    $connectsTo1($segment1, $segment3) isa connectsTo;"+
					"delete" +
					"    $connectsTo1 isa connectsTo;";

				System.out.println("Executing TypeQL Delete: ConnectedSegmentsInjectDelete");
				System.out.println(query);
				t.query().delete(TypeQL.parseQuery(query).asDelete());

				query = "match" +
					"    $sensor isa Sensor, has id "+ match.getSensor() + ";" +
					"    $segment1 isa Segment, has id "+ match.getSegment1() + ";" +
					"    $segment3 isa Segment, has id "+ match.getSegment3() + ";" +
					" insert " +
					"    $segment2 isa Segment, has id "+ injectID + ";" +
					"    $connectsTo2($segment1, $segment2) isa connectsTo;" +
					"    $connectsTo3($segment2, $segment3) isa connectsTo;" +
					"    $monitoredBy3($segment2, $sensor) isa monitoredBy;";

				System.out.println("Executing TypeQL Insert: ConnectedSegmentsInjectInsert");
				System.out.println(query);
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
		injectID--;
	}
}
