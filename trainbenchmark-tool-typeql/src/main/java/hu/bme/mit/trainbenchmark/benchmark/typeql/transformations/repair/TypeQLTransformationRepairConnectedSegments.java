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
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLConnectedSegmentsMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationRepairConnectedSegments<TTypeQLDriver extends TypeQLDriver>
		extends TypeQLTransformation<TypeQLConnectedSegmentsMatch, TTypeQLDriver> {

	public TypeQLTransformationRepairConnectedSegments(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLConnectedSegmentsMatch> matches) {
		for (final TypeQLConnectedSegmentsMatch match : matches) {
			driver.transaction(t -> {
				String query = "match" +
					"    $sensor isa Sensor, has id " + match.getSensor() + ";" +
					"    $segment1 isa Segment, has id " + match.getSegment1() + ";" +
					"    $segment2 isa Segment, has id " + match.getSegment2() + ";" +
					"    $segment3 isa Segment, has id " + match.getSegment3() + ";" +
					"    $connectsTo2(TrackElement: $segment1, TrackElement: $segment2) isa connectsTo;" +
					"    $connectsTo3(TrackElement: $segment2, TrackElement: $segment3) isa connectsTo;" +
					"    $monitoredBy3(TrackElement: $segment2, Sensor: $sensor) isa monitoredBy;" +
					"delete" +
					"    $connectsTo2 isa connectsTo;" +
					"    $connectsTo3 isa connectsTo;" +
					"    $monitoredBy3 isa monitoredBy;" +
					"    $segment2 isa Segment;";

				//System.out.println("Executing TypeQL Delete: ConnectedSegmentsRepairDelete");
				t.query().delete(TypeQL.parseQuery(query).asDelete());

				query = "match" +
					"    $sensor isa Sensor, has id " + match.getSensor() + ";" +
					"    $segment1 isa Segment, has id " + match.getSegment1() + ";" +
					"    $segment3 isa Segment, has id " + match.getSegment3() + ";" +
					"insert" +
					"    $connectsTo1(TrackElement: $segment1, TrackElement: $segment3) isa connectsTo;";

				//System.out.println("Executing TypeQL Insert: ConnectedSegmentsRepairInsert");
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
	}
}
