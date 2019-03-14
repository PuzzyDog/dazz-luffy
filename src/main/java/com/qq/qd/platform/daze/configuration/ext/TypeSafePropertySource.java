package com.qq.qd.platform.daze.configuration.ext;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValue;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.Reader;

/**
 * 注: 一般的propertiesFile并不能和TypeSafe完全等同。比如TypeSafe可以获取一个非节点的值，可以理解为一个目录;
 * 为了兼容这种情况, 约定如下：
 * 1. 若想拿取TypeSafe配置中的ConfigObject, 加上后缀".@cObj"
 * 2. 若想拿取TypeSafe配置中的Config, 加上后缀".@c"
 * 由于在一般的Spring配置文件使用中，几乎不会遇到上述情况，所有可以像正常使用propertiesFile一样
 * created by weixuecai on 2018/9/25
 */
public class TypeSafePropertySource extends PropertySource<Config> {
    public TypeSafePropertySource(String name, EncodedResource resource) {
        super(name, ConfigFactory.parseReader(getResourceReader(resource)));
    }

    public TypeSafePropertySource(EncodedResource resource) {
        super(getNameForResource(resource.getResource()), ConfigFactory.parseReader(getResourceReader(resource)));
    }

    private static Reader getResourceReader(EncodedResource resource) {
        Reader reader = null;
        try {
            reader = resource.getReader();
        } catch (Exception ex) {
            throw new RuntimeException("cannot get TypeSafe resource, check pls!");
        }

        return reader;
    }

    private static String getNameForResource(Resource resource) {
        String name = resource.getDescription();
        if(name != null && !name.isEmpty()) {
            return resource.getClass().getSimpleName() + "@" + System.identityHashCode(resource);
        }

        return name;
    }


    @Override
    public Object getProperty(String name) {
        try {
            if(name.endsWith(TypeSafeConfigFactory.CONFIG_OBJ_SUFFIX)) {
                String removeSuffix = name.substring(0, name.lastIndexOf("."));
                return super.getSource().getObject(removeSuffix);
            } else if(name.endsWith(TypeSafeConfigFactory.CONFIG_SUFFIX)) {
                String removeSuffix = name.substring(0, name.lastIndexOf("."));
                return super.getSource().getConfig(removeSuffix);
            }

            ConfigValue configValue = super.getSource().getValue(name);

            if (configValue == null)
                return null;

            return configValue.unwrapped();
        } catch (Exception ex) {//默认情况下，typeSafe在发现空配置时，会抛异常
            return null;
        }
    }

}
