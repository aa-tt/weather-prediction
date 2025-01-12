package life.outorin.myday.config;

import com.datastax.astra.client.DataAPIClient;
import com.datastax.astra.client.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraDBConfig {

    @Value("${spring.data.cassandra.keyspace-name}")
    private String keyspace;
    @Value("${spring.data.cassandra.token}")
    private String token;
    @Value("${spring.data.cassandra.contact-points}")
    private String dbUrl;

    @Bean
    public DataAPIClient dataAPIClient() {
        return new DataAPIClient(token);
    }

    @Bean
    public Database database(DataAPIClient client) {
        return client.getDatabase(dbUrl, keyspace);
    }
}