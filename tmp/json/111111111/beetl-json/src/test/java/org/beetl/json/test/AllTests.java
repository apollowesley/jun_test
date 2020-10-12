package org.beetl.json.test;

import org.beetl.json.test.action.AsJsonActionTest;
import org.beetl.json.test.action.CycleActionTest;
import org.beetl.json.test.action.ExcludeActionTest;
import org.beetl.json.test.action.ExpressActionTest;
import org.beetl.json.test.action.FormatActionTest;
import org.beetl.json.test.action.IfActionTest;
import org.beetl.json.test.action.IncludeActionTest;
import org.beetl.json.test.cycle.CycleRefActionTest;
import org.beetl.json.test.location.AttributeLocationTest;
import org.beetl.json.test.location.TypeLocationTest;
import org.beetl.json.test.one2many.CustomerTest;
import org.beetl.json.test.simple.IteratorTest;
import org.beetl.json.test.simple.JavaTypeTest;
import org.beetl.json.test.simple.PojoTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	PojoTest.class,
	JavaTypeTest.class,
	IteratorTest.class,
	CustomerTest.class,
	IncludeActionTest.class,
	ExcludeActionTest.class,
	FormatActionTest.class,
	IfActionTest.class,
	CycleActionTest.class,
	ExpressActionTest.class,
	CycleRefActionTest.class,
	TypeLocationTest.class,
	AttributeLocationTest.class,
	AsJsonActionTest.class
	
})
public class AllTests {

}
