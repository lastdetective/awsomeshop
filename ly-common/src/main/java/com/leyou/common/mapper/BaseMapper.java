package com.leyou.common.mapper;

import sun.awt.util.IdentityArrayList;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

/**
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName BaseMapper
 * @Description
 * @date 2019-06-23 21:25
 */
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T>, IdListMapper<T, Long>, InsertListMapper<T> {
}
