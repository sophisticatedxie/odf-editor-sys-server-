package com.onesports.editor.entity.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.onesports.editor.entity.vo.ResultVO;
import com.onesports.editor.entity.wrappers.EntityWrapper;
import com.onesports.editor.utils.FieldUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: odf-editor-system
 * @description: web控制层父类
 * @author: xjr
 * @create: 2020-07-21 15:14
 **/
@Slf4j
public class BaseController<Service extends BaseService<Entity>,Entity> {

    protected Class<Entity> entityClass =
            (Class<Entity>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];


    @Autowired
    protected Service service;

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    @ApiOperation("根据ID获取单条数据")
    @GetMapping("/{id}")
    public ResultVO<Entity> getById(@PathVariable Serializable id) {
        Entity entity = this.service.getById(id);
        if (entity == null) {
            return ResultVO.failure("找不到指定的记录");
        }
        return ResultVO.data(entity);
    }

    @ApiOperation(value = "根据参数获取单条数据,如果有多条记录,则抛出异常(该已过时,新接口URL不需要加param后缀)",
            notes = "请求示例:\n" +
                    "url : http://api.com/getByParam?fields=age&fields=create_time\n" +
                    "fields为要返回的指定字段,不填默认返回所有字段\n" +
                    "body部分(查询条件) : { id : 10000 , name : jack }\n" +
                    "该请求为查询 id =  10000  , name =  jack  的单条记录 , 并只返回name和age两个字段," +
                    "如果需要返回所有字段,fields参数为空不填即可")
    @GetMapping("/param")
    @Deprecated
    public ResultVO<Entity> getByParam(Entity entity, @RequestParam(required = false) String[] fields) {
        return this.get(entity, fields);
    }

    @ApiOperation(value = "根据参数获取单条数据,如果有多条记录,则抛出异常",
            notes = "请求示例:\n" +
                    "url : http://api.com/getByParam?fields=age&fields=create_time\n" +
                    "fields为要返回的指定字段,不填默认返回所有字段\n" +
                    "body部分(查询条件) : { id : 10000 , name : jack }\n" +
                    "该请求为查询 id =  10000  , name =  jack  的单条记录 , 并只返回name和age两个字段," +
                    "如果需要返回所有字段,fields参数为空不填即可")
    @GetMapping("")
    public ResultVO<Entity> get(Entity entity, @RequestParam(required = false) String[] fields) {
        QueryWrapper<Entity> queryWrapper = this.getEntityQueryWrapper(entity, fields);
        Entity result = this.service.getOne(queryWrapper, true);
        return ResultVO.data(result);
    }

    @ApiOperation(value = "分页查询",
            notes = "注意: 分页参数直接在url后面添加\n\n" +
                    "url: http://api.com/list?username=jack&current=1&size=5&ascs=name&ascs=age&descs=create_time " +
                    "\n\n" +
                    "表示查询第一页前5条数据,并根据名字和年龄正序排序,根据时间倒序排序 \n\n" +
                    "参数说明: \n" +
                    "current = 当前页,默认 1 \n" +
                    "size = 每页显示条数,默认 10 \n" +
                    "ascs = 根据字段正序查询 , 数组格式 , 可选\n" +
                    "descs = 根据字段倒序查询 , 数组格式 , 可选")
    @GetMapping("/list")
    public ResultVO<IPage<Entity>> list(@ApiIgnore Page<Entity> page,
                                        Entity entity,
                                        @RequestParam(required = false) String[] fields) {
        QueryWrapper<Entity> queryWrapper = this.getEntityQueryWrapper(entity, fields);
        IPage<Entity> resultPage = this.service.listByPage(page, queryWrapper);
        return ResultVO.<IPage<Entity>>success().message("获取列表数据成功").data(resultPage).build();
    }


