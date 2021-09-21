package com.example.mysql.appmysql.dao;

import com.example.mysql.appmysql.entity.T1;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (T1)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-21 21:25:07
 */
public interface T1Dao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    T1 queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param t1 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<T1> queryAllByLimit(T1 t1, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param t1 查询条件
     * @return 总行数
     */
    long count(T1 t1);

    /**
     * 新增数据
     *
     * @param t1 实例对象
     * @return 影响行数
     */
    int insert(T1 t1);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<T1> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<T1> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<T1> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<T1> entities);

    /**
     * 修改数据
     *
     * @param t1 实例对象
     * @return 影响行数
     */
    int update(T1 t1);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

