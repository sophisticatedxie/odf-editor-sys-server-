package com.onesports.editor.entity.model;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.onesports.editor.entity.wrappers.EntityWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @program: odf-editor-system
 * @description: 服务层父接口
 * @author: xjr
 * @create: 2020-07-21 14:30
 **/

public interface BaseService<E> {

    /**
     * 根据实体类字段和关键词查询,关键词使用keywords
     *
     * @param entity          实体
     * @param likeFields      增加自定义的模糊查询字段
     * @param useDefaultField 是否使用默认的模糊查询字段
     * @param page            分页
     * @return 查询结果集合
     * @throws Exception e
     */
    IPage<E> listByKeywords(E entity, List<String> likeFields, boolean useDefaultField, IPage<E> page) throws Exception;

    /**
     * 根据实体类字段和关键词查询,关键词使用keywords
     *
     * @param params          参数
     * @param likeFields      增加自定义的模糊查询字段
     * @param useDefaultField 是否使用默认的模糊查询字段
     * @return 查询结果集合
     */
    IPage<E> listByKeywords(Map<String, Object> params, List<String> likeFields, boolean useDefaultField,
                            IPage<E> page);

    /**
     * 根据字段使用in函数
     *
     * @param column 字段名
     * @param values 值
     * @return 集合
     */
    List<E> listIn(String column, Object[] values);

    /**
     * 根据主键判断是否存在该记录
     *
     * @param id 主键
     * @return 是否存在
     */
    boolean existsById(Serializable id);

    /**
     * 根据实体条件判断是否存在指定的记录
     *
     * @param entity 实体条件
     * @return 是否存在
     */
    boolean existsByEntity(E entity);

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    E insert(E entity) throws ApiException;

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     */
    void insertBatch(Collection<E> entityList) throws ApiException;

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    void insertBatch(Collection<E> entityList, int batchSize) throws ApiException;

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     */
    void insertOrUpdateBatch(Collection<E> entityList);

    /**
     * 批量修改插入
     *
     * @param entityList 实体对象集合
     * @param batchSize  每次的数量
     */
    void insertOrUpdateBatch(Collection<E> entityList, int batchSize);

    /**
     * 根据 ID 删除
     *
     * @param id 主键ID
     */
    void deleteById(Serializable id) throws ApiException;

    /**
     * 根据 columnMap 条件，删除记录
     *
     * @param columnMap 表字段 map 对象
     */
    void deleteByMap(Map<String, Object> columnMap) throws ApiException;

    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    void delete(Wrapper<E> queryWrapper) throws ApiException;

    void deleteOne(Wrapper<E> wrapper) throws ApiException;

    /**
     * 根据 entity 条件，删除记录
     *
     * @param entityList 实体对象集合
     */
    void deleteBatch(Collection<E> entityList, int batchSize);

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    void deleteByIds(Collection<? extends Serializable> idList) throws ApiException;

    /**
     * 根据 ID 选择修改
     *
     * @param entity 实体对象
     */
    void updateById(E entity) throws ApiException;

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    void updateByParam(E entity, Wrapper<E> updateWrapper) throws ApiException;

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     */
    void updateBatchById(Collection<E> entityList) throws ApiException;

    void updateByWrapper(Wrapper<E> updateWrapper) throws ApiException;

    /**
     * 批量更新
     *
     * @param entityList 要更新的实体列表
     * @throws ApiException e
     */
    void updateBatch(Collection<EntityWrapper<E>> entityList) throws ApiException;

    /**
     * 更新单条记录
     *
     * @param entity        要更新的实体
     * @param updateWrapper 更新条件
     * @throws ApiException e
     */
    void updateOne(E entity, Wrapper<E> updateWrapper) throws ApiException;

    /**
     * 自动根据主键更新实体
     *
     * @param entity 实体
     * @throws ApiException e
     */
    void updateByParam(EntityWrapper<E> entity) throws ApiException;

    /**
     * 根据ID 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    void updateBatchById(Collection<E> entityList, int batchSize) throws ApiException;

    /**
     * EableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     */
    E insertOrUpdate(E entity) throws ApiException;

    /**
     * 根据 ID 查询
     *
     * @param id 主键ID
     */
    E getById(Serializable id);

    /**
     * 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    Collection<E> listByIds(Collection<? extends Serializable> idList);

    /**
     * 查询（根据 columnMap 条件）
     *
     * @param columnMap 表字段 map 对象
     */
    Collection<E> listByMap(Map<String, Object> columnMap);

    /**
     * 获取单条记录,如果没有找到,则抛出异常
     *
     * @param queryWrapper 查询条件
     * @param errorMsg     自定义错误信息
     * @return 查询结果
     */
    E getOneAndNotNull(Wrapper<E> queryWrapper, String errorMsg);

    /**
     * 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIE 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    E getOne(Wrapper<E> queryWrapper);

    E getFirst(Wrapper<E> queryWrapper);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    E getOne(Wrapper<E> queryWrapper, boolean throwEx);

    /**
     * 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    Map<String, Object> getMap(Wrapper<E> queryWrapper);


    /**
     * 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    int count(Wrapper<E> queryWrapper);

    /**
     * 查询总记录数
     *
     * @see
     */
    int count();

    /**
     * 查询所有
     *
     */
    List<E> list();


    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    List<E> list(Wrapper<E> queryWrapper);

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    IPage<E> listByPage(IPage<E> page, Wrapper<E> queryWrapper);

    /**
     * 无条件翻页查询
     *
     * @param page 翻页对象
     */
    IPage<E> listByPage(IPage<E> page);

    /**
     * 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    List<Map<String, Object>> listMaps(Wrapper<E> queryWrapper);


    /**
     * 根据 Wrapper 条件，查询全部记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param mapper       转换函数
     */
    <V> List<V> listObjs(Wrapper<E> queryWrapper, Function<? super Object, V> mapper);

    /**
     * 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    IPage<Map<String, Object>> pageMaps(IPage<Map<String, Object>> page, Wrapper<E> queryWrapper);

    /**
     * 获取对应 entity 的 BaseDao
     *
     * @return BaseMapper
     */
    BaseDao<E> getBaseDao();

    /**
     * 以下的方法使用介绍:
     *
     * 一. 名称介绍
     * 1. 方法名带有 query 的为对数据的查询操作, 方法名带有 update 的为对数据的修改操作
     * 2. 方法名带有 lambda 的为内部方法入参 column 支持函数式的
     *
     * 二. 支持介绍
     * 1. 方法名带有 query 的支持以 {@link ChainQuery} 内部的方法名结尾进行数据查询操作
     * 2. 方法名带有 update 的支持以 {@link ChainUpdate} 内部的方法名为结尾进行数据修改操作
     *
     * 三. 使用示例,只用不带 lambda 的方法各展示一个例子,其他类推
     * 1. 根据条件获取一条数据: `query().eq("column", value).one()`
     * 2. 根据条件删除一条数据: `update().eq("column", value).delete()`
     *
     */

    /**
     * 链式查询 普通
     *
     * @return QueryWrapper 的包装类
     */
    QueryChainWrapper<E> query();

    /**
     * 链式查询 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaQueryWrapper 的包装类
     */
    LambdaQueryChainWrapper<E> lambdaQuery();

    /**
     * 链式更改 lambda 式
     * <p>注意：不支持 Kotlin </p>
     *
     * @return LambdaUpdateWrapper 的包装类
     */
    LambdaUpdateChainWrapper<E> lambdaUpdate();


}
