package ${impl.daoPackage}.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import ${impl.codecPackage}.${impl.entitySimpleName}Codec;
import ${impl.daoPackage}.${impl.entitySimpleName}Dao;
import ${impl.entityName};
<#if impl.queryModelName !="" >
import ${impl.queryModelName};
</#if>
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;


@Repository
public class Mongo${impl.entitySimpleName}Dao implements ${impl.entitySimpleName}Dao {

    @Resource(name = "${impl.entitySimpleName?uncap_first}Collection")
    public MongoCollection<Document> ${impl.entitySimpleName?uncap_first}Collection;

    @Resource(name = "${impl.entitySimpleName?uncap_first}Codec")
    public ${impl.entitySimpleName}Codec ${impl.entitySimpleName?uncap_first}Codec;


    @Override
    public void insert(${impl.entitySimpleName} ${impl.entitySimpleName?uncap_first}) {
        this.${impl.entitySimpleName?uncap_first}Collection.insertOne(${impl.entitySimpleName?uncap_first}Codec.encoder(${impl.entitySimpleName?uncap_first}));
    }

    @Override
    public void update(${impl.entitySimpleName} ${impl.entitySimpleName?uncap_first}) {
        final Document encoder = ${impl.entitySimpleName?uncap_first}Codec.encoder(${impl.entitySimpleName?uncap_first});
        Document dest = new Document();
        encoder.entrySet().forEach(entry -> dest.append("$set", new Document(entry.getKey(), entry.getValue())));
        this.${impl.entitySimpleName?uncap_first}Collection.updateOne(new Document().append("_id", ${impl.entitySimpleName?uncap_first}.getId()), dest);
    }

    @Override
    public void merge(${impl.entitySimpleName} ${impl.entitySimpleName?uncap_first}, String... fields) {
        if (fields != null && fields.length > 0) {
            final Document encoder = ${impl.entitySimpleName?uncap_first}Codec.encoder(${impl.entitySimpleName?uncap_first});
            final HashSet<String> strings = new HashSet<>(Arrays.asList(fields));
            Document dest = new Document();
            encoder.entrySet().stream().filter(entry -> strings.contains(entry.getKey())).forEach(entry -> dest.append("$set", new Document(entry.getKey(), entry.getValue())));
            this.${impl.entitySimpleName?uncap_first}Collection.updateOne(new Document().append("_id", ${impl.entitySimpleName?uncap_first}.getId()), dest);
        }
    }


    @Override
    public void delete(ObjectId id) {
        this.${impl.entitySimpleName?uncap_first}Collection.deleteOne(new Document("_id", id));
    }

    @Override
    public ${impl.entitySimpleName} findOne(ObjectId id) {
        return ${impl.entitySimpleName?uncap_first}Codec.decoder(this.${impl.entitySimpleName?uncap_first}Collection.find(new Document("_id", id)).first());
    }
    <#if impl.queryModelName != "" >
    @Override
    public List<${impl.entitySimpleName}> findAll(${impl.querySimpleName} ${impl.querySimpleName?uncap_first}) {
        List<${impl.entitySimpleName}> result = new ArrayList<>();
        this.${impl.entitySimpleName?uncap_first}Collection.find(paserQueryModel(${impl.querySimpleName?uncap_first})).skip((${impl.querySimpleName?uncap_first}.getPageNumber() - 1) * ${impl.querySimpleName?uncap_first}.getPageSize()).limit(${impl.querySimpleName?uncap_first}.getPageSize()).forEach((Consumer<Document>) document -> result.add(${impl.entitySimpleName?uncap_first}Codec.decoder(document)));
        return result;
    }

    @Override
    public long count(${impl.querySimpleName} ${impl.querySimpleName?uncap_first}) {
        return this.${impl.entitySimpleName?uncap_first}Collection.count(paserQueryModel(${impl.querySimpleName?uncap_first}));
    }


    private Document paserQueryModel(${impl.querySimpleName} ${impl.querySimpleName?uncap_first}) {
        Document doc = new Document();
        <#list impl.querys as q>
            if (${impl.querySimpleName?uncap_first}.get${q.name?cap_first}() != null && !"".equals(${impl.querySimpleName?uncap_first}.get${q.name?cap_first}()))
                <#if q.start == "">
                doc.append("${q.fieldName}", new Document("${q.link}",${impl.querySimpleName?uncap_first}.get${q.name?cap_first}()));
                    <#else >
                doc.append("${q.fieldName}",${q.start}${impl.querySimpleName?uncap_first}.get${q.name?cap_first}()${q.end});
                </#if>
        </#list>
        return doc;
    }
    </#if>
}
