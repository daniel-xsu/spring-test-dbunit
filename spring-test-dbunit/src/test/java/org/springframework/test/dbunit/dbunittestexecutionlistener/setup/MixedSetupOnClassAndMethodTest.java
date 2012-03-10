/*
 * Copyright 2010 the original author or authors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.test.dbunit.dbunittestexecutionlistener.setup;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.dbunit.TransactionDbUnitTestExecutionListener;
import org.springframework.test.dbunit.annotation.DatabaseOperation;
import org.springframework.test.dbunit.annotation.DatabaseSetup;
import org.springframework.test.dbunit.entity.EntityAssert;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/META-INF/dbunit-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("/META-INF/db/insert.xml")
@Transactional
public class MixedSetupOnClassAndMethodTest {

	@Autowired
	private EntityAssert entityAssert;

	@Test
	@DatabaseSetup("/META-INF/db/insert2.xml")
	public void testInsert() throws Exception {
		entityAssert.assertValues("fromDbUnit", "fromDbUnit2");
	}

	@Test
	@DatabaseSetup(type = DatabaseOperation.REFRESH, value = "/META-INF/db/refresh.xml")
	public void testRefresh() throws Exception {
		entityAssert.assertValues("addedFromDbUnit", "replacedFromDbUnit");
	}
}