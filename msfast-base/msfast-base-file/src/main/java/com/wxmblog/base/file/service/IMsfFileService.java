package com.wxmblog.base.file.service;

public interface IMsfFileService {

    void before(String filename);

    void after(String filename);
}
