package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchMonitoredInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationInjectSwitchMonitored <TTypeQLDriver extends TypeQLDriver> extends TypeQLTransformation<TypeQLSwitchMonitoredInjectMatch, TTypeQLDriver> {
	public TypeQLTransformationInjectSwitchMonitored(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLSwitchMonitoredInjectMatch> matches) throws Exception {
		for(final TypeQLSwitchMonitoredInjectMatch match : matches){
			driver.transaction(t -> {
				String query = "match" +
					"    $switch isa Switch, has id " + match.getSw() + ";" +
					"    $sensor1 isa Sensor;" +
					"    $monitoredBy(Sensor: $sensor1, TrackElement: $switch) isa monitoredBy;" +
					"delete" +
					"    $monitoredBy isa monitoredBy;";

				//System.out.println("Executing TypeQL Delete: SwitchMonitoredInjectDelete");
				//System.out.println(query);
				t.query().delete(TypeQL.parseQuery(query).asDelete());
			}, "WRITE");
		}
	}
}
