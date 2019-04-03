
package dto.create_object


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder([
    "objectTypeId",
    "attributes"
])
public class JsonCreateObjectDTO {

    @JsonProperty("objectTypeId")
    private int objectTypeId;
    @JsonProperty("attributes")
    private List<AttributeCreateObjectDTO> attributes = null;
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

    @JsonProperty("attributes")
    public List<AttributeCreateObjectDTO> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<AttributeCreateObjectDTO> attributes) {
        this.attributes = attributes;
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
