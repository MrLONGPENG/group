package com.mujugroup.core.service;
import java.util.Set;

public interface DepartmentService  {
    Set<Integer> findOidByHid(String hid);
}
