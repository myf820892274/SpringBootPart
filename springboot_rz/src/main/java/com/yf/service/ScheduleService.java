package com.yf.service;

import com.yf.pojo.ScheduleJob;
import com.yf.utils.Pager;
import com.yf.utils.R;
import com.yf.utils.ResultData;

import java.util.List;

public interface ScheduleService {
    public ResultData scheduleList(Pager pager,String search);
    public R save(ScheduleJob scheduleJob);
    public R scheduleInfo(long id);
    public R update(ScheduleJob scheduleJob);
    public R delete(List<Long> jobIds);
    //暂停
    public R pause(List<Long> jobIds);
    //恢复
    public R resume(List<Long> jobIds);
    //立即执行
    public R run(List<Long> jobIds);

}
