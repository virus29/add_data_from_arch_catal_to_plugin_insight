import dto.create_object.JsonCreateObjectDTO
import dto.update_object.JsonUpdateObjectDTO
import dto.update_object.UpdateAttributeOfObjectDTO
import dto.update_object.UpdateObjectAttributeValueDTO
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class UpdateObject {

    void requestUpdateObject(Map authorizationAndURI, Object valueAttributeIdObjectType) {

        final def pathToUpdateObject = "/rest/insight/1.0/object/{id}"
        def http = new HTTPBuilder((Object) authorizationAndURI.get("inURI"))
        http.request(Method.PUT, ContentType.JSON) {
            request ->
                uri.path = pathToUpdateObject
                log.info("URI of pathToUpdateObject = " + pathToUpdateObject)
                headers.'Authorization' = authorizationAndURI.get("authorization")
                //headers.'Accept' = 'application/json;charset=UTF-8'
                body = jsonUpdateObjectDTO
                log.info("body of jsonUpdateObjectDTO = " + jsonUpdateObjectDTO)
                response.success = { resp ->
                    log.info("Object has created!!!!")
                    "Object Created!!!"
                }
                response.failure = { resp ->
                    log.error "Request to Create Object failed with status 5 " + resp.status
                    int i = 0
                    resp.headers.each {
                        i++
                        log.error("Resonse header : " + i + " - " + it)
                    }
                    "Request to create object failed with status" + resp.status
                }
        }
    }
}
