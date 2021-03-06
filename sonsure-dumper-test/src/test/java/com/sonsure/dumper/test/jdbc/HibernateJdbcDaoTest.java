package com.sonsure.dumper.test.jdbc;


import com.sonsure.dumper.core.persist.JdbcDao;
import com.sonsure.dumper.test.model.HbUserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-hibernate.xml"})
public class HibernateJdbcDaoTest {

    @Autowired
    private JdbcDao jdbcDao;

    @Test
    public void findList() {

        List<HbUserInfo> userInfos = jdbcDao.find(HbUserInfo.class);

        System.out.println(userInfos);
    }
}
