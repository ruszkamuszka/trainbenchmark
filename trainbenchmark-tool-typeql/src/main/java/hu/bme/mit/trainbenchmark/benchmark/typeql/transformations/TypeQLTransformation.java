package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations;

import hu.bme.mit.trainbenchmark.benchmark.operations.ModelTransformation;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLMatch;

public abstract class TypeQLTransformation <TQLMatch extends TypeQLMatch, TTypeQLDriver extends TypeQLDriver>
	extends ModelTransformation<TQLMatch, TTypeQLDriver> {

	protected TypeQLTransformation(final TTypeQLDriver driver) {
		super(driver);
	}
}
