package mbds_grails_22_23

class UrlMappings {

    static mappings = {
        "/users" (resources: "user", excludes:  ['update', 'create'])
        "/users" (resources: "user", includes:  ['index','show','delete'])
        "/users" (resources: "user")

        "/announces"(resources: "annonce", excludes:['update', 'create'])
        "/announces"(resources : "annonce", includes:['index','show', 'delete'])

        "/announces"(resources: "annonce")

        "/illustrations"(resources: "illustration", excludes:['update', 'create'])
        "/illustrations"(resources: "illustration", includes:[ 'index','show', 'delete'])
        "/illustrations"(resources: "illustration")


        "/$controller/$action?/$id?(.$format)?"{ constraints {

            }
        }
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
