package com.onesports.editor.entity.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;

/**
 * @program: odf-editor-system
 * @description: 持久层接口
 * @author: xjr
 * @create: 2020-07-21 14:28
 **/

public interface BaseDao<E> extends BaseMapper<E> {

    Integer existsById(Serializable id);
}
