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
					"    $switchPosition isa SwitchPosition, has id $switchPositionID, has target $target, has position $position;" +
					"    $sensor isa Sensor, has id " + match.getSensor() + ";" +
					"    $switch isa Switch, has id $switchID;" +
					"    $follows(Route: $route, SwitchPosition: $switchPosition) isa follows;" +
					"    $monitoredBy(TrackElement: $switch, Sensor: $sensor) isa monitoredBy;" +
					"    $require(Route: $route, Sensor: $sensor) isa requires;" +
					"    $switchID=$target;" +
					"delete" +
					"    $require isa requires;";

				System.out.println("Executing TypeQL Delete: RouteSensorInjectDelete");
				t.query().delete(TypeQL.parseQuery(query).asDelete());
			}, "WRITE");
		}
	}
}
