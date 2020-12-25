//package com.github.wzclouds.database.parsers;
//
//import cn.hutool.core.util.StrUtil;
//import com.github.wzclouds.context.BaseContextHandler;
//import lombok.AllArgsConstructor;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Properties;
//
//
///**
// * 多租户拦截器
// *
// * @author wzclouds
// * @date 2019-11-26 09:27
// */
//@Intercepts(value = {
//        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})}
//)
//@AllArgsConstructor
//public class MultiTenantInterceptor implements Interceptor {
//
//    private static Logger logger = LoggerFactory.getLogger(MultiTenantInterceptor.class);
//
//    private String tenantDatabasePrefix;
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        Object[] args = invocation.getArgs();
//        MappedStatement mappedStatement = (MappedStatement) args[0];
//        Object parameter = args[1];
//
//        String tenantCode = BaseContextHandler.getTenant();
//        if (StrUtil.isEmpty(tenantCode)) {
//            return invocation.proceed();
//        }
//        args[0] = getNewMappedStatement(parameter, mappedStatement);
//        return invocation.proceed();
//    }
//
//    private String getSchemaName() {
//        // 想要 执行sql时， 不切换到 wzclouds_base_{TENANT} 库, 请直接返回null
//        String tenantCode = BaseContextHandler.getTenant();
//        if (StrUtil.isEmpty(tenantCode)) {
//            return null;
//        }
//
//        return StrUtil.format("{}_{}", tenantDatabasePrefix, tenantCode);
//    }
//
//    private MappedStatement getNewMappedStatement(Object parameter, MappedStatement mappedStatement) {
//        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
//        logger.debug("原SQL：{}", boundSql.getSql());
//        String resultSql = ReplaceSql.replaceSql(getSchemaName(), boundSql.getSql());
//        logger.debug("结果SQL：{}", resultSql);
//        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), resultSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
//        MappedStatement.Builder builder = new MappedStatement.Builder(mappedStatement.getConfiguration(), mappedStatement.getId(), parameterObject -> newBoundSql, mappedStatement.getSqlCommandType());
//        builder.resource(mappedStatement.getResource());
//        builder.fetchSize(mappedStatement.getFetchSize());
//        builder.statementType(mappedStatement.getStatementType());
//        builder.keyGenerator(mappedStatement.getKeyGenerator());
//        if (mappedStatement.getKeyProperties() != null && mappedStatement.getKeyProperties().length > 0) {
//            builder.keyProperty(mappedStatement.getKeyProperties()[0]);
//        }
//        builder.timeout(mappedStatement.getTimeout());
//        builder.parameterMap(mappedStatement.getParameterMap());
//        builder.resultMaps(mappedStatement.getResultMaps());
//        builder.resultSetType(mappedStatement.getResultSetType());
//        builder.cache(mappedStatement.getCache());
//        builder.flushCacheRequired(mappedStatement.isFlushCacheRequired());
//        builder.useCache(mappedStatement.isUseCache());
//
//        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
//            String prop = mapping.getProperty();
//            if (boundSql.hasAdditionalParameter(prop)) {
//                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
//            }
//        }
//        return builder.build();
//    }
//
//
//}
