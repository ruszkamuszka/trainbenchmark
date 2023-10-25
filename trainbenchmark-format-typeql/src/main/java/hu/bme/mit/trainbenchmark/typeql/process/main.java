package hu.bme.mit.trainbenchmark.typeql.process;

import com.vaticle.typedb.driver.TypeDB;
import com.vaticle.typedb.driver.api.TypeDBDriver;
import com.vaticle.typedb.driver.api.TypeDBSession;
import com.vaticle.typedb.driver.api.TypeDBTransaction;
import com.vaticle.typedb.driver.api.concept.type.EntityType;

public class main {

	public static void main(final String[] args) throws Exception {
			TypeDBDriver driver = TypeDB.coreDriver(TypeDB.DEFAULT_ADDRESS);
			driver.databases().create("typ101r6767111112");
			TypeDBSession session = driver.session("typedb", TypeDBSession.Type.DATA);
			TypeDBTransaction tx = session.transaction(TypeDBTransaction.Type.WRITE);
			tx.close();
			session.close();
			driver.close();
	}
}
