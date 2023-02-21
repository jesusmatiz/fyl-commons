package co.com.ingeneo.utils.jndi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceLocator {
	
    private static Map<String, Object> cache = new HashMap();

    public static <T> T lookup(Class<T> clazz, String beanName){
        Object obj = cache.get(beanName);
        if (obj == null) {
            try {
                Context ctx = new InitialContext();
                log.info("lookup class-"+clazz);//TODO eliminar finalizar pruebas
                log.info("lookup bean"+beanName);//TODO eliminar finalizar pruebas
                obj = ctx.lookup(beanName);
                cache.put(beanName, obj);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex.getCause());
            }
        }
        return (T)obj;
    }

    public static <T> T lookup(Properties props, Class<T> clazz, String beanName){
        Object obj = cache.get(beanName);
        if (obj == null) {
            try {
                Context ctx = new InitialContext(props);
                obj = ctx.lookup(beanName);
                cache.put(beanName, obj);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex.getCause());
            }
        }
        return (T)obj;
    }

    public static <T> T lookup(Class<T> clazz, String beanName, T testObject) {
        if (testObject == null) {
            return lookup(clazz, beanName);
        }
        return testObject;
    }
    
    /**
     * 
     * @param <T>
     * @param clazz
     * @param beanName
     * @return
     */
    public static <T> T lookup(Class<T> clazz) {
    	String jndi = EJBPropertiesLoader.getInstance().getProperty(clazz.getCanonicalName());
        Object obj = cache.get(jndi);
        if (obj == null) {
            try {
                obj = new InitialContext().lookup(jndi);
                cache.put(jndi, obj);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage(), ex.getCause());
            }
        }
        return (T)obj;
    }

    private static Context createInitialContext() throws NamingException {
        Hashtable<String, Object> jndiProperties = new Hashtable<>();
        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        jndiProperties.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
        return new InitialContext(jndiProperties);
    }
}
