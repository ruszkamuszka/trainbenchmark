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
package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.repair;

import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;
import hu.bme.mit.trainbenchmark.constants.ModelConstants;

import java.util.Collection;

public class TypeQLTransformationRepairConnectedSegments<TTypeQLDriver extends TypeQLDriver>
		extends TypeQLTransformation<TypeQLConnectedSegmentsMatch, TTypeQLDriver> {

	public TypeQLTransformationRepairConnectedSegments(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLConnectedSegmentsMatch> matches) {
		for (final TypeQLConnectedSegmentsMatch match : matches) {
			//TODO
		}
	}

}
