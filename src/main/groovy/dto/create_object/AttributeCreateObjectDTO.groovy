
package dto.create_object


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder([
    "objectTypeAttributeId",
    "objectAttributeValue"
])
public class AttributeCreateObjectDTO {

    @JsonProperty("objectTypeAttributeId")
    private int objectTypeAttributeId;
    @JsonProperty("objectAttributeValue")
    private List<CreateObjectAttributeValueDTO> objectAttributeValues = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("objectTypeAttributeId")
    public int getObjectTypeAttributeId() {
        return objectTypeAttributeId;
    }

    @JsonProperty("objectTypeAttributeId")
    public void setObjectTypeAttributeId(int objectTypeAttributeId) {
        this.objectTypeAttributeId = objectTypeAttributeId;
    }

    @JsonProperty("objectAttributeValue")
    public List<CreateObjectAttributeValueDTO> getObjectAttributeValues() {
        return objectAttributeValues;
    }

    @JsonProperty("objectAttributeValue")
    public void setObjectAttributeValues(List<CreateObjectAttributeValueDTO> objectAttributeValues) {
        this.objectAttributeValues = objectAttributeValues;
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
