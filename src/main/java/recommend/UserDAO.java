    package recommend;

    import org.hibernate.Session;
    import org.hibernate.SessionFactory;
    import org.hibernate.Transaction;
    import recommend.User;

    public class UserDAO {
        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }
         
        public void save(User u) {
            Session session = this.sessionFactory.openSession();
            Transaction tx = session.beginTransaction();
            session.persist(u);
            tx.commit();
            session.close();
        }
     
    }