package dev.filters.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.filters.backend.dto.*;
import dev.filters.backend.dto.operator.AmountOperator;
import dev.filters.backend.dto.operator.DateOperator;
import dev.filters.backend.dto.operator.TitleOperator;
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

  public FilterDTO toDto(FilterEntity entity) {
    FilterDTO dto = FilterDTO.builder()
        .id(entity.getId())
        .name(entity.getName())
        .active(entity.isActive())
        .build();

    if (entity.getConditions() != null) {
      List<Condition> conditions = entity.getConditions().stream().map(this::toConditionDTO).toList();
      dto.setConditions(conditions);
    }

    return dto;
  }

  private Condition toConditionDTO(FilterConditionEntity conditionEntity) {
    JsonNode jsonNode;
    try {
      jsonNode = objectMapper.readTree(conditionEntity.getConditionDetails());
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }

    return switch (conditionEntity.getConditionType()) {
      case "amount" -> new AmountConditionDTO(AmountOperator.valueOf(jsonNode.path("operator").asText()), jsonNode.path("value").asDouble());
      case "date" -> new DateConditionDTO(DateOperator.valueOf(jsonNode.path("operator").asText()), jsonNode.path("value").asText());
      case "title" -> new TitleConditionDTO(TitleOperator.valueOf(jsonNode.path("operator").asText()), jsonNode.path("value").asText());
      default
        -> throw new IllegalArgumentException("Unknown condition type: " + conditionEntity.getConditionType());
    };
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
          // TODO type!!!
          FilterConditionEntity conditionEntity = new FilterConditionEntity(UUID.randomUUID().toString(), entity, null, jsonDetails);
          entity.addCondition(conditionEntity);
        } catch (IOException e) {
          throw new RuntimeException("Error serializing condition DTO", e);
        }
      });
    }
    return entity;
  }

  private String serializeConditionDTO(Condition conditionDTO) throws JsonProcessingException {
    return objectMapper.writeValueAsString(conditionDTO);
  }
}
