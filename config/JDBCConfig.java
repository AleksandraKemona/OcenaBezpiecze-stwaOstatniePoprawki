
package pl.lodz.p.it.spjava.e11.sa.config;

import java.sql.Connection;
import javax.annotation.sql.DataSourceDefinition;

@DataSourceDefinition( // Ta pula połączeń jest na potrzeby operacji realizowanych przez moduł aplikacji
        name = "java:app/jdbc/SafetyAssessementDS",
        className = "org.apache.derby.jdbc.ClientDataSource",
        user = "safety",
        password = "safety",
        serverName = "localhost",
        portNumber = 1527,
        databaseName = "SafetyAssessementDB",
        transactional = true,
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED
)

public class JDBCConfig {
    
}
