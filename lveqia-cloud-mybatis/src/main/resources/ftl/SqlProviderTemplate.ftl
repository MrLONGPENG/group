package ${baseInfo.packageName}.sql;

import ${baseInfo.packageName}.model.${tables.className};
import org.apache.ibatis.jdbc.SQL;

/**
 * ${tables.comment},SQL语句组装类
 * 类名:${tables.className}SqlProvider
 * @author ${baseInfo.author}
 * 创建时间:${baseInfo.createTime}
 */
public class ${tables.className}SqlProvider {

    public String insert(${tables.className} ${tables.fieldName}){
        return new SQL(){{
            INSERT_INTO("${tables.tableName}");
        <#list columns as columnData>
            if(${tables.fieldName}.get${columnData.filedName?cap_first}()!= null) VALUES("`${columnData.columnName}`", "${r'#{'}${columnData.filedName}${r'}'}");
        </#list>
        }}.toString();
    }



    public String update(${tables.className} ${tables.fieldName}){
        return new SQL(){{
            UPDATE("${tables.tableName}");
        <#list columns as columnData>
            if(${tables.fieldName}.get${columnData.filedName?cap_first}()!= null) SET("`${columnData.columnName}` = ${r'#{'}${columnData.filedName}${r'}'}");
        </#list>
            WHERE("id = ${r'#{id}'}");
        }}.toString();
    }
}
