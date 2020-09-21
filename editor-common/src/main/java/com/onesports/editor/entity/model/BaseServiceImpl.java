package com.onesports.editor.entity.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.onesports.editor.entity.wrappers.EntityWrapper;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * @program: odf-editor-system
 * @description: 基础服务实现父类
 * @author: xjr
 * @create: 2020-07-21 14:34
 **/
@Slf4j
public class BaseServiceImpl<Dao extends BaseDao<T>,T> implements BaseService<T> {

    public static final String UPDATE = "UPDAEE";

    public static final String INSERT = "INSERE";

    public static final String DELETE = "DELEEE";

    @Autowired
    protected Dao baseDao;

    @Override
    public Dao getBaseDao() {
        return baseDao;
    }

    @Override
    public QueryChainWrapper<T> query() {
        return new QueryChainWrapper<>(getBaseDao());
    }

    @Override
    public LambdaQueryChainWrapper<T> lambdaQuery() {
        return new LambdaQueryChainWrapper<>(getBaseDao());
    }

    public UpdateChainWrapper<T> updateByParam() {
        return new UpdateChainWrapper<>(getBaseDao());
    }

    @Override
    public LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return new LambdaUpdateChainWrapper<>(getBaseDao());
    }

    @Override
    public IPage<T> listByKeywords(T entity, List<String> likeFields, boolean useDefaultField, IPage<T> page) throws Exception {
        Map<String, Object> params = BeanUtils.beanToMap(entity);
        params = params.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .collect(Collectors.toMap(e -> StrUtil.toUnderlineCase(e.getKey()), Map.Entry::getValue));
        return this.listByKeywords(params, likeFields, useDefaultField, page);
    }

    @Override
    public IPage<T> listByKeywords(Map<String, Object> params, List<String> likeFields, boolean useDefaultField,
                                   IPage<T> page) {
        if (params == null || params.size() == 0) {
            return this.listByPage(page);
        }
        Object keywords = params.get("keywords");
        params.remove("keywords");

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        params.forEach(queryWrapper::like);

        if (keywords != null) {

            Set<String> likeFieldSet = new HashSet<>();

            // 是否使用默认字段 : SPELL_CODE , WBZX_CODE
            if (useDefaultField) {
                likeFieldSet.add("SPELL_CODE");
                likeFieldSet.add("WBZX_CODE");
            }

            // 检查是否有传自定义的模糊查询字段
            if (CollectionUtils.isNotEmpty(likeFields)) {
                likeFields.forEach(fieldName -> likeFieldSet.add(StrUtil.toUnderlineCase(fieldName)));
            }

            // 检查模糊查询字段是否不为空
            if (CollectionUtils.isNotEmpty(likeFieldSet)) {
                queryWrapper.and(wrapper -> {
                            likeFieldSet.forEach(fieldName -> wrapper.like(fieldName, keywords).or());
                        }
                );
            }

        }
        return this.listByPage(page, queryWrapper);
    }

    @Override
    public List<T> listIn(String column, Object[] values) {
        QueryWrapper<T> in = new QueryWrapper<T>().in(column, values);
        return this.list(in);
    }

    @Override
    public boolean existsById(Serializable id) {
        Integer result = this.baseDao.existsById(id);
        return result != null && result != 0;
    }

    @Override
    public boolean existsByEntity(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
        int count = this.count(queryWrapper);
        return count > 0;
    }

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    public T insert(T entity) throws ApiException {
        int result = baseDao.insert(entity);
        log(INSERT, entity, result);
        if (!retBool(result)) {
            throw new ApiException("插入数据失败");
        }
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(Collection<T> entityList) throws ApiException {
        this.insertBatch(entityList, 1000);
    }

    /**
     * 批量插入
     *
     * @param entityList ignore
     * @param batchSize  ignore
     * @return ignore
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertBatch(Collection<T> entityList, int batchSize) throws ApiException {
        // 批量插入不支持创建时间,创建人代码等自动生成,需要每个字段都填写.
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T entity : entityList) {
                batchSqlSession.insert(sqlStatement, entity);
                log(INSERT, entity, 1);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrUpdateBatch(Collection<T> entityList) {
        this.insertOrUpdateBatch(entityList, 1000);
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public T insertOrUpdate(T entity) throws ApiException {
        if (entity == null) {
            throw new ApiException("保存或更新的实体类不能为空");
        }
        Class<?> cls = entity.getClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        Assert.notNull(tableInfo, "找不到实体的TableInfo缓存");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "无法从实体中找到id列");
        Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
        if ((StrUtil.isEmptyIfStr(idVal) || Objects.isNull(getById((Serializable) idVal)))) {
            this.insert(entity);
        } else {
            this.updateById(entity);
        }
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertOrUpdateBatch(Collection<T> entityList, int batchSize) {
        Assert.notEmpty(entityList, "error: entityList must not be empty");
        Class<?> cls = currentModelClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        Assert.notNull(tableInfo, "找不到实体的TableInfo缓存");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "无法从实体中找到id列");
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T entity : entityList) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
                if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
                    log(INSERT, entity, 1);
                } else {
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                    param.put(Constants.ENTITY, entity);
                    int result = batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
                    log(UPDATE, entity, result);
                }
                // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
    }

    @Override
    public void deleteById(Serializable id) throws ApiException {
        int result = baseDao.deleteById(id);
        log(DELETE, id, result);
    }

    @Override
    public void deleteByMap(Map<String, Object> columnMap) throws ApiException {
        this.checkNullParam(columnMap);
        int result = baseDao.deleteByMap(columnMap);
        log(DELETE, columnMap, result);
    }

    @Override
    public void delete(Wrapper<T> wrapper) throws ApiException {
        this.checkNullParam(wrapper);
        int result = baseDao.delete(wrapper);
        log(DELETE, wrapper, result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(Wrapper<T> wrapper) throws ApiException {
        this.checkNullParam(wrapper);
        int result = baseDao.delete(wrapper);
        log(DELETE, wrapper, result);
        if (result != 1) {
            throw new ApiException("删除失败,删除数量必须为1");
        }

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.DELETE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T entity : entityList) {
                QueryWrapper<T> queryWrapper = new QueryWrapper<>(entity);
                Map<String, Object> map = new HashMap(1);
                map.put("ew", queryWrapper);
                int result = batchSqlSession.delete(sqlStatement, map);
                log(DELETE, entity, result);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
    }

    @Override
    public void deleteByIds(Collection<? extends Serializable> idList) throws ApiException {
        int result = baseDao.deleteBatchIds(idList);
        log(DELETE, idList, result);
    }

    @Override
    public void updateById(T entity) throws ApiException {
        int result = baseDao.updateById(entity);
        log(UPDATE, entity, result);
        if (!retBool(result)) {
            throw new ApiException("根据ID更新实体失败");
        }

    }

    @Override
    public void updateByParam(T entity, Wrapper<T> updateWrapper) throws ApiException {
        int result = baseDao.update(entity, updateWrapper);
        log(UPDATE, Arrays.asList(entity, updateWrapper.getEntity()), result);
        if (!retBool(result)) {
            throw new ApiException("根据实体参数更新实体失败");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchById(Collection<T> entityList) throws ApiException {
        this.updateBatchById(entityList, 1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOne(T entity, Wrapper<T> updateWrapper) throws ApiException {
        int result = baseDao.update(entity, updateWrapper);
        log(UPDATE, Arrays.asList(entity, updateWrapper.getEntity()), result);
        if (result != 1) {
            throw new ApiException("更新失败,只能更新一条记录");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByParam(EntityWrapper<T> entityWrapper) throws ApiException {
        T oldInfo = entityWrapper.getOldInfo();
        if (BeanUtil.isEmpty(oldInfo)) {
            throw new ApiException("更新条件不能为空");
        }
        T newInfo = entityWrapper.getNewInfo();
        UpdateWrapper<T> updateWrapper = new UpdateWrapper<>(oldInfo);
        this.checkKeyColumn(oldInfo, newInfo, updateWrapper);
        this.updateOne(newInfo, updateWrapper);

    }

    @Override
    public void updateByWrapper(Wrapper<T> updateWrapper) throws ApiException {
        this.updateByParam(updateWrapper.getEntity(), updateWrapper);
    }

    @Override
    public void updateBatch(Collection<EntityWrapper<T>> entityList) throws ApiException {
        for (EntityWrapper<T> entity : entityList) {
            UpdateWrapper<T> updateWrapper = new UpdateWrapper<>(entity.getOldInfo());
            this.updateByParam(entity.getNewInfo(), updateWrapper);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatchById(Collection<T> entityList, int batchSize) throws ApiException {
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        @Cleanup SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        for (T entity : entityList) {
            if (BeanUtil.isEmpty(entity)) {
                throw new ApiException("更新参数不能为空");
            }
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            int result = batchSqlSession.update(sqlStatement, param);
            log(UPDATE, param, result);
            if (i >= 1 && i % batchSize == 0) {
                batchSqlSession.flushStatements();
            }
            i++;
        }
        batchSqlSession.flushStatements();
    }

    @Override
    public T getById(Serializable id) {
        return baseDao.selectById(id);
    }

    @Override
    public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        return baseDao.selectBatchIds(idList);
    }

    @Override
    public Collection<T> listByMap(Map<String, Object> columnMap) {
        return baseDao.selectByMap(columnMap);
    }

    @Override
    public T getOneAndNotNull(Wrapper<T> queryWrapper, String errorMsg) {
        T entity = this.getOne(queryWrapper);
        if (entity == null) {
            throw new RuntimeException(errorMsg);
        }
        return entity;
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper) {
        return this.getOne(queryWrapper, true);
    }

    @Override
    public T getFirst(Wrapper<T> queryWrapper) {
        return this.getOne(queryWrapper, false);
    }

    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        if (throwEx) {
            return baseDao.selectOne(queryWrapper);
        }
        List<T> resList = baseDao.selectList(queryWrapper);
        return this.getOneFromList(resList);
    }

    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return this.getOneFromList(baseDao.selectMaps(queryWrapper));
    }

    @Override
    public int count(Wrapper<T> queryWrapper) {
        return SqlHelper.retCount(baseDao.selectCount(queryWrapper));
    }

    @Override
    public int count() {
        return this.count(Wrappers.emptyWrapper());
    }

    @Override
    public List<T> list() {
        return this.list(Wrappers.emptyWrapper());
    }

    @Override
    public List<T> list(Wrapper<T> queryWrapper) {
        return baseDao.selectList(queryWrapper);
    }


    @Override
    public IPage<T> listByPage(IPage<T> page, Wrapper<T> queryWrapper) {
        return baseDao.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<T> listByPage(IPage<T> page) {
        return this.listByPage(page, Wrappers.emptyWrapper());
    }

    @Override
    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
        return baseDao.selectMaps(queryWrapper);
    }

    @Override
    public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return this.baseDao.selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    @Override
    public IPage<Map<String, Object>> pageMaps(IPage<Map<String, Object>> page, Wrapper<T> queryWrapper) {
        return this.baseDao.selectMapsPage(page, queryWrapper);
    }


    /**
     * 检查主键字段是否一致,如果不一致,把主键字段一起更新
     *
     * @param oldInfo       旧信息
     * @param newInfo       新信息
     * @param updateWrapper 更新条件包装类
     */
    private void checkKeyColumn(T oldInfo, T newInfo, UpdateWrapper<T> updateWrapper) throws ApiException {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(oldInfo.getClass());
        Field field = ReflectionUtils.findField(oldInfo.getClass(), tableInfo.getKeyProperty());
        if (field != null) {
            try {
                field.setAccessible(true);
                Object oldId = field.get(oldInfo);
                Object newId = field.get(newInfo);
                if (!Objects.equals(oldId, newId)) {
                    updateWrapper.set(tableInfo.getKeyColumn(), newId);
                }
            } catch (IllegalAccessException e) {
                throw new ApiException("对比主键差异时发生异常");
            }
        }
    }


    /**
     * 检查删除参数是否为空
     *
     * @param wrapper 查询参数
     * @throws ApiException e
     */
    private void checkNullParam(Wrapper<T> wrapper) throws ApiException {
        Map<String, Object> paramMap;
        paramMap = BeanUtil.beanToMap(wrapper.getEntity());
        this.checkNullParam(paramMap);
    }

    private void checkNullParam(Map<String, Object> paramMap) throws ApiException {
        if (paramMap == null
                || paramMap.size() == 0
                || paramMap.values().stream().anyMatch(StrUtil::isEmptyIfStr)) {
            throw new ApiException("删除参数不能为空");
        }
    }

    /**
     * 从list中取第一条数据返回对应List中泛型的单个结果
     *
     * @param list<T> ignore
     * @return ignore
     */
    private <E> E getOneFromList(List<E> list) {
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    private void log(String action,Object data,int rows){
        log.info("数据库执行操作:{},数据:{},影响行数:{}",action,data,rows);
    }


}
