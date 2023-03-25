package cc.sybx.saas.setting.baseconfig.service;

import cc.sybx.saas.common.util.KsBeanUtil;
import cc.sybx.saas.setting.baseconfig.model.root.BaseConfig;
import cc.sybx.saas.setting.baseconfig.repository.BaseConfigRepository;
import cc.sybx.saas.setting.bean.vo.BaseConfigVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class BaseConfigService {
    @Resource
    private BaseConfigRepository baseConfigRepository;


    /**
     * 根据店铺Id查询基本配置信息
     * @param storeId
     * @return
     */
    public BaseConfig findByStoreId(Long storeId){
        return baseConfigRepository.findByStoreId(storeId).orElse(null);
    }

    @Transactional
    public BaseConfig init(Long storeId) {
        BaseConfig entity = new BaseConfig();
        entity.setStoreId(storeId);
        baseConfigRepository.save(entity);
        return entity;
    }

    /**
     * 将实体包装成VO
     */
    public BaseConfigVO wrapperVo(BaseConfig baseConfig) {
        if (baseConfig != null){
            BaseConfigVO baseConfigVO=new BaseConfigVO();
            KsBeanUtil.copyPropertiesThird(baseConfig,baseConfigVO);
            return baseConfigVO;
        }
        return null;
    }
}
