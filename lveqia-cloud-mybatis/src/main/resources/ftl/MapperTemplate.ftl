package ${baseInfo.packageName}.mapper;

import ${baseInfo.packageName}.model.${tables.className};
import ${baseInfo.packageName}.sql.${tables.className}SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ${tables.comment},数据库操作接口类
 * 类名:${tables.className}Mapper
 * @author ${baseInfo.author}
 * 创建时间:${baseInfo.createTime}
 */
@Mapper
@Component(value ="${tables.fieldName}Mapper")
public interface ${tables.className}Mapper {

    @InsertProvider(type = ${tables.className}SqlProvider.class, method = "insert")
    boolean insert(${tables.className} ${tables.fieldName});

    @UpdateProvider(type = ${tables.className}SqlProvider.class, method = "update")
    boolean update(${tables.className} ${tables.fieldName});

    @Delete("delete from ${tables.tableName} where id= ${r'#{id}'}")
    boolean deleteById(int id);

    @Select("SELECT * FROM ${tables.tableName} WHERE id = ${r'#{id}'}")
    @Results(id = "${tables.fieldName}", value = {
    <#list columns as columnData>
         <#if columnData.columnName != "id">,</#if>@Result(<#if columnData.columnName == "id">id=true, </#if>column="${columnData.columnName}",property="${columnData.filedName}",javaType=${columnData.filedType}.class)
    </#list>})
    ${tables.className} findById(Integer id);

    @Select("SELECT * FROM ${tables.tableName} limit 1000")
    @ResultMap("${tables.fieldName}")
    List<${tables.className}> findListAll();

}