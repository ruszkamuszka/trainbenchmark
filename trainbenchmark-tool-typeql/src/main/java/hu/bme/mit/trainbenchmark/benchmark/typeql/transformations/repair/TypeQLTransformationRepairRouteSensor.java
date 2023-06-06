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
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLRouteSensorMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationRepairRouteSensor<TTypeQLDriver extends TypeQLDriver>
		extends TypeQLTransformation<TypeQLRouteSensorMatch, TTypeQLDriver> {

	public TypeQLTransformationRepairRouteSensor(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLRouteSensorMatch> matches) {
		for (final TypeQLRouteSensorMatch match : matches) {
			driver.transaction(t -> {
				String query = "match" +
					"    $route isa Route, has id " + match.getRoute() + ";" +
					"    $sensor isa Sensor, has id " + match.getSensor() + ";" +
					"insert" +
					"    $require(Route: $route, Sensor: $sensor) isa requires;";

				//System.out.println("Executing TypeQL Insert: RouteSensorRepairInsert");
				//System.out.println(query);
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
	}

}
