import {Component} from 'angular2/core';
import {NgFor} from 'angular2/common';
import {PAGINATION_DIRECTIVES} from 'ng2-bootstrap/ng2-bootstrap';
import {ListPage} from '../../common/page/list-page';
import {Application} from '../../model/database/application';
import {ApplicationService} from '../../service/application-service';
import {ApplicationInformation} from './application-information';

@Component({
    selector: 'application-management',
    templateUrl: 'app/template/application/application-management.html',
    styleUrls: ['asset/css/common/list-page.css', 'asset/css/application/application-management.css'],
    directives: [NgFor, ApplicationInformation, PAGINATION_DIRECTIVES],
    providers: [ApplicationService]
})

export class ApplicationManagement extends ListPage<Application> {
    constructor(private _applicationService: ApplicationService) {
        super(_applicationService);
    }
}