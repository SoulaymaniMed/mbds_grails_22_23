package com.mbds.grails

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AnnonceController {

    AnnonceService annonceService
    SpringSecurityService springSecurityService
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond annonceService.list(params), model:[annonceCount: annonceService.count()]
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def indexClient(Integer max){
        params.max=Math.min(max ?:10,100)
        def currentUser = springSecurityService.getCurrentUser()
        def y = Annonce.findAllByAuthor(currentUser)
        respond y,model:[annonceCount: annonceService.count()]
}

    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def show(Long id) {
        respond annonceService.get(id)
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def create() {
        respond new Annonce(params)
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def save(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*' { respond annonce, [status: CREATED] }
        }
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def edit(Long id) {
        respond annonceService.get(id)
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR','ROLE_CLIENT'])
    def update(Annonce annonce) {
        if (annonce == null) {
            notFound()
            return
        }

        try {
            annonceService.save(annonce)
        } catch (ValidationException e) {
            respond annonce.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'annonce.label', default: 'Annonce'), annonce.id])
                redirect annonce
            }
            '*'{ respond annonce, [status: OK] }
        }
    }
    @Secured(['ROLE_ADMIN','ROLE_CLIENT'])
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        annonceService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'annonce.label', default: 'Annonce'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'annonce.label', default: 'Annonce'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
