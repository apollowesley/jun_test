import {Component} from 'angular2/core';
import {NgIf} from 'angular2/common';
import {Router} from 'angular2/router';
import {LoginService} from '../service/login-service';
import {AppCache} from '../common/cache/app-cache';

@Component({
    selector: 'login',
    templateUrl: 'app/template/login.html',
    styleUrls: ['asset/css/login.css'],
    directives: [NgIf],
    providers: [LoginService]
})

export class Login {
    username: string;
    password: string;
    loading: boolean;
    loginFaild: boolean;
    constructor(private _loginService: LoginService, private _router: Router, private _appCache: AppCache) {

    }
    protected passwordKeyUp(e) {
        if (e.which === 13) {
            this.login();
        }
    }
    protected login() {
        this.loginFaild = false;
        this.loading = true;
        this._loginService.login(this.username, this.password).then((result) => {
            if (result === null) {
                this.loginFaild = true;
            }
            else {
                this._appCache.loginUser = result;
                this._router.navigate(["Desktop"]);
            }
            this.loading = false;
        });
    }
}