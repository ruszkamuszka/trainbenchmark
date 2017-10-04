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

package hu.bme.mit.trainbenchmark.benchmark.orientdb.config;

import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfig;
import hu.bme.mit.trainbenchmark.benchmark.config.BenchmarkConfigBase;
import hu.bme.mit.trainbenchmark.benchmark.tinkergraph.config.TinkerGraphEngine;

public class OrientDbBenchmarkConfig extends BenchmarkConfig {

	protected TinkerGraphEngine engine;

	protected OrientDbBenchmarkConfig(final BenchmarkConfigBase configBase, final TinkerGraphEngine engine) {
		super(configBase);
		this.engine = engine;
	}

	public TinkerGraphEngine getEngine() {
		return engine;
	}

	@Override
	public String getToolName() {
		return String.format("OrientDb (%s)", getEngine());
	}

	@Override
	public String getProjectName() {
		return "OrientDb";
	}

}
