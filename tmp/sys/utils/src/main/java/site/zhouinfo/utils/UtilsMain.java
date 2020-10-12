package site.zhouinfo.utils;

import site.zhouinfo.stringutils.PinyinUtils;

/**
 * utils
 * Author:      zhou
 * Create Date：2016-02-10 20:51
 */
public class UtilsMain {
	public static void main(String... args){
		UtilsMain utilsMain = new UtilsMain();
		//utilsMain.pinyinUtilsMain();

		utilsMain.emailUtilsMain();

	}

	public void pinyinUtilsMain(){
		PinyinUtils pinyinUtils = new PinyinUtils();
		System.out.println("全拼："+ pinyinUtils.getPinyin("中华人民共和国"));
		System.out.println("首字母："+ pinyinUtils.getFirstSpell("中华人民共和国"));
	}

	public void emailUtilsMain(){
		// 设置发件人地址、收件人地址和邮件标题
		EmailUtils emailUtils = new EmailUtils();
		//emailUtils.setAddress("1205657837@qq.com", "123456@qq.com", "源代码");
		emailUtils.send("<td style=\"-moz-hyphens: auto; -webkit-hyphens: auto; border-collapse: collapse !important; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; hyphens: auto; line-height: 19px; margin: 0; padding: 0; text-align: left; vertical-align: top; word-break: break-word\" align=\"left\" valign=\"top\">\n" +
				"\n" +
				"                  <table class=\"email-header\" style=\"border-collapse: collapse; border-spacing: 0; margin-top: 20px; padding: 0; text-align: left; vertical-align: top; width: 100%\">\n" +
				"                    <tbody><tr style=\"padding: 0; text-align: left; vertical-align: top\" align=\"left\">\n" +
				"                      <td class=\"center\" align=\"center\" style=\"-moz-hyphens: auto; -webkit-hyphens: auto; border-collapse: collapse !important; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; hyphens: auto; line-height: 19px; margin: 0; padding: 0; text-align: center; vertical-align: top; word-break: break-word\" valign=\"top\">\n" +
				"                        <center style=\"min-width: 580px; width: 100%\">\n" +
				"                          <div class=\"invertocat-mark\" style=\"margin-bottom: 30px; margin-top: 20px; text-align: center !important\" align=\"center !important\">\n" +
				"                            <img class=\"center\" src=\"https://assets-cdn.github.com/images/email/global/invertocat-mark.png\" width=\"50\" height=\"48\" style=\"-ms-interpolation-mode: bicubic; clear: both; display: block; float: none; height: 48px; margin: 0 auto; max-height: 48px; max-width: 50px; outline: none; text-decoration: none; width: 50px\" align=\"none\">\n" +
				"                          </div>\n" +
				"                        </center>\n" +
				"                      </td>\n" +
				"                      <td class=\"expander\" style=\"-moz-hyphens: auto; -webkit-hyphens: auto; border-collapse: collapse !important; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; hyphens: auto; line-height: 19px; margin: 0; padding: 0; text-align: left; vertical-align: top; visibility: hidden; width: 0px; word-break: break-word\" align=\"left\" valign=\"top\"></td>\n" +
				"                    </tr>\n" +
				"                  </tbody></table>\n" +
				"\n" +
				"                  <table class=\"wrapper email-body\" style=\"background: #ffffff; border-collapse: collapse; border-radius: 3px !important; border-spacing: 0; border: 1px solid #dddddd; box-shadow: 0 1px 3px rgba(0,0,0,0.05) !important; padding: 0; text-align: left; vertical-align: top\" bgcolor=\"#ffffff\">\n" +
				"                    <tbody><tr style=\"padding: 0; text-align: left; vertical-align: top\" align=\"left\">\n" +
				"                      <td class=\"text-pad\" style=\"-moz-hyphens: auto; -webkit-hyphens: auto; border-collapse: collapse !important; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; hyphens: auto; line-height: 19px; margin: 0; padding: 0; text-align: left; vertical-align: top; word-break: break-word\" align=\"left\" valign=\"top\">\n" +
				"\n" +
				"                        <div class=\"email-content\" style=\"color: #333333; font-size: 14px; font-weight: normal; line-height: 20px; margin: 20px\">\n" +
				"\n" +
				"                          <p style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; line-height: 19px; margin: 0 0 10px; padding: 0; text-align: left\" align=\"left\">\n" +
				"  Hi <strong>@zhoufox</strong>!\n" +
				"</p>\n" +
				"<p style=\"color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; line-height: 19px; margin: 0 0 10px; padding: 0; text-align: left\" align=\"left\">\n" +
				"  Help us secure your GitHub account by verifying your email address\n" +
				"  (<a class=\"plaintext\" href=\"mailto:zhouinfo@qq.com\" style=\"color: #333333; text-decoration: none\" target=\"_blank\">zhouinfo@qq.com</a>).\n" +
				"    This will let you receive notifications and password resets from GitHub.\n" +
				"</p>\n" +
				"\n" +
				"\n" +
				"<div class=\"cta-button-wrap cta-button-wrap-centered\" style=\"color: #ffffff; padding: 20px 0 33px; text-align: center\" align=\"center\">\n" +
				"\n" +
				"    <a class=\"cta-button\" href=\"https://github.com/users/zhoufox/emails/19602152/confirm_verification/c050228e0de37b6079f4dcb8cfd4594ad7d23f44?utm_campaign=github-email-verification&amp;utm_content=html&amp;utm_medium=email&amp;utm_source=verification-email\" style=\"-webkit-border-radius: 3px; -webkit-text-size-adjust: none; background: #4183C4; border-radius: 3px; box-shadow: 0px 3px 0px #25588c; color: #ffffff; display: inline-block; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 17px; font-weight: bold; letter-spacing: normal; margin: 0 auto; padding: 12px 24px; text-align: center; text-decoration: none; width: auto !important\" target=\"_blank\">Verify email address</a>\n" +
				"\n" +
				"</div>\n" +
				"\n" +
				"\n" +
				"<hr style=\"background: #d9d9d9; border: none; color: #d9d9d9; height: 1px; margin: 10px 0 20px\">\n" +
				"\n" +
				"<p class=\"email-text-small email-text-gray\" style=\"color: #777777; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 12px; font-weight: normal; line-height: 19px; margin: 0 0 10px; padding: 0; text-align: left\" align=\"left\">\n" +
				"  Button not working? Paste the following link into your browser:<br>\n" +
				"  <a href=\"https://github.com/users/zhoufox/emails/19602152/confirm_verification/c050228e0de37b6079f4dcb8cfd4594ad7d23f44?utm_campaign=github-email-verification&amp;utm_content=html&amp;utm_medium=email&amp;utm_source=verification-email\" style=\"color: #4183C4; text-decoration: underline\" target=\"_blank\">https://github.com/users/zhoufox/emails/19602152/confirm_verification/c050228e0de37b6079f4dcb8cfd4594ad7d23f44</a>\n" +
				"</p>\n" +
				"\n" +
				"<p class=\"email-text-small email-text-gray\" style=\"color: #777777; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 12px; font-weight: normal; line-height: 19px; margin: 0; padding: 0; text-align: left\" align=\"left\">\n" +
				"  You’re receiving this email because you recently\n" +
				"  created a new GitHub account or added a new email address.\n" +
				"  If this wasn’t you, please ignore this email.\n" +
				"</p>\n" +
				"\n" +
				"\n" +
				"                        </div>\n" +
				"\n" +
				"                      </td>\n" +
				"                    </tr>\n" +
				"                  </tbody></table>\n" +
				"\n" +
				"                  <table class=\"email-footer\" style=\"border-collapse: collapse; border-spacing: 0; margin-bottom: 30px; padding: 0; text-align: left; vertical-align: top; width: 100%\">\n" +
				"                    <tbody><tr style=\"padding: 0; text-align: left; vertical-align: top\" align=\"left\">\n" +
				"                      <td class=\"center\" align=\"center\" style=\"-moz-hyphens: auto; -webkit-hyphens: auto; border-collapse: collapse !important; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; hyphens: auto; line-height: 19px; margin: 0; padding: 0; text-align: center; vertical-align: top; word-break: break-word\" valign=\"top\">\n" +
				"                        <center style=\"min-width: 580px; width: 100%\">\n" +
				"                          <div class=\"wordmark\" style=\"margin-bottom: 25px; margin-top: 25px; text-align: center !important\" align=\"center !important\">\n" +
				"                            <img class=\"center\" src=\"https://assets-cdn.github.com/images/email/global/wordmark.png\" width=\"102\" height=\"28\" style=\"-ms-interpolation-mode: bicubic; clear: both; display: block; float: none; height: 28px; margin: 0 auto; max-height: 28px; max-width: 102px; outline: none; text-decoration: none; width: 102px\" align=\"none\">\n" +
				"                          </div>\n" +
				"                          <p class=\"footer-text\" style=\"color: #888888; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 12px; font-weight: normal; line-height: 1.5; margin: 0 0 10px; padding: 0; text-align: center\" align=\"center\">\n" +
				"                            Sent with &lt;3 by <strong>GitHub</strong>.<br>\n" +
				"                            GitHub, Inc. 88 Colin P Kelly Jr Street<br>San Francisco, CA <span style=\"border-bottom:1px dashed #ccc;z-index:1\" t=\"7\" onclick=\"return false;\" data=\"94107\">94107</span>\n" +
				"                          </p>\n" +
				"                        </center>\n" +
				"                      </td>\n" +
				"                      <td class=\"expander\" style=\"-moz-hyphens: auto; -webkit-hyphens: auto; border-collapse: collapse !important; color: #222222; font-family: 'Helvetica', 'Arial', sans-serif; font-size: 14px; font-weight: normal; hyphens: auto; line-height: 19px; margin: 0; padding: 0; text-align: left; vertical-align: top; visibility: hidden; width: 0px; word-break: break-word\" align=\"left\" valign=\"top\"></td>\n" +
				"                    </tr>\n" +
				"                  </tbody></table>\n" +
				"\n" +
				"                </td>");
	}
}
