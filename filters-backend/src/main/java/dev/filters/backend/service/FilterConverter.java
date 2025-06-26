package dev.filters.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.backend.dto.*;
import dev.filters.backend.entity.FilterConditionEntity;
import dev.filters.backend.entity.FilterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FilterConverter {

  private final ObjectMapper objectMapper;

  public FilterConditionDTO deserializeConditionDetails(String conditionType, String jsonDetails) throws IOException {
    return switch (conditionType) {
      case "amount" -> objectMapper.readValue(jsonDetails, AmountConditionDTO.class);
      case "title" -> objectMapper.readValue(jsonDetails, TitleConditionDTO.class);
      case "date" -> objectMapper.readValue(jsonDetails, DateConditionDTO.class);
      default -> throw new IllegalArgumentException("Unknown condition type: " + conditionType);
    };
  }

  public String serializeConditionDTO(FilterConditionDTO conditionDTO) throws IOException {
    return objectMapper.writeValueAsString(conditionDTO);
  }

  public FilterDTO toDto(FilterEntity entity) {
    FilterDTO dto = FilterDTO.builder()
        .id(entity.getId())
        .name(entity.getName())
        .active(entity.isActive())
        .build();

    // Map conditions
    if (entity.getConditions() != null) {
      List<FilterConditionDTO> dtoList = entity.getConditions().stream()
          .map(conditionEntity -> {
            try {
              log.debug(">>> type = {}, details = {}", conditionEntity.getConditionType(), conditionEntity.getConditionDetails());
              return deserializeConditionDetails(conditionEntity.getConditionType(), conditionEntity.getConditionDetails());
            } catch (IOException e) {
              throw new RuntimeException("Error deserializing condition details", e);
            }
          })
          .toList();
      dto.setConditions(dtoList);
    }
    return dto;
  }

  public FilterEntity toEntity(FilterDTO dto) {
    FilterEntity entity = new FilterEntity(
        dto.getId() != null ? dto.getId() : UUID.randomUUID(),
        dto.getName(),
        dto.getActive()
    );

    if (dto.getConditions() != null) {
      dto.getConditions().forEach(conditionDTO -> {
        try {
          // Serialize the DTO into a JSON string for the entity
          String jsonDetails = serializeConditionDTO(conditionDTO);
          FilterConditionEntity conditionEntity = new FilterConditionEntity(UUID.randomUUID().toString(), entity, conditionDTO.getType(), jsonDetails);
          entity.addCondition(conditionEntity); // Use helper to set bidirectional link
        } catch (IOException e) {
          throw new RuntimeException("Error serializing condition DTO", e);
        }
      });
    }
    return entity;
  }
}
