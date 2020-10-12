package org.gen.code.meta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by meter on 2015/8/3.
 */
public class CodecMeta {
    private String codecPackage;
    private String klassName;
    private String klassSimpleName;
    private List<String> fields = new ArrayList<>();
    private Set<String> importList = new HashSet<>();
    private List<EncoderMeta> encoderMetas = new ArrayList<>();

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public Set<String> getImportList() {
        return importList;
    }

    public void setImportList(Set<String> importList) {
        this.importList = importList;
    }

    public List<EncoderMeta> getEncoderMetas() {
        return encoderMetas;
    }

    public void setEncoderMetas(List<EncoderMeta> encoderMetas) {
        this.encoderMetas = encoderMetas;
    }

    public String getCodecPackage() {
        return codecPackage;
    }

    public void setCodecPackage(String codecPackage) {
        this.codecPackage = codecPackage;
    }

    public String getKlassName() {
        return klassName;
    }

    public void setKlassName(String klassName) {
        this.klassName = klassName;
    }

    public String getKlassSimpleName() {
        return klassSimpleName;
    }

    public void setKlassSimpleName(String klassSimpleName) {
        this.klassSimpleName = klassSimpleName;
    }
}
