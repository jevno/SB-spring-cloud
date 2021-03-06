package com.spdata.oauth2.configurer.exceptiontranslator;

import com.spdata.common.base.BaseResul;
import com.spdata.common.base.Basemessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * 获取token失败----返回信息进行处理
 *
 * @author yangqifang
 * @描述:
 * @data 2018/12/621:44
 **/
@Component
public class SpdataWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        ResponseEntity responseEntity = super.translate(e);
        BaseResul baseResul = new BaseResul();
        if (e instanceof OAuth2Exception) {
            baseResul.setCode(Basemessage.error);
            baseResul.setMessage(e.getMessage());
        }
        ResponseEntity resulEntity = new ResponseEntity(baseResul, responseEntity.getHeaders(), HttpStatus.OK);
        return resulEntity;
    }
}
