package com.trial.crossover;

import com.trial.crossover.controller.CustomerControllerTest;
import com.trial.crossover.controller.ProductControllerTest;
import com.trial.crossover.controller.SalesOrderControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by: dambros
 * Date: 12/3/2015
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		CustomerControllerTest.class,
		ProductControllerTest.class,
		SalesOrderControllerTest.class
})
public class TestSuite {
}
