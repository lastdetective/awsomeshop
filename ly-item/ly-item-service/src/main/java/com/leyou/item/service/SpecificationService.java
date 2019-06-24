package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyExceptiion;
import com.leyou.item.mapper.SpecMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName SpecificationService
 * @Description
 * @date 2019-06-22 15:18
 */
@Service
public class SpecificationService {
    @Autowired
    private SpecMapper groupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> getSpecGroup(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> list = groupMapper.select(specGroup);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyExceptiion(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }


    public void saveSpecParams(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    public void updateSpecParams(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    public void deleteSpecParams(Long id) {
        SpecParam specParam = new SpecParam();
        specParam.setId(id);
        specParamMapper.delete(specParam);
    }

    public List<SpecParam> getSpecParam(Long gid, Long cid, Boolean searching, Boolean generic) {

        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        param.setGeneric(generic);
        List<SpecParam> list = this.specParamMapper.select(param);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyExceptiion(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }
}
