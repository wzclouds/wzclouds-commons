package com.github.wzclouds.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.github.wzclouds.base.R;
import com.github.wzclouds.base.entity.SuperEntity;
import com.github.wzclouds.log.annotation.SysLog;
import com.github.wzclouds.security.annotation.PreAuth;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 修改Controller
 *
 * @param <Entity>    实体
 * @param <UpdateDTO> 修改参数
 * @author wzclouds
 * @date 2020年03月07日22:30:37
 */
public interface UpdateController<Entity, UpdateDTO> extends BaseController<Entity> {

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    @SysLog(value = "'修改:' + #updateDTO?.id", request = false)
    @PreAuth("hasPermit('{}update')")
    default R<Entity> update(@RequestBody @Validated(SuperEntity.Update.class) UpdateDTO updateDTO) {
        R<Entity> result = handlerUpdate(updateDTO);
        if (result.getDefExec()) {
            Entity model = BeanUtil.toBean(updateDTO, getEntityClass());
            getBaseService().updateAllById(model);
            result.setData(model);
        }
        return result;
    }

    /**
     * 自定义更新
     *
     * @param model
     * @return 返回SUCCESS_RESPONSE, 调用默认更新, 返回其他不调用默认更新
     */
    default R<Entity> handlerUpdate(UpdateDTO model) {
        return R.successDef();
    }
}