    @ApiOperation("获取所有")
    @GetMapping("/all")
    public ResultVO<Object> all(Entity entity, @RequestParam(required = false) String[] fields) {
        QueryWrapper<Entity> queryWrapper = this.getEntityQueryWrapper(entity, fields);
        List<Entity> resultList = this.service.list(queryWrapper);
        if (fields != null && fields.length > 0) {
            List<Map<String, Object>> data = resultList.stream()
                    .map(BeanUtil::beanToMap)
                    .peek(MapUtil::removeNullValue)
                    .collect(Collectors.toList());
            return ResultVO.data(data);
        } else {
            return ResultVO.data(resultList);
        }
    }




    @ApiOperation("插入数据")
    @PostMapping("/insert")
    public ResultVO<String> insert(@RequestBody @Valid Entity entity) throws ApiException {
        this.service.insert(entity);
        return ResultVO.success("插入数据成功");
    }

    @ApiOperation(value = "批量插入(JSON数组格式),如果已经存在,抛出异常",
            notes = "请求示例:\n" +
                    "url: http://api.com/insertBatch?batchSize=20   其中batchSize为更新批次数量,默认一次10条(可选)\n" +
                    "body部分:\n" +
                    "[ \n" +
                    "   { id : 10000 , name : jack } , \n" +
                    "   { id : 10001 , name : rose } \n" +
                    "]"
    )
    @PostMapping("/insert/batch")
    public ResultVO<String> insertBatch(@RequestBody List<Entity> entityList,
                                        @RequestParam(defaultValue = "10") Integer batchSize) throws ApiException {
        this.service.insertBatch(entityList, batchSize);
        return ResultVO.success("批量插入数据成功");
    }

    @ApiOperation(value = "根据主键更新数据 (默认不更新值为null的字段)",
            notes = "请求Body示例(更新主键ID= 10000 的记录):\n" +
                    "{ id : 10000 , name : jack , age :21}")
    @PostMapping("/update")
    public ResultVO<String> updateById(@RequestBody Entity entity) throws ApiException {
        this.service.updateById(entity);
        return ResultVO.success("更新数据成功");
    }

    @ApiOperation(value = "根据主键批量更新",
            notes = "请求示例:\n" +
                    "url: http://api.com/updateBatch?batchSize=20   其中batchSize为更新批次数量,默认一次10条(可选)\n" +
                    "body部分:\n" +
                    "[ \n" +
                    "   { id : 10000 , name : jack } , \n" +
                    "   { id : 10001 , name : rose } \n" +
                    "]"
    )
    @PostMapping("/update/batch")
    public ResultVO<String> updateBatchById(@RequestBody List<Entity> entityList,
                                            @RequestParam(defaultValue = "10") Integer batchSize) throws ApiException {
        this.service.updateBatchById(entityList, batchSize);
        return ResultVO.success("批量更新数据成功");
    }

    @ApiOperation(value = "根据字段条件更新 (默认不更新值为null的字段)",
            notes = "请求Body示例:\n" +
                    "{\n" +
                    " oldInfo :{ id : 10000 , name : jack }\n" +
                    " newInfo :{ name : rose , age :21}\n" +
                    "}")
    @PostMapping("/update/param")
    public ResultVO<String> update(@RequestBody EntityWrapper<Entity> entityWrapper) throws ApiException {
        this.service.updateByParam(entityWrapper);
        return ResultVO.success("更新成功");
    }

    @ApiOperation(value = "根据字段条件更新 (默认不更新值为null的字段)",
            notes = "请求Body示例:\n" +
                    "[{\n" +
                    " oldInfo :{ id : 10000 , name : jack }\n" +
                    " newInfo :{ name : rose , age :21}\n" +
                    "}]")
    @PostMapping("/update/batch/param")
    public ResultVO<String> updateBatch(@RequestBody List<EntityWrapper<Entity>> entityList) throws ApiException {
        this.service.updateBatch(entityList);
        return ResultVO.success("更新成功");
    }

    @ApiOperation(value = "保存数据,存在则更新,不存在则插入",
            notes = "请求Body示例:\n" +
                    "{ id : 10000 , name : jack , age :21}\n" +
                    "当存在主键ID= 10000 的记录时,更新该记录,否则根据实体参数插入一条ID= 10000 的记录")
    @PostMapping("/save")
    public ResultVO<String> save(@RequestBody @Valid Entity entity) throws ApiException {
        this.service.insertOrUpdate(entity);
        return ResultVO.success("保存成功");
    }

    @ApiOperation(value = "批量修改插入(JSON数组格式),存在则更新,不存在则插入",
            notes = "请求示例:\n" +
                    "url: http://api.com/insertBatch?batchSize=20   其中batchSize为更新批次数量,默认一次10条(可选)\n" +
                    "body部分:\n" +
                    "[ \n" +
                    "   { id : 10000 , name : jack } , \n" +
                    "   { id : 10001 , name : rose } \n" +
                    "]\n" +
                    "说明:如果id= 10000 的记录存在,而id= 10001 的记录不存在,则会更新id= 10000 的记录,插入一条id= 10001 的记录")
    @PostMapping("/save/batch")
    public ResultVO<String> saveBatch(@RequestBody List<Entity> entityList,
                                      @RequestParam(defaultValue = "10") Integer batchSize) throws ApiException {
        this.service.insertOrUpdateBatch(entityList, batchSize);
        return ResultVO.success("批量保存数据成功");
    }

    @ApiOperation("根据ID删除数据")
    @DeleteMapping("/{id}")
    public ResultVO<String> deleteById(@PathVariable Serializable id) throws Exception {
        this.service.deleteById(id);
        return ResultVO.success("删除成功");
    }

    @ApiOperation(value = "根据参数(JSON格式)删除数据,只能删除单条记录,如果删除结果不等于1,则抛出异常",
            notes = "请求Body示例(更新主键ID= 10000 的记录):\n" +
                    "{ id : 10000 , name : jack}\n" +
                    "说明:删除id = 10000 , name = jack 的记录")
    @DeleteMapping("/one")
    public ResultVO<String> deleteOne(@RequestBody Entity entity) throws ApiException {
        QueryWrapper<Entity> queryWrapper = new QueryWrapper<>(entity);
        this.service.deleteOne(queryWrapper);
        return ResultVO.success("删除数据成功");
    }

    @ApiOperation(value = "根据ID批量删除数据,只要删除成功的记录数超过一条就视为操作成功",
            notes = "说明: 只要删除成功的记录数超过一条就视为操作成功,比如删除3条记录,返回的数量大于1,便视为删除成功,\n" +
                    "请求Body示例 : [ 100 , 101 , 102 ] ")
    @DeleteMapping("/batch")
    public ResultVO<String> deleteBatchIds(@RequestBody List<String> ids) throws ApiException {
        this.service.deleteByIds(ids);
        return ResultVO.success("批量删除成功");
    }

    @ApiOperation(value = "批量删除(JSON数组格式)",
            notes = "请求示例:\n" +
                    "url: http://api.com/deleteBatch?batchSize=20   其中batchSize为更新批次数量,默认一次10条(可选)\n" +
                    "body部分:\n" +
                    "[ \n" +
                    "   { id : 10000 , name : jack } , \n" +
                    "   { id : 10001 , name : rose } \n" +
                    "]"
    )
    @DeleteMapping("/batch/param")
    public ResultVO<String> deleteBatch(@RequestBody List<Entity> entityList,
                                        @RequestParam(defaultValue = "10") Integer batchSize) throws ApiException {
        this.service.deleteBatch(entityList, batchSize);
        return ResultVO.success("批量删除数据成功");
    }

    protected QueryWrapper<Entity> getEntityQueryWrapper(Entity entity,
                                                         @RequestParam(required = false) String[] fields) {
        QueryWrapper<Entity> queryWrapper = new QueryWrapper<>();
        if (entity != null) {
            queryWrapper.setEntity(entity);
        }
        if (fields != null && fields.length > 0) {
            FieldUtils.checkFieldsThrow(entityClass, Arrays.asList(fields));
            queryWrapper.select(fields);
        }
        return queryWrapper;
    }

}
