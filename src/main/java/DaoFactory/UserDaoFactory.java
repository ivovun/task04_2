package DaoFactory;

import dao.UserDao;
import dao.UserHibernateDAO;
import dao.UserJdbcDAO;
import model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import util.DBHelper;

public class UserDaoFactory implements AbstractDaoFactory<UserDao> {
    @Override
    public UserDao create(String daoType) throws RuntimeException {
        if (daoType.equals("Hibernate")) {
            Configuration configuration = DBHelper.getConfiguration(new Class[] {User.class});
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
            builder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = builder.build();
            return new UserHibernateDAO(configuration.buildSessionFactory(serviceRegistry));
        } else if (daoType.equals("JDBC")) {
            return new UserJdbcDAO(DBHelper.getConnection());
        }
        throw  new RuntimeException("type of DAO is not found");
    }
}
