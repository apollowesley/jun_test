import {Component} from 'angular2/core';
import {Router, ROUTER_DIRECTIVES} from 'angular2/router';
import {AppCache} from '../common/cache/app-cache';
import {User} from '../model/database/user';

@Component({
    selector: 'header',
    templateUrl: 'app/template/header.html',
    styleUrls: ['asset/css/header.css'],
    directives: [ROUTER_DIRECTIVES]
})

export class Header {
    loginUser: User;
    constructor(private _appCache: AppCache, private _router: Router) {
        this.loginUser = this._appCache.loginUser;
    }
    protected logout() {
        this._appCache.loginUser = null;
        this._router.navigate(['Login']);
    }
}