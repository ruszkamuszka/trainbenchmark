package hu.bme.mit.trainbenchmark.benchmark.result;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public final class ResultHelper {

	public static final String WORKSPACE_DIR = "../";
	public static final String RESULT_DIR = "results/";
	public static final String SCRIPT_PATH = WORKSPACE_DIR + "trainbenchmark-scripts/src/";
	public static final String BENCHMARK_SCRIPT = "BenchmarkScript.groovy";

	/**
	 * Note that this method should be called from the workspace directory.
	 *
	 * @return
	 */

	public static int getNextId() {
		final File[] resultDirs = new File(WORKSPACE_DIR + RESULT_DIR).listFiles(File::isDirectory);
		final Integer lastId = Arrays.stream(resultDirs) //
				.map(dir -> dir.getName()) //
				.filter(name -> StringUtils.isNumeric(name)) //
				.map(name -> Integer.parseInt(name)) //
				.max(Integer::compare) //
				.orElse(0);
		final Integer newId = lastId + 1;
		return newId;
	}

	public static String getResultDirForId(int id) {
		return RESULT_DIR + String.format("%04d/", id);
	}

	public static String getRelativeResultPathForId(int id) {
		return WORKSPACE_DIR + getResultDirForId(id);
	}

	public static void createResultDir(int id) {
		final String dir = WORKSPACE_DIR + getResultDirForId(id);
		System.out.println("Creating dir: " + dir);
		if (new File(dir).mkdir()) {
			// change/create symlink for recent
			Path recent = Paths.get(WORKSPACE_DIR, RESULT_DIR, "recent");
			try {
				Files.deleteIfExists(recent);
				//Files.createSymbolicLink(recent, Paths.get(dir));
			} catch (IOException e) {
				System.out.println(dir);
				e.printStackTrace();
			}
		} else {
			System.err.println("Could not create dir " + dir);
		}
	}

	public static int createNewResultDir() {
		int id = getNextId();
		createResultDir(id);
		return id;
	}

	public static void saveConfiguration(int id) throws IOException {
		final File srcFile = new File(SCRIPT_PATH + BENCHMARK_SCRIPT);
		final File destFile = new File(getRelativeResultPathForId(id) + BENCHMARK_SCRIPT);
		FileUtils.copyFile(srcFile, destFile);
	}

}
