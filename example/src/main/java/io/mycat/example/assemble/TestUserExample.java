package io.mycat.example.assemble;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TestUserExample {

    Connection getMySQLConnection(int port) throws SQLException {
        String username = "root";
        String password = "123456";
        String url = "jdbc:mysql://127.0.0.1:" + port;
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl(url);
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(password);
        mysqlDataSource.setServerTimezone("UTC");
        return mysqlDataSource.getConnection();
    }

    @Test
    public void testCreateUser() throws SQLException {
        try (Connection connection = getMySQLConnection(8066)) {
            AssembleExample.execute(connection, "/*+ mycat:addUser{\n" +
                    "\t\"username\":\"user\",\n" +
                    "\t\"password\":\"\",\n" +
                    "\t\"ip\":\"127.0.0.1\",\n" +
                    "\t\"transactionType\":\"xa\"\n" +
                    "} */");
            List<Map<String, Object>> maps = AssembleExample.executeQuery(connection, "/*+ mycat:showUsers */");
            Assert.assertTrue(maps.stream().map(i -> i.get("username")).anyMatch(i -> i.equals("user")));
            AssembleExample.execute(connection, "/*+ mycat:removeUser{\n" +
                    "\t\"username\":\"user\"" +
                    "} */");
            List<Map<String, Object>> maps2 = AssembleExample.executeQuery(connection, "/*+ mycat:showUsers */");
            Assert.assertFalse(maps2.stream().map(i -> i.get("username")).anyMatch(i -> i.equals("user")));
            System.out.println();
        }
    }
}
