package executor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author v.chibrikov
 *         <p>
 *         Пример кода для курса на https://stepic.org/
 *         <p>
 *         Описание курса и лицензия: https://github.com/vitaly-chibrikov/stepic_java_webserver
 */
public interface ResultHandler<T, U, V extends Throwable> {
	T handle(U resultSet) throws V;
}
