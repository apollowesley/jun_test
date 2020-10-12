package org.neuedu.his.service.impl;

import org.neuedu.his.entity.Invoice;
import org.neuedu.his.mapper.InvoiceMapper;
import org.neuedu.his.service.IInvoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发票 服务实现类
 * </p>
 *
 * @author CDHong
 * @since 2020-03-30
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements IInvoiceService {

}
