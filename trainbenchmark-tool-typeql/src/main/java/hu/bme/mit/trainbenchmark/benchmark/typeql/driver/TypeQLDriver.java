package hu.bme.mit.trainbenchmark.benchmark.typeql.driver;

import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBOptions;
import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
import hu.bme.mit.trainbenchmark.benchmark.driver.Driver;

public class TypeQLDriver extends Driver {
	TypeDBClient client;
	TypeDBSession session;
	TypeDBTransaction transaction;

	TypeDBOptions options;

	public TypeQLDriver(){
		super();
		options = TypeDBOptions.core();
		options.transactionTimeoutMillis(3000000);
	}

	@Override
	public void read(String modelPath) throws Exception {
		transaction = session.transaction(TypeDBTransaction.Type.READ, options);
	}

	@Override
	public void initialize() throws Exception {
		try{
			client.close();
		}catch(final Exception e){
			//do nothing
		}
		client = TypeDB.coreClient("localhost:1729");
		if(!client.databases().contains("TRAIN0522")){
			System.out.println("The TRAIN0522 database does not exist!");
		}else{
			System.out.println("Connection succeeded!");
		}
		session = client.session("TRAIN0522", TypeDBSession.Type.DATA);
	}
	@Override
	public void destroy(){
		session.close();
		client.close();
	}

	public TypeDBTransaction getTransaction(){
		return transaction;
	}

	@Override
	public String getPostfix() {
		return "TypeQL";
	}

	@Override
	public Long generateNewVertexId() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void beginTransaction() throws Exception {
		read("...");
	}

	@Override
	public void finishTransaction(){
		transaction.close();
	}
}
