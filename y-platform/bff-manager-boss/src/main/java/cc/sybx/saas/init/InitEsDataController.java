package cc.sybx.saas.init;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/init")
@Api(value = "初始化ES服务", tags = "InitEsDataController")
public class InitEsDataController {


}
