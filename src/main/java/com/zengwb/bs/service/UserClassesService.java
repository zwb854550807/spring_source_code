package com.zengwb.bs.service;

import java.time.LocalDate;
import java.util.List;

public interface UserClassesService {

    List applyUserClasses(List<String> amsUserIds, LocalDate start, LocalDate end, String plan);
}
