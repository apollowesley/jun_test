package com.site.web.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.site.common.utils.Page;
import com.site.common.utils.date.DateUtil;
import com.site.dto.BusiLogDto;
import com.site.dto.DownloadBusiLogDto;
import com.site.entity.BusiLog;
import com.site.service.BusiLogService;

/**
 * @author Vincent.wang
 *
 */
@Controller
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private BusiLogService rpcLogService;

    @InitBinder
    public void initBinder(WebDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, dateEditor);
    }

    /**
     * 首页
     * 
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        log.warn("## rpc-logs index page.");
        Page<BusiLog> page = new Page<BusiLog>();
        page = rpcLogService.findBusiLogByPage(page, null);
        model.addAttribute("page", page);
        return "busi_log";
    }

    /**
     * 分页查询日志
     * 
     * @param busiLogDto
     *            日志查询条件
     * @param model
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String page(@ModelAttribute("busiLogDto") BusiLogDto busiLogDto, Model model) {
        log.warn("## rpc-logs index page.");
        Page<BusiLog> page = new Page<BusiLog>(busiLogDto.getCurrentPage(), busiLogDto.getPageSize());
        page = rpcLogService.findBusiLogByPage(page, busiLogDto);
        model.addAttribute("page", page);
        model.addAttribute("busiLog", busiLogDto);
        return "busi_log_page";
    }

    /**
     * 下载当前分页数据
     * 
     * @param filepath
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/download/all", method = RequestMethod.POST)
    public ResponseEntity<byte[]> downloadAll(@ModelAttribute("busiLogDto") BusiLogDto busiLogDto) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        DownloadBusiLogDto dto = rpcLogService.getBytes(busiLogDto);
        headers.setContentDispositionFormData("attachment", new String(dto.getFileName().getBytes("UTF-8"), "ISO8859-1"));
        return new ResponseEntity<byte[]>(dto.getBytes(), headers, HttpStatus.CREATED);
    }

    /**
     * 下载当前分页数据
     * 
     * @param filepath
     * @return
     * @throws IOException
     */

    @RequestMapping(value = "/download/currentPage", method = RequestMethod.POST)
    public ResponseEntity<byte[]> downloadCurrentPageBusiLog(@ModelAttribute("busiLogDto") BusiLogDto busiLogDto) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(("第" + busiLogDto.getCurrentPage() + "页数据.csv").getBytes("UTF-8"), "ISO8859-1"));
        byte[] bytes = getBytes(busiLogDto);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
    }

    public byte[] getBytes(BusiLogDto busiLogDto) {
        Page<BusiLog> page = new Page<BusiLog>(busiLogDto.getCurrentPage(), busiLogDto.getPageSize());
        page = rpcLogService.findBusiLogByPage(page, busiLogDto);
        StringBuilder csv = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(page.getResultList())) {
            for (BusiLog bl : page.getResultList()) {
                csv.append(bl.getId() + "\t,\t");
                csv.append(bl.getSystemName() + "\t,\t");
                csv.append(DateUtil.dateToString(bl.getCreateTime(), DateUtil.fm_yyyy_MM_dd_HHmmssSSS) + "\t,\t");
                csv.append(bl.getLevel() + "\t,\t");
                csv.append(bl.getThreadName() + "\t,\t");
                csv.append(bl.getLogName() + "\t,\t");
                csv.append(bl.getMessage() + "\t\r\n");
            }
        }
        return csv.toString().getBytes();
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error403() {
        return "/common/403";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "/common/404";
    }

}
