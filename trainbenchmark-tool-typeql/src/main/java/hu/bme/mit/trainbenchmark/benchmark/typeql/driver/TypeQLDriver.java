package hu.bme.mit.trainbenchmark.benchmark.typeql.driver;

import com.vaticle.typedb.driver.TypeDB;
import com.vaticle.typedb.driver.api.TypeDBDriver;
import com.vaticle.typedb.driver.api.TypeDBOptions;
import com.vaticle.typedb.driver.api.TypeDBSession;
import com.vaticle.typedb.driver.api.TypeDBTransaction;
import hu.bme.mit.trainbenchmark.benchmark.driver.Driver;

import java.util.function.Consumer;

public class TypeQLDriver extends Driver {
	TypeDBDriver client;
	TypeDBSession session;
	TypeDBTransaction transaction;

	TypeDBOptions options;

	public TypeQLDriver(){
		super();
		options = new TypeDBOptions();
		options.transactionTimeoutMillis(3000000);
	}
	public static void transaction(Consumer<TypeDBTransaction> queries, final String mode) {
		TypeDBDriver client = TypeDB.coreDriver("localhost:1729");
		TypeDBSession session = client.session("TRAIN0522", TypeDBSession.Type.DATA);
		TypeDBOptions options = new TypeDBOptions();
		TypeDBTransaction transaction = mode == "WRITE"
			? session.transaction(TypeDBTransaction.Type.WRITE, options)
			: session.transaction(TypeDBTransaction.Type.READ, options);

		queries.accept(transaction);
		if (mode == "WRITE") {
			transaction.commit();
		}

		transaction.close();
		session.close();
		client.close();
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
		client = TypeDB.coreDriver("localhost:1729");
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
