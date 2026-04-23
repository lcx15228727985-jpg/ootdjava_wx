package com.fitting.app.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fitting.app.entity.Garment;
import com.fitting.app.mapper.GarmentMapper;
import org.springframework.stereotype.Service;

@Service
public class GarmentService extends ServiceImpl<GarmentMapper, Garment> {
}