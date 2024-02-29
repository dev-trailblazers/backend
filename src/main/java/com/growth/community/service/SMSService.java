package com.growth.community.service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SMSService {
    /* @Value로 값을 전달할 때는 Spring Bean을 통해 주입하는 방식이기 때문에 Bean 등록을 하지않거나,
        static 변수, new를 통한 객체 생성에서는 값이 정상적으로 들어가지 않는다.*/
    @Value("${api.coolsms.key}")
    private String key;
    @Value("${api.coolsms.secret}")
    private String secret;
    @Value("${api.coolsms.from}")
    private final String rep = "";  //대표 번호

    private final String url = "https://api.coolsms.co.kr";

    private final static int AUTHENTICATION_NUMBER_LENGTH = 6;


    public void sendSMS(String phoneNumber){
        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(key, secret, url);

        Message message = new Message();
        message.setFrom(rep);
        message.setTo(phoneNumber);
        /* SMS는 한글 45자, 영어 90자까지 입력할 수 있으며 초과 시 자동으로 LMS 타입으로 변경됨 */
        message.setText("[TrailBlazers] 인증번호: " + generateAuthenticationNumber());

        try {
            messageService.send(message);
        } catch(NurigoMessageNotReceivedException e){
            log.warn(e.getFailedMessageList().toString());
            log.warn(e.getMessage());
        } catch(Exception e){
            log.warn(e.getMessage());
        }
    }


    private String generateAuthenticationNumber(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<AUTHENTICATION_NUMBER_LENGTH; i++){
            sb.append((int) (Math.random() * 10));
        }
        return sb.toString();
    }
}
