package com.ilvyou.data;

import com.ilvyou.core.dao.CommonDao;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GuanYuCai on 2016/9/27 0027.
 */
public class TableMap {

    public static Map<String, CommonDao.Table> lhf = new HashMap() {
        {
            this.put("ScrAlipayEntity", new CommonDao.Table("dbo.scr_alipay", "actid"));
            this.put("AlipayAccountEntity", new CommonDao.Table("dbo.alipay_account", "actid"));
            this.put("AlipayPeriodEntity", new CommonDao.Table("dbo.alipay_period", "periodid"));
            this.put("CreditAccountEntity", new CommonDao.Table("dbo.credit_account", "actid"));
            this.put("CreditPeriodHeadEntity", new CommonDao.Table("dbo.credit_period_head", "periodid"));
            this.put("ScrAnswerEntity", new CommonDao.Table("dbo.scr_answer", "id"));
            this.put("ScrPhoneEntity", new CommonDao.Table("dbo.scr_phone", "sendid"));
            this.put("ScrQuestionEntity", new CommonDao.Table("dbo.scr_question", "id"));
            this.put("ScrUserEntity", new CommonDao.Table("dbo.scr_user", "userid"));
            this.put("AlipayTransferEntity", new CommonDao.Table("dbo.alipay_transfer", "logid"));
            this.put("AlipayPbankEntity", new CommonDao.Table("dbo.alipay_pbank", "banknumber"));
            this.put("AlipayQpcardEntity", new CommonDao.Table("dbo.alipay_qpcard", "pkid"));
            this.put("AlipayBanktypeEntity", new CommonDao.Table("dbo.alipay_banktype", "bankid"));
            this.put("SrcAuthbaseEntity", new CommonDao.Table("dbo.src_authbase", "id"));
            this.put("AlipayLogEntity", new CommonDao.Table("dbo.alipay_log", "logid"));
            this.put("AlipayWithdrawEntity", new CommonDao.Table("dbo.alipay_withdraw", "logid"));
            this.put("AlipayDecreaseEntity", new CommonDao.Table("dbo.alipay_decrease", "logid"));
            this.put("AlipayIncreaseEntity", new CommonDao.Table("dbo.alipay_increase", "logid"));
            this.put("AlipayInanddeEntity", new CommonDao.Table("dbo.alipay_inandde", "pkid"));
            this.put("AlipayRechargeEntity", new CommonDao.Table("dbo.alipay_recharge", "logid"));
            this.put("AlipayReceiveEntity", new CommonDao.Table("dbo.alipay_receive", "logid"));
            this.put("AlipayPaymentEntity", new CommonDao.Table("dbo.alipay_payment", "logid,factid"));
            this.put("AlipayCpayEntity",new CommonDao.Table("dbo.alipay_cpay","logid"));
            this.put("AlipayCreceiveEntity",new CommonDao.Table("dbo.alipay_creceive","reflogid"));
        }
    };

