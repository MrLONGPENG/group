package ${baseInfo.packageName}.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * ${tables.comment}
 * 类名:${tables.className}
 * 创建人:${baseInfo.author}
 * 创建时间:${baseInfo.createTime}
 */
@SuppressWarnings("serial")
@Table(name = "${tables.tableName}")
public class ${tables.className} implements Serializable {

<#list columns as columnData>
    /**
    <#if columnData.columnName == "id">
     * 主键
    </#if>
     * ${columnData.remarks}
     * 表字段 : ${tables.tableName}.${columnData.columnName}
     */
    <#if columnData.columnName == "id">
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    @Column(name = "${columnData.columnName}")
    private ${columnData.filedType} ${columnData.filedName};

</#list>

<#list columns as columnData>

    public ${columnData.filedType} get${columnData.filedName?cap_first}() {
		return ${columnData.filedName};
	}

	public void set${columnData.filedName?cap_first}(${columnData.filedType} ${columnData.filedName}) {
		this.${columnData.filedName} = ${columnData.filedName};
	}
</#list>

}