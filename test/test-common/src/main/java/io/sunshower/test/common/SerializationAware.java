package io.sunshower.test.common;


import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Properties;

public class SerializationAware {


    public enum Format {
        XML("eclipselink.media-type", "application/xml"),
        JSON("eclipselink.media-type", "application/json");

        private final String propertyName;
        private final String propertyValue;

        Format(String propertyName, String propertyValue) {
            this.propertyName = propertyName;
            this.propertyValue = propertyValue;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public String getPropertyValue() {
            return propertyValue;
        }
    }


    public static javax.xml.bind.JAXBContext createContext(Class<?>... types) {
        try {
            final Class<?>[] atypes = new Class<?>[types.length];
            for(int i = 0; i < types.length; i++) {
                atypes[i] = types[i];
            }
            final Properties properties = new Properties();
            return JAXBContextFactory.createContext(atypes, new Properties());
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }


    public static Marshaller createMarshaller(Format format, Class<?>... types) {
        try {
            Marshaller marshaller = createContext(types).createMarshaller();
            marshaller.setProperty(format.propertyName, format.propertyValue);
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
            return marshaller;
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Unmarshaller createUnmarshaller(Format format, Class<?>...types) {
        try {
            Unmarshaller unmarshaller = createContext(types).createUnmarshaller();
            unmarshaller.setProperty(format.propertyName, format.propertyValue);
            unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
            return unmarshaller;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }




    @SuppressWarnings("unchecked")
    public static <T> T read(String t, Format format, Class<T> type) {
        try {
            return (T) createUnmarshaller(format, type).unmarshal(new StringReader(t));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public static <T> T read(InputStream inputStream, Format format, Class<T> type) {
        try {
            return (T) createUnmarshaller(format, type).unmarshal(new InputStreamReader(inputStream));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void write(T value, Format format, OutputStream output) {
        try {
            createMarshaller(format, value.getClass()).marshal(value, output);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String write(T value, Format format) {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller(format, value.getClass()).marshal(value, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }



}
