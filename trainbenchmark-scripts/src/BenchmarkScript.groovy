import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBaseBuilder
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBuilder
import hu.bme.mit.trainbenchmark.benchmark.config.ModelSetConfig
import hu.bme.mit.trainbenchmark.benchmark.config.TransformationChangeSetStrategy
import hu.bme.mit.trainbenchmark.benchmark.result.ResultHelper
import hu.bme.mit.trainbenchmark.benchmark.runcomponents.BenchmarkReporter
import hu.bme.mit.trainbenchmark.benchmark.runcomponents.BenchmarkRunner
import hu.bme.mit.trainbenchmark.benchmark.typeql.config.TypeQLBenchmarkConfigBuilder
import hu.bme.mit.trainbenchmark.config.ExecutionConfig
import hu.bme.mit.trainbenchmark.constants.RailwayOperation

def benchmarkId = ResultHelper.createNewResultDir()
ResultHelper.saveConfiguration(benchmarkId)
def ec = new ExecutionConfig(2000, 4000)

def minSize = 1
def maxSize = 1
def timeout = 900
def runs = 1

def tools = [
		new TypeQLBenchmarkConfigBuilder(),
]

def workloads = [
//	ConnectedSegments: [ modelVariant: "repair", operations: [RailwayOperation.CONNECTEDSEGMENTS], ],
//	PosLength:         [ modelVariant: "repair", operations: [RailwayOperation.POSLENGTH        ], ],
//	RouteSensor:       [ modelVariant: "repair", operations: [RailwayOperation.ROUTESENSOR      ], ],
//	SemaphoreNeighbor: [ modelVariant: "repair", operations: [RailwayOperation.SEMAPHORENEIGHBOR], ],
//	SwitchMonitored:   [ modelVariant: "repair", operations: [RailwayOperation.SWITCHMONITORED  ], ],
//	SwitchSet:         [ modelVariant: "repair", operations: [RailwayOperation.SWITCHSET        ], ],

	Inject: [
		modelVariant: "inject",
		operations: [
			//RailwayOperation.CONNECTEDSEGMENTS,
			RailwayOperation.POSLENGTH,
			RailwayOperation.ROUTESENSOR,
			RailwayOperation.SEMAPHORENEIGHBOR,
			RailwayOperation.SWITCHSET,
			RailwayOperation.SWITCHMONITORED,
			//RailwayOperation.CONNECTEDSEGMENTS_INJECT,
			RailwayOperation.POSLENGTH_INJECT,
			RailwayOperation.ROUTESENSOR_INJECT,
			RailwayOperation.SEMAPHORENEIGHBOR_INJECT,
			RailwayOperation.SWITCHSET_INJECT,
			RailwayOperation.SWITCHMONITORED_INJECT,
		],
		strategy: TransformationChangeSetStrategy.FIXED,
		constant: 10, // elements
		queryTransformationCount: 6, // iterations
	],
	Repair: [
		modelVariant: "repair",
		operations: [
			//RailwayOperation.CONNECTEDSEGMENTS_REPAIR,
			RailwayOperation.POSLENGTH_REPAIR,
			RailwayOperation.ROUTESENSOR_REPAIR,
			RailwayOperation.SEMAPHORENEIGHBOR_REPAIR,
			RailwayOperation.SWITCHSET_REPAIR,
			RailwayOperation.SWITCHMONITORED_REPAIR,
		],
		strategy: TransformationChangeSetStrategy.FIXED,
		constant: 5, // percent
		queryTransformationCount: 2, // iterations
	]
]

def runBenchmarkSeries(BenchmarkConfigBaseBuilder configBaseBuilder, BenchmarkConfigBuilder configBuilder,
		ExecutionConfig ec, ModelSetConfig modelSetConfig) {
	try {
		for (def size = modelSetConfig.minSize; size <= modelSetConfig.maxSize; size += 1) {
			def modelFilename = "railway-${modelSetConfig.modelVariant}-${size}"

			println("------------------------------------------------------------")
			println("Model: $modelFilename")
			println("------------------------------------------------------------")

			configBaseBuilder.setModelFilename(modelFilename)
			def configBase = configBaseBuilder.createConfigBase()
			def config = configBuilder.setConfigBase(configBase).createConfig()

			def exitValue = BenchmarkRunner.runPerformanceBenchmark(config, ec)
			if (exitValue != 0) {
				println "Timeout or error occurred, skipping models for larger sizes. Error code: ${exitValue}"
				break
			}
		}
	} catch (all) {
		println "Exception occurred during execution."
	}
}

workloads.each { workload ->
	def workloadName = workload.key

	def workloadConfiguration = workload.value
	def modelVariant = workloadConfiguration["modelVariant"]
	def operations = workloadConfiguration["operations"]
	def strategy = workloadConfiguration["strategy"]
	def constant = workloadConfiguration["constant"]
	def queryTransformationCount = workloadConfiguration["queryTransformationCount"]

	println("============================================================")
	println("Workload: $workloadName")
	println("============================================================")

	def modelSetConfig = new ModelSetConfig(modelVariant, minSize, maxSize)

	def bcbb = new BenchmarkConfigBaseBuilder()
			.setBenchmarkId(benchmarkId).setTimeout(timeout).setRuns(runs)
			.setOperations(operations).setWorkload(workloadName)
			.setQueryTransformationCount(queryTransformationCount).setTransformationConstant(constant)
			.setTransformationChangeSetStrategy(strategy)

	tools.each{ bcb -> runBenchmarkSeries(bcbb, bcb, ec, modelSetConfig) }
}

if (binding.variables.get("reportUrl")) {
	BenchmarkReporter.reportReady(reportUrl)
}
