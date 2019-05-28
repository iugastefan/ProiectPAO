package servicii;

import oracle.jdbc.pool.OracleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

class ServiciuDB {

    private Connection conn;
    private static ServiciuDB instance;

    private ServiciuDB() {
        String jdbcUrl = null;
        String user = null;
        String pass = null;
        try (InputStream input = new FileInputStream("resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            jdbcUrl = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            pass = prop.getProperty("db.pass");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        OracleDataSource ds;
        try {
            ds = new OracleDataSource();
            ds.setURL(jdbcUrl);
            conn = ds.getConnection(user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ServiciuDB getInstance() {
        if (instance == null)
            instance = new ServiciuDB();
        return instance;
    }

    ResultSet executeQuery(String query) {
        Statement stmt;
        ResultSet rset = null;
        try {
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            rset = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Query incorect: " + query);
        }
        return rset;
    }
}
