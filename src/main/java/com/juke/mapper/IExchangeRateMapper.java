package com.juke.mapper;

import com.juke.dto.ExchangeRateDto;
import com.juke.entity.ExchangeRateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IExchangeRateMapper extends IBaseMapper<ExchangeRateEntity, ExchangeRateDto> {

  IExchangeRateMapper MAPPER = Mappers.getMapper(IExchangeRateMapper.class);

  @Override
  ExchangeRateDto toDto(ExchangeRateEntity entity);

  @Override
  ExchangeRateEntity toEntity(ExchangeRateDto dto);

  @Override
  Iterable<ExchangeRateDto> toDtoList(Iterable<ExchangeRateEntity> entityList);

  @Override
  Iterable<ExchangeRateEntity> toEntityList(Iterable<ExchangeRateDto> dtoList);
}