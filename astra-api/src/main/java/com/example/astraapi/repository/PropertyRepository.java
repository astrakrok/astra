package com.example.astraapi.repository;

import com.example.astraapi.entity.PropertyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface PropertyRepository {
  List<PropertyEntity> getPropertiesByNames(@Param("names") Set<String> names);

  Optional<PropertyEntity> getPropertyByName(@Param("name") String name);

  void update(@Param("entity") PropertyEntity entity);
}
