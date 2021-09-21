package com.example.mysql.appmysql.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.mysql.appmysql.entity.T1;
import com.example.mysql.appmysql.dao.T1Dao;
import com.example.mysql.appmysql.service.T1Service;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (T1)表服务实现类
 *
 * @author makejava
 * @since 2021-09-21 21:25:25
 */
@Service("t1Service")
@DS("slave")
public class T1ServiceImpl implements T1Service {
    @Resource
    private T1Dao t1Dao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public T1 queryById(Integer id) {
        return this.t1Dao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param t1 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<T1> queryByPage(T1 t1, PageRequest pageRequest) {
        long total = this.t1Dao.count(t1);
        return new PageImpl<>(this.t1Dao.queryAllByLimit(t1, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param t1 实例对象
     * @return 实例对象
     */
    @Override
    public T1 insert(T1 t1) {
        this.t1Dao.insert(t1);
        return t1;
    }

    /**
     * 修改数据
     *
     * @param t1 实例对象
     * @return 实例对象
     */
    @Override
    public T1 update(T1 t1) {
        this.t1Dao.update(t1);
        return this.queryById(t1.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param  主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.t1Dao.deleteById(id) > 0;
    }
}
