package com.meiriyouke.easycode.main;

import com.meiriyouke.easycode.generator.GenerationOrganizer;
import com.meiriyouke.easycode.utils.XmlParser;

/**
 * 工具启动类
 *
 * User: liyd
 * Date: 13-12-6
 * Time: 下午3:59
 */
public class EasyCodeStarter {

    public static void main(String[] args) {

        //解析配置文件
        XmlParser.parseConfigXml("easyCode.xml");

        GenerationOrganizer generationOrganizer = new GenerationOrganizer();
        generationOrganizer.codeGenerate();

    }
}
