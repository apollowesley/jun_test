import {Http, Headers, Request, RequestMethod} from 'angular2/http';
import {JsonInvokeArgument} from '../model/service-center/json-invoke-argument';
import {JsonInvokeResult} from '../model/service-center/json-invoke-result';
import {InvokeArgument} from '../model/service-center/invoke-argument';
import {InvokeResult} from '../model/service-center/invoke-result';
import {CycleJson} from '../common/json/cycle-json';
import {User} from '../model/database/user';

export abstract class Service {
    private scInvokerUrl: string = "http://localhost:13001/";
    protected cycleJson: CycleJson = new CycleJson();
    constructor(protected http: Http) {
        this.http = http;
    }
    private request(methodName: string, data: any = {}): Promise<string> {
        return new Promise<string>((resolve) => {
            var headers = new Headers();
            headers.append("Content-Type", 'application/json; charset=UTF-8');
            this.http.request(new Request({
                url: this.scInvokerUrl + methodName,
                method: RequestMethod.Post,
                headers: headers,
                body: JSON.stringify(data)
            })).subscribe((res) => {
                resolve(res.text());
            });
        });
    }
    private invoke<T>(methodName: string, data: any = {}): Promise<InvokeResult<T>> {
        return new Promise<InvokeResult<T>>((resolve) => {
            this.request(methodName, data).then((text) => {
                var temp: JsonInvokeResult = JSON.parse(text);
                var result = new InvokeResult<T>();
                result.ApplicationNo = temp.ApplicationNo;
                result.ServiceNo = temp.ServiceNo;
                result.OperationNo = temp.OperationNo;
                result.Arguments = [];
                temp.Arguments.forEach((argumentString) => {
                    result.Arguments.push(JSON.parse(argumentString));
                });
                result.Result = JSON.parse(temp.Result);
                this.cycleJson.retrocycle(result);
                resolve(result);
            });
        });
    }
    protected invokeLogin(username: string, password: string): Promise<InvokeResult<User>> {
        return this.invoke("login", { username: username, password: password });
    }
    public getGuid(): Promise<string> {
        return this.request("getGuid");
    }
    public getGuids(count: number): Promise<string[]> {
        return new Promise<string[]>((resolve) => {
            this.request("getGuids", { count: count }).then((text) => {
                resolve(JSON.parse(text));
            });
        });
    }
    protected invokeByInvokeArgument<T>(invokeArgument: InvokeArgument): Promise<InvokeResult<T>> {
        var service = this;
        var jsonInvokeArgument = new JsonInvokeArgument();
        jsonInvokeArgument.ApplicationNo = invokeArgument.ApplicationNo;
        jsonInvokeArgument.ServiceNo = invokeArgument.ServiceNo;
        jsonInvokeArgument.OperationNo = invokeArgument.OperationNo;
        jsonInvokeArgument.Arguments = [];
        for (var i = 0; i < invokeArgument.Arguments.length; i++) {
            jsonInvokeArgument.Arguments.push(JSON.stringify(service.cycleJson.decycle(invokeArgument.Arguments[i])));
        }
        return this.invoke("invokeByJsonInvokeArgument", { invokeArgument: jsonInvokeArgument });
    }
}