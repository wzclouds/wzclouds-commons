package com.github.wzclouds.database.plugins;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.github.wzclouds.context.BaseContextHandler;
import com.github.wzclouds.database.parsers.ReplaceSql;

/**
 * SCHEMA模式插件
 *
 * @author wzclouds
 * @date 2020/8/26 上午10:00
 */
public class SchemaInterceptor extends DynamicTableNameInnerInterceptor {

    private String tenantDatabasePrefix;

    public SchemaInterceptor(String tenantDatabasePrefix) {
        this.tenantDatabasePrefix = tenantDatabasePrefix;
    }


    @Override
    protected String changeTable(String sql) {
        // 想要 执行sql时， 不切换到 wzclouds_base_{TENANT} 库, 请直接返回null
        String tenantCode = BaseContextHandler.getTenant();
        if (StrUtil.isEmpty(tenantCode)) {
            return sql;
        }

        String schemaName = StrUtil.format("{}_{}", tenantDatabasePrefix, tenantCode);
        // 想要 执行sql时， 切换到 切换到自己指定的库， 直接修改 setSchemaName
        String parsedSql = ReplaceSql.replaceSql(schemaName, sql);
        return parsedSql;
    }

}
