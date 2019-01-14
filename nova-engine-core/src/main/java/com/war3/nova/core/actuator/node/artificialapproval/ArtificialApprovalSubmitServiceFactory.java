package com.war3.nova.core.actuator.node.artificialapproval;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.war3.nova.Constants;
import com.war3.nova.NVRuntimeException;
import com.war3.nova.NovaException;
import com.war3.nova.core.UnsupportArgumentException;
import com.war3.nova.core.enumeration.ApproverType;
import com.war3.nova.core.factory.MapperFactory;
import com.war3.nova.core.util.Novas;
import com.war3.nova.core.util.SpringContexts;
import com.war3.nova.core.util.Strings;


/**
 * 
 * 人工审批提交服务工厂
 * 
 * @author Cytus_
 * @since 2018年6月26日 下午2:14:20
 * @version 1.0
 *
 */
public class ArtificialApprovalSubmitServiceFactory {
    
    private final static Logger logger = LoggerFactory.getLogger(ArtificialApprovalSubmitServiceFactory.class);
    
    private static String defaultServiceBeanName = null;
    
    private static class ArtificialApprovalSubmitServiceFactoryHolder {
        private static final ArtificialApprovalSubmitServiceFactory FACTORY = new ArtificialApprovalSubmitServiceFactory();
    }
    
    private ArtificialApprovalSubmitServiceFactory() {
        
        
    } 

    public final static ArtificialApprovalSubmitServiceFactory factory() {
        return ArtificialApprovalSubmitServiceFactoryHolder.FACTORY;
    }
    
    /**
     * 获取审批类型提交处理
     * @param approverType 审批类型
     * @return
     * @throws NovaException
     */
    public ArtificialApprovalSubmitService getArtificialApprovalSubmitServicer(ApproverType approverType) throws NovaException {
        if (Objects.isNull(approverType)) {
            String errorMsg = Novas.formatMessage("Unsupported Approver Type[{}]!", approverType);
            logger.error(errorMsg);
            throw new UnsupportArgumentException(errorMsg);
        }
        String bean = MapperFactory.factory().getMapper(Constants.ARTIFICIAL_APPROVAL_SUBMIT_SERVICE, approverType);
        if (Strings.isEmpty(bean)) {
            if (Strings.isEmpty(defaultServiceBeanName)) {
                initDefaultServiceBeanName();
            }
            bean = defaultServiceBeanName;
        }
        ArtificialApprovalSubmitService artificialApprovalSubmitService = SpringContexts.getBean(bean, ArtificialApprovalSubmitService.class);
        return artificialApprovalSubmitService;
    }
    
    /**
     * 初始化defaultServiceBeanName
     */
    private synchronized void initDefaultServiceBeanName() {
        if (Strings.isEmpty(defaultServiceBeanName)) {
            String[] beanNames = SpringContexts.getBeanNamesForAnnotation(DefaultArtificialApprovalSubmit.class);
            List<String> primaryKey = new ArrayList<>();
            for (String beanName : beanNames) {
                ArtificialApprovalSubmitService artificialApprovalSubmitService = SpringContexts.getBean(beanName, ArtificialApprovalSubmitService.class);
                DefaultArtificialApprovalSubmit defaultArtificialApprovalSubmit = artificialApprovalSubmitService.getClass().getAnnotation(DefaultArtificialApprovalSubmit.class);
                if (Objects.nonNull(defaultArtificialApprovalSubmit) && defaultArtificialApprovalSubmit.isPrimary()) {
                    primaryKey.add(beanName);
                }
            }
            
            if (Novas.isNullOrEmpty(primaryKey)) {
                if (beanNames.length > 1) {
                    String errorMsg = Novas.formatMessage("There [{}] are many default implementation classes. Please specify a major implementation class", ArtificialApprovalSubmitService.class.getName());
                    logger.error(errorMsg);
                    throw new NVRuntimeException(errorMsg) ;
                } else {
                    defaultServiceBeanName = beanNames[0];
                }
            } else if (primaryKey.size() > 1) {
                String errorMsg = Novas.formatMessage("There [{}] are many primary implementation classes!", ArtificialApprovalSubmitService.class.getName());
                logger.error(errorMsg);
                throw new NVRuntimeException(errorMsg) ;
            } else {
                defaultServiceBeanName = primaryKey.get(0);
            }
            
        }
    }
    
}
