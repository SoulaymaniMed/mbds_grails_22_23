package com.mbds.grails

import net.sf.ehcache.pool.Size

class Illustration {

    String filename

    static belongsTo = [annonce: Annonce]

    static constraints = {
        filename nullable: false, blank: false, Size: 5.50

    }
}
