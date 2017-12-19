/**
 * Created by haswell on 4/7/17.
 */
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(
                value = IdentifierConverter.class,
                type = Identifier.class
        )
})
@XmlSchema(
        namespace = "https://opensky.org/arbiter/schema/v1",
        elementFormDefault = XmlNsForm.QUALIFIED,
        xmlns = @XmlNs(
                prefix = "opensky",
                namespaceURI = "https://opensky.org/arbiter/schema/v1"
        )

)
package io.sunshower.common.rs;

import io.sunshower.common.Identifier;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;