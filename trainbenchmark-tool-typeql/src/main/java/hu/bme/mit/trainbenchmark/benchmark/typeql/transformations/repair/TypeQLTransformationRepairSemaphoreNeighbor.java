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

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSemaphoreNeighborMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationRepairSemaphoreNeighbor<TTypeQLDriver extends TypeQLDriver>
		extends TypeQLTransformation<TypeQLSemaphoreNeighborMatch, TTypeQLDriver> {

	public TypeQLTransformationRepairSemaphoreNeighbor(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLSemaphoreNeighborMatch> matches) {
		for (final TypeQLSemaphoreNeighborMatch match : matches) {
			driver.transaction(t -> {
				String query = "match" +
					"    $route isa Route, has id " + match.getRoute2() + ", has entry $entry;" +
					"delete" +
					"    $route has $entry;";

				//System.out.println("Executing TypeQL Delete: SemaphoreNeighborRepairDelete");
				t.query().delete(TypeQL.parseQuery(query).asDelete());

				query = "match" +
					"    $route isa Route, has id " + match.getRoute2() + ", has entry $entry;" +
					"insert" +
					"    $route has entry " + match.getSemaphore() + ";";

				//System.out.println("Executing TypeQL Insert: SemaphoreNeighborRepairInsert");
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
	}
}
