package hu.bme.mit.trainbenchmark.typeql.schema;

import com.vaticle.typedb.driver.api.TypeDBSession;
import com.vaticle.typedb.driver.api.TypeDBTransaction;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.common.TypeQLArg;
import com.vaticle.typeql.lang.query.TypeQLDefine;

import static com.vaticle.typeql.lang.TypeQL.type;


public class TypeQLSchema {
	public void writeSchema(TypeDBSession session){
		try{
			// creating write transaction
			TypeDBTransaction writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
			// write transaction is open
			// write transaction must always be committed (closed)

			TypeQLDefine query = TypeQL.define(
				//attributes
				type("id").sub("attribute").value(TypeQLArg.ValueType.LONG), //ok
				type("position").sub("attribute").value(TypeQLArg.ValueType.STRING).regex("(FAILURE|STRAIGHT|DIVERGING)"), //ok
				type("active").sub("attribute").value(TypeQLArg.ValueType.BOOLEAN), //ok
				//type("currentPosition").sub("attribute").value(TypeQLArg.ValueType.STRING),
				type("entry").sub("attribute").value(TypeQLArg.ValueType.LONG), //ok
				type("exit").sub("attribute").value(TypeQLArg.ValueType.LONG),	//ok
				type("length").sub("attribute").value(TypeQLArg.ValueType.LONG),	//ok
				type("signal").sub("attribute").value(TypeQLArg.ValueType.STRING).regex("(GO|STOP|FAILURE)"),
				type("target").sub("attribute").value(TypeQLArg.ValueType.LONG),
				//name to the reverse edges
				//type("name").sub("attribute").value(TypeQLArg.ValueType.STRING),

				//nodes, entities
				type("Route").sub("RailwayContainer")
					.owns("active")
					.owns("entry")
					.owns("exit")
					.plays("requires", "Route")
					.plays("follows", "Route"),
				type("Semaphore").sub("RailwayContainer")
					.owns("signal")
					.plays("semaphores", "Semaphore"),
				type("Region").sub("RailwayContainer")
					.plays("elements", "Region")
					.plays("sensors", "Region"),
				type("Sensor").sub("RailwayContainer")
					.plays("monitoredBy", "Sensor")
					.plays("requires","Sensor")
					.plays("sensors", "Sensor"),
				type("Switch").sub("TrackElement")
					.owns("position"),
				type("Segment").sub("TrackElement")
					.owns("length")
					.plays("semaphores", "Segment"),
				type("SwitchPosition").sub("RailwayContainer")
					.owns("position") //here changed
					.owns("target")
					.plays("follows", "SwitchPosition"),
				type("TrackElement").sub("RailwayContainer").isAbstract()
					.plays("elements", "TrackElement")
					.plays("connectsTo", "TrackElement")
					.plays("monitoredBy","TrackElement"),
				type("RailwayContainer").sub("entity").isAbstract()
					.owns("id"),

				//edges, relations
				type("connectsTo").sub("relation").relates("TrackElement").relates("TrackElement"),
				type("monitoredBy").sub("relation").relates("TrackElement").relates("Sensor"),
				type("requires").sub("relation").relates("Route").relates("Sensor"),
				type("elements").sub("relation").relates("Region").relates("TrackElement"),
				type("follows").sub("relation").relates("Route").relates("SwitchPosition"),
				type("sensors").sub("relation").relates("Region").relates("Sensor"),
				type("semaphores").sub("relation").relates("Segment").relates("Semaphore")
//				type("target").sub("relation").relates("SwitchPosition").relates("Switch"),
//				type("entry").sub("relation").relates("Route").relates("Semaphore"),
//				type("exit").sub("relation").relates("Route").relates("Semaphore")

				//reverse edges
//				type("sensor-region").sub("relation").owns("id",true).relates("Sensor").relates("Region"),
//				type("semaphore-segment").sub("relation").owns("id",true).relates("Semaphore").relates("Segment"),
//				type("switchposition-route").sub("relation").owns("id",true).relates("SwitchPosition").relates("Route"),
//				type("tre-region").sub("relation").owns("id",true).relates("TrackElement").relates("Region")

			);

			writeTransaction.query().define(query);


			writeTransaction.commit();
			// creating a read transaction
			TypeDBTransaction readTransaction = session.transaction(TypeDBTransaction.Type.READ);
			// read transaction is open
			// read transaction must always be closed
			readTransaction.close();


		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// session is open
		session.close();
	}
}
