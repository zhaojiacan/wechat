package com.ace.core.wechat.message.response;
 
/**
 * ClassName: VoiceMessage
 * @Description: 语音消息
 * @author dapengniao
 * @date 2016年3月8日 下午6:02:13
 */
public class VoiceMessage extends BaseMessage {
     
    private Voice Voice;
 
    public Voice getVoice() {
        return Voice;
    }
 
    public void setVoice(Voice voice) {
        Voice = voice;
    }
 
 
     
 
}