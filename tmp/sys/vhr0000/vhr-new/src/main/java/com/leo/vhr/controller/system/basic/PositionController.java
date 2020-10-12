package com.leo.vhr.controller.system.basic;

import com.leo.vhr.model.Position;
import com.leo.vhr.model.RespBean;
import com.leo.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/16
 * @version: 1.0
 */
@RestController
@RequestMapping("/system/basic/pos")
public class PositionController
{
    @Autowired
    PositionService positionService;

    @GetMapping("/")
    public List<Position> getAllPositions()
    {
        return positionService.getAllPositions();
    }

    @PostMapping("/")
    public RespBean addPositions(@RequestBody Position position)
    {
        if (positionService.addPositions(position) == 1)
        {
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @PutMapping("/")
    public RespBean updatePositions(@RequestBody Position position)
    {
        if (positionService.updatePositions(position) == 1)
        {
            return RespBean.ok("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePositionsById(@PathVariable Integer id)
    {
        if (positionService.deletePositionsById(id) == 1)
        {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @DeleteMapping("/")
    public RespBean deletePositionsByIds(Integer[] ids)
    {
        if (positionService.deletePositionsByIds(ids) == ids.length)
        {
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
