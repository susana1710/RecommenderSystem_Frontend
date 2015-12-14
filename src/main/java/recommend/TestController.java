package recommend;


import recommend.QuestionAnswer;
import recommend.NewUser;
import recommend.User;
import recommend.HibernateUtil;
import recommend.TestResults;
import org.hibernate.Query;
import org.hibernate.Session;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.Iterator;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
public class TestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();


    User usr = new User();
    User currentUser;


    
    public void saveInDB()throws Exception{
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(usr);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
        }        
    }
    public void updateInDB()throws Exception{
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(usr);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e){
            e.printStackTrace();
        }        
    }
    @RequestMapping("/test")
    public String greeting() {
        return "{\"key\": \"Hola\"}";
    }

    @RequestMapping(value="/test", method=RequestMethod.POST)
    public void postmethod(@RequestBody QuestionAnswer ans) throws Exception{
    	System.out.println("Llegue");
    	usr.setA1(ans.getValue());
    	System.out.println(usr.getA1());

        saveInDB();
        //System.out.println(ans.getValue());
    }
    //Recibe una lista. El primer elemento es el username, el segundo es el password
    @RequestMapping(value="/signUp", method=RequestMethod.POST)
    public ResponseEntity<User> signUp(@RequestBody QuestionAnswer ans) throws Exception{
    	System.out.println("Entre");
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	// La siguiente linea es solo si es un nuevo usuario
    	usr = new User();
    	String usrnm = ans.getUsername();
		String psswd = ans.getPassword();
        int val = ans.getValue();
		System.out.println(usrnm);
		System.out.println(psswd);
        System.out.println(val);
		// Verificar si ese nombre de usuario ya existe
		Query query = session.createQuery("from User where username = :usr ");
		query.setParameter("usr", usrnm);

		// Si la lista es vacia, el username no existe, es decir, esta disponible.
		// En ese caso, se guarda en la BD
		if (query.list().isEmpty()) {
			usr.setUsername(usrnm);
    		usr.setPassword(psswd);    		
        	saveInDB();
        	return new ResponseEntity<User>(HttpStatus.OK);
		} 
		// EL username ya existe
		else {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
    }

    @RequestMapping(value="/signIn", method=RequestMethod.POST)
    public ResponseEntity<User> signIn(@RequestBody QuestionAnswer ans) throws Exception{

    	Session session = HibernateUtil.getSessionFactory().openSession();
    	//usr = new User();
    	String usrnm = ans.getUsername();
		String psswd = ans.getPassword();
		// Verificar si ese nombre de usuario ya existe
		Query query = session.createQuery("from User where username = :usr and password = :psw");
		query.setParameter("usr", usrnm);
		query.setParameter("psw", psswd);

		// Si la lista es vacia, el username no existe, es decir, esta disponible.
		// En ese caso, se guarda en la BD
		if (query.list().isEmpty()) {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		} 
		// EL username ya existe
		else {	        
			List<?> list = query.list();
	        usr = (User)list.get(0);
        	return new ResponseEntity<User>(HttpStatus.OK);
		}
    }

    @RequestMapping(value="/test1", method=RequestMethod.POST)
    public void postA1(@RequestBody QuestionAnswer ans) throws Exception{
    	// La siguiente linea es solo si es un nuevo usuario
    	// usr = new User();
    	//	user.flushValues();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA1(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test2", method=RequestMethod.POST)
    public void postA2(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA2(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test3", method=RequestMethod.POST)
    public void postA3(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA3(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }

    @RequestMapping(value="/test4", method=RequestMethod.POST)
    public void postA4(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA4(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }

    @RequestMapping(value="/test5", method=RequestMethod.POST)
    public void postA5(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA5(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test6", method=RequestMethod.POST)
    public void postA6(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA6(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test7", method=RequestMethod.POST)
    public void postA7(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA7(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test8", method=RequestMethod.POST)
    public void postA8(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA8(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test9", method=RequestMethod.POST)
    public void postA9(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA9(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test10", method=RequestMethod.POST)
    public void postA10(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA10(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test11", method=RequestMethod.POST)
    public void postA11(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA11(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test12", method=RequestMethod.POST)
    public void postA12(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA12(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test13", method=RequestMethod.POST)
    public void postA13(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA13(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test14", method=RequestMethod.POST)
    public void postA14(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA14(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test15", method=RequestMethod.POST)
    public void postA15(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA15(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test16", method=RequestMethod.POST)
    public void postA16(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA16(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test17", method=RequestMethod.POST)
    public void postA17(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA17(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test18", method=RequestMethod.POST)
    public void postA18(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA18(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test19", method=RequestMethod.POST)
    public void postA19(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA19(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test20", method=RequestMethod.POST)
    public void postA20(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA20(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test21", method=RequestMethod.POST)
    public void postA21(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA21(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test22", method=RequestMethod.POST)
    public void postA22(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA22(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test23", method=RequestMethod.POST)
    public void postA23(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA23(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test24", method=RequestMethod.POST)
    public void postA24(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA24(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test25", method=RequestMethod.POST)
    public void postA25(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA25(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test26", method=RequestMethod.POST)
    public void postA26(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA26(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test27", method=RequestMethod.POST)
    public void postA27(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA27(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test28", method=RequestMethod.POST)
    public void postA28(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA28(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test29", method=RequestMethod.POST)
    public void postA29(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA29(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test30", method=RequestMethod.POST)
    public void postA30(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA30(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test31", method=RequestMethod.POST)
    public void postA31(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA31(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test32", method=RequestMethod.POST)
    public void postA32(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA32(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test33", method=RequestMethod.POST)
    public void postA33(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA33(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test34", method=RequestMethod.POST)
    public void postA34(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA34(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test35", method=RequestMethod.POST)
    public void postA35(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA35(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test36", method=RequestMethod.POST)
    public void postA36(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA36(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test37", method=RequestMethod.POST)
    public void postA37(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA37(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test38", method=RequestMethod.POST)
    public void postA38(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA38(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test39", method=RequestMethod.POST)
    public void postA39(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA39(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test40", method=RequestMethod.POST)
    public void postA40(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA40(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test41", method=RequestMethod.POST)
    public void postA41(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA41(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test42", method=RequestMethod.POST)
    public void postA42(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA42(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test43", method=RequestMethod.POST)
    public void postA43(@RequestBody QuestionAnswer ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA43(ans.getValue());
            usr.big5Scoring();
        }
        updateInDB();
    }
    @RequestMapping(value="/test44", method=RequestMethod.POST)
    public void postA44(@RequestBody QuestionAnswer ans) throws Exception{
    	Session session = HibernateUtil.getSessionFactory().openSession();
        String usrnm = ans.getUsername();
        String psswd = ans.getPassword();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA44(ans.getValue());
            usr.big5Scoring();
        }
        usr.big5Scoring();
        System.out.println(usr.getExtr());
        System.out.println(usr.getCons());
        System.out.println(usr.getAgr());
        System.out.println(usr.getNeur());
        System.out.println(usr.getOpn());
        updateInDB();
    }
    @RequestMapping(value="/submitTest", method=RequestMethod.POST)
    public void submitTest(@RequestBody List<QuestionAnswer> ans) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        Iterator<QuestionAnswer> iter = ans.iterator();
        QuestionAnswer actual = iter.next();
        String usrnm = actual.getUsername();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", usrnm);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ usrnm);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);
            usr.setA1(actual.getValue());
            usr.setA2(iter.next().getValue());
            usr.setA3(iter.next().getValue());
            usr.setA4(iter.next().getValue());
            usr.setA5(iter.next().getValue());
            usr.setA6(iter.next().getValue());
            usr.setA7(iter.next().getValue());
            usr.setA8(iter.next().getValue());
            usr.setA9(iter.next().getValue());
            usr.setA10(iter.next().getValue());
            usr.setA11(iter.next().getValue());
            usr.setA12(iter.next().getValue());
            usr.setA13(iter.next().getValue());
            usr.setA14(iter.next().getValue());
            usr.setA15(iter.next().getValue());
            usr.setA16(iter.next().getValue());
            usr.setA17(iter.next().getValue());
            usr.setA18(iter.next().getValue());
            usr.setA19(iter.next().getValue());
            usr.setA20(iter.next().getValue());
            usr.setA21(iter.next().getValue());
            usr.setA22(iter.next().getValue());
            usr.setA23(iter.next().getValue());
            usr.setA24(iter.next().getValue());
            usr.setA25(iter.next().getValue());
            usr.setA26(iter.next().getValue());
            usr.setA27(iter.next().getValue());
            usr.setA28(iter.next().getValue());
            usr.setA29(iter.next().getValue());
            usr.setA30(iter.next().getValue());
            usr.setA31(iter.next().getValue());
            usr.setA32(iter.next().getValue());
            usr.setA33(iter.next().getValue());
            usr.setA34(iter.next().getValue());
            usr.setA35(iter.next().getValue());
            usr.setA36(iter.next().getValue());
            usr.setA37(iter.next().getValue());
            usr.setA38(iter.next().getValue());
            usr.setA39(iter.next().getValue());
            usr.setA40(iter.next().getValue());
            usr.setA41(iter.next().getValue());
            usr.setA42(iter.next().getValue());
            usr.setA43(iter.next().getValue());
            usr.setA44(iter.next().getValue());
        }
        usr.big5Scoring();
        System.out.println(usr.getExtr());
        System.out.println(usr.getCons());
        System.out.println(usr.getAgr());
        System.out.println(usr.getNeur());
        System.out.println(usr.getOpn());
        updateInDB();
    }

    @RequestMapping(value="/testResults", method=RequestMethod.GET)
    public TestResults getTestResults(@RequestParam("username") String username) throws Exception{
        Session session = HibernateUtil.getSessionFactory().openSession();
        TestResults test = new TestResults();
        Query query = session.createQuery("from User where username = :usr ");
        query.setParameter("usr", username);
        if (query.list().isEmpty()) {
            System.out.println("Username: "+ username);
        }
        else {
            List<?> list = query.list();
            usr = (User)list.get(0);            
            test.setExtr(usr.getExtr());
            test.setAgr(usr.getAgr());
            test.setCons(usr.getCons());
            test.setNeur(usr.getNeur());
            test.setOpn(usr.getOpn());
        }
        return test;
    }

}