package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationInjectRouteSensor <TTypeQLDriver extends TypeQLDriver> extends TypeQLTransformation<TypeQLRouteSensorInjectMatch, TTypeQLDriver> {
	public TypeQLTransformationInjectRouteSensor(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLRouteSensorInjectMatch> matches) throws Exception {
		for(final TypeQLRouteSensorInjectMatch match : matches){
			driver.transaction(t -> {
				String query = "match" +
					"    $route isa Route, has id " + match.getRoute() + ";" +
					"    $sensor isa Sensor, has id " + match.getSensor() + ";" +
					"    $require(Route: $route, Sensor: $sensor) isa requires;" +
					"delete" +
					"    $require isa requires;";

				System.out.println("Executing TypeQL Delete: RouteSensorInjectDelete");
				System.out.println(query);
				t.query().delete(TypeQL.parseQuery(query).asDelete());
			}, "WRITE");
		}
	}
}
