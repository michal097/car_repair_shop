import lombok.Getter;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class HibernateTest {
    private static SessionFactory sessionFactory;
    public static void main(String [] args){
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable throwable){
            System.err.println("failed to open sessionFactory");
            throw new ExceptionInInitializerError(throwable);
        }

        HibernateTest hibernateTest = new HibernateTest();

        Integer emp1 = hibernateTest.addClient("asd","dsa");
        System.out.println(emp1);


    }

    public Integer addClient(String name, String lname) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {

            Integer empl = null;
            transaction = session.beginTransaction();
            Emplo emplo = new Emplo(name, lname);
            empl = (Integer) session.save(emplo);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return 0;
    }
}


class Emplo{
    @Getter@Setter
    private String name;
    @Getter@Setter
    private String lname;

    public Emplo(String name, String lname){
        this.name=name;
        this.lname=lname;
    }
}

