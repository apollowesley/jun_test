import {Component} from 'angular2/core';
import {ROUTER_DIRECTIVES, RouteConfig} from 'angular2/router';
import {Header} from './header';
import {ApplicationManagement} from './application/application-management';
import {ApplicationInformation} from './application/application-information';
import {RegionManagement} from './region/region-management';
import {OrganizationManagement} from './organization/organization-management';
import {RoleManagement} from './role/role-management';
import {UserManagement} from './user/user-management';

@Component({
    selector: 'desktop',
    templateUrl: 'app/template/desktop.html',
    styleUrls: ['asset/css/desktop.css'],
    directives: [Header, ROUTER_DIRECTIVES]
})

@RouteConfig([
    { path: '/applicationManagement', name: 'ApplicationManagement', component: ApplicationManagement, useAsDefault: true },
    { path: '/applicationInformation/:guid', name: 'ApplicationInformation', component: ApplicationInformation },
    { path: '/regionManagement', name: 'RegionManagement', component: RegionManagement },
    { path: '/organizationManagement', name: 'OrganizationManagement', component: OrganizationManagement },
    { path: '/roleManagement', name: 'RoleManagement', component: RoleManagement },
    { path: '/userManagement', name: 'UserManagement', component: UserManagement }
])

export class Desktop {

}