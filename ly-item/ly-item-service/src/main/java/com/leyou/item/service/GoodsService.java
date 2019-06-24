package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExceptiion;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName GoodsService
 * @Description
 * @date 2019-06-22 20:48
 */
@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SpuDetailMapper detailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> querySpuByPage(Integer pageNum,
                                          Integer pageSize,
                                          Boolean saleable,
                                          String key) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();

        // 过滤调逻辑删除的项目
        criteria.andEqualTo("valid", true);
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);
        }
        // 默认排序
        example.setOrderByClause("last_update_time desc");
        List<Spu> list = spuMapper.selectByExample(example);
        loadCategoryAndBrandName(list);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyExceptiion(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        // 解析分页结果
        PageInfo<Spu> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(), list);
    }

    public void loadCategoryAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }

    }

    @Transactional
    public void save(Spu spu) {
        // 新增 spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);

        int count = spuMapper.insert(spu);
        if (count != 1) {
            throw new LyExceptiion(ExceptionEnum.SAVE_GOODS_ERROR);
        }
        // 新增 detail
        SpuDetail detail = spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        detailMapper.insert(detail);

        List<Stock> stockList = new ArrayList<>();
        List<Sku> skus = spu.getSkus();
        skus.stream().forEach(sku -> {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            int countSku = skuMapper.insert(sku);
            if (countSku != 1) {
                throw new LyExceptiion(ExceptionEnum.SAVE_GOODS_ERROR);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        });
        stockMapper.insertList(stockList);
        // 新增 sku

        // 新增库存
    }
}
