package org.coody.czone.web.blog.schema;

import java.io.UnsupportedEncodingException;

import org.coody.czone.common.utils.GZIPUtils;
import org.coody.czone.web.blog.domain.JournalInfo;
import org.coody.framework.core.util.PropertUtil;

@SuppressWarnings("serial")
public class JournalSchema extends JournalInfo{
	
	public JournalSchema(JournalInfo journal) {
		PropertUtil.copyPropertys(journal, this);
		try {
			this.detail=new String(GZIPUtils.uncompress(journal.getContext()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private String detail;

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	
	
}
