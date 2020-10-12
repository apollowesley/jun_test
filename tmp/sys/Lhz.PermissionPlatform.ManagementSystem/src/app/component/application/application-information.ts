import {Component} from 'angular2/core';
import {NgIf} from 'angular2/common';
import {Modal} from "ng2-modal/Modal";
import {DetailPage} from '../../common/page/detail-page';
import {Application} from '../../model/database/application';
import {ApplicationService} from '../../service/application-service';

@Component({
    selector: 'application-information',
    templateUrl: 'app/template/application/application-information.html',
    styleUrls: ['asset/css/common/detail-page.css', 'asset/css/application/application-information.css'],
    directives: [NgIf, Modal],
    providers: [ApplicationService]
})

export class ApplicationInformation extends DetailPage<Application> {
    protected parents: Application[];
    constructor(private _applicationService: ApplicationService) {
        super(_applicationService, Application);
    }
    protected beforeGetEntity(guid: string): Promise<void> {
        return new Promise<void>((r) => {
            this._applicationService.query().then((result) => {
                var parents = [];
                var pleaseSelect = new Application();
                pleaseSelect.Name = "请选择";
                parents.push(pleaseSelect);
                result.Result.forEach((item, index, items) => {
                    if (!guid || guid !== item.Guid) {
                        parents.push(item);
                    }
                });
                this.parents = parents;
                r();
            });
        });
    };
}