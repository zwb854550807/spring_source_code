package com.zengwb.bs.service;

import com.zengwb.bs.entity.ApplyClassesEntity;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface GroupOfUserService {

    void generateClasses(ApplyClassesEntity applyClassesEntity);

    List generateAnnual(List amsUserIds) throws ExecutionException, InterruptedException;
}
