package hu.bme.mit.trainbenchmark.typeql.driver;

import hu.bme.mit.trainbenchmark.benchmark.driver.DriverFactory;

public class TypeQLDriverFactory extends DriverFactory<TypeQLDriver> {

	protected final String modelDir;
	public TypeQLDriverFactory(final String modelDir){
		super();
		this.modelDir=modelDir;
	}

	@Override
	public TypeQLDriver createInstance() throws Exception {
		return new TypeQLDriver();
	}
}
