import {ReferenceObject} from './reference-object';
import {ReferenceRelation} from './reference-relation';

export class CycleJson {
    private idProperty: string = "$id";
    private refProperty: string = "$ref";
    private metaProperty: string = "$meta";

    private id: number;
    private items: any[];

    decycle<T>(content: T): T {
        this.id = 1;
        this.items = [];
        var result = this.decycleObject(content);
        for (var i = 0; i < this.items.length; i++) {
            var item = this.items[i];
            delete item["$meta"];
        }
        return result;
    }

    private decycleObject<T>(content: T): T {
        var temp: any;
        var items = this.items;
        if (!content || content.constructor == String || content.constructor == Number || content.constructor == Boolean) {
            return content;
        }
        else if (Array.isArray(content)) {
            var length = content["length"];
            for (var i = 0; i < length; i++) {
                var child = content[i];
                var item = null;
                for (var j = 0; j < items.length; j++) {
                    if (items[j][this.metaProperty] == child) {
                        item = items[j];
                    }
                }
                if (item == null) {
                    content[i] = this.decycleObject(content[i]);
                }
                else {
                    content[i] = this.buildRefObject(item);
                }
            }
        }
        else if (content instanceof Object) {
            content[this.idProperty] = this.id.toString();
            content[this.metaProperty] = content;
            this.id += 1;
            items.push(content);
            for (var propertyName in content) {
                if (propertyName != this.metaProperty && propertyName != this.idProperty && propertyName != this.refProperty) {
                    var propertyValue = content[propertyName];
                    if (propertyValue != null) {
                        if (Array.isArray(propertyValue)) {
                            content[propertyName] = this.decycleObject(propertyValue);
                        }
                        else if (propertyValue instanceof Object) {
                            var item = null;
                            for (var i = 0; i < items.length; i++) {
                                if (items[i][this.metaProperty] == propertyValue) {
                                    item = items[i];
                                }
                            }
                            if (item == null) {
                                content[propertyName] = this.decycleObject(propertyValue);
                            }
                            else {
                                content[propertyName] = this.buildRefObject(item);
                            }
                        }
                    }
                }
            }
        }
        return content;
    }

    private buildRefObject(item: Object) {
        var refObject = new Object();
        refObject[this.refProperty] = item[this.idProperty];
        return refObject;
    }

    retrocycle<T>(content: T) {
        var objects = this.getReferenceObjects(content);
        for (var i = 0; i < objects.referenceItems.length; i++) {
            var referenceItem = objects.referenceItems[i];
            for (var j = 0; j < objects.items.length; j++) {
                var item = objects.items[j];
                if (referenceItem.child[this.refProperty] == item[this.idProperty]) {
                    referenceItem.parent[referenceItem.propertyName] = item;
                    break;
                }
            }
        }
        for (var i = 0; i < objects.items.length; i++) {
            var item = objects.items[i];
            delete item[this.idProperty];
        }
    }

    private getReferenceObjects(content: any, parent: any = null, parentPropertyName: any = null): ReferenceObject {
        var result = new ReferenceObject();
        if (Array.isArray(content)) {
            for (var i = 0; i < content.length; i++) {
                var item = content[i];
                var children = this.getReferenceObjects(item, content, i);
                for (var childItem of children.items) {
                    result.items.push(childItem);
                }
                for (var childReferenceItem of children.referenceItems) {
                    result.referenceItems.push(childReferenceItem);
                }
            }
        }
        else if (content instanceof Object) {
            if (content != null) {
                if (content.hasOwnProperty(this.refProperty)) {
                    var refItem = new ReferenceRelation();
                    refItem.parent = parent;
                    refItem.propertyName = parentPropertyName;
                    refItem.child = content;
                    result.referenceItems.push(refItem);
                }
                else {
                    if (content.hasOwnProperty(this.idProperty)) {
                        result.items.push(content);
                    }
                    for (var propertyName in content) {
                        var propertyValue = content[propertyName];
                        if (propertyValue != null && (propertyValue instanceof Object || Array.isArray(propertyValue))) {
                            var children = this.getReferenceObjects(propertyValue, content, propertyName);
                            for (var i = 0; i < children.items.length; i++) {
                                result.items.push(children.items[i]);
                            }
                            for (var i = 0; i < children.referenceItems.length; i++) {
                                result.referenceItems.push(children.referenceItems[i]);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}