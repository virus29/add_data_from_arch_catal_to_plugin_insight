import ch.qos.logback.classic.encoder.*
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy
import ch.qos.logback.core.util.FileSize
import groovy.util.logging.Slf4j
import org.slf4j.LoggerFactory

@Slf4j
class SaveChangesToPluginIsight {
    static {
        new RollingFileAppender().with {
            name = 'addobjarchitcatal'
            file = '/log/groovy.log'
            // the policy to roll files
            rollingPolicy = new TimeBasedRollingPolicy().with {
                context = LoggerFactory.getILoggerFactory()
                // file name pattern for the rolled files
                fileNamePattern = '/log/groovy.%date{yyyy-MM-dd}.%i.log'
                // the maximum number of files to be keeped.
                maxHistory = 10
                timeBasedFileNamingAndTriggeringPolicy = new SizeAndTimeBasedFNATP().with {
                    context = LoggerFactory.getILoggerFactory()
                    // the max size of each rolled file
                    setMaxFileSize(FileSize.valueOf('3MB'))
                }
            }
            context = LoggerFactory.getILoggerFactory()
            encoder = new PatternLayoutEncoder().with {
                context = LoggerFactory.getILoggerFactory()
                pattern = "%date{HH:mm:ss.SSS} [%thread] %-5level %logger{35} - %msg%n"
                start()
                it
            }
            start()
            log.addAppender(it)
        }
    }

    public static void main(String[] args) {

        String username = "svc_asup"
        String password = "P@ssw0rd"
        String workstation = "JIRASUP2"
        String domain = "PORTAL"

        String authorization = "Basic ${"$username:$password".bytes.encodeBase64().toString()}"
        String inURI = "http://s39jira06.vip.cbr.ru:8094"

        Map<String, String> authorizationAndURI = new HashMap<String, String>()
        authorizationAndURI.put("authorization", authorization)
        authorizationAndURI.put("inURI", inURI)

        Map<String, String> ntlmAuthorization = new HashMap<String, String>()
        ntlmAuthorization.put("username", username)
        ntlmAuthorization.put("password", password)
        ntlmAuthorization.put("workstation", workstation)
        ntlmAuthorization.put("domain", domain)

        String nameObjectScheme = "Temporary"
        String nameObjectType = "TestUsersObjectType"
        String nameAttributeIdObjectType = "ID_architectural_catalog"
        String nameAttributeNameObjectType = "Name"
        String nameAttributeFullNameObjectType = "Full name"

//String createJsonString = '{"objectTypeId":$idObjectType,"attributes":[{"objectTypeAttributeId":$idAttributeNameObjectType,"objectAttributeValues":[{"value": $valueAttributeNameObjectType}]},{"objectTypeAttributeId":$idAttributeFullNameObjectType,"objectAttributeValues":[{"value": $valueAttributeFullNameObjectType}]},{"objectTypeAttributeId":$idAttributeIdObjectType,"objectAttributeValues":[{"value": $valueAttributeIdObjectType}]}]}'
        String pathAllObjectSchemas = "/rest/insight/1.0/objectschema/list/"
        String idObjectScheme = new ObjectScheme().getIdObjectScheme(authorizationAndURI, pathAllObjectSchemas, nameObjectScheme) //= "22"

        String pathAllObjectTypes = "/rest/insight/1.0/objectschema/$idObjectScheme/objecttypes/flat/"
        String idObjectType = new ObjectTypes().getIdObjectType(authorizationAndURI, pathAllObjectTypes, nameObjectType) //= "21"

        String pathAllIdAttributes = "/rest/insight/1.0/objecttype/$idObjectType/attributes/"

        String pathAllObjects = "/rest/insight/1.0/object/navlist/iql"
        String bodyGetAllObjects = '{"objectTypeId":'+idObjectType+',"objectSchemaId":'+idObjectScheme+',"resultsPerPage": 1000,"includeAttributes": true}'
        String pathToCreateObject = "/rest/insight/1.0/object/create"
        String pathArchitecturalCatalog = "http://simr.cbr.ru/sites/Dep_IT/Upr_AS/Otd_MITA/ITSolutions/_api/web/lists/GetByID('810f80a5-ffb3-4855-af9d-a692029b6b61')/items"

        Map<String, String> nameAttributesObjectType = new HashMap<String, String>()
        nameAttributesObjectType.put("nameAttributeIdObjectType", nameAttributeIdObjectType)//201
        nameAttributesObjectType.put("nameAttributeNameObjectType", nameAttributeNameObjectType)//82
        nameAttributesObjectType.put("nameAttributeFullNameObjectType", nameAttributeFullNameObjectType)//203

        TransferObject to = new ObjectsAndAttributes().getIdAttributesAndAllObjects(authorizationAndURI, pathAllObjects,bodyGetAllObjects, nameAttributesObjectType,pathAllIdAttributes)
        log.info("TransferObject = "+ to)

        Map<String, String> idsAndAttribObjType = new HashMap<String, String>()
        idsAndAttribObjType.put("idObjectType", (String)idObjectType)
        idsAndAttribObjType.put("idAttributeNameObjectType",(String)to.getMapIdAttributesObjectType().get("idAttributeNameObjectType"))
        idsAndAttribObjType.put("idAttributeFullNameObjectType",(String)to.getMapIdAttributesObjectType().get("idAttributeFullNameObjectType"))
        idsAndAttribObjType.put("idAttributeIdObjectType", (String)to.getMapIdAttributesObjectType().get("idAttributeIdObjectType"))
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")
        List<String> idObjects =to.getIdObjectsList()
        log.info("3333333333333333333333333333333")
//        new CreateObject().createObject(authorizationAndURI, idsAndAttribObjType, idObjects, pathToCreateObject)
        new CreateObject().createObject(authorizationAndURI, ntlmAuthorization, idsAndAttribObjType, idObjects, pathArchitecturalCatalog, pathToCreateObject)
    }
}
