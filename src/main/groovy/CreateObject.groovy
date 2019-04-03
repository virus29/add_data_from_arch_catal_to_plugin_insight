import dto.create_object.AttributeCreateObjectDTO
import dto.create_object.CreateObjectAttributeValueDTO
import dto.create_object.JsonCreateObjectDTO
import groovy.util.logging.Slf4j
import groovyx.net.http.AuthConfig
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import javax.xml.ws.http.HTTPException

@Slf4j
class CreateObject {
    void createObject(Map<String, String> authorizationAndURI, Map<String, String> ntlmAuthorization, Map<String,
        String> idsAndAttribObjType, List<String> idObjects, String pathArchitecturalCatalog, String pathToCreateObject) throws HTTPException {
        def http = new HTTPBuilder(pathArchitecturalCatalog)
        http.ignoreSSLIssues()
        AuthConfig ac = new AuthConfig(http)
        ac.ntlm(ntlmAuthorization.get("username"), ntlmAuthorization.get("password"), ntlmAuthorization.get("workstation"), ntlmAuthorization.get("domain"))
        http.request(Method.GET, ContentType.TEXT) {
            req ->
                response.success = { resp, xml ->
                    String xmlString = xml.text
                    def xmlFile = new XmlParser().parseText(xmlString)
                    xmlFile.each {
                        String valueAttributeIdObjectType
                        it.'**'.'d:Id'[0].each {
                            valueAttributeIdObjectType = it
                        }
                        log.error("idObjects = "+idObjects)
                        log.error("valueAttributeIdObjectType = "+valueAttributeIdObjectType)
                        if (!valueAttributeIdObjectType.equals(null) && !idObjects.contains(valueAttributeIdObjectType)) {
                            String valueAttributeNameObjectType
                            it.'**'.'d:Title'[0].each {
                                String valueAttributeNameObjectTypeOriginal = it as String
                                valueAttributeNameObjectType= valueAttributeNameObjectTypeOriginal.replaceAll(/\s/," ").trim().replaceAll(/[^\w А-Яа-я]/,"")
                            }
                            String valueAttributeFullNameObjectType
                            it.'**'.'d:ITS_DetailName'[0].each {
                                String valueAttributeFullNameObjectTypeOriginal = it as String
                                log.error(valueAttributeFullNameObjectTypeOriginal)
                                valueAttributeFullNameObjectType = valueAttributeFullNameObjectTypeOriginal.replaceAll(/\s/," ").trim().replaceAll(/[^\w А-Яа-я]/,"")
                                log.error(valueAttributeFullNameObjectType)
                            }
                            String idObjectType = idsAndAttribObjType.get("idObjectType")
                            String idAttributeIdObjectType = idsAndAttribObjType.get("idAttributeIdObjectType")
                            String idAttributeNameObjectType = idsAndAttribObjType.get("idAttributeNameObjectType")
                            String idAttributeFullNameObjectType = idsAndAttribObjType.get("idAttributeFullNameObjectType")
                            if (!idObjectType.equals(null) && !idAttributeIdObjectType.equals(null) && !idAttributeNameObjectType.equals(null) && !idAttributeFullNameObjectType.equals(null)) {
                                /**
                                 * Json для создание Объекта
                                 */
                                JsonCreateObjectDTO jsonCreateObjectDTO= new JsonCreateObjectDTO()
                                jsonCreateObjectDTO.setObjectTypeId(idObjectType) //Выбираемтип объекта по idObjectType
//ID
                                AttributeCreateObjectDTO attributeCreateObjectIdDTO =new AttributeCreateObjectDTO()
                                attributeCreateObjectIdDTO.setObjectTypeAttributeId(idAttributeIdObjectType) // idID
                                List<CreateObjectAttributeValueDTO> createObjectAttributeValueIdDTOList = new ArrayList<>()
                                CreateObjectAttributeValueDTO createObjectAttributeValueIdDTO = new CreateObjectAttributeValueDTO()
                                createObjectAttributeValueIdDTO.setValue(valueAttributeIdObjectType) //valueID
                                createObjectAttributeValueIdDTOList.add(createObjectAttributeValueIdDTO)
                                attributeCreateObjectIdDTO.setObjectAttributeValues(createObjectAttributeValueIdDTOList)
//Name
                                AttributeCreateObjectDTO attributeCreateObjectNameDTO =new AttributeCreateObjectDTO()
                                attributeCreateObjectNameDTO.setObjectTypeAttributeId(idAttributeNameObjectType) // idID
                                List<CreateObjectAttributeValueDTO> createObjectAttributeValueNameDTOList = new ArrayList<>()
                                CreateObjectAttributeValueDTO createObjectAttributeValueNameDTO = new CreateObjectAttributeValueDTO()
                                createObjectAttributeValueNameDTO.setValue(valueAttributeNameObjectType) //valueID
                                createObjectAttributeValueNameDTOList.add(createObjectAttributeValueNameDTO)
                                attributeCreateObjectNameDTO.setObjectAttributeValues(createObjectAttributeValueNameDTOList)
//FullName
                                AttributeCreateObjectDTO attributeCreateObjectFullNameDTO =new AttributeCreateObjectDTO()
                                attributeCreateObjectFullNameDTO.setObjectTypeAttributeId(idAttributeFullNameObjectType) // idID
                                List<CreateObjectAttributeValueDTO> createObjectAttributeValueFullNameDTOList = new ArrayList<>()
                                CreateObjectAttributeValueDTO createObjectAttributeValueFullNameDTO = new CreateObjectAttributeValueDTO()
                                createObjectAttributeValueFullNameDTO.setValue(valueAttributeFullNameObjectType) //valueID
                                createObjectAttributeValueFullNameDTOList.add(createObjectAttributeValueFullNameDTO)
                                attributeCreateObjectFullNameDTO.setObjectAttributeValues(createObjectAttributeValueFullNameDTOList)

                                List<AttributeCreateObjectDTO> attributeCreateObjectDTOList = new ArrayList<>()
                                attributeCreateObjectDTOList.add(attributeCreateObjectIdDTO)
                                attributeCreateObjectDTOList.add(attributeCreateObjectNameDTO)
                                attributeCreateObjectDTOList.add(attributeCreateObjectFullNameDTO)
                                jsonCreateObjectDTO.setAttributes(attributeCreateObjectDTOList)

//                                StringBuilder sb = new StringBuilder()
//                                sb.append('{"objectTypeId": ').append(idObjectType).append(',"updateAttributeOfObjectDTOS": [{"objectTypeAttributeId": ')
//                                        .append(idAttributeIdObjectType).append(',"updateObjectAttributeValueDTOS": [{"value": "')
//                                        .append(valueAttributeIdObjectType).append('"}]},{"objectTypeAttributeId": ')
//                                        .append(idAttributeNameObjectType).append(',"updateObjectAttributeValueDTOS": [{"value": "')
//                                        .append(valueAttributeNameObjectType).append('"}]},{"objectTypeAttributeId": ')
//                                        .append(idAttributeFullNameObjectType).append(',"updateObjectAttributeValueDTOS": [{"value": "')
//                                        .append(valueAttributeFullNameObjectType).append('"}]}]}')
//                                String createObjJsonString = sb
                                        log.info("createObjJsonString = " + createObjJsonString)
                                new RequestCreateObject().requestCreateObject(authorizationAndURI, pathToCreateObject, jsonCreateObjectDTO)
                            }
                        }
                        if(idObjects.contains(valueAttributeIdObjectType)){

                        }
                    }
                    if (xmlFile.link[0] != null) {
                        pathArchitecturalCatalog = (String) xmlFile.link[0].@'href'
                        createObject(authorizationAndURI, ntlmAuthorization, idsAndAttribObjType, idObjects, pathArchitecturalCatalog, pathToCreateObject)
                    }
                }
                response.failure = { resp ->
                    "Request Create Object failed with status " + resp.status
                    int i=0
                    resp.headers.each{
                        i++
                        log.error("Response header : "+i+" - "+it)}
                }
        }
    }
}
