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

package hu.bme.mit.trainbenchmark.benchmark.sesame;

import java.util.Comparator;

import org.openrdf.model.URI;

import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.AbstractBenchmarkCaseRunner;
import hu.bme.mit.trainbenchmark.benchmark.benchmarkcases.transformations.Transformation;
import hu.bme.mit.trainbenchmark.benchmark.rdf.RDFBenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.sesame.checkers.SesameChecker;
import hu.bme.mit.trainbenchmark.benchmark.sesame.driver.SesameDriver;
import hu.bme.mit.trainbenchmark.benchmark.sesame.matches.SesameMatch;
import hu.bme.mit.trainbenchmark.benchmark.sesame.matches.SesameMatchComparator;
import hu.bme.mit.trainbenchmark.benchmark.sesame.transformations.SesameTransformation;

public class SesameBenchmarkCase extends AbstractBenchmarkCaseRunner<SesameMatch, URI, SesameDriver, RDFBenchmarkConfig> {

	@Override
	protected void initialize() throws Exception {
		driver = new SesameDriver(bc);
		checker = new SesameChecker(driver, bc);
	}

	@Override
	protected Comparator<?> getMatchComparator() {
		return new SesameMatchComparator();
	}

	@Override
	protected Transformation<?> getTransformation() {
		return SesameTransformation.newInstance(driver, bc.getQuery(), bc.getScenario());
	}

}
