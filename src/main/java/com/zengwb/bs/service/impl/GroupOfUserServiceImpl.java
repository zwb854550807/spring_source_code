package com.zengwb.bs.service.impl;

import com.zengwb.bs.entity.ApplyClassesEntity;
import com.zengwb.bs.service.GroupOfUserService;
import com.zengwb.bs.service.UserClassesService;
import com.zengwb.bs.service.UserInfoService;
import com.zengwb.utils.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

@Service
public class GroupOfUserServiceImpl implements GroupOfUserService {

    private static final Integer THRESHOLD = 10;

    @Autowired
    private UserClassesService userClassesService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void generateClasses(ApplyClassesEntity applyClassesEntity) {
        batchApplyClassesTask task = new batchApplyClassesTask(userClassesService, applyClassesEntity);
        ThreadPoolUtil.INSTANCE.getForkJoinPool().execute(task);
    }

    @Override
    public List generateAnnual(List amsUserIds) throws ExecutionException, InterruptedException {
        batchGenerateAnnualTask task = new batchGenerateAnnualTask(userInfoService, amsUserIds);
        List list = ThreadPoolUtil.INSTANCE.getForkJoinPool().submit(task).get();
        return list;
    }


    class batchApplyClassesTask extends RecursiveAction {

        private List<String> amsUserIds;
        private LocalDate start;
        private LocalDate end;
        private String planId;
        ApplyClassesEntity entity;
        private UserClassesService userClassesService;

        batchApplyClassesTask(UserClassesService service, ApplyClassesEntity applyClassesEntity) {
            this.amsUserIds = applyClassesEntity.getAmsUserIds();
            this.userClassesService = service;
            this.start = applyClassesEntity.getStartData();
            this.end = applyClassesEntity.getEndData();
            this.planId = applyClassesEntity.getPlanId();
            entity = applyClassesEntity;
        }

        @Override
        protected void compute() {
            if (amsUserIds.size() < THRESHOLD) {
                userClassesService.applyUserClasses(amsUserIds, start, end, planId);
            } else {
                List<String> leftUserIds = amsUserIds.subList(0, amsUserIds.size() / 2);
                List<String> rightUserIds = amsUserIds.subList(amsUserIds.size() / 2, amsUserIds.size());
                entity.setAmsUserIds(leftUserIds);
                batchApplyClassesTask leftTask = new batchApplyClassesTask(userClassesService, entity);
                entity.setAmsUserIds(rightUserIds);
                batchApplyClassesTask rightTask = new batchApplyClassesTask(userClassesService, entity);
                invokeAll(leftTask, rightTask);
            }
        }
    }

    class batchGenerateAnnualTask extends RecursiveTask<List> {

        private UserInfoService userInfoService;
        private List amsUserIds;

        batchGenerateAnnualTask(UserInfoService service, List amsUserIds) {
            this.userInfoService = service;
            this.amsUserIds = amsUserIds;
        }


        @Override
        protected List compute() {
            if (amsUserIds.size() < THRESHOLD) {
                return userInfoService.queryAll();
            } else {
                batchGenerateAnnualTask leftTask = new batchGenerateAnnualTask(userInfoService, amsUserIds.subList(0, amsUserIds.size()));
                batchGenerateAnnualTask rightTask = new batchGenerateAnnualTask(userInfoService, amsUserIds.subList(amsUserIds.size(), amsUserIds.size()));
                invokeAll(
                        leftTask,
                        rightTask
                );
                leftTask.join().addAll(rightTask.join());
                return leftTask.join();
            }

        }
    }

}
