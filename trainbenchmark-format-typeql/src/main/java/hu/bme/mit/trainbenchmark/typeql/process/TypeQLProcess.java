package hu.bme.mit.trainbenchmark.typeql.process;

import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBSession;
import hu.bme.mit.trainbenchmark.typeql.schema.TypeQLSchema;

public class TypeQLProcess {
	public void setupDB(){
		System.out.println("Hurrá");
		TypeDBClient client = TypeDB.coreClient("localhost:1729");
		//Checks if the database already exists
		if(client.databases().contains("TRAIN0423")){
			client.databases().get("TRAIN0423").delete();
		}
		// client is open
		client.databases().create("TRAIN0423");
		TypeDBSession session1 = client.session("TRAIN0423", TypeDBSession.Type.SCHEMA);
		TypeQLSchema schema = new TypeQLSchema();
		schema.writeSchema(session1);
		client.close();
		System.out.println("Hurrá2");
	}
}
