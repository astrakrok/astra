package com.example.astraapi.repository;

import com.example.astraapi.entity.ImportEntity;
import com.example.astraapi.entity.projection.ImportStatsProjection;
import com.example.astraapi.model.Page;
import com.example.astraapi.model.Pageable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImportRepository {
    void save(@Param("entity") ImportEntity entity);

    Page<ImportStatsProjection> getStats(
            @Param("searchText") String searchText,
            @Param("pageable") Pageable pageable);
}
