package dto.update_object

class CreateJSONUpdateObject {
    UpdateAttributeOfObjectDTO createUpdateAttributeOfObjectDTO(def id, def value) {
        UpdateAttributeOfObjectDTO updateAttributeOfObjectDTO = new UpdateAttributeOfObjectDTO()
        updateAttributeOfObjectDTO.setObjectTypeAttributeId(id)//idID

        List<UpdateObjectAttributeValueDTO> updateObjectAttributeValueIdDTOList = new ArrayList<>()
        UpdateObjectAttributeValueDTO updateObjectAttributeValueIdDTO = new UpdateObjectAttributeValueDTO()
        updateObjectAttributeValueIdDTO.setValue(value)//valueID
        updateObjectAttributeValueIdDTOList.add(updateObjectAttributeValueIdDTO)

        updateAttributeOfObjectIdDTO.setUpdateObjectAttributeValueDTOS(updateObjectAttributeValueIdDTOList)
        return updateAttributeOfObjectIdDTO
    }

    JsonUpdateObjectDTO createJsonUpdateObjectDTO(def idId, def valueId, def idName, def valueName, def idFullName, def valueFullName) {
        JsonUpdateObjectDTO jsonUpdateObjectDTO = new JsonUpdateObjectDTO()
        List<UpdateAttributeOfObjectDTO> updateAttributeOfObjectDTOList = new ArrayList<>()
//Id
        UpdateAttributeOfObjectDTO updateAttributeOfObjectIdDTO = createUpdateAttributeOfObjectDTO(idId, valueId)
//Name
        UpdateAttributeOfObjectDTO updateAttributeOfObjectNameDTO = createUpdateAttributeOfObjectDTO(idName, valueName)
//FullName
        UpdateAttributeOfObjectDTO updateAttributeOfObjectFullNameDTO = createUpdateAttributeOfObjectDTO(idFullName, valueFullName)

        updateAttributeOfObjectDTOList.add(updateAttributeOfObjectIdDTO)
        updateAttributeOfObjectDTOList.add(updateAttributeOfObjectNameDTO)
        updateAttributeOfObjectDTOList.add(updateAttributeOfObjectFullNameDTO)
        jsonUpdateObjectDTO.setUpdateAttributeOfObjectDTOS(updateAttributeOfObjectDTOList)
        return jsonUpdateObjectDTO
    }
}
