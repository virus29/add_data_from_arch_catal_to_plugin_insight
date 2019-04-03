import dto.get_all_objects.JsonGetAllObjects
import dto.update_object.JsonUpdateObjectDTO
import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Slf4j
class ObjectsAndAttributes {

    private int idObjectScheme = new ObjectScheme().getIdObjectScheme()        //= "22"
    private int idObjectType = new ObjectType().getIdObjectType(idObjectScheme)  //= "21"

    Map getIdAttributesOfObjectType() {
        String pathAllIdAttributes = "/rest/insight/1.0/objecttype/$idObjectType/attributes/"
        Map<String, String> mapIdAttributesObjectType = new HashMap<String, String>()
        def http = new HTTPBuilder(SaveChangesToPluginIsight.inURI)
        http.request(Method.GET, ContentType.JSON) {
            req ->
                uri = pathAllIdAttributes
                headers.'Authorization' = AuthorizationParameters.BasicAuthorization
                headers.'Accept' = 'application/json;charset=UTF-8'
                response.success = { resp, json ->
                    json.each {
                        if (it.name.equals(SaveChangesToPluginIsight.nameAttributeIdObjectType)) {
                            mapIdAttributesObjectType.put("idAttributeIdObjectType", (String) it.id)
                        }
                        if (it.name.equals(SaveChangesToPluginIsight.nameAttributeNameObjectType)) {
                            mapIdAttributesObjectType.put("idAttributeNameObjectType", (String) it.id)
                        }
                        if (it.name.equals(SaveChangesToPluginIsight.nameAttributeFullNameObjectType)) {
                            mapIdAttributesObjectType.put("idAttributeFullNameObjectType", (String) it.id)
                        }
                        //New element
//                        if (it.name.equals(SaveChangesToPluginIsight.nameAttributeNNNNNNNNNNNNNNNNNNNNNNNNNNNObjectType)) {
//                            mapIdAttributesObjectType.put("idAttributeNNNNNNNNNNNNNNNNNNNObjectType", (String) it.id)
//                        }
                    }
                }
                response.failure = { resp ->
                    log.error "Request Id UpdateAttributeOfObjectDTO failed with status " + resp.status
                    int i = 0
                    resp.headers.each {
                        i++
                        log.error("Resonse header : " + i + " - " + it)
                    }
                    return "Request failed with status " + resp.status
                }
        }
        return mapIdAttributesObjectType
    }

    List getIdOfObjects() {
        String pathAllObjects = "/rest/insight/1.0/object/navlist/iql"
//        String bodyGetAllObjects = '{"objectTypeId":' + idObjectType + ',"objectSchemaId":' + idObjectScheme + ',"resultsPerPage": 1000,"includeAttributes": true}'

        JsonGetAllObjects jsonGetAllObjects = new JsonGetAllObjects()
        jsonGetAllObjects.setObjectTypeId(idObjectType)
        jsonGetAllObjects.setResultsPerPage(1000)//устанавливаем по умолчанию
        jsonGetAllObjects.setIncludeAttributes(true)//устанавливаем по умолчанию
        jsonGetAllObjects.setObjectSchemaId(idObjectScheme)

        List ObjectsList = new ArrayList<String>()
        def http = new HTTPBuilder(SaveChangesToPluginIsight.inURI)
        http.request(Method.POST, ContentType.JSON,) {
            request ->
                uri.path = pathAllObjects
                headers.'Authorization' = AuthorizationParameters.BasicAuthorization
                body = jsonGetAllObjects
                response.success = { resp, json ->
                    json.objectEntries.each {
                        it.setUpdateAttributeOfObjectDTOS.each {
                            if (it.setUpdateObjectAttributeValueDTOS.value[0] != null && (it.objectTypeAttributeId).equals(mapIdAttributesObjectType.get("idAttributeIdObjectType"))) {
                                idObjectsList.add((String) it.setUpdateObjectAttributeValueDTOS.value[0])
                                log.info("attributeValues[0] = " + it.setUpdateObjectAttributeValueDTOS.value[0])
                            }
                        }
                    }
                    log.info("idObjectsList = " + idObjectsList)
                    log.info("mapIdAttributesObjectType = " + mapIdAttributesObjectType)
                }
                response.failure = { resp ->
                    log.error "Request Id Object failed with status " + resp.status
                    int i = 0
                    resp.headers.each {
                        i++
                        log.error("Resonse header : " + i + " - " + it)
                    }
                    return "Request failed with status " + resp.status
                }
        }
        return idObjectsList
    }

    TransferObject getIdAttributesAndAllObjects(Map authorizationAndURI, String pathAllObjects, String bodyGetAllObjects, Map nameAttributesObjectType, idObjectType) {

        TransferObject to = new TransferObject()
        to.setIdObjectsList(idObjectsList)
        to.setMapIdAttributesObjectType(mapIdAttributesObjectType)

        log.info("TransferObject = " + to.getMapIdAttributesObjectType())
        log.info("TransferObject = " + to.getIdObjectsList())
        log.info("TransferObject = " + to)
        return to
    }
}
