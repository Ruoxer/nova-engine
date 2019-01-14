package com.war3.nova.core.factory;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NovaException;
import com.war3.nova.core.CoreSelector;
import com.war3.nova.core.UnsupportArgumentException;
import com.war3.nova.core.enumeration.RouteType;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.SpringContexts;

/**
 * 选择器工厂
 * 
 * @author Cytus_
 * @since 2018年12月14日 上午9:56:23
 * @version 1.0
 */
public class SelectorFactory {
    
    private final static Logger logger = LoggerFactory.getLogger(SelectorFactory.class);
    
    private static class SelectorFactoryHolder {
        private static final SelectorFactory FACTORY = new SelectorFactory();
    }
    
    private SelectorFactory() {} 

    public final static SelectorFactory factory() {
        return SelectorFactoryHolder.FACTORY;
    }
    
    @SuppressWarnings("unchecked")
    public <T> CoreSelector<T> getRouteSelector(RouteType routeType) throws NovaException {
        if (Objects.isNull(routeType)) {
            String errorMsg = Novas.formatMessage("Unsupported RouteSelector type[{}]!", routeType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.ROUTE_SELECTOR, routeType);
        CoreSelector<T> selector = SpringContexts.getBean(bean, CoreSelector.class);
        return selector;
    }
    
}
