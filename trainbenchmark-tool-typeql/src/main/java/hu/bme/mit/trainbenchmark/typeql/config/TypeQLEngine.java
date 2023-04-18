package hu.bme.mit.trainbenchmark.typeql.config;

public enum TypeQLEngine {

	BASIC("Basic");

	private String name;

	TypeQLEngine(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
