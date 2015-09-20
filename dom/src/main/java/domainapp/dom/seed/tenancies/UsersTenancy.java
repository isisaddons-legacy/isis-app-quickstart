/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
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
package domainapp.dom.seed.tenancies;

import org.isisaddons.module.security.seed.scripts.AbstractTenancyFixtureScript;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;

public class UsersTenancy extends AbstractTenancyFixtureScript {

    public static final String TENANCY_NAME = "Users";
    public static final String TENANCY_PATH = "/users/";

    @Override
    protected void execute(ExecutionContext executionContext) {
        create(TENANCY_NAME, TENANCY_PATH, GlobalTenancy.TENANCY_PATH, executionContext);
    }

}
