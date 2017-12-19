package io.sunshower.common.rs;

import org.opensky.Definition;
import org.opensky.OpenSky;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

/**
 * Created by haswell on 4/7/17.
 */
@OpenSky
public class OpenSkyWriterInterceptor implements WriterInterceptor {
    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        Class<?> type = context.getType();
        if (type != null) {
            final Definition definition =
                    type.getAnnotation(Definition.class);
            MediaType mediaType =
                    context.getMediaType();
            context.setMediaType(MediaType.valueOf(
                    String.format("application/%s+%s",
                            resolveRoot(definition, mediaType),
                            resolveLeaf(definition, mediaType)
                    )
            ));
        }
    }

    private String resolveLeaf(Definition definition, MediaType mediaType) {
        return mediaType.getSubtype();
    }

    private String resolveRoot(Definition definition, MediaType mediaType) {
        return String.format("%s-%s-%s",
                definition.root(),
                definition.organization(),
                definition.type()
        );
    }
}
