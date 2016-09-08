package configs;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {

    private ApplicationContext ctx;

    @Override
    public void onStart(Application app) {
    	super.onStart(app);
        ctx = new AnnotationConfigApplicationContext(PersistenceJpaConfig.class, AppConfig.class);
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) {
    	try {
    		return ctx.getBean(clazz);
    	} catch(BeansException e) {
    		return null;
    	}
    }
  
}
