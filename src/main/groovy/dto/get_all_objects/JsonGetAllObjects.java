package dto.get_all_objects;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "objectTypeId",
    "resultsPerPage",
    "includeAttributes",
    "objectSchemaId"
})
public class JsonGetAllObjects {

    @JsonProperty("objectTypeId")
    private int objectTypeId;
    @JsonProperty("resultsPerPage")
    private int resultsPerPage;
    @JsonProperty("includeAttributes")
    private boolean includeAttributes;
    @JsonProperty("objectSchemaId")
    private int objectSchemaId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("objectTypeId")
    public int getObjectTypeId() {
        return objectTypeId;
    }

    @JsonProperty("objectTypeId")
    public void setObjectTypeId(int objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    @JsonProperty("resultsPerPage")
    public int getResultsPerPage() {
        return resultsPerPage;
    }

    @JsonProperty("resultsPerPage")
    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = resultsPerPage;
    }

    @JsonProperty("includeAttributes")
    public boolean getIncludeAttributes() {
        return includeAttributes;
    }

    @JsonProperty("includeAttributes")
    public void setIncludeAttributes(boolean includeAttributes) {
        this.includeAttributes = includeAttributes;
    }

    @JsonProperty("objectSchemaId")
    public int getObjectSchemaId() {
        return objectSchemaId;
    }

    @JsonProperty("objectSchemaId")
    public void setObjectSchemaId(int objectSchemaId) {
        this.objectSchemaId = objectSchemaId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
