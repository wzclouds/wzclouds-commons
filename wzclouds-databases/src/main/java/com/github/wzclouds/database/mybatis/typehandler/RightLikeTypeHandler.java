package com.github.wzclouds.database.mybatis.typehandler;


import com.baomidou.mybatisplus.core.enums.SqlLike;
import org.apache.ibatis.type.Alias;

/**
 * 仅仅用于like查询
 *
 * @author wzclouds
 */
@Alias("rightLike")
public class RightLikeTypeHandler extends BaseLikeTypeHandler {
    public RightLikeTypeHandler() {
        super(SqlLike.RIGHT);
    }
}

