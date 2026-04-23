package com.fitting.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fitting.app.entity.Garment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GarmentMapper extends BaseMapper<Garment> {
}