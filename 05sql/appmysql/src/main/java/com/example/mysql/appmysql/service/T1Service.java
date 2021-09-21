package com.example.mysql.appmysql.service;

import com.example.mysql.appmysql.entity.T1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (T1)表服务接口
 *
 * @author makejava
 * @since 2021-09-21 21:25:22
 */
public interface T1Service {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    T1 queryById(Integer id);

    /**
     * 分页查询
     *
     * @param t1 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<T1> queryByPage(T1 t1, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param t1 实例对象
     * @return 实例对象
     */
    T1 insert(T1 t1);

    /**
     * 修改数据
     *
     * @param t1 实例对象
     * @return 实例对象
     */
    T1 update(T1 t1);

    /**
     * 通过主键删除数据
     *
     * @param  id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
