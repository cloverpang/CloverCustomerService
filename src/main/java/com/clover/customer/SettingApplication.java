package com.clover.customer;

import com.clover.customer.service.CustomerInfoService;
import com.clover.customer.service.CustomerLegacyService;
import com.clover.customer.service.CustomerService;
import com.clover.customer.service.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableTransactionManagement// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven/>
@SpringBootApplication
public class SettingApplication {
    protected Logger logger = LoggerFactory.getLogger(SettingApplication.class);

    @Autowired
    @Qualifier("customerLegacyService")
    private CustomerLegacyService customerLegacyService;

    @Autowired
    @Qualifier("customerService")
    private CustomerService customerService;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private SettingService settingService;

    @Value("${env}")
    private String env;

    @RequestMapping("/settingFieldsAndCategory")
    public String settingFieldsAndCategory(){
        try{
            String successTip = "Setting Fields And Category successful!";

            settingService.featureSetting();

            return successTip;
        }catch (Exception e){
            String filedTip = "Setting Fields And Category failed!" + e.getMessage();
            return filedTip;
        }
    }


}
