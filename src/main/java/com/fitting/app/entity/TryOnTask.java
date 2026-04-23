package com.fitting.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("try_on_task")
public class TryOnTask {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Long userId;

    private Long garmentId;

    private String modelImageUrl;

    /** 状态: 0-处理中 1-成功 2-失败 */
    private Integer status;

    private String resultImageUrl;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}