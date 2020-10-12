import {Injectable} from 'angular2/core';
import {Http} from 'angular2/http';
import {EntityService} from './entity-service';
import {Application} from '../model/database/application';

@Injectable()
export class ApplicationService extends EntityService<Application> {
    constructor(http: Http) {
        super(http, "Pp", "application");
    }
}