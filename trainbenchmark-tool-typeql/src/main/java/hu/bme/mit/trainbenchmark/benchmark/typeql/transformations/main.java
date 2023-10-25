package hu.bme.mit.trainbenchmark.benchmark.typeql.transformations;

import com.vaticle.typedb.driver.TypeDB;
import com.vaticle.typedb.driver.api.TypeDBDriver;
import com.vaticle.typedb.driver.api.TypeDBSession;
import com.vaticle.typedb.driver.api.TypeDBTransaction;
import com.vaticle.typedb.driver.api.concept.type.EntityType;

public class main {

	public static void main(final String[] args) throws Exception {
			TypeDBDriver driver = TypeDB.coreDriver(TypeDB.DEFAULT_ADDRESS);
			driver.databases().create("type");
			TypeDBSession session = driver.session("typedb", TypeDBSession.Type.DATA);
			TypeDBTransaction tx = session.transaction(TypeDBTransaction.Type.WRITE);
			EntityType root = tx.concepts().getRootEntityType();
			tx.close();
			session.close();
			driver.close();
	}
}
