/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.integtests.tests.fixturetest;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Test;

import org.apache.isis.applib.fixturescripts.FixtureScripts;

import domainapp.dom.quick.QuickObject;
import domainapp.dom.quick.QuickObjectMenu;
import domainapp.fixture.dom.quick.QuickObjectsTearDown;
import domainapp.fixture.scenarios.spreadsheets.CreateUsingSpreadsheetQuickObjects;
import domainapp.integtests.tests.DomainAppIntegTest;
import static org.assertj.core.api.Assertions.assertThat;

public class SpreadsheetFixtureIntegTest extends DomainAppIntegTest {

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    QuickObjectMenu quickObjectMenu;

    public static class ListAll extends SpreadsheetFixtureIntegTest {

        @Test
        public void happyCase() throws Exception {

            // given
            fixtureScripts.runFixtureScript(new QuickObjectsTearDown(), null);

            // when
            final CreateUsingSpreadsheetQuickObjects fs = new CreateUsingSpreadsheetQuickObjects();
            fs.setResourceName("QuickObjects-1.xlsx");

            fixtureScripts.runFixtureScript(fs, null);
            nextTransaction();
            assertThat(fs.getObjects()).hasSize(3);

            // then
            final List<QuickObject> all = wrap(quickObjectMenu).listAll();

            assertThat(all.get(0).getName()).isEqualTo("Foo");
            assertThat(all.get(0).getInteger()).isEqualTo(111);
            assertThat(all.get(0).getLocalDate()).isEqualTo(new LocalDate(2015,1,12));
            assertThat(all.get(0).getBoolean()).isEqualTo(true);

            assertThat(all.get(1).getName()).isEqualTo("Bar");
            assertThat(all.get(1).getBoolean()).isEqualTo(false);

            assertThat(all.get(2).getName()).isEqualTo("Baz");
            assertThat(all.get(2).getLocalDate()).isNull();
            assertThat(all.get(2).getBoolean()).isEqualTo(true);
        }
    }

}