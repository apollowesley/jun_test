package io.lemur.map.model.baidu.web.direction;

import io.lemur.map.model.baidu.web.base.BaiduBaseModel;

/**
 * 瀵艰埅鍩虹model
 * @author JueYue
 * @date 2015骞?链?7镞?
 */
public class DirectionBaseModel extends BaiduBaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 1锛氲捣缁堢偣鏄ā绯婄殑锛屾镞剁粰鍑虹殑鏄€夋嫨椤甸溃锛?锛氲捣缁堢偣閮芥槸鏄庣‘镄勶紝鐩磋烦
     */
    private int               type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
