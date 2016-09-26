package com.padc.interactive_training.data.models;

import com.padc.interactive_training.data.agents.CourseDataAgent;
import com.padc.interactive_training.data.agents.retrofit.RetrofitDataAgent;

import de.greenrobot.event.EventBus;

/**
 * Created by NayLinAung on 9/22/2016.
 */
public abstract class BaseModel {

    private static final int INIT_DATA_AGENT_RETROFIT = 4;

    protected CourseDataAgent dataAgent;

    public BaseModel() {
        initDataAgent(INIT_DATA_AGENT_RETROFIT);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    private void initDataAgent(int initType) {
        dataAgent = RetrofitDataAgent.getInstance();
    }

    public void onEventMainThread(Object obj) {

    }

}
