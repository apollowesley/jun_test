import { PureComponent, Fragment } from "react";
import { connect } from 'dva';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import Block from '@/custom/Block';
import { Card, Form, Button, Table, Divider, Popconfirm, Select, Modal, TreeSelect, Input, Icon, message, Radio } from "antd";


const FormItem = Form.Item;

const MenuType = {
    'MENU': '菜单',
    'BUTTON': '按钮'
}

@connect(({ systemmenu , loading })=>({
    systemmenu,
    loading: loading.models.systemmenu
}))
@Form.create()
export default class extends PureComponent{

    state={
        visible: false ,
        confirmLoading: false,
        current:{}
    }

    componentDidMount() {
        const { dispatch } = this.props;
        dispatch({
            type: 'systemmenu/fetchAll',
        })
    }

    /**
     * 添加或者更新
     */
    handlerModalOnOk=()=>{
        const { dispatch } = this.props;
        const { form } = this.formRef.props;

        this.setState({confirmLoading: true});

        form.validateFields((err , fieldsValue)=>{
            if(err) return this.setState({confirmLoading: false});

            let currentObj = this.state.current;

            currentObj = {
                ...currentObj,
                ...fieldsValue,
            };

            dispatch({
                type: 'systemmenu/save',
                payload: {
                    ...currentObj,
                },
                callback: (result)=>{
                    if(result.success){
                        this.setState({visible: false , confirmLoading: false });
                        dispatch({
                            type: 'systemmenu/fetchAll',
                        });
                    }else {
                        message.error(result.message);
                        this.setState({confirmLoading: false})
                    }
                }
            })

        });
    }

    handlerDelOnClick=(e , id)=>{
        e.preventDefault();

        const { dispatch } = this.props;
        dispatch({
            type: "systemmenu/delById",
            payload: {
                id: id
            },
            callback: (result)=>{
                if(result.success){
                    dispatch({
                        type: 'systemmenu/fetchAll',
                    });
                }else {
                    message.error(result.message);
                }
            }
        });
    }

    render() {

        const { systemmenu: {MenuList} , loading } = this.props;

        const columns =[{
            title: "",
            dataIndex:'id',
        },{
            title: "菜单名称",
            dataIndex:'name',
        },{
            title: "URL",
            dataIndex:'path',
        },{
            title: "类型",
            dataIndex:'type',
            render:(val)=> MenuType[val]
        },{
            title: "权限名",
            dataIndex:'permission',
        },{
            title: "图标",
            dataIndex:'icon',
            render:(val)=>{
                if(val)
                return <Icon type={val} />
            }
        },{
            title: "操作",
            render:(record)=>{
    
                return (
                    <Fragment>
                        <a href="javascript:void(0)" onClick={()=>{this.setState({visible: true , current: record, confirmLoading: false})}}>编辑</a>
                        <Divider type="vertical" />
                        <Popconfirm title="是否要删除菜单？" onConfirm={(e)=>this.handlerDelOnClick(e , record.id)}>
                            <a href="javascript:void(0)" style={{color:'red'}} >删除</a>
                        </Popconfirm>
                    </Fragment>
                )
            }
        }];

        return(
            <PageHeaderWrapper title="菜单管理">
                <Card>

                    <Block>
                        <Button icon="plus" type="primary" onClick={()=>{this.setState({visible: true , confirmLoading: false , current:{}})}}>新增</Button>
                    </Block>
                    <Table 
                        columns={columns}
                        rowKey={'id'}
                        dataSource={MenuList}
                        loading={loading}
                    />
                    <Modal 
                        title="编辑菜单"
                        centered={true}
                        maskClosable={true}
                        visible={this.state.visible}
                        onCancel={()=>{this.setState({visible: false})}}
                        onOk={this.handlerModalOnOk}
                        confirmLoading={this.state.confirmLoading}
                        destroyOnClose={true}
                    >
                        <EditMenu wrappedComponentRef={formRef => this.formRef= formRef} current={this.state.current} parentMenu={MenuList}/>
                        {/*  parentMenu={MenuTree} */}
                    </Modal>
                </Card>
            </PageHeaderWrapper>
        )
    }
}

@Form.create()
class EditMenu extends PureComponent{

    assbleMenu(menuTree){

        let parentMenu={
            title: '根菜单',
            value: '0',
            key: '0'
        }
        this.handlerAssbleMenu(parentMenu , menuTree);
        return [parentMenu];
    }

    handlerAssbleMenu(parentMenu , menus){

        let childMenus=[];
        for(let item of menus){
            let childMenu = {
                title: item.name  ,
                value: item.id ,
                key: item.id ,
            };
            if(item.children && item.children.length > 0){
                this.handlerAssbleMenu(childMenu , item.children);
            }
            childMenus.push(childMenu);
        }
        parentMenu.children = childMenus;
    }

    render() {

        const { getFieldDecorator } = this.props.form;

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

        const { current , parentMenu } = this.props;

        const flag = (Object.keys(current).length == 0);

        return(
            <div>
                <Form layout="horizontal" {...formItemLayout}>
                    <FormItem label="类型">
                        {getFieldDecorator('type',{
                            initialValue: current.type,
                            rules: [
                                {
                                    required: true,
                                    message: '请选择父级菜单',
                                }
                            ]
                        })(
                            <Select>
                                <Select.Option value="MENU">菜单</Select.Option>
                                <Select.Option value="BUTTON">按钮</Select.Option>
                            </Select>
                        )}
                    </FormItem>
                    <FormItem label="父级菜单">
                        {getFieldDecorator("parentId",{
                            initialValue: current.parentId,
                            rules:[
                                {
                                    required: true,
                                    message: '请选择父级菜单',
                                }
                            ]
                        })(
                            <TreeSelect 
                                treeData={this.assbleMenu(parentMenu)}
                                treeDefaultExpandAll
                            />
                        )}
                    </FormItem>
                    <FormItem label="名称">
                        {getFieldDecorator("name",{
                            initialValue: current.name,
                            rules: [
                                {
                                    required: true,
                                    message: '请选择父级菜单',
                                }
                            ]
                        })(
                            <Input />
                        )}
                    </FormItem>
                    <FormItem label="URL">
                        {getFieldDecorator("path",{
                            initialValue: current.path,
                        })(
                            <Input />
                        )}
                    </FormItem>
                    <FormItem label="是否显示">
                        {getFieldDecorator("hideInMenu",{
                            initialValue: current.hideInMenu || false
                        })(
                            <Radio.Group>
                                <Radio value={false}>显示</Radio>
                                <Radio value={true}>隐藏</Radio>
                            </Radio.Group>
                        )}
                    </FormItem>
                    <FormItem label="权限">
                        {getFieldDecorator("permission",{
                            initialValue: current.permission,
                        })(
                            <Input />
                        )}
                    </FormItem>
                    <FormItem label="图标">
                        {getFieldDecorator("icon",{
                            initialValue: current.icon,
                        })(
                            <Input />
                        )}
                    </FormItem>
                </Form>
            </div>
        )
    }
}