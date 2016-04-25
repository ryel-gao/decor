package com.bluemobi.decor.thread;

import com.bluemobi.decor.service.AttentionService;

/**
 * Created by gaoll on 2015/8/27.
 */
public class UserHasUpdateThread extends Thread {

    private AttentionService attentionService;

    private Integer userId;

    public UserHasUpdateThread(AttentionService attentionService,Integer useId){
        this.attentionService = attentionService;
        this.userId = useId;
    }

    @Override
    public void run(){
        attentionService.userHasUpdate(userId);
    }

}
