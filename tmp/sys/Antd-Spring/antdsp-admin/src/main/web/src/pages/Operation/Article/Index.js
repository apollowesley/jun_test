import { PureComponent, Fragment } from "react";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import Block from "@/custom/Block";
import { Table, Divider, Popconfirm, Card, Button, message } from "antd";
import moment from "moment";
import { connect } from "dva";
import router from 'umi/router';

@connect(({article , loading})=>({
    article,
    loading: loading.models.article
}))
export default class ArticleIndex extends PureComponent{

    state = {
        formValue: {
            page: 1,
            count: 10,
        }
    }

    componentDidMount(){
        const { dispatch }= this.props;
        dispatch({
            type: 'article/fetchAll',
            payload: {
                ...this.state.formValue,
            }
        })
    }

    handlerTableOnChange =(pagination, filters, sorter)=>{
        const { dispatch } = this.props;
        let { formValue } = this.state;

        formValue = {
            ...formValue,
            page: pagination.current,
            count: pagination.pageSize,
        };
        this.setState({
            formValue: {
              ...formValue,
            },
        });

        dispatch({
            type: 'article/fetchAll',
            payload: {
              ...formValue,
            },
        });
    }

    handlerAddOnClick=()=>{
        router.push("/operation/article/0");
    } 

    handlerDelOnClick=(id)=>{
        const { dispatch } = this.props;

        dispatch({
            type: 'article/del',
            payload: {
                id: id
            },
            callback:(result)=>{
                if(result.success){
                    message.info(result.message);
                    dispatch({
                        type: 'article/fetchAll',
                        payload: {
                            ...this.state.formValue
                        },
                    });
                }else {
                    message.error(result.message);
                }
            }
        });
    }

    render(){

        const columns = [{
            title: '标题',
            dataIndex: 'title'
        },{
            title: '副标题',
            dataIndex: 'subTitle',
        },{
            title: '创建时间',
            dataIndex: 'created',
            render:(val)=><span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span>
        },{
            title: '操作',
            render:(record)=>{
                return <Fragment>
                    <a onClick={(e)=>{
                        e.preventDefault();
                        router.push(`/operation/article/${record.id}`);
                    }}>编辑</a>
                    <Divider type="vertical" />
                    <Popconfirm title="确认要删除当前文章吗？" onConfirm={()=>{this.handlerDelOnClick(record.id)}}>
                        <a style={{color:'red'}}>删除</a>
                    </Popconfirm>
                </Fragment>
            }
        }];

        const { article: { ArticleList } , loading } = this.props;

        return (
            <PageHeaderWrapper title="文章管理">
                <Card>
                    <Block>
                        <Button type="primary" icon="plus" onClick={this.handlerAddOnClick}>新建</Button>
                    </Block>
                    <Table 
                        columns={columns}
                        rowKey={'id'}
                        dataSource={ArticleList.data}
                        pagination={ArticleList.pagination}
                        onChange={this.handlerTableOnChange}
                        loading={loading}
                    />
                </Card>
            </PageHeaderWrapper>
        )
    }
}