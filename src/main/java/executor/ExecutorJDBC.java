package executor;

import exception.DBException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExecutorJDBC {
    private final Connection connection;

    public ExecutorJDBC(Connection connection) {
        this.connection = connection;
    }

    public void execUpdate(String update, Object[] params) throws DBException {
        // https://stackoverflow.com/questions/15761791/transaction-rollback-on-sqlexception-using-new-try-with-resources-block
        boolean initialAutocommit = false;
        try {
            initialAutocommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            PreparedStatement preparedTransactStatement = connection.prepareStatement(update);
            for (int i = 0; i < params.length; i++) {
                preparedTransactStatement.setObject(i + 1, params[i]);
            }
            preparedTransactStatement.executeUpdate();
            connection.commit();
        } catch (Throwable throwable) {
            // You may not want to handle all throwables, but you should with most, e.g.
            // Scala has examples: https://github.com/scala/scala/blob/v2.9.3/src/library/scala/util/control/NonFatal.scala#L1
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
            throw new DBException(throwable);
        } finally {
            if (connection != null) {
                try {
                    if (initialAutocommit) {
                        connection.setAutoCommit(true);
                    }
                    connection.close();
                } catch (Throwable e) {
                    // Use your own logger here. And again, maybe not catch throwable,
                    // but then again, you should never throw from a finally ;)
                    StringWriter out = new StringWriter();
                    e.printStackTrace(new PrintWriter(out));
                    System.err.println("Could not close connection " + out.toString());
                }
            }
        }
    }

    public <T> T execQuery(String query, Object[] params,
                            ResultHandler<T, ResultSet, ? extends Throwable> handler)
            throws DBException {
        // https://stackoverflow.com/questions/15761791/transaction-rollback-on-sqlexception-using-new-try-with-resources-block
        T returnValue;
        boolean initialAutocommit = false;
        try {
            initialAutocommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            PreparedStatement stmt = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet result = stmt.executeQuery();
            returnValue = handler.handle(result);
            connection.commit();
            return returnValue;
        } catch (Throwable throwable) {
            // You may not want to handle all throwables, but you should with most, e.g.
            // Scala has examples: https://github.com/scala/scala/blob/v2.9.3/src/library/scala/util/control/NonFatal.scala#L1
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
            throw new DBException(throwable);
        } finally {
            if (connection != null) {
                try {
                    if (initialAutocommit) {
                        connection.setAutoCommit(true);
                    }
                    connection.close();
                } catch (Throwable e) {
                    // Use your own logger here. And again, maybe not catch throwable,
                    // but then again, you should never throw from a finally ;)
                    StringWriter out = new StringWriter();
                    e.printStackTrace(new PrintWriter(out));
                    System.err.println("Could not close connection " + out.toString());
                }
            }
        }
    }

}
