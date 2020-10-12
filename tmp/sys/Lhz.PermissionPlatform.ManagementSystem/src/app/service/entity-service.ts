import {Http} from 'angular2/http';
import {Service} from './service';
import {InvokeArgument} from '../model/service-center/invoke-argument';
import {InvokeResult} from '../model/service-center/invoke-result';
import {Pagination} from '../model/data/pagination';

export abstract class EntityService<T> extends Service {
    constructor(http: Http, protected applicationNo: string, protected serviceNo: string) {
        super(http);
    }
    query(paths: string[] = null): Promise<InvokeResult<T[]>> {
        return new Promise<InvokeResult<T[]>>((resolve) => {
            var ia = new InvokeArgument();
            ia.ApplicationNo = this.applicationNo;
            ia.ServiceNo = this.serviceNo;
            ia.OperationNo = "query";
            ia.Arguments = [paths];
            super.invokeByInvokeArgument<T[]>(ia).then((result) => {
                resolve(result);
            })
        });
    }
    queryByPk(pk: string, paths: string[] = null): Promise<T> {
        return new Promise<T>((resolve) => {
            var ia = new InvokeArgument();
            ia.ApplicationNo = this.applicationNo;
            ia.ServiceNo = this.serviceNo;
            ia.OperationNo = "queryByPk";
            ia.Arguments = [pk, paths];
            super.invokeByInvokeArgument<T>(ia).then((result) => {
                resolve(result.Result);
            })
        });
    }
    queryByPagination(pagination: Pagination, paths: string[] = null): Promise<InvokeResult<T[]>> {
        return new Promise<InvokeResult<T[]>>((resolve) => {
            var ia = new InvokeArgument();
            ia.ApplicationNo = this.applicationNo;
            ia.ServiceNo = this.serviceNo;
            ia.OperationNo = "queryByPagination";
            ia.Arguments = [pagination, paths];
            super.invokeByInvokeArgument<T[]>(ia).then((result) => {
                resolve(result);
            })
        });
    }
    add(entity: T) {
        return new Promise<T>((resolve) => {
            var ia = new InvokeArgument();
            ia.ApplicationNo = this.applicationNo;
            ia.ServiceNo = this.serviceNo;
            ia.OperationNo = "add";
            ia.Arguments = [entity];
            super.invokeByInvokeArgument<T>(ia).then((result) => {
                resolve();
            })
        });
    }
    update(entity: T) {
        return new Promise<InvokeResult<T>>((resolve) => {
            var ia = new InvokeArgument();
            ia.ApplicationNo = this.applicationNo;
            ia.ServiceNo = this.serviceNo;
            ia.OperationNo = "update";
            ia.Arguments = [entity];
            super.invokeByInvokeArgument<T>(ia).then((result) => {
                resolve();
            })
        });
    }
    deleteByPk(pk: string) {
        return new Promise<InvokeResult<T>>((resolve) => {
            var ia = new InvokeArgument();
            ia.ApplicationNo = this.applicationNo;
            ia.ServiceNo = this.serviceNo;
            ia.OperationNo = "deleteByPk";
            ia.Arguments = [pk];
            super.invokeByInvokeArgument<T>(ia).then((result) => {
                resolve();
            })
        });
    }
}