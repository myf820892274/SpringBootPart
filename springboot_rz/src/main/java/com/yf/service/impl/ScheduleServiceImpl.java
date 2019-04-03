package com.yf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.mapper.ScheduleJobMapper;
import com.yf.pojo.ScheduleJob;
import com.yf.pojo.ScheduleJobExample;
import com.yf.service.ScheduleService;
import com.yf.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private ScheduleJobMapper scheduleJobMapper;


    @Override
    public ResultData scheduleList(Pager pager, String search) {

        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        ScheduleJobExample example = new ScheduleJobExample();
        if (StringUtils.isNotEmpty(search)){
            ScheduleJobExample.Criteria criteria = example.createCriteria();
            criteria.andBeanNameLike("%"+search+"%");
        }
        List<ScheduleJob> list = scheduleJobMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());
        return resultData;
    }

    @Override
    public R save(ScheduleJob scheduleJob) {
        int i = scheduleJobMapper.insert(scheduleJob);
        return i>0?R.ok():R.error();
    }

    @Override
    public R scheduleInfo(long id) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
        return R.ok().put("scheduleJob",scheduleJob);
    }

    @Override
    public R update(ScheduleJob scheduleJob) {
        int i = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
        return i>0?R.ok():R.error();
    }

    @Override
    public R delete(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        int i = scheduleJobMapper.deleteByExample(example);
        return i>0?R.ok():R.error();
    }

    @Override
    public R pause(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());//暂停为1
        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
        return i>0?R.ok():R.error();
    }

    @Override
    public R resume(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());//暂停为1
        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
        return i>0?R.ok():R.error();
    }

    @Override
    public R run(List<Long> jobIds) {
//        ScheduleJobExample example = new ScheduleJobExample();
//        ScheduleJobExample.Criteria criteria = example.createCriteria();
//        criteria.andJobIdIn(jobIds);
//        ScheduleJob scheduleJob = new ScheduleJob();
//        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());//暂停为1
//        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);
        return R.ok();
    }
}
