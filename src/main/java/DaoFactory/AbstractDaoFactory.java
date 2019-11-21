package DaoFactory;

public interface AbstractDaoFactory<T>  {
    T create(String daoType)  throws RuntimeException;
}
