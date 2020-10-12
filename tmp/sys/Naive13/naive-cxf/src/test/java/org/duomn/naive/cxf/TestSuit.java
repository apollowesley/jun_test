package org.duomn.naive.cxf;

import org.duomn.naive.cxf.restful.service.impl.TestRESTfulClient;
import org.duomn.naive.cxf.soap.service.impl.TestSOAPClient;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestRESTfulClient.class,
	TestSOAPClient.class
	})
public class TestSuit {

}
