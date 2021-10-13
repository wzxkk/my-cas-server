package org.wzx.mycasserver.controller.base;

import cn.hutool.core.text.StrSpliter;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import org.wzx.model.Result;
import org.wzx.mycasserver.entity.base.CommonEntity;
import org.wzx.mycasserver.service.BaseService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: web基础虚类
 * @author: 鱼头(韦忠幸)
 * @since: 2020-06-28
 * @version: 0.0.1
 */
@Slf4j
public abstract class BaseController<S extends BaseService<T>, T extends Serializable> {
    @Autowired
    protected S s;

    @PostMapping
    @ApiOperation(value = "添加信息")
    public Result insert(@RequestBody T obj) {
        Result result = new Result();
        if (s.checkInsert(obj)) {
            boolean ret = s.save(obj);
            if (ret) {
                result.setMessage("添加成功");
                result.setCode(0);
            } else {
                result.setMessage("添加失败");
                result.setCode(-1);
            }
        } else {
            result.setMessage("业务内容不允许添加！");
            result.setCode(0);
        }
        return result;
    }

    @DeleteMapping
    @ApiOperation(value = "删除信息")
    public Result delete(String uuid) {
        Result result = new Result();
        if (s.checkDelete(uuid)) {
            boolean ret = s.removeById(uuid);
            if (ret) {
                result.setMessage("删除成功");
                result.setCode(0);
            } else {
                result.setMessage("删除失败");
                result.setCode(-1);
            }
        } else {
            result.setMessage("业务内容不允许删除！");
            result.setCode(0);
        }
        return result;
    }

    @PutMapping
    @ApiOperation(value = "更新信息")
    public Result update(@RequestBody T obj) {
        Result result = new Result();
        if (s.checkUpdate(obj)) {
            boolean ret = s.updateById(obj);
            if (ret) {
                result.setMessage("修改成功");
                result.setCode(0);
            } else {
                result.setMessage("修改失败");
                result.setCode(-1);
            }
        } else {
            result.setMessage("业务内容不允许更新！");
            result.setCode(0);
        }
        return result;
    }

    @GetMapping
    @ApiOperation(value = "查询所有信息")
    public Result selectAll() {
        return new Result(s.list(), "获取所有数据成功", 0);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "按id查询某条信息")
    public Result selectById(@PathVariable("id") String uuid) {
        Result result = new Result();
        T ret = s.getById(uuid);
        if (ret != null) {
            result.setData(ret);
            result.setMessage("获取成功");
            result.setCode(0);
        } else {
            result.setMessage("获取失败");
            result.setCode(-1);
        }
        return result;
    }

    @GetMapping("/{page}/{size}")
    @ApiOperation(value = "分页查找信息")
    public Result selectByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        return new Result(s.page(new Page<>(page, size)), "数据分页返回成功", 0);
    }

    @GetMapping("/{page}/{size}/{sort}")
    @ApiOperation(value = "排序并分页查找信息")
    public Result selectByPageSort(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("sort") String sort) {
        List list = new ArrayList<>();
        for (String item : sort.split("\\,")) {
            String fieldName = parseColumnName(getEntityClass(), item.substring(2));
            //小写正序，大写反序
            list.add("d_".equalsIgnoreCase(item.substring(0, 2)) ? new OrderItem().setColumn(fieldName).setAsc(false) : new OrderItem().setColumn(fieldName));
        }
        return new Result(s.page(new Page<T>(page, size).addOrder(list)), "数据排序并分页返回成功", 0);
    }

    @GetMapping("/{page}/{size}/{sort}/{search}")
    @ApiOperation(value = "查找和排序并分页查找信息")
    public Result selectBySearchPageSort(@PathVariable("page") int page, @PathVariable("size") int size, @PathVariable("sort") String sort, @PathVariable("search") String search) {
        List list = new ArrayList<>();
        for (String item : StrSpliter.split(sort, ",", true, true)) {
            String fieldName = parseColumnName(getEntityClass(), item.substring(2));
            //小写正序，大写反序
            list.add("d_".equalsIgnoreCase(item.substring(0, 2)) ? new OrderItem().setColumn(fieldName).setAsc(false) : new OrderItem().setColumn(fieldName));
        }
        QueryWrapper wrapper = new QueryWrapper<T>();
        if (JSONUtil.isJsonObj(search)) {
            Map<String, Object> searchMap = JSONUtil.toBean(search, Map.class);
            for (String col : searchMap.keySet()) {
                String fieldName = parseColumnName(getEntityClass(), col);
                wrapper.like(fieldName, searchMap.get(col));
            }
        }
        return new Result(s.page(new Page<T>(page, size).addOrder(list), wrapper), "数据搜索并排序及分页返回成功", 0);
    }

    @GetMapping("/sort/{sort}")//sort内容形式：d_col1,a_col2,...,d_coln
    @ApiOperation(value = "只排序查找信息")
    public Result sort(@PathVariable("sort") String sort) {
        QueryWrapper queryWrapper = new QueryWrapper<T>();
        for (String item : sort.split("\\,")) {
            String fieldName = parseColumnName(getEntityClass(), item.substring(2));
            if ("d_".equalsIgnoreCase(item.substring(0, 2))) {
                queryWrapper.orderByDesc(fieldName);
            } else {
                queryWrapper.orderByAsc(fieldName);
            }
        }
        return new Result(s.list(queryWrapper), "数据排序返回成功", 0);
    }

    @GetMapping("/sort/{sort}/{page}/{size}")
    @ApiOperation(value = "排序后分页查找信息")
    public Result sortAndPage(@PathVariable("sort") String sort, @PathVariable("page") int page, @PathVariable("size") int size) {
        List list = new ArrayList<>();
        for (String item : sort.split("\\,")) {
            String fieldName = parseColumnName(getEntityClass(), item.substring(2));
            //小写正序，大写反序
            list.add("D_".equals(item.substring(0, 2)) ? new OrderItem().setColumn(fieldName).setAsc(false) : new OrderItem().setColumn(fieldName));
        }
        return new Result(s.page(new Page<T>(page, size).addOrder(list)), "数据排序并分页返回成功", 0);
    }

    @GetMapping("/search/{search}")
    @ApiOperation(value = "只查找信息")
    public Result search(@PathVariable("search") String search) {
        QueryWrapper queryWrapper = new QueryWrapper<T>();
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        try {
            Map<String, Object> searchMap = jacksonJsonParser.parseMap(search);
            for (String col : searchMap.keySet()) {
                String fieldName = parseColumnName(getEntityClass(), col);
                queryWrapper.like(true, fieldName, searchMap.get(col));
            }
        } catch (Exception e) {
            log.error("搜索内容不是map形式：" + search);
            queryWrapper = null;
        }
        return new Result(s.list(queryWrapper), "数据搜索返回成功", 0);
    }

    @GetMapping("/search/{search}/{sort}}")
    @ApiOperation(value = "查找后排序信息")
    public Result searchAndSort(@PathVariable("search") String search, @PathVariable("sort") String sort) {
        QueryWrapper wrapper = new QueryWrapper<T>();
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        try {
            Map<String, Object> searchMap = jacksonJsonParser.parseMap(search);
            for (String col : searchMap.keySet()) {
                String fieldName = parseColumnName(getEntityClass(), col);
                wrapper.like(true, fieldName, searchMap.get(col));
            }
        } catch (Exception e) {
            log.error("搜索内容不是map形式：" + search);
            wrapper = null;
        }
        for (String item : sort.split("\\,")) {
            String fieldName = parseColumnName(getEntityClass(), item.substring(2));
            //小写正序，大写反序
            if ("d_".equalsIgnoreCase(item.substring(0, 2))) {
                wrapper.orderByDesc(fieldName);
            } else {
                wrapper.orderByAsc(fieldName);
            }
        }
        return new Result(s.list(wrapper), "数据搜索并排序返回成功", 0);
    }

    @GetMapping("/search/{search}/{sort}/{page}/{size}")
    @ApiOperation(value = "查找后排序并分页信息")
    public Result searchAndSortAndPage(@PathVariable("search") String search, @PathVariable("sort") String sort, @PathVariable("page") int page, @PathVariable("size") int size) {
        QueryWrapper queryWrapper = new QueryWrapper<T>();
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        try {
            Map<String, Object> searchMap = jacksonJsonParser.parseMap(search);
            for (String col : searchMap.keySet()) {
                String fieldName = parseColumnName(getEntityClass(), col);
                queryWrapper.like(fieldName, searchMap.get(col));
            }
        } catch (Exception e) {
            log.error("搜索内容有误：" + search);
            queryWrapper = null;
        }
        List list = new ArrayList<>();
        for (String item : sort.split("\\,")) {
            String fieldName = parseColumnName(getEntityClass(), item.substring(2));
            //小写正序，大写反序
            list.add("d_".equalsIgnoreCase(item.substring(0, 2)) ? new OrderItem().setColumn(fieldName).setAsc(false) : new OrderItem().setColumn(fieldName));
        }
        return new Result(s.page(new Page<T>(page, size).addOrder(list), queryWrapper), "数据搜索并排序及分页返回成功", 0);
    }

    //检查重名
    @GetMapping("/checkRename/{name}")
    @ApiOperation(value = "检查信息是否有重复")
    public Result checkRename(@PathVariable("name") String name) {
        QueryWrapper wrapper = new QueryWrapper<T>();
        JacksonJsonParser jacksonJsonParser = new JacksonJsonParser();
        try {
            Map<String, Object> searchMap = jacksonJsonParser.parseMap(name);
            for (String col : searchMap.keySet()) {
                String fieldName = parseColumnName(getEntityClass(), col);
                wrapper.eq(fieldName, searchMap.get(col));
            }
        } catch (Exception e) {
            wrapper = null;
        }
        return new Result(s.count(wrapper) <= 0, "返回true表示可以命名，false表示已有此名称", 0);
    }

    protected String parseColumnName(Class<?> clazz, String columnName) {
        Field field = ReflectionUtils.findField(clazz, columnName);
        if (field == null) {
            return "";
        }
        TableField annotation = field.getAnnotation(TableField.class);
        if (annotation == null || "".equals(annotation.value())) {
            //驼峰转下划线处理
            return camelToUnderline(columnName);
        } else {
            return annotation.value();
        }
    }

    protected String camelToUnderline(String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append('_');
                c = Character.toLowerCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private final Class<T> getEntityClass() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        for (Type typeArgument : type.getActualTypeArguments()) {
            if (ClassUtils.isAssignable(CommonEntity.class, (Class<?>) typeArgument)) {
                return (Class<T>) typeArgument;
            }
        }
        throw new RuntimeException("无法获取到实体对象的Class，请检查泛型声明");
    }
}
