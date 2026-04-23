package com.fitting.app.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fitting.app.entity.TryOnTask;
import com.fitting.app.mapper.TryOnTaskMapper;
import org.springframework.stereotype.Service;

@Service
public class TryOnTaskService extends ServiceImpl<TryOnTaskMapper, TryOnTask> {
}