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
		client = TypeDB.coreClient("localhost:1729");
		session = client.session("TRAIN0423", TypeDBSession.Type.DATA);
	}

	@Override
	public void persistModel() throws Exception {
		session.close();
	}

//	@Override
//	public void endTransaction(){
//		session.close();
//		client.close();
//	}

	@Override
	public Object createVertex(int variable, String type, final Map<String, ?> attributes, Map<String, Object> outgoingEdges, Map<String, Object> incomingEdges) throws IOException{
		long nextID = id++;
		switch (type){
			case "Route":
				String str = type+variable;
				Collection collection = attributes.values();
				String first = collection.iterator().next().toString();
				String second = collection.iterator().next().toString();
				String third = collection.iterator().next().toString();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery = TypeQL.insert(var(str).isa(type).has("id",nextID).has("active", first).has("entry", second).has("exit", third));
				writeTransaction.query().insert(insertQuery);
				writeTransaction.commit();
				return nextID;
			case "Segment":
				String str1 = type+variable;
				Collection collection1 = attributes.values();
				String first1 = collection1.iterator().next().toString();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery1 = TypeQL.insert(var(str1).isa(type).has("id",nextID).has("length", first1));
				writeTransaction.query().insert(insertQuery1);
				writeTransaction.commit();
				return nextID;
			case "Semaphore":
				String str2 = type+variable;
				Collection collection2 = attributes.values();
				String first2 = collection2.iterator().next().toString();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery2 = TypeQL.insert(var(str2).isa(type).has("id",nextID).has("signal", first2));
				writeTransaction.query().insert(insertQuery2);
				writeTransaction.commit();
				return nextID;
			case "TrackElement":
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
				Collection collection4 = attributes.values();
				String first4 = collection4.iterator().next().toString();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery4 = TypeQL.insert(var(str4).isa(type).has("id",nextID).has("currentPosition", first4));
				writeTransaction.query().insert(insertQuery4);
				writeTransaction.commit();
				return nextID;
			case "SwitchPosition":
				String str5 = type+variable;
				Collection collection5 = attributes.values();
				String first5 = collection5.iterator().next().toString();
				String second5 = collection5.iterator().next().toString();
				writeTransaction = session.transaction(TypeDBTransaction.Type.WRITE);
				TypeQLInsert insertQuery5 = TypeQL.insert(var(str5).isa(type).has("id",nextID).has("currentPosition",first5).has("target",second5));
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

		if(label==MONITORED_BY){
			query = TypeQL.match(
				var(from.toString()).isa(SWITCH).has("id",(long)from),
				var(to.toString()).isa(SENSOR).has("id",(long)to)
			).insert(
				var(label).rel(SWITCH, from.toString()).rel(SENSOR, to.toString()).isa(label)
			);
			//var result = writeTransaction.query().insert(query);
			//result.forEach(x -> print(x));
			//System.out.println(result);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label==CONNECTS_TO){
			query = TypeQL.match(
				var(from.toString()).isa(SEGMENT).has("id",(long)from),
				var(to.toString()).isa(SWITCH).has("id",(long)to)
			).insert(
				var(label).rel(SEGMENT, from.toString()).rel(SWITCH, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label==REQUIRES){
			query = TypeQL.match(
				var(from.toString()).isa(ROUTE).has("id",(long)from),
				var(to.toString()).isa(SENSOR).has("id",(long)to)
			).insert(
				var(label).rel(ROUTE, from.toString()).rel(SENSOR, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label==ELEMENTS){
			query = TypeQL.match(
				var(from.toString()).isa(REGION).has("id",(long)from),
				var(to.toString()).isa(SEGMENT).has("id",(long)to)
			).insert(
				var(label).rel(REGION, from.toString()).rel(SEGMENT, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label==FOLLOWS){
			query = TypeQL.match(
				var(from.toString()).isa(ROUTE).has("id",(long)from),
				var(to.toString()).isa(SWITCHPOSITION).has("id",(long)to)
			).insert(
				var(label).rel(ROUTE, from.toString()).rel(SWITCHPOSITION, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label==SENSORS){
			query = TypeQL.match(
				var(from.toString()).isa(REGION).has("id",(long)from),
				var(to.toString()).isa(SENSOR).has("id",(long)to)
			).insert(
				var(label).rel(REGION, from.toString()).rel(SENSOR, to.toString()).isa(label)
			);
			writeTransaction.query().insert(query);
			writeTransaction.commit();
		}
		if(label==SEMAPHORES){
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
