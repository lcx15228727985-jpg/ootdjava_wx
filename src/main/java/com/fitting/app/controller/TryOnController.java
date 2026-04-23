package com.fitting.app.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.fitting.app.common.Result;
import com.fitting.app.entity.TryOnTask;
import com.fitting.app.service.TryOnTaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tryon")
@RequiredArgsConstructor
public class TryOnController {

    private final TryOnTaskService tryOnTaskService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/submit")
    public Result<Map<String, Object>> submit(@RequestBody Map<String, String> params) {
        try {
            String userId = params.get("userId");
            String garmentId = params.get("garmentId");
            String category = params.get("category");
            String modelImageUrl = params.get("modelImageUrl");
            String clothImageUrl = params.get("clothImageUrl");

            // 生成任务ID
            String taskId = cn.hutool.core.util.IdUtil.fastSimpleUUID();

            // 创建任务记录，状态为处理中(0)
            TryOnTask task = new TryOnTask();
            task.setId(taskId);
            task.setUserId(Long.parseLong(userId));
            task.setGarmentId(Long.parseLong(garmentId));
            task.setModelImageUrl(modelImageUrl);
            task.setStatus(0);
            task.setCreateTime(LocalDateTime.now());
            task.setUpdateTime(LocalDateTime.now());
            tryOnTaskService.save(task);

            // 构建转发请求
            Map<String, Object> proxyRequest = new HashMap<>();
            proxyRequest.put("model_url", modelImageUrl);
            proxyRequest.put("cloth_url", clothImageUrl);
            proxyRequest.put("category", category);
            proxyRequest.put("model_type", "dc");

            try {
                // 转发到Python服务
                HttpResponse response = HttpRequest.post("http://127.0.0.1:5000/api/proxy/tryon")
                        .header("Content-Type", "application/json")
                        .body(objectMapper.writeValueAsString(proxyRequest))
                        .execute();

                if (response.getStatus() == 200) {
                    // 解析返回结果
                    Map<String, Object> resultMap = objectMapper.readValue(response.body(), Map.class);
                    String resultUrl = (String) resultMap.get("result_url");

                    // 更新任务状态为成功(1)
                    task.setStatus(1);
                    task.setResultImageUrl(resultUrl);
                    task.setUpdateTime(LocalDateTime.now());
                    tryOnTaskService.updateById(task);
                } else {
                    // HTTP非200，标记失败(2)
                    task.setStatus(2);
                    task.setUpdateTime(LocalDateTime.now());
                    tryOnTaskService.updateById(task);
                }
            } catch (Exception e) {
                // 通信异常，标记失败(2)
                task.setStatus(2);
                task.setUpdateTime(LocalDateTime.now());
                tryOnTaskService.updateById(task);
            }

            // 返回任务ID
            Map<String, Object> data = new HashMap<>();
            data.put("taskId", taskId);
            return Result.success(data);

        } catch (Exception e) {
            return Result.error("提交任务失败: " + e.getMessage());
        }
    }

    @GetMapping("/status/{taskId}")
    public Result<Map<String, Object>> status(@PathVariable String taskId) {
        TryOnTask task = tryOnTaskService.getById(taskId);
        if (task == null) {
            return Result.error(404, "任务不存在");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("taskId", task.getId());
        data.put("status", task.getStatus());
        data.put("resultImageUrl", task.getResultImageUrl());
        data.put("createTime", DateUtil.format(task.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        data.put("updateTime", DateUtil.format(task.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));

        return Result.success(data);
    }
}