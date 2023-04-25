package hu.bme.mit.trainbenchmark.typeql.process;

import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBSession;
import hu.bme.mit.trainbenchmark.typeql.schema.TypeQLSchema;

public class TypeQLProcess {
	TypeDBClient client;
	String nameOfDatabase;
	public TypeQLProcess(String nameDB){
		client = TypeDB.coreClient("localhost:1729");
		this.nameOfDatabase = nameDB;
	}

	public void checkExistence(){ 	//Checks if the database already exists
		if(client.databases().contains(nameOfDatabase)){
			client.databases().get(nameOfDatabase).delete();
		}
	}

	public void setupDB(){		// client is open
		client.databases().create(nameOfDatabase);
		TypeDBSession session = client.session(nameOfDatabase, TypeDBSession.Type.SCHEMA);
		TypeQLSchema schema = new TypeQLSchema();
		schema.writeSchema(session);
		closeBD();
	}

	public void closeBD(){
		client.close();
	}
}
