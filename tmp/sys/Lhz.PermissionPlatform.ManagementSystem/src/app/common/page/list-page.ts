import {ViewChild} from 'angular2/core';
import {Pagination} from 'ng2-bootstrap/ng2-bootstrap';
import {Entity} from '../../model/database/entity';
import {EntityService} from '../../service/entity-service';
import {Pagination as PaginationEntity} from '../../model/data/pagination';

export abstract class ListPage<TEntity extends Entity> {
    protected entities: TEntity[];
    protected pageIndex: number = 1;
    protected pageSize: number = 10;
    protected pageNumberSize: number = 5;
    protected total: number = 0;
    @ViewChild(Pagination) pagination: Pagination;
    constructor(private _entityService: EntityService<TEntity>) {
        this.query();
    }
    protected query(pageIndex: number = null) {
        var pe = new PaginationEntity();
        if (pageIndex === null) {
            pe.Index = this.pageIndex;
        }
        else {
            pe.Index = pageIndex;
        }
        pe.Size = this.pageSize;
        this._entityService.queryByPagination(pe).then((result) => {
            this.total = result.Arguments[0].Count;
            this.entities = result.Result;
        });
    }
    protected remove(guid) {
        if (confirm("确认删除？") === true) {
            this._entityService.deleteByPk(guid).then((result) => {
                this.query();
            });
        }
    }
}