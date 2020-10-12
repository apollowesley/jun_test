package weixin.core;

import java.io.Writer;

import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class XppDriver4Weixin extends XppDriver {
	
	public XppDriver4Weixin() {
		super();
	}

	public XppDriver4Weixin(XmlFriendlyReplacer replacer) {
		super(replacer);
	}
	
	private static final String CDTATA_START = "<![CDATA[";
	private static final String CDTATA_END = "]]>";

	// 对所有xml节点的转换都增加CDATA标记  
    private boolean cdata = true;

	@Override
	public HierarchicalStreamWriter createWriter(Writer out) {
		return new PrettyPrintWriter(out) {  

            protected void writeText(QuickWriter writer, String text) {  
                if (cdata) {  
                    writer.write(CDTATA_START);  
                    writer.write(text);  
                    writer.write(CDTATA_END);  
                } else {  
                    writer.write(text);  
                }  
            }  
        };  
	}

	public void setCdata(boolean cdata) {
		this.cdata = cdata;
	}
    
}
