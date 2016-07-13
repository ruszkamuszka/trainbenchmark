/*******************************************************************************
 * Copyright (c) 2010-2015, Benedek Izso, Gabor Szarnyas, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Benedek Izso - initial API and implementation
 *   Gabor Szarnyas - initial API and implementation
 *******************************************************************************/

package hu.bme.mit.trainbenchmark.benchmark.jena.test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import hu.bme.mit.trainbenchmark.benchmark.jena.JenaBenchmarkScenario;
import hu.bme.mit.trainbenchmark.benchmark.jena.config.JenaBenchmarkConfigWrapper;
import hu.bme.mit.trainbenchmark.benchmark.rdf.tests.RdfTest;
import hu.bme.mit.trainbenchmark.benchmark.runcomponents.BenchmarkResult;

@RunWith(Parameterized.class)
public class JenaTest extends RdfTest {
	
	@Override
	protected BenchmarkResult runTest() throws Exception {
		final JenaBenchmarkConfigWrapper rbcw = new JenaBenchmarkConfigWrapper(bc, inferencing);
		final JenaBenchmarkScenario scenario = new JenaBenchmarkScenario(rbcw);
		final BenchmarkResult result = scenario.performBenchmark();
		return result;
	}

}
