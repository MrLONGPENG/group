package ${baseInfo.packageName}.service.impl;

import ${baseInfo.packageName}.mapper.${tables.className}Mapper;
import ${baseInfo.packageName}.service.${tables.className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ${baseInfo.author}
 */
@Service("${tables.fieldName}Service")
public class ${tables.className}ServiceImpl implements ${tables.className}Service {


    private final ${tables.className}Mapper ${tables.fieldName}Mapper;

    @Autowired
    public ${tables.className}ServiceImpl(${tables.className}Mapper ${tables.fieldName}Mapper) {
        this.${tables.fieldName}Mapper = ${tables.fieldName}Mapper;
    }

    @Override
    public String test() {
        return "hello world!";
    }
}
