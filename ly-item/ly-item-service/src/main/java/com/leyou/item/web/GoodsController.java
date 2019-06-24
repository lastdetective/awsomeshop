package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Bruce Liu
 * @version V1.0.0
 * @ClassName GoodsController
 * @Description
 * @date 2019-06-22 20:46
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService service;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    ) {
        return ResponseEntity.ok(service.querySpuByPage(pageNum, pageSize, saleable, key));
    }

    @PostMapping("goods")
    public ResponseEntity<Void> saveSomeThing(@RequestBody Spu spu) {
        service.save(spu);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
