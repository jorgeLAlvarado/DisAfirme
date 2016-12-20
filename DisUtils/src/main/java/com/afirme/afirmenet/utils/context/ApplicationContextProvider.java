package com.afirme.afirmenet.utils.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * This class provides an application-wide access to the
 * Spring ApplicationContext! The ApplicationContext is
 * injected in a static method of the class "AppContext".
 *
 * Use AppContext.getApplicationContext() to get access
 * to all Spring Beans.
 *
 * @author Siegfried Bolz
 *
 * Modificado on Sep 17, 2015 7:26:55 PM by eguzher
 * <br>
 * <br>
 * @author epifanio.guzman@afirme.com
 * <br>
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // Wiring the ApplicationContext into a static method
        AppContext.setApplicationContext(ctx);
    }
}
