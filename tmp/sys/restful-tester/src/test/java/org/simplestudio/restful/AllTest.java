package org.simplestudio.restful;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.simplestudio.restful.expression.ExpressionParserTest;
import org.simplestudio.restful.expression.NestedParamHelperTest;
import org.simplestudio.restful.util.PropertyUtilTest;
import org.simplestudio.restful.util.RestUtilTest;

@RunWith(Suite.class)
@SuiteClasses(value = { PropertyUtilTest.class, RestUtilTest.class, NestedParamHelperTest.class,
        ExpressionParserTest.class, ExpressionParserTest.class })
public class AllTest {

}
