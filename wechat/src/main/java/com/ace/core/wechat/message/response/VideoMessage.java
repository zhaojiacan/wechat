package com.ace.core.wechat.message.response;
 
/**
 * ClassName: VideoMessage
 * @Description: 视频消息
 * @author dapengniao
 * @date 2016年3月8日 下午6:06:29
 */
public class VideoMessage extends BaseMessage {
     
    private Video Video;
 
    public Video getVideo() {
        return Video;
    }
 
    public void setVideo(Video video) {
        Video = video;
    }
 
 
}