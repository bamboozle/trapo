/**
 * Copyright 2009 Bamboozle Who
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.code.trapo.persistence;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bamboozle Who
 *
 * @since 31/10/2009
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
	"classpath:/trapo-servlet.xml",
	"classpath:/applicationContextTest.xml"
})
@Transactional
@TransactionConfiguration(transactionManager = "txManager")
public abstract class AbstractRepositoryTest<Entity, Id extends Serializable> {

	@Autowired private SessionFactory testSessionFactory;
	
	public abstract AbstractRepository<Entity, Id> repository();
	public abstract String[] entities();
	
	@Before
	public void loadDatabaseData() throws Exception {
		runDBUnitOperation(DatabaseOperation.CLEAN_INSERT, entities());
		reconfigureRepository();
	}
	
	private void reconfigureRepository() {
		this.repository().setSessionFactory(testSessionFactory);
	}

	protected void runDBUnitOperation(final DatabaseOperation operation, final String ... entities) throws Exception {
		testSessionFactory.getCurrentSession().doWork(new Work() {
			public void execute(Connection connection) {
				try {
					operation.execute(new DatabaseConnection(connection), dataSet(entities));
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		});
	}

	protected IDataSet dataSet(String ... entities) throws Exception {
		List<IDataSet> datasets = new ArrayList<IDataSet>();
		for (String entity : entities) {
			String location = "src/test/resources/datasets/" + entity + "/dataset.xml";
			datasets.add(new FlatXmlDataSet(new File(location)));
		}
		IDataSet[] datasetArray = new IDataSet[entities.length];
		return new CompositeDataSet(datasets.toArray(datasetArray));
	}
}
