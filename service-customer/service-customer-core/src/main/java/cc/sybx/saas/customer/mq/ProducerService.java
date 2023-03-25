package cc.sybx.saas.customer.mq;

import cc.sybx.saas.common.base.MessageMQRequest;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProducerService {

    /**
     * 发送push、站内信、短信
     * @param request
     */
    public void sendMessage(MessageMQRequest request){
        log.info("ProducerSerivce 发送push、站内信、短信 ['{}']", JSONObject.toJSONString(request));
    }
}
