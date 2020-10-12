import {ReferenceRelation} from './reference-relation';

export class ReferenceObject {
    items: any[];
    referenceItems: ReferenceRelation[];
    constructor() {
        this.items = [];
        this.referenceItems = [];
    }
}