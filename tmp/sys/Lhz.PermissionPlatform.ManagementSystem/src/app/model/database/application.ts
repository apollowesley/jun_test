import {Entity} from './entity';

export class Application extends Entity {
    Name: string;
    ParentGuid: string;
    Parent: Application;
    Children: Application[];
}