package co.gov.movilidadbogota.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ContextHelper implements ApplicationContextAware {

	private static final ContextHelper instance = new ContextHelper();
	private ApplicationContext applicationContext;

	private ContextHelper() {}

	public static ContextHelper getInstance() {
		return instance;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		instance.applicationContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
