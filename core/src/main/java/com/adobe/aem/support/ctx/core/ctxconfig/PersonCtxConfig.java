package com.adobe.aem.support.ctx.core.ctxconfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "CTX Person Config", description = "A Sample CTX Config to show case its use case")
public @interface PersonCtxConfig {
    
    @Property(label = "First Name", description = "A persons first name")
    String first_name() default StringUtils.EMPTY;

    @Property(label = "Last Name", description = "A persons last name")
    String last_name() default StringUtils.EMPTY;

}
