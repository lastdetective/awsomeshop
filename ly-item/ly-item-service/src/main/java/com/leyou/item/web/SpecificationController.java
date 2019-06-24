package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName SpecificationController
 * @Description
 * @date 2019-06-22 15:24
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService service;

    /**
     * 查询某类商品的特征分组
     *
     * @param cid 商品类目的id
     * @return 某类商品详细特征的列表
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> getGroupsList(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(service.getSpecGroup(cid));
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> getSpecParam(    @RequestParam(value="gid", required = false) Long gid,
                                                            @RequestParam(value="cid", required = false) Long cid,
                                                            @RequestParam(value="searching", required = false) Boolean searching,
                                                            @RequestParam(value="generic", required = false) Boolean generic) {
        return ResponseEntity.ok(service.getSpecParam(gid,cid,searching,generic));
    }

    @PostMapping("params")
    public ResponseEntity<Void> saveSpecification(@RequestBody SpecParam specParam) {
        service.saveSpecParams(specParam);
        return ResponseEntity.ok().build();
    }

    @PutMapping("params")
    public ResponseEntity<Void> updateSpecification(@RequestBody SpecParam specParam) {
        service.updateSpecParams(specParam);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("params/{id}")
    public ResponseEntity<Void> deleteSpecification(@PathVariable("id") Long id) {
        service.deleteSpecParams(id);
        return  ResponseEntity.ok().build();
    }
}
