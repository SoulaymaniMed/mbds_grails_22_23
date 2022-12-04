package com.mbds.grails

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import grails.plugin.springsecurity.SpringSecurityService
import static org.springframework.http.HttpStatus.*

class IllustrationController {

    IllustrationService illustrationService
    SpringSecurityService springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond illustrationService.list(params), model:[illustrationCount: illustrationService.count()]
    }


    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def show(Long id) {
        respond illustrationService.get(id)
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def create() {
        respond new Illustration(params)
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def save(Illustration illustration) {
        if (illustration == null) {
            notFound()
            return
        }

        try {
            illustrationService.save(illustration)
        } catch (ValidationException e) {
            respond illustration.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'illustration.label', default: 'Illustration'), illustration.id])
                redirect illustration
            }
            '*' { respond illustration, [status: CREATED] }
        }
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def edit(Long id) {
        respond illustrationService.get(id)
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def update(Illustration illustration) {
        if (illustration == null) {
            notFound()
            return
        }

        try {
            illustrationService.save(illustration)
        } catch (ValidationException e) {
            respond illustration.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'illustration.label', default: 'Illustration'), illustration.id])
                redirect illustration
            }
            '*'{ respond illustration, [status: OK] }
        }
    }
    @Secured(['ROLE_ADMIN','ROLE_CLIENT'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        illustrationService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'illustration.label', default: 'Illustration'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'illustration.label', default: 'Illustration'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
