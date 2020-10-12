import {ViewChild, Output, EventEmitter} from 'angular2/core';
import {NgForm} from 'angular2/common';
import {Modal} from 'ng2-modal/Modal';
import {Entity} from '../../model/database/entity';
import {EntityService} from '../../service/entity-service';

export abstract class DetailPage<TEntity extends Entity> {
    protected isAdd: boolean;
    protected entity: TEntity;
    protected beforeGetEntity(guid: string): Promise<void> {
        return new Promise<void>((r) => {
            r();
        });
    };
    @ViewChild(Modal) modal: Modal;
    @ViewChild(NgForm) ngForm: NgForm;
    @Output() saveCompleted: EventEmitter<TEntity> = new EventEmitter<TEntity>();
    constructor(private _entityService: EntityService<TEntity>, private _entityBuilder: { new (): TEntity; }) {

    }
    protected show(guid: string) {
        this.modal.open();
        this.beforeGetEntity(guid).then(() => {
            if (guid === null) {
                this.isAdd = true;
                this.entity = new this._entityBuilder();
            }
            else {
                this.isAdd = false;
                this._entityService.queryByPk(guid).then((result) => {
                    this.entity = result;
                });
            }
        });
    }
    protected close() {
        this.modal.close();
        this.entity = null;
    }
    protected save() {
        if (this.ngForm.form.valid === true) {
            if (this.isAdd === true) {
                this._entityService.getGuid().then((guid) => {
                    this.entity.Guid = guid;
                    this._entityService.add(this.entity).then((result) => {
                        this.close();
                        this.saveCompleted.emit(this.entity);
                    });
                });
            }
            else {
                this._entityService.update(this.entity).then((result) => {
                    this.close();
                    this.saveCompleted.emit(this.entity);
                });
            }
        }
        else {
            alert("请检查错误。");
        }
    }
    protected cancel() {
        this.close();
    }
}