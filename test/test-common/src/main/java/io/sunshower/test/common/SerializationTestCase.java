
package io.sunshower.test.common;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@SuppressWarnings("unchecked")
public class SerializationTestCase {


    private final JAXBContext context;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;


    public SerializationTestCase(SerializationAware.Format format, Class... bound) {
        this(format, false, bound);
    }


    public SerializationTestCase(SerializationAware.Format format, boolean includeRoot, Class... bound) {
        this.context = SerializationAware.createContext(bound);

        try {
            this.marshaller = this.context.createMarshaller();
            this.marshaller.setProperty("eclipselink.json.include-root", includeRoot);
        } catch (JAXBException var5) {
            throw new RuntimeException(var5);
        }

        try {
            this.unmarshaller = this.context.createUnmarshaller();
            this.unmarshaller.setProperty("eclipselink.json.include-root", includeRoot);
        } catch (JAXBException var4) {
            throw new RuntimeException(var4);
        }

        this.setFormat(format);
    }


    protected void setFormat(SerializationAware.Format format) {
        try {
            this.unmarshaller.setProperty(format.getPropertyName(), format.getPropertyValue());
            this.marshaller.setProperty(format.getPropertyName(), format.getPropertyValue());
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T copy(T input) {
        return copy(input, (Class<T>) input.getClass());
    }

    public <T> T copy(T input, Class<T> type) {
        return this.read(this.write(input), type);
    }

    public <T> T read(String t) {
        try {
            return (T) this.unmarshaller.unmarshal(new StringReader(t));
        } catch (JAXBException var3) {
            throw new RuntimeException(var3);
        }
    }

    public <T> T read(String t, Class<T> type) {
        try {
            return (T) this.unmarshaller.unmarshal(
                    new StreamSource(new StringReader(t)), type).getValue();
        } catch (JAXBException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> T read(InputStream inputStream, Class<T> type) {
        try {
            return (T) this.unmarshaller.unmarshal(
                    new StreamSource(
                            new InputStreamReader(inputStream)), type).getValue();
        } catch (JAXBException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> void write(T value, OutputStream output) {
        try {
            this.marshaller.marshal(value, output);
        } catch (JAXBException var4) {
            throw new RuntimeException(var4);
        }
    }

    public <T> String write(T value) {
        try {
            StringWriter e = new StringWriter();
            this.marshaller.marshal(value, e);
            return e.toString();
        } catch (JAXBException var3) {
            throw new RuntimeException(var3);
        }
    }
}
