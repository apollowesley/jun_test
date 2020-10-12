import { PureComponent , Fragment } from "react";
import { connect } from 'dva';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import Block from '@/custom/Block';
import { Card, Table, Divider, Form, Button, Modal, Input, Tree, message, Popconfirm } from "antd";

import MenuTree from './MenuTree'
import moment from "moment";

const FormItem = Form.Item;

@Form.create()
@connect(({systemrole , loading})=>({
    systemrole,
    loading: loading.models.systemrole
}))
export default class Index extends PureComponent{

    state={
        visible: false ,
        confirmLoading: false,
        current:{},
        formValue: {
            count: 10,
            page: 1
        }
    }

    componentDidMount(){

        const { dispatch } = this.props;
        dispatch({
            type: 'systemrole/fetchAll',
            payload:{
                ...this.state.formValue
            }
        });
    }

    handlerDelOnClick=(e , id)=>{
        const { dispatch } = this.props;
        dispatch({
            type: 'systemrole/del',
            payload:{
                id: id
            },
            callback:(result)=>{
                if(result.success){
                    dispatch({
                        type: 'systemrole/fetchAll',
                        payload:{
                            ...this.state.formValue
                        }
                    });
                }else {
                    message.error(result.message);
                }
            }
        });
    }

    handlerModalOnOk=()=>{
        const { dispatch } = this.props;
        const { form } = this.formRef.props;

        this.setState({confirmLoading: true});

        form.validateFields((err , fieldsValue)=>{
            if(err) return this.setState({confirmLoading: false});

            const {current} = this.state;
            let currentObj = {
                ...current,
                ...fieldsValue
            }

            dispatch({
                type: 'systemrole/save',
                payload: {
                    role: currentObj
                },
                callback: (result)=>{
                    if(result.success){
                        this.setState({
                            confirmLoading: false,
                            visible: false,
                        })
                        dispatch({
                            type: 'systemrole/fetchAll',
                            payload:{
                                page: 1,
                                count: 10
                            }
                        });
                    }else {
                        message.error(result.message);
                        this.setState({confirmLoading: false});
                    }
                }
            })
            
            
        });
    }

    handlerEditOnclick=(e , id)=>{

        const { dispatch } = this.props;
        dispatch({
            type: 'systemrole/fetchById',
            payload: {
                id: id
            },
            callback:(result)=>{
                this.showModal(result);
            }
        });

    }

    showModal=(currentObj)=>{
        this.setState({
            confirmLoading: false,
            visible: true,
            current: currentObj
        });
        const { dispatch } = this.props;
        dispatch({
            type: 'systemrole/menus',
        });
    }

    handlerTableOnChange=(pagination , filters , sorter)=>{
        const { dispatch } = this.props;
        let { formValue } = this.state;

        formValue = {
            ...formValue,
            page: pagination.current,
            count: pagination.pageSize,
        }

        this.setState({
            formValue:{
                ...formValue,
            }
        });

        dispatch({
            type: 'systemrole/fetchAll',
            payload:{
                ...formValue,
            }
        })
    }

    handlerQueryOnClick=()=>{
        const { form , dispatch } = this.props;
        form.validateFields((err , fieldsValue)=>{
            let formValue = {
                ...fieldsValue,
                page: 1,
                count: 10
            };
            this.setState({formValue});

            dispatch({
                type: 'systemrole/fetchAll',
                payload: {
                    ...formValue
                }
            })
        });
    }

    render(){

        const { loading, systemrole: {RoleList, Menus}, form: {getFieldDecorator}} = this.props

        const columns = [{
            title: '角色名称',
            dataIndex: 'roleName'
        },{
            title: '描述',
            dataIndex: 'description',
        },{
            title: '创建者',
            dataIndex: 'creator',
        },{
            title: '创建时间',
            dataIndex: 'created',
            render: (val)=> <span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span>
        },{
            title: '操作',
            render:(record)=>{

                return (
                    <Fragment>
                        <a onClick={(e)=>{this.handlerEditOnclick(e , record.id)}}>修改</a>
                        <Divider type="vertical" />
                        <Popconfirm title="确认要删除当前角色吗？" onConfirm={(e)=>{this.handlerDelOnClick(e ,record.id)}}>
                            <a style={{color:'red'}}>删除</a>
                        </Popconfirm>
                    </Fragment>
                )
            }
        }];

        return(
            <PageHeaderWrapper>

                <Card>
                    <Block>
                        <Form layout="inline">
                            <FormItem label="角色名称">
                                {getFieldDecorator("roleName")(
                                    <Input placeholder={"请输入角色名称"}/>
                                )}
                            </FormItem>
                            <FormItem>
                                <Button type="primary" onClick={this.handlerQueryOnClick}>查询</Button>
                            </FormItem>
                        </Form>
                    </Block>
                    <Block>
                        <Button type="primary" icon="plus" onClick={()=>{this.showModal({})}}>新增</Button>
                    </Block>
                    <Table 
                        columns={columns}
                        rowKey="id"
                        loading={loading}
                        dataSource={RoleList.data}
                        pagination={RoleList.pagination}
                        onChange={this.handlerTableOnChange}
                    />
                    <Modal 
                        title="编辑角色信息"
                        centered={true}
                        maskClosable={true}
                        visible={this.state.visible}
                        onCancel={()=>{this.setState({visible: false})}}
                        onOk={this.handlerModalOnOk}
                        confirmLoading={this.state.confirmLoading}
                        destroyOnClose={true}
                    >
                        <EditRole wrappedComponentRef={formRef => this.formRef= formRef} current={this.state.current} menus={Menus.children}/>
                    </Modal>
                </Card>
            </PageHeaderWrapper>
        )
    }

}

@Form.create()
export class EditRole extends PureComponent{

    handlerOnCheck=(checkedKeys)=>{
        const { setFieldsValue } = this.props.form;
        setFieldsValue({
            menuIds: checkedKeys
        });
    }

    render(){

        const { form: {getFieldDecorator} , menus , current} = this.props;

        const formItemLayout = {
            labelCol:{
                xs: { span : 6},
                sm: { span : 6 },
            },
            wrapperCol:{
                xs: { span : 12},
                sm: { span : 12 },
            }
        }

        return(
            <div>
                <Form layout="horizontal" {...formItemLayout}>
                    <FormItem label="角色名称">
                        {getFieldDecorator("roleName",{
                            initialValue: current.roleName,
                            rules:[{
                                required: true,
                                message: '角色名称不能为空',
                            }]
                        })(
                            <Input placeholder={"请输入角色名称"} />
                        )}
                    </FormItem>
                    <FormItem label="角色描述">
                        {getFieldDecorator("description",{
                            initialValue: current.description
                        })(
                            <Input placeholder={"请输入角色描述"} />
                        )}
                    </FormItem>
                    <FormItem label="权限菜单">
                        {getFieldDecorator("menuIds",{
                            initialValue: current.menuIds
                        })(
                            <MenuTree menus={menus} handlerOnCheck={this.handlerOnCheck} checkedKeys={current.menuIds}/>
                        )}
                    </FormItem>
                </Form>
            </div>
        )
    }
}