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
package domainapp.app.services.export;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.xmlsnapshot.XmlSnapshotService;
import org.apache.isis.applib.value.Clob;

import domainapp.app.services.homepage.HomePageViewModel;
import domainapp.dom.quick.QuickObject;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ExportAsXmlContributions extends AbstractFactoryAndRepository {

    // region > exportAsXml for ToDoItem (action)
    @Action(semantics = SemanticsOf.SAFE)
    public Clob exportAsXml(
            final QuickObject quickObject,
            String fileName
    ) {
        if(!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }

        final XmlSnapshotService.Builder builder = xmlSnapshotService.builderFor(quickObject);
        //builder.includePath("children");

        final XmlSnapshotService.Snapshot snapshot = builder.build();

        return new Clob(
                fileName,
                "application/xml",
                snapshot.getXmlDocumentAsString());
    }

    public String default1ExportAsXml() {
        return "simpleObject";
    }
    //endregion

    // region > exportAsXml for ToDoItem (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    public Clob exportAsXml(
            final HomePageViewModel dashboard,
            String fileName
    ) {
        if(!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }

        final XmlSnapshotService.Builder builder = xmlSnapshotService.builderFor(dashboard);
        builder.includePath("objects");

        final XmlSnapshotService.Snapshot snapshot = builder.build();

        return new Clob(
                fileName,
                "application/xml",
                snapshot.getXmlDocumentAsString());
    }

    public String default1ExportAsXml(final HomePageViewModel viewModel) {
        return "simpleObject";
    }
    //endregion

    @Inject
    private XmlSnapshotService xmlSnapshotService;
}
