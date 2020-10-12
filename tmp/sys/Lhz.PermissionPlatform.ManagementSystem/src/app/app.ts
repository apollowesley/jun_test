import {Component} from 'angular2/core';
import {bootstrap} from 'angular2/platform/browser';
import {HTTP_PROVIDERS} from 'angular2/http';
import {ROUTER_DIRECTIVES, RouteConfig, ROUTER_PROVIDERS} from 'angular2/router';
import {CycleJson} from './common/json/cycle-json';
import {Login} from './component/login';
import {Desktop} from './component/desktop';
import {AppCache} from './common/cache/app-cache'

@Component({
    selector: 'pp-ms',
    template: `<router-outlet></router-outlet>`,
    directives: [ROUTER_DIRECTIVES]
})

@RouteConfig([
    { path: '/login', name: 'Login', component: Login, useAsDefault: true },
    { path: '/desktop/...', name: 'Desktop', component: Desktop }
])

export class App { }

bootstrap(App, [HTTP_PROVIDERS, CycleJson, ROUTER_PROVIDERS, AppCache]);