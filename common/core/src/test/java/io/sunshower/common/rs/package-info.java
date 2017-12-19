
@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(
                value = IdentifierConverter.class,
                type = Identifier.class
        )
})
package io.sunshower.common.rs;

import io.sunshower.common.Identifier;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;