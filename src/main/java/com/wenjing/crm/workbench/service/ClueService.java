package com.wenjing.crm.workbench.service;

import com.wenjing.crm.workbench.domain.Clue;
import com.wenjing.crm.workbench.domain.Tran;

import javax.servlet.http.HttpServletRequest;


public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String cid, String[] aids);


    boolean convert(String clueId, Tran t, String createBy);
}
