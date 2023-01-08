package com.example.astraapi.repository;

import com.example.astraapi.entity.ImportTestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImportTestRepository {
    void saveAll(@Param("entities") List<ImportTestEntity> entities);
}
