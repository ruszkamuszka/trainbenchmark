package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.inject;

import com.vaticle.typeql.lang.TypeQL;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLSwitchSetInjectMatch;
import hu.bme.mit.trainbenchmark.benchmark.typeql.transformations.TypeQLTransformation;

import java.util.Collection;

public class TypeQLTransformationInjectSwitchSet <TTypeQLDriver extends TypeQLDriver> extends TypeQLTransformation<TypeQLSwitchSetInjectMatch, TTypeQLDriver> {
	public TypeQLTransformationInjectSwitchSet(final TTypeQLDriver driver) {
		super(driver);
	}

	@Override
	public void activate(final Collection<TypeQLSwitchSetInjectMatch> matches) throws Exception {
		for(final TypeQLSwitchSetInjectMatch match : matches){
			driver.transaction(t -> {
				String query = "match " +
					"    $switch isa Switch, has id " + match.getSw() + ", has position $currentposition;" +
					"delete" +
					"    $switch has $currentposition;";

				//System.out.println("Executing TypeQL Delete: SwitchSetInjectDelete");
				//System.out.println(query);
				t.query().delete(TypeQL.parseQuery(query).asDelete());

				query = "match" +
					"    $switch isa Switch, has id " + match.getSw() + ";" +
					"insert" +
					"    $switch has position \"" + match.getNextValue() + "\";";

				//System.out.println("Executing TypeQL Insert: SwitchSetInjectInsert");
				//System.out.println(query);
				t.query().insert(TypeQL.parseQuery(query).asInsert());
			}, "WRITE");
		}
	}
}
