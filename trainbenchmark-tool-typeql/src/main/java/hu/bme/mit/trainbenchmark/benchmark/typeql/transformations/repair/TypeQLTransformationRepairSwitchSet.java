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
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationRepairSwitchSet<TTypeQLDriver extends TypeQLDriver>
		extends TypeQLTransformation<TypeQLSwitchSetMatch, TTypeQLDriver> {

	public TypeQLTransformationRepairSwitchSet(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLSwitchSetMatch> matches) {
		for (final TypeQLSwitchSetMatch match : matches) {
			driver.transaction(t -> {
				String query = "match " +
					"    $switch isa Switch, has id " + match.getSw() + ", has position $currentposition;" +
					"delete" +
					"    $switch has $currentposition;";

				System.out.println("Executing TypeQL Delete: SwitchSetRepairDelete");
				t.query().delete(TypeQL.parseQuery(query).asDelete());

				query = "match" +
					"    $switch isa Switch, has id " + match.getSw() + ";" +
					"insert" +
					"    $switch has position \"" + match.getPos() + "\";";

				System.out.println("Executing TypeQL Insert: SwitchSetRepairInsert");
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
	}

}
