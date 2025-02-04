package hu.bme.mit.trainbenchmark.generator.typeql;

import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
import com.vaticle.typedb.client.api.answer.ConceptMap;
import com.vaticle.typeql.lang.TypeQL;
import com.vaticle.typeql.lang.query.TypeQLInsert;
import hu.bme.mit.trainbenchmark.generator.ModelSerializer;
import hu.bme.mit.trainbenchmark.generator.typeql.config.TypeQLGeneratorConfig;
import hu.bme.mit.trainbenchmark.typeql.process.TypeQLProcess;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.vaticle.typeql.lang.TypeQL.var;
import static hu.bme.mit.trainbenchmark.constants.ModelConstants.*;

public class TypeQLSerializer extends ModelSerializer<TypeQLGeneratorConfig> {

	private List<ConceptMap> insertedIds;

	private long id = 1;
	private TypeDBTransaction writeTransaction;
	private TypeDBSession session;
	private TypeDBClient client;
	public TypeQLSerializer(final TypeQLGeneratorConfig gc) {
		super(gc);
	}

	@Override
	public String syntax() {
		return "typeQL";
	}

	@Override
	public void initModel() throws IOException {
		final TypeQLProcess typeQLProcess = new TypeQLProcess("TRAIN0522");
		typeQLProcess.checkExistence();
		typeQLProcess.setupDB();
		client = TypeDB.coreClient("localhost:1729");
		session = client.session("TRAIN0522", TypeDBSession.Type.DATA);
	}

	@Override
	public void persistModel() throws Exception {
		session.close();
		client.close();
	}

	@Override
	public Object createVertex(int variable, String type, final Map<String, ?> attributes, Map<String, Object> outgoingEdges, Map<String, Object> incomingEdges) throws IOException{
		long nextID = id++;
		switch (type){
			case "Route":
				String str = type+variable;
				Collection<?> collection = attributes.values();
				boolean active = (boolean) collection.iterator().next();
				Long entry = (Long) outgoingEdges.get(ENTRY);
				Long exit = (Long) outgoingEdges.get(EXIT);
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery = TypeQL.insert(var(str).isa(type).has("id",nextID).has("active", active).has("entry", entry).has("exit", exit));
				writeTransaction.query().insert(insertQuery);
				writeTransaction.commit();
				return nextID;
			case "Segment":
				String str1 = type+variable;
				Collection<?> collection1 = attributes.values();
				Integer length = (Integer) collection1.iterator().next();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery1 = TypeQL.insert(var(str1).isa(type).has("id",nextID).has("length", length));
				writeTransaction.query().insert(insertQuery1);
				writeTransaction.commit();
				return nextID;
			case "Semaphore":
				String str2 = type+variable;
				Collection<?> collection2 = attributes.values();
				String signal = collection2.iterator().next().toString();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery2 = TypeQL.insert(var(str2).isa(type).has("id",nextID).has("signal", signal));
				writeTransaction.query().insert(insertQuery2);
				writeTransaction.commit();
				return nextID;
			//case "TrackElement":
			case "Sensor":
			case "Region":
				String str3 = type+variable;
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery3 = TypeQL.insert(var(str3).isa(type).has("id",nextID));
				writeTransaction.query().insert(insertQuery3);
				writeTransaction.commit();
				return nextID;
			case "Switch":
				String str4 = type+variable;
				Collection<?> collection4 = attributes.values();
				String currentPosition = collection4.iterator().next().toString();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery4 = TypeQL.insert(var(str4).isa(type).has("id", nextID).has("position", currentPosition));
				writeTransaction.query().insert(insertQuery4);
				writeTransaction.commit();
				return nextID;
			case "SwitchPosition":
				String str5 = type+variable;
				Collection<?> collection5 = attributes.values();
				Collection<?> collectionTarget = outgoingEdges.values();
				String position = collection5.iterator().next().toString();
				Long target = (Long) collectionTarget.iterator().next();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery5 = TypeQL.insert(var(str5).isa(type).has("id",nextID).has("position", position).has("target", target));
				writeTransaction.query().insert(insertQuery5);
				writeTransaction.commit();
				return nextID;
			default:
				return type;
		}
	}

	@Override
	public void createEdge(String label, Object from, Object to) throws IOException {
		if (from == null || to == null) {
			return;
		}
		writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
		TypeQLInsert query = null;

		if(label.equals(MONITORED_BY)){
			query = TypeQL.match(
				var(from.toString()).isa(TRACKELEMENT).has("id",(long)from),
				var(to.toString()).isa(SENSOR).has("id",(long)to)
			).insert(
				var(label).rel(TRACKELEMENT, from.toString()).rel(SENSOR, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label.equals(CONNECTS_TO)){
			query = TypeQL.match(
				var(from.toString()).isa(TRACKELEMENT).has("id",(long)from),
				var(to.toString()).isa(TRACKELEMENT).has("id",(long)to)
			).insert(
				var(label).rel(TRACKELEMENT, from.toString()).rel(TRACKELEMENT, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label.equals(REQUIRES)){
			query = TypeQL.match(
				var(from.toString()).isa(ROUTE).has("id",(long)from),
				var(to.toString()).isa(SENSOR).has("id",(long)to)
			).insert(
				var(label).rel(ROUTE, from.toString()).rel(SENSOR, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label.equals(ELEMENTS)){
			query = TypeQL.match(
				var(from.toString()).isa(REGION).has("id",(long)from),
				var(to.toString()).isa(TRACKELEMENT).has("id",(long)to)
			).insert(
				var(label).rel(REGION, from.toString()).rel(TRACKELEMENT, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label.equals(FOLLOWS)){
			query = TypeQL.match(
				var(from.toString()).isa(ROUTE).has("id",(long)from),
				var(to.toString()).isa(SWITCHPOSITION).has("id",(long)to)
			).insert(
				var(label).rel(ROUTE, from.toString()).rel(SWITCHPOSITION, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label.equals(SENSORS)){
			query = TypeQL.match(
				var(from.toString()).isa(REGION).has("id",(long)from),
				var(to.toString()).isa(SENSOR).has("id",(long)to)
			).insert(
				var(label).rel(REGION, from.toString()).rel(SENSOR, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label.equals(SEMAPHORES)){
			query = TypeQL.match(
				var(from.toString()).isa(SEGMENT).has("id",(long)from),
				var(to.toString()).isa(SEMAPHORE).has("id",(long)to)
			).insert(
				var(label).rel(SEGMENT, from.toString()).rel(SEMAPHORE, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
	}
}
