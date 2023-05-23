package hu.bme.mit.trainbenchmark.benchmark.typeql.queries.storage;

import com.vaticle.typedb.client.TypeDB;
import com.vaticle.typedb.client.api.TypeDBClient;
import com.vaticle.typedb.client.api.TypeDBOptions;
import com.vaticle.typedb.client.api.TypeDBSession;
import com.vaticle.typedb.client.api.TypeDBTransaction;
import hu.bme.mit.trainbenchmark.benchmark.typeql.driver.TypeQLDriver;
import hu.bme.mit.trainbenchmark.benchmark.typeql.matches.TypeQLMatch;
import hu.bme.mit.trainbenchmark.constants.RailwayQuery;
import hu.bme.mit.trainbenchmark.benchmark.typeql.queries.TypeQLQuery;

import java.util.function.Consumer;

public abstract class TypeQLMainQuery<TQLMatch extends TypeQLMatch> extends TypeQLQuery<TQLMatch> {

	static void transaction(Consumer<TypeDBTransaction> queries, final String mode) {
		TypeDBClient client = TypeDB.coreClient("localhost:1729");
		TypeDBSession session = client.session("TRAIN0522", TypeDBSession.Type.DATA);
		TypeDBOptions options = TypeDBOptions.core().infer(true);
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

	public TypeQLMainQuery(final RailwayQuery query, final TypeQLDriver driver) {
		super(query, driver);
	}

}
