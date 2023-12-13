import hu.bme.mit.trainbenchmark.config.ExecutionConfig
import hu.bme.mit.trainbenchmark.generator.config.GeneratorConfigBase
import hu.bme.mit.trainbenchmark.generator.config.Scenario
import hu.bme.mit.trainbenchmark.generator.graph.neo4j.config.Neo4jGraphGeneratorConfigBuilder
import hu.bme.mit.trainbenchmark.generator.runner.GeneratorRunner
import hu.bme.mit.trainbenchmark.neo4j.config.Neo4jGraphFormat;

def ec = new ExecutionConfig(4000, 6000)
def minSize = 5
def maxSize = 5

def scenarios = [
	Scenario.BATCH,
	//Scenario.INJECT,
	//Scenario.REPAIR,
]

def formats = [
		//new EmfGeneratorConfigBuilder(),
		new Neo4jGraphGeneratorConfigBuilder().setGraphFormat(Neo4jGraphFormat.CSV),
		new Neo4jGraphGeneratorConfigBuilder().setGraphFormat(Neo4jGraphFormat.GRAPHML),
		//new TinkerGraphGeneratorConfigBuilder().setGraphFormat(TinkerGraphFormat.GRAPHML),
		//new RdfGeneratorConfigBuilder().setFormat(RdfFormat.TURTLE).setInferred(true),
		//new RdfGeneratorConfigBuilder().setFormat(RdfFormat.TURTLE).setInferred(false),
		//new SqlGeneratorConfigBuilder(),
		//new TypeQLGeneratorConfigBuilder(),
]

for (scenario in scenarios) {
	formats.each { generatorConfigBuilder ->
		try {
			for (def size = minSize; size <= maxSize; size *= 2) {
				println("Scenario: ${scenario}, size: ${size}")

				def configBase = new GeneratorConfigBase(scenario, size)
				def config = generatorConfigBuilder.setConfigBase(configBase).createConfig()

				def exitValue = GeneratorRunner.run(config, ec)
				if (exitValue != 0) {
					println "Timeout or error occurred, skipping models for larger sizes. Error code: ${exitValue}"
					break
				}
			}
		} catch (all) {
			println "Exception occurred during execution."
		}
	}
}
