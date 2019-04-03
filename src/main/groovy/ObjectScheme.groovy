import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Slf4j
class ObjectScheme {
    private Object pathAllObjectSchemas = "/rest/insight/1.0/objectschema/list/"

    int getIdObjectScheme() {
        def http = new HTTPBuilder(SaveChangesToPluginIsight.inURI)
        int idObjectScheme
        http.request(Method.GET, ContentType.JSON) {
            req ->
                uri.path = pathAllObjectSchemas
                headers.'Authorization' = AuthorizationParameters.BasicAuthorization
                headers.'Accept' = 'application/json;charset=UTF-8'
                response.success = { resp, json ->
                    json.objectschemas.each {
                        if (it.name.equals(SaveChangesToPluginIsight.nameObjectScheme)) {
                            idObjectScheme = it.id
                            log.info("idObjectScheme = " + idObjectScheme)
                        }
                    }
                }
                response.failure = { resp ->
                    log.error "Request Object Scheme failed with status " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Resonse header : "+i+" - "+it)}
                    log.error "idObjectScheme is failed"
                    return "Request failed with status" + resp.status
                }
        }
        return idObjectScheme
    }
}
