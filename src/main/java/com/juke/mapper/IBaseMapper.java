package com.juke.mapper;

public interface IBaseMapper<E,D> {

  D toDto(E entity);

  E toEntity(D dto);

  Iterable<D> toDtoList(Iterable<E> entityList);

  Iterable<E> toEntityList(Iterable<D> dtoList);
}