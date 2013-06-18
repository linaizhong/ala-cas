// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

/******************************************************************************\
 *  EXTERNAL SERVERS
 \******************************************************************************/
bie.baseURL = "http://bie.ala.org.au"
bie.searchPath = "/search"
biocache.baseURL = "http://biocache.ala.org.au"
spatial.baseURL = "http://spatial.ala.org.au"
ala.baseURL = "http://www.ala.org.au"
collectory.baseURL = "http://collections.ala.org.au"
bhl.baseURL = "http://bhlidx.ala.org.au"
speciesList.baseURL ="http://lists.ala.org.au"

userDetails.url ="http://auth.ala.org.au/userdetails/userDetails/"
userDetails.path ="getUserList"
alerts.baseUrl = "http://alerts.ala.org.au/ws/"
//brds.guidUrl = "http://cs.ala.org.au/bdrs-ala/bdrs/user/atlas.htm?surveyId=1&guid="
brds.guidUrl = "http://sightings.ala.org.au/"
collectory.threatenedSpeciesCodesUrl = collectory.baseURL + "/public/showDataResource"
ranking.readonly = false

/******************************************************************************\
 *  SECURITY
 \******************************************************************************/
security.cas.casServerName = 'https://auth.ala.org.au'
security.cas.uriFilterPattern = "/admin, /admin/.*"// pattern for pages that require authentication
security.cas.uriExclusionFilterPattern = "/images.*,/css.*/less.*,/js.*,.*json,.*xml"
security.cas.authenticateOnlyIfLoggedInPattern = "/species/.*" // pattern for pages that can optionally display info about the logged-in user
security.cas.loginUrl = 'https://auth.ala.org.au/cas/login'
security.cas.logoutUrl = 'https://auth.ala.org.au/cas/logout'
security.cas.casServerUrlPrefix = 'https://auth.ala.org.au/cas'
security.cas.bypass = false

nonTruncatedSources = ["http://www.environment.gov.au/biodiversity/abrs/online-resources/flora/main/index.html"]

auth.admin_role = "ROLE_ADMIN"

springcache {
    defaults {
        // set default cache properties that will apply to all caches that do not override them
        eternal = false
        diskPersistent = false
        timeToLive = 600
        timeToIdle = 600
    }
    caches {
        userListCache {
            // set any properties unique to this cache
            memoryStoreEvictionPolicy = "LRU"
        }
    }
}

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      //xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      //json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

grails.project.war.file = "bie-webapp2.war"
// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// enable query caching by default
grails.hibernate.cache.queries = true

// set per-environment serverURL stem for creating absolute links
environments {
    development {
        grails.logging.jul.usebridge = true
        grails.host = "http://dev.ala.org.au"
        grails.serverURL = "${grails.host}:8080/${appName}"
        bie.baseURL = grails.serverURL
        security.cas.appServerName = "${grails.host}:8080"
        security.cas.contextPath = "/${appName}"
        // cached-resources plugin - keeps original filenames but adds cache-busting params
        grails.resources.debug = true
      //  bie.baseURL = "http://diasbtest1-cbr.vm.csiro.au:8080/bie-service"
    }
    test {
        grails.logging.jul.usebridge = false
        grails.host = "bie-test.ala.org.au"
        grails.serverURL = "http://bie-test.ala.org.au"
        security.cas.appServerName = grails.serverURL
        security.cas.contextPath = ""
        log4j.appender.'errors.File'="/var/log/tomcat/biewebapp2-stacktrace.log"
    }
    production {
        grails.logging.jul.usebridge = false
        grails.host = "bie.ala.org.au"
        grails.serverURL = "http://bie.ala.org.au"
        security.cas.appServerName = grails.serverURL
        security.cas.contextPath = ""
        log4j.appender.'errors.File'="/var/log/tomcat6/biewebapp2-stacktrace.log"
    }
}


// log4j configuration
log4j = {
    appenders {
        environments {
            production {
                rollingFile name: "bie-prod",
                    maxFileSize: 104857600,
                    file: "/var/log/tomcat6/biewebapp2.log",
                    threshold: org.apache.log4j.Level.DEBUG,
                    layout: pattern(conversionPattern: "%-5p: %d [%c{1}]  %m%n")
                rollingFile name: "stacktrace",
                    maxFileSize: 1024,
                    file: "/var/log/tomcat6/biewebapp2-stacktrace.log"
            }
            development{
                console name: "stdout", layout: pattern(conversionPattern: "%d [%c{1}]  %m%n"),
                    threshold: org.apache.log4j.Level.DEBUG
            }
        }
    }

    root {
        debug  'bie-prod'
    }

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
	       'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
	       'org.codehaus.groovy.grails.commons', // core / classloading
	       'org.codehaus.groovy.grails.plugins', // plugins
           'org.springframework.jdbc',
           'org.springframework.transaction',
           'org.codehaus.groovy',
           'org.grails',
           'org.grails.plugin.resource',
           'org.apache',
           'grails.spring',
           'grails.util.GrailsUtil',
           'net.sf.ehcache'

    debug  'ala'
}

appContext = grails.util.Metadata.current.'app.name'
headerAndFooter.baseURL = 'http://www2.ala.org.au/commonui'
ala.baseURL = "http://www.ala.org.au"
bie.baseURL = "http://bie.ala.org.au"
bie.searchPath = "/search"
grails.project.groupId = "au.org.ala" // change this to alter the default package name and Maven publishing destination
