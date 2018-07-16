package ${baseInfo.packageName}.controller;


import ${baseInfo.packageName}.service.${tables.className}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ${baseInfo.author}
 */
@RestController
@RequestMapping("/${tables.fieldName}")
public class ${tables.className}Controller {

    private ${tables.className}Service ${tables.fieldName}Service;

    @Autowired
    public ${tables.className}Controller(${tables.className}Service ${tables.fieldName}Service) {
        this.${tables.fieldName}Service = ${tables.fieldName}Service;
    }

    @RequestMapping(value = "/test")
    public String test(){
        return ${tables.fieldName}Service.test();
    }

}
