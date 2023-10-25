package hu.bme.mit.trainbenchmark.typeql.process;


import com.vaticle.typedb.driver.TypeDB;
import com.vaticle.typedb.driver.api.TypeDBDriver;
import com.vaticle.typedb.driver.api.TypeDBSession;
import hu.bme.mit.trainbenchmark.typeql.schema.TypeQLSchema;
import org.apache.commons.lang.ObjectUtils;


public class TypeQLProcess {
	public static TypeDBDriver driver;
	public static String nameOfDatabase;
	public TypeQLProcess(String nameDB){
		this.nameOfDatabase = nameDB;
	}


	public static void checkExistence(){ 	//Checks if the database already exists
		driver = TypeDB.coreDriver(TypeDB.DEFAULT_ADDRESS);
		if(driver.databases().contains(nameOfDatabase)){
			driver.databases().get(nameOfDatabase).delete();
		}
	}

	public void setupDB(){		// client is open
		driver.databases().create(nameOfDatabase);
		TypeDBSession session = driver.session(nameOfDatabase, TypeDBSession.Type.SCHEMA);
		TypeQLSchema schema = new TypeQLSchema();
		schema.writeSchema(session);
		closeBD();
	}

	public void closeBD(){
		driver.close();
	}
}
