package com.managementsystem.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.managementsystem.entity.ProcedureConfigs;
import com.managementsystem.service.ProcedureConfigsService;

@Controller
@RequestMapping("/ProcedureConfig")
public class ProcedureConfigController {
	@Autowired
	private ProcedureConfigsService procedureConfigsService;
	
    @RequestMapping(value = "/getProcedureConfigs", method = RequestMethod.GET)
	public @ResponseBody List<ProcedureConfigs> getProcedureConfigs() {
		List<ProcedureConfigs> procedureConfigs=procedureConfigsService.getAll();
		Collections.sort(procedureConfigs, new Comparator<ProcedureConfigs>() {
            @Override
            public int compare(ProcedureConfigs o1, ProcedureConfigs o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = o1.getCreateDate();
                    Date dt2 = o2.getCreateDate();
                    if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
		return procedureConfigs;		
	}
    //增加
    @RequestMapping(value = "/addProcedureConfig", method = RequestMethod.POST)
	public @ResponseBody String addProcedureConfig(ProcedureConfigs procedure) {
    	procedure.setCreateDate(new Date());
		int checked=procedureConfigsService.save(procedure);
		if(1!=checked) {
			return "保存失败!";
		}else {
			return "保存成功!";
		}
	}
    @RequestMapping(value = "/deleteProcedureConfig", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> deleteProcedureConfig(long id) {
    	try {
        	ProcedureConfigs procedureConfigs=procedureConfigsService.getById(id);
        	List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
        	Map<String, Object> map=new HashMap<String, Object>();
        	map.put("status", "200");
        	map.put("message", "200");

        	int checked = procedureConfigsService.delete(procedureConfigs);
    		if(1!=checked) {
    			map.put("message", "删除失败!");
    		}else {
    			map.put("message", "删除成功!");
    		}
    		
    		result.add(map);
    		return result;
    	}catch (Exception e) {
			return null;
		}
	}
}
