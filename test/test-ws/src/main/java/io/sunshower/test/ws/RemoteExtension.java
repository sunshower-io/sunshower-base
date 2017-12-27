package io.sunshower.test.ws;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.lang.reflect.Field;
import java.util.NoSuchElementException;


public class RemoteExtension extends SpringExtension {


    public RemoteExtension() {
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        Class<?> testClass = context.getTestClass()
                .orElseThrow(NoSuchElementException::new);
//        Object testInstance = context.getTestInstance()
//                .orElseThrow(NoSuchElementException::new);
        WebTarget client = createClient(context);
        inject(testClass, testInstance, client);
        super.postProcessTestInstance(testInstance, context);
    }


    private WebTarget createClient(ExtensionContext context) {
        ApplicationContext applicationContext = 
                SpringExtension.getApplicationContext(context);
        Environment environment = applicationContext.getEnvironment();
        Integer port = Integer.parseInt(String.valueOf(
                environment.getProperty("local.server.port")));
        Client client = ClientBuilder.newClient();
        WebTarget localhost = client.target(String.format("http://%s:%s/", "localhost", port));
        return localhost;
    }

    private void inject(Class<?> testClass, Object testInstance, WebTarget client) throws IllegalAccessException {
        
        for(
                Class<?> current = testClass; 
                current != null; 
                current = current.getSuperclass()
                ) {

            Field[] declaredFields = current.getDeclaredFields();
            for(Field field : declaredFields) {
                if(field.isAnnotationPresent(Remote.class)) {
                    injectField(testClass, testInstance, client, field);
                }
            }

        }
    }

    private void injectField(Class<?> testClass, Object testInstance, WebTarget client, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Class<?> type = field.getType();
        Class<?> actualType = findTypeAnnotatedWith(type, Path.class, testClass, field);
        Object   o = WebResourceFactory.newResource(actualType, client);
        field.set(testInstance, o);
    }

    private Class<?> findTypeAnnotatedWith(Class<?> type, Class<Path> pathClass, Class<?> testClass, Field field) {
        for(Class<?> current = type; current != null; current = current.getSuperclass()) {
            if(current.isAnnotationPresent(Path.class)) {
                return current;
            }
            Class<?>[] interfaces = current.getInterfaces();
            for(Class<?> iface : interfaces) {
                if(iface.isAnnotationPresent(Path.class)) {
                    return iface;
                }
            }
        }
        throw new IllegalStateException(String.format("Type '%s' of field '%s' in class " +
                "'%s' is not derived from any resource " +
                "(no @Path annotation found in type hierarchy)", type, testClass, field));
    }

}

