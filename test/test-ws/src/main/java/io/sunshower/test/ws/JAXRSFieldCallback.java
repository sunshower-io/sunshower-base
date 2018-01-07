package io.sunshower.test.ws;

import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

import javax.ws.rs.Path;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JAXRSFieldCallback implements ReflectionUtils.FieldCallback {
    private final ResteasyWebTarget               webTarget;
    private       Object                          bean;
    private       ConfigurableListableBeanFactory factory;

    public JAXRSFieldCallback(ConfigurableListableBeanFactory factory, Object bean) {
        this.bean = bean;
        this.factory = factory;
        this.webTarget = (ResteasyWebTarget) factory.getBean(WebTarget.class);
    }

    @Override
    public void doWith(Field field)
            throws IllegalArgumentException, IllegalAccessException {
        if (!isInjectable(field)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        if (isRemote(field)) {
            injectRemote(bean, field);
        }
        if (isContext(field)) {
            injectContext(field);
        }

    }


    private void injectRemote(Object instance, Field field) {
        Class<?> type = field.getType();
        for (Class<?> current = type; type != null; type = type.getSuperclass()) {
            if (doInject(current, instance, field)) {
                return;
            }
            for (Class<?> iface : current.getInterfaces()) {
                if (doInject(iface, instance, field)) {
                    return;
                }
            }
        }

    }

    private boolean doInject(Class<?> current, Object instance, Field field) {
        if (current.isAnnotationPresent(Path.class)) {
            int modifiers = current.getModifiers();
            if (Modifier.isInterface(modifiers)) {
                Object value = webTarget.proxy(current);
                try {
                    field.set(instance, value);
                    return true;
                } catch(ReflectiveOperationException ex) {
                    throw new IllegalStateException(ex);
                }
            }
        }
        return false;
    }

    private void injectContext(Field field) {

    }

    private boolean isContext(Field field) {
        return field.isAnnotationPresent(Context.class);
    }


    private boolean isRemote(Field field) {
        return field.isAnnotationPresent(Remote.class);
    }

    private boolean isInjectable(Field field) {
        return isContext(field) || isRemote(field);
    }


}