package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		ShoeTest.class,
		ShoeListTest.class,
		UserTest.class,
		UserListTest.class,
		OrderTest.class,
		OrderListTest.class,
		ValidateTest.class,
		ProtocolTest.class
})

public class TestSuite {
	// the class remains empty,
	// used only as a holder for the above annotations
}