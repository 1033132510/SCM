package com.zzc.core;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * AbstractTransactionalJUnit4SpringContextTests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/spring.xml"})
@ActiveProfiles(profiles = "dev")
public abstract class BaseServiceTest extends TestCase {
    private static Logger logger = LoggerFactory.getLogger(BaseServiceTest.class);

    @Autowired
    protected ApplicationContext ctx;

    @Before
    public void before() {

        logger.debug("before****************");
    }


    @After
    public void after() {

    }

}
