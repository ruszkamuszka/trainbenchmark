package hu.bme.mit.trainbenchmark.typeql.schema;

import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
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
				type("id").sub("attribute").value(TypeQLArg.ValueType.LONG),
				type("position").sub("attribute").value(TypeQLArg.ValueType.STRING),
				type("active").sub("attribute").value(TypeQLArg.ValueType.STRING),
				type("currentPosition").sub("attribute").value(TypeQLArg.ValueType.STRING),
				type("entry").sub("attribute").value(TypeQLArg.ValueType.STRING),
				type("exit").sub("attribute").value(TypeQLArg.ValueType.STRING),
				type("length").sub("attribute").value(TypeQLArg.ValueType.STRING),
				type("signal").sub("attribute").value(TypeQLArg.ValueType.STRING),

				/*type("GO").sub("attribute").value(TypeQLArg.ValueType.BOOLEAN),
				type("FAILURE").sub("attribute").value(TypeQLArg.ValueType.BOOLEAN),
				type("STOP").sub("attribute").value(TypeQLArg.ValueType.BOOLEAN),*/

				type("target").sub("attribute").value(TypeQLArg.ValueType.STRING),
				//name to the reverse edges
				type("name").sub("attribute").value(TypeQLArg.ValueType.STRING),
				//nodes
				type("Route").sub("entity")
					.owns("id", true)
					.owns("active")
					.owns("entry")
					.owns("exit")
					.plays("requires", "Route")
					.plays("follows", "Route"),
				type("Segment").sub("entity")
					.owns("id", true).owns("length")
					.plays("connectsTo", "Segment")
					.plays("elements", "Segment")
					.plays("semaphores", "Segment"),
				//.plays("monitoredBy","Segment"),
				type("Semaphore").sub("entity")
					.owns("id", true)
					.owns("signal")
					.plays("semaphores", "Semaphore"),
				type("Region").sub("entity")
					.owns("id", true)
					.plays("elements", "Region")
					.plays("sensors", "Region"),
				type("Sensor").sub("entity")
					.owns("id", true)
					.plays("monitoredBy", "Sensor")
					.plays("requires","Sensor")
					.plays("sensors", "Sensor"),
				type("Switch").sub("entity")
					.owns("id", true).owns("currentPosition")
					.plays("connectsTo", "Switch")
					.plays("monitoredBy","Switch"),
				type("SwitchPosition").sub("entity").owns("id", true)
					.owns("currentPosition")
					.owns("target")
					.plays("follows", "SwitchPosition"),
				type("TrackElement").sub("entity").owns("id", true),

				//edges
				type("connectsTo").sub("relation").relates("Segment").relates("Switch"),
				type("monitoredBy").sub("relation").relates("Sensor").relates("Switch"),
				type("requires").sub("relation").relates("Route").relates("Sensor"),
				type("elements").sub("relation").relates("Region").relates("Segment"),
				type("follows").sub("relation").relates("Route").relates("SwitchPosition"),
				type("sensors").sub("relation").relates("Region").relates("Sensor"),
				type("semaphores").sub("relation").relates("Segment").relates("Semaphore"),


				//reverse edges
				type("sensor-region").sub("relation").owns("id",true).relates("Sensor").relates("Region"),
				type("semaphore-segment").sub("relation").owns("id",true).relates("Semaphore").relates("Segment"),
				type("switchposotion-route").sub("relation").owns("id",true).relates("SwitchPosition").relates("Route"),
				type("tre-region").sub("relation").owns("id",true).relates("TrackElement").relates("Region")

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