    public static Map<String, CommonDao.Table> lxb = new HashMap() {
        {
            this.put("ApplyAccountEntity", new CommonDao.Table("public.apply_account", "taskid"));
            this.put("ApplyAuditEntity", new CommonDao.Table("public.apply_audit", "taskid,pkid"));
            this.put("ApplyFuserEntity", new CommonDao.Table("public.apply_fuser", "taskid"));
            this.put("ApplyPersonEntity", new CommonDao.Table("public.apply_person", "taskid"));
            this.put("ApplyRuleEntity", new CommonDao.Table("public.apply_rule", "pkid"));
            this.put("BaseCategoryEntity",new CommonDao.Table("public.base_category","id"));
            this.put("BaseDicdataEntity",new CommonDao.Table("public.base_dicdata","categoryid,diccode"));
            this.put("BaseSequenceEntity",new CommonDao.Table("public.base_sequence","tablename,columnname"));
            this.put("CreditAccountEntity", new CommonDao.Table("public.credit_account", "actid"));
            this.put("CreditAdailyEntity", new CommonDao.Table("public.credit_adaily", "actid"));
            this.put("CreditAdataEntity", new CommonDao.Table("public.credit_adata", "actid"));
            this.put("CreditAdjustEntity", new CommonDao.Table("public.credit_adjust", "adjustid"));
            this.put("CreditAlogEntity", new CommonDao.Table("public.credit_alog", "pkid"));
            this.put("CreditOrderEntity", new CommonDao.Table("public.credit_order", "logid"));
            this.put("CreditPconfig1Entity", new CommonDao.Table("public.credit_pconfig1", "periodmonth"));
            this.put("CreditPeriodEntity", new CommonDao.Table("public.credit_period", "periodid"));
            this.put("CreditPextEntity", new CommonDao.Table("public.credit_pext", "periodid"));
            this.put("CreditPlogEntity", new CommonDao.Table("public.credit_plog", "logid"));
            this.put("CreditPvogEntity", new CommonDao.Table("public.credit_pvog", "logid"));
            this.put("CreditTpayEntity", new CommonDao.Table("public.credit_tpay", "logid"));
            this.put("CreditTrefundEntity", new CommonDao.Table("public.credit_trefund", "djid"));
            this.put("CreditTrefund1Entity", new CommonDao.Table("public.credit_trefund1", "logid"));
            this.put("CreditTrefund2Entity", new CommonDao.Table("public.credit_trefund2", "logid"));
            this.put("CreditTrepayEntity", new CommonDao.Table("public.credit_trepay", "djid"));
            this.put("CreditTrepay1Entity", new CommonDao.Table("public.credit_trepay1", "logid"));
            this.put("CreditTrepay2Entity", new CommonDao.Table("public.credit_trepay2", "logid"));
            this.put("OperatAruleEntity", new CommonDao.Table("public.operat_arule", "rulecode"));
            this.put("OperatConfigEntity", new CommonDao.Table("public.operat_config", "snum"));
            this.put("PlatBudgetEntity", new CommonDao.Table("public.plat_budget", "budid"));
            this.put("PlatValogEntity", new CommonDao.Table("public.plat_valog", "platid"));
            this.put("PlatVirtualEntity", new CommonDao.Table("public.plat_virtual", "platid"));
            this.put("PlatVrlogEntity", new CommonDao.Table("public.plat_vrlog", "logid"));
            this.put("SecurityApplyEntity", new CommonDao.Table("public.security_apply", "actid"));
            this.put("SecurityFuserEntity", new CommonDao.Table("public.security_fuser", "userid"));
            this.put("SysLoginhistorEntity", new CommonDao.Table("public.sys_loginhistor", "pkid"));
            this.put("SysLoginipEntity", new CommonDao.Table("public.sys_loginip", "pkid"));
            this.put("SysOptlogEntity", new CommonDao.Table("public.sys_optlog", "pkid"));
            this.put("WatchDamountEntity", new CommonDao.Table("public.watch_damount", "pkid"));
        }
    };

    public static Map<String, CommonDao.Table> lvzan = new HashMap() {
        {
            this.put("BaseSequenceEntity", new CommonDao.Table("public.base_sequence", "columnname,tablename"));
            this.put("AppUserEntity", new CommonDao.Table("public.app_user", "id"));
            this.put("BppUserEntity", new CommonDao.Table("public.bpp_user", "userid"));
            this.put("BppCompanyEntity", new CommonDao.Table("public.bpp_company", "id"));
            this.put("BppCompanyApplyEntity", new CommonDao.Table("public.bpp_company_apply", "id"));
        }
    };

    public static Map<String, CommonDao.Table> lzClub = new HashMap() {
        {
            this.put("MonRewardEntity", new CommonDao.Table("public.mon_reward", "monid"));
        }
    };

    public static Map<String, CommonDao.Table> ilvyou = new HashMap() {
        {
            this.put("AccountEntity", new CommonDao.Table("public.account", "id,loginname"));
        }
    };

    public static Map<String, CommonDao.Table> risk = new HashMap() {
        {
            this.put("CustomerPverifyEntity", new CommonDao.Table("public.customer_pverify", "userid"));
        }
    };


    public TableMap() {
    }
}
