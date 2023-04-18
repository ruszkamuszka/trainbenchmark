package hu.bme.mit.trainbenchmark.typeql.driver;

import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
import hu.bme.mit.trainbenchmark.benchmark.driver.Driver;

public class TypeQLDriver extends Driver {
	TypeDBClient client;
	TypeDBSession session;
	TypeDBTransaction transaction;

	@Override
	public void read(String modelPath) throws Exception {
		client = TypeDB.coreClient("localhost:1729");
		if(!client.databases().contains("Train2")){
			System.out.println("The Train2 database does not exist!");
		}else{
			System.out.println("Connection succeeded!");
		}
		session = client.session("Train2", TypeDBSession.Type.DATA);
		transaction = session.transaction(TypeDBTransaction.Type.READ);
	}

	public TypeDBTransaction getTransaction(){
		return transaction;
	}

	@Override
	public String getPostfix() {
		return null;
	}

	@Override
	public Number generateNewVertexId() throws Exception {
		return null;
	}

	public void beginTransaction() throws Exception {
		read("...");
	}

	@Override
	public void finishTransaction(){
		transaction.close();
		session.close();
		client.close();
	}
}
