package com.mbds.grails

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*


class UserController {
UserService userService
    SpringSecurityService springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        def currentUser =springSecurityService.getCurrentUser().username
        respond userService.list(params), model:[userCount: userService.count(), username: currentUser]
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def show(Long id) {
        respond userService.get(id)
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def create() {
        def roleList = Role.list()
        respond new User(params),model:[roleList:roleList]
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def save(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            def role = Role.get(params.role)
            userService.save(user)
            UserRole.create(user,role,true)
        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def edit(Long id) {
        def roleList=Role.list()
        respond userService.get(id),model:[roleList:roleList]
    }
    @Secured(['ROLE_ADMIN','ROLE_MODERATEUR'])
    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            def role=Role.get(params.role)
                userService.save(user)
                UserRole.removeAll(user)
                UserRole.create(user,role,true)
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }
    @Secured('ROLE_ADMIN')
    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        def user= userService.get(id)
        UserRole.removeAll(user)
        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
