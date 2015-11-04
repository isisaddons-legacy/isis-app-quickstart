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

package domainapp.fixture.scenarios;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.io.Resources;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.excel.dom.ExcelFixture;

import domainapp.dom.quick.QuickObject;
import domainapp.fixture.dom.quick.QuickObjectsTearDown;
import domainapp.fixture.scenarios.spreadsheets.QuickObjectRowHandler;

public class RecreateQuickObjects extends FixtureScript {


    public RecreateQuickObjects() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }


    //region > simpleObjects (output)
    private final List<QuickObject> quickObjects = Lists.newArrayList();

    /**
     * The simpleobjects created by this fixture (output).
     */
    public List<QuickObject> getQuickObjects() {
        return quickObjects;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // zap everything
        ec.executeChild(this, new QuickObjectsTearDown());

        // load data from spreadsheet
        final URL spreadsheet = Resources.getResource(QuickObjectRowHandler.class, "QuickObjects-1.xlsx");
        Class<?>[] handlers = new Class[]{QuickObject.class, QuickObjectRowHandler.class};
        final ExcelFixture fs = new ExcelFixture(spreadsheet, handlers);
        ec.executeChild(this, fs);

        // make objects created by ExcelFixture available to our caller.
        final List objects = fs.getObjects();
        getQuickObjects().addAll((Collection<? extends QuickObject>) objects);
    }
}
