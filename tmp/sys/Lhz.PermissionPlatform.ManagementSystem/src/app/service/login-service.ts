import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';
import {Service} from './service';
import {User} from '../model/database/user';

@Injectable()
export class LoginService extends Service {
    constructor(http: Http) {
        super(http);
    }
    login(username: string, password: string): Promise<User> {
        return new Promise<User>((resolve) => {
            super.invokeLogin(username, password).then((result) => {
                resolve(result.Result);
            })
        });
    };
}