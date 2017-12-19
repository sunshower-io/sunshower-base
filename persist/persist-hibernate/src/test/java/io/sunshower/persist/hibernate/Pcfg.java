package io.sunshower.persist.hibernate;

import io.sunshower.persistence.annotations.Persistence;

/**
 * Created by haswell on 3/11/17.
 */
@Persistence(
        id = "audit",
        scannedPackages = {
                "test.entities",
                "test.entities.one2one",
                "test.entities.one2many",
                "test.entities.many2many",
        },
        migrationLocations = "classpath:h2"
)
public class Pcfg {
}
