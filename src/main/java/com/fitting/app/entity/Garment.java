package com.fitting.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("garment")
public class Garment {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    /** 服装分类: 0-上装 1-下装 2-裙装 */
    private Integer category;

    private String imageUrl;

    private LocalDateTime createTime;
}