package com.example.mysql.appmysql.controller;

import com.example.mysql.appmysql.entity.T1;
import com.example.mysql.appmysql.service.T1Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (T1)表控制层
 *
 * @author makejava
 * @since 2021-09-21 21:25:01
 */
@RestController
@RequestMapping("t1")
public class T1Controller {
    /**
     * 服务对象
     */
    @Resource
    private T1Service t1Service;

    /**
     * 分页查询
     *
     * @param t1 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @PostMapping("/list")
    public ResponseEntity<Page<T1>> queryByPage(T1 t1, PageRequest pageRequest) {
        return ResponseEntity.ok(this.t1Service.queryByPage(t1, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<T1> queryById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.t1Service.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param t1 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<T1> add(@RequestBody T1 t1) {
        return ResponseEntity.ok(this.t1Service.insert(t1));
    }

    /**
     * 编辑数据
     *
     * @param t1 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<T1> edit(@RequestBody T1 t1) {
        return ResponseEntity.ok(this.t1Service.update(t1));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.t1Service.deleteById(id));
    }

}

