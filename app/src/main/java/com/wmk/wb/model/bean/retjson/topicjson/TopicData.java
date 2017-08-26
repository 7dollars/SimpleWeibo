package com.wmk.wb.model.bean.retjson.topicjson;

import com.google.gson.annotations.SerializedName;
import com.wmk.wb.model.bean.retjson.Status;
import com.wmk.wb.model.bean.retjson.Statuses;

import java.util.List;

/**
 * Created by wmk on 2017/8/4.
 */

public class TopicData {
    @SerializedName("statuses")
    public List<TopicStatuses> statuses;



    public List<TopicStatuses> getTopicStatuses() {
        return statuses;
    }
}
