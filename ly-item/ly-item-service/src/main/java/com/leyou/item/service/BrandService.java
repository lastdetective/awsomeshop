package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExceptiion;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper mapper;

    public PageResult<Brand> getBrandByConditions(Integer page,
                                                  Integer rows,
                                                  String sortBy,
                                                  Boolean desc,
                                                  String key) {
        // 分页
        PageHelper.startPage(page, rows);
        Example example = new Example(Brand.class);
        // 查询条件
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key.toUpperCase());
        }
        // 排序
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        //  PageInfo<Brand> list = (Page<Brand>) mapper.selectByExample(example);
       /* // 查询
        Page<Brand> pageInfo = (Page<Brand>) mapper.selectByExample(example);
        // 返回结果
        return new PageResult<>(pageInfo.getTotal(), pageInfo);*/
        List<Brand> list = mapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyExceptiion(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return new PageResult<>(pageInfo.getTotal(), list);
    }

    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int count = mapper.insert(brand);
        if (count != 1) {
            throw new LyExceptiion(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        for (Long cid : cids) {
            count = mapper.saveBrandCategory(cid, brand.getId());
            if (count != 1) {
                throw new LyExceptiion(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }

    public Brand queryById(Long id) {
        Brand brand = mapper.selectByPrimaryKey(id);
        if (brand == null) {
            throw new LyExceptiion(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brand;
    }

    public List<Brand> getBrandByCid(Long cid) {
        List<Brand> list = mapper.queryBrandByCategoryId(cid);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyExceptiion(ExceptionEnum.CATEGORY_BRAND_NOT_FOUND);
        }
        return list;
    }
}
