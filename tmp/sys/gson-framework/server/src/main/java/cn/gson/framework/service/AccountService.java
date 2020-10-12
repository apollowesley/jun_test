package cn.gson.framework.service;

import cn.gson.framework.model.mapper.AccountMapper;
import cn.gson.framework.model.pojo.Account;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends ServiceImpl<AccountMapper, Account>{

}
