package com.foo.common.base.pojo;

import java.util.Arrays;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Sets;

public class MavenDependencyModel implements Comparable<MavenDependencyModel> {

    private final static Logger logger = LoggerFactory
            .getLogger(MavenDependencyModel.class);

    private String groupId;
    private String artifactId;
    private String version;
    private String type;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static void main(String[] args) {

        int a[] = {2, 6, 8};
        Arrays.sort(a);

        Set<MavenDependencyModel> set = Sets.newTreeSet();

        MavenDependencyModel model1 = new MavenDependencyModel();
        model1.setGroupId("org.hibernate");
        model1.setArtifactId("b");
        model1.setVersion("4.3.7.Final");

        MavenDependencyModel model2 = new MavenDependencyModel();
        model2.setGroupId("org.hibernate");
        model2.setArtifactId("a");
        model2.setVersion("4.3.7.Final");

        set.add(model1);
        set.add(model2);

        logger.info("result:{} and hashCode for model1:{} and model2:{}",
                model1.equals(model2), model1.hashCode(), model2.hashCode());

        for (MavenDependencyModel mavenDependencyModel : set) {
            logger.info("{}", mavenDependencyModel);
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MavenDependencyModel) {
            MavenDependencyModel that = (MavenDependencyModel) obj;
            return Objects.equal(this.artifactId, that.artifactId)
                    && Objects.equal(this.groupId, that.groupId)
                    && Objects.equal(this.version, that.version);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(groupId, artifactId, version);
    }

    @Override
    public int compareTo(MavenDependencyModel that) {
        return ComparisonChain.start().compare(this.groupId, that.groupId)
                .compare(this.artifactId, that.artifactId).result();

    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("groupId", groupId)
                .add("artifactId", artifactId).add("version", version)
                .toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
