package logica;

import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import logica.HibernateUtil;

import org.hibernate.Query;
import org.hibernate.Session;

public class preprocess{


  public static void main(String[] args) throws IOException {

    ReadCSV obj = new ReadCSV();
    obj.run();

    Session session = HibernateUtil.getSessionFactory().openSession();
    String hql = "SELECT SUM(Positiv) FROM HarvardDictionary WHERE Entry IN (" + ReadCSV.usrCommentsSeparated[0] +")";
    Query query = session.createQuery(hql);
    List results = query.list();
    System.out.println(results.get(0));

    //System.out.println(ReadCSV.usrCommentsSeparated[0]);
    
  }


}


