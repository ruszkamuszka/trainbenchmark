package hu.bme.mit.trainbenchmark.benchmark.typeql.matches;

import hu.bme.mit.trainbenchmark.benchmark.matches.SwitchSetInjectMatch;
import hu.bme.mit.trainbenchmark.constants.QueryConstants;

import java.util.Map;

public class TypeQLSwitchSetInjectMatch extends TypeQLMatch implements SwitchSetInjectMatch {
	public TypeQLSwitchSetInjectMatch(Map<String, Object> match) {
		super(match);
	}

	@Override
	public Object getSw() {
		return match.get(QueryConstants.VAR_SW);
	}

	public String getNextValue(){
		String currentposition = (String) match.get(QueryConstants.VAR_CURRENTPOSITION);
		String nextvalue = null;
		switch (currentposition){
			case "STRAIGHT":
				nextvalue = "DIVERGING";
				break;
			case "DIVERGING":
				nextvalue = "FAILURE";
				break;
			case "FAILURE":
				nextvalue = "STRAIGHT";
				break;
		}
		return nextvalue;
	}
}
