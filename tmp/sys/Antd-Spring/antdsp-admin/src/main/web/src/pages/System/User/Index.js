import { PureComponent, Fragment } from 'react';
import { connect } from 'dva';
import PageHeaderWrapper from '@/components/PageHeaderWrapper';
import Block from '@/custom/Block';
import {
  Card,
  Form,
  Button,
  Table,
  Divider,
  Input,
  Select,
  Modal,
  message,
  Popconfirm,
  Checkbox,
  Upload,
  Icon
} from 'antd';
import md5 from 'js-md5'
import UnipicUpload from '@/custom/UnipicUpload';
import AntdspConfig from '@/AntdspConfig'

const FormItem = Form.Item;

const UserStatus = {
  NORMAL: '正常',
  FORBIDDEN: '异常',
};

@connect(({ systemuser, loading }) => ({
  systemuser,
  loading: loading.models.systemuser,
}))
@Form.create()
export default class extends PureComponent {
  state = {
    confirmLoading: false,
    visible: false,
    current: {},
    formValue: {
      page: 1,
      count: 10,
    },
  };

  componentDidMount() {
    const { dispatch } = this.props;
    dispatch({
      type: 'systemuser/fetchAll',
      payload: {
        page: 1,
        count: 10,
      },
    });
  }

  handlerQueryOnClick = () => {
    const { form, dispatch } = this.props;
    form.validateFields((err, fieldsValue) => {
      let { formValue } = this.state.formValue;
      formValue = {
        ...fieldsValue,
        page: 1,
        count: 10,
      };
      this.setState({ formValue });

      dispatch({
        type: 'systemuser/fetchAll',
        payload: {
          ...formValue,
        },
      });
    });
  };

  renderQueryForm = () => {
    const { getFieldDecorator } = this.props.form;

    return (
      <Block>
        <Form layout="inline">
          <FormItem label="登录名">
            {getFieldDecorator('loginname')(<Input placeholder={'请输入登录名'} />)}
          </FormItem>
          <FormItem label="状态">
            {getFieldDecorator('status')(
              <Select style={{ width: '120px' }} placeholder="请选择状态">
                <Select.Option value={'NORMAL'}>正常</Select.Option>
                <Select.Option value={'FORBIDDEN'}>异常</Select.Option>
              </Select>
            )}
          </FormItem>
          <FormItem>
            <Button type="primary" onClick={this.handlerQueryOnClick}>
              查询
            </Button>
          </FormItem>
        </Form>
      </Block>
    );
  };

  handlerTableOnChange = (pagination, filters, sorter) => {
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
      type: 'systemuser/fetchAll',
      payload: {
        ...formValue,
      },
    });
  };

  showModal = (e , currentObj) => {
    e.preventDefault();
    const { dispatch } = this.props;

    dispatch({
      type: 'systemuser/fetchOne',
      payload:{
        id: currentObj.id || 0
      }
    });

    this.setState({
      confirmLoading: false,
      visible: true
    })
  };

  handlerModalOnOk = e => {
    e.preventDefault();
    this.setState({
      confirmLoading: true,
    });
    const { form } = this.formRef.props;
    const { dispatch } = this.props;
    form.validateFields((err, fieldsValue) => {
      if (err) return this.setState({ confirmLoading: false });

      const { detail } = this.props.systemuser;
      let password = fieldsValue.password;
      const roleIds = fieldsValue.roleIds;
      delete fieldsValue.roleIds;
      if(password){
        fieldsValue.password = md5(password);
      }

      let currentObj = {
        ...detail.user,
        ...fieldsValue,
      };
      dispatch({
        type: 'systemuser/save',
        payload: {
          user: currentObj,
          roleIds: roleIds,
        },
        callback: result => {
          if (result.success) {
            this.setState({
              confirmLoading: false,
              visible: false,
            });
            dispatch({
              type: 'systemuser/fetchAll',
              payload: {
                page: 1,
                count: 10,
              },
            });
          } else {
            message.error(result.message);
            this.setState({ confirmLoading: false });
          }
        },
      });
    });
  };

  handlerDelOnClick = (e, id) => {
    const { dispatch } = this.props;
    dispatch({
      type: 'systemuser/del',
      payload: {
        id: id,
      },
      callback: result => {
        if (result.success) {
          dispatch({
            type: 'systemuser/fetchAll',
            payload: {
              ...this.state.formValue,
            },
          });
        } else {
          message.error(result.message);
        }
      },
    });
  };

  render() {
    const {
      systemuser: { UserList, detail },
      loading,
    } = this.props;
    
    const columns = [
      {
        title: '头像',
        dataIndex: 'avatar',
        render:(val)=>{
          if(val){
            return <div><img src={`${AntdspConfig.AntdspDownloadApi}/${val}`} style={{width:'24px',height: '24px'}}/></div>
          }
        }
      },
      {
        title: '登录名',
        dataIndex: 'loginname',
      },
      {
        title: '真实姓名',
        dataIndex: 'realname',
      },
      {
        title: 'Email',
        dataIndex: 'email',
      },
      {
        title: 'QQ',
        dataIndex: 'qq',
      },
      {
        title: '状态',
        dataIndex: 'status',
        render: value => <span>{UserStatus[value]}</span>,
      },
      {
        title: '操作',
        render: record => {
          return (
            <Fragment>
              <a
                href="javascrpit:void(0);"
                onClick={(e) => {
                  this.showModal(e, record);
                }}
              >
                编辑
              </a>
              <Divider type="vertical" />
              <Popconfirm
                title="确认要删除当前用户信息吗？"
                onConfirm={e => {
                  this.handlerDelOnClick(e, record.id);
                }}
              >
                <a href="javascrpit:void(0);" style={{ color: 'red' }}>
                  删除
                </a>
              </Popconfirm>
            </Fragment>
          );
        },
      },
    ];

    return (
      <PageHeaderWrapper title="用户管理">
        <Card>
          {this.renderQueryForm()}

          <Block>
            <Button
              icon="plus"
              type="primary"
              onClick={(e) => {
                this.showModal(e , {});
              }}
            >
              新增
            </Button>
          </Block>
          <Table
            columns={columns}
            rowKey={'id'}
            dataSource={UserList.data}
            pagination={UserList.pagination}
            onChange={this.handlerTableOnChange}
            loading={loading}
          />
          <Modal
            title="编辑用户信息"
            centered={true}
            maskClosable={true}
            visible={this.state.visible}
            onCancel={() => {
              this.setState({ visible: false });
            }}
            onOk={this.handlerModalOnOk}
            confirmLoading={this.state.confirmLoading}
            destroyOnClose={true}
          >
            <EditUser
              wrappedComponentRef={formRef => (this.formRef = formRef)}
              detail={detail}
            />
          </Modal>
        </Card>
      </PageHeaderWrapper>
    );
  }
}

@Form.create()
export class EditUser extends PureComponent {

  compareToPassword = (rule, value, callback) => {
    const form = this.props.form;
    if (value && value !== form.getFieldValue('password')) {
      callback('两次密码输入不一致');
    } else {
      callback();
    }
  };

  handlerUploadOnChange=(imageurl)=>{
    console.log(imageurl);
    this.setState({
      avatar: imageurl
    })
  }

  roleToNameId(roleNameId){
        
    let newRoleNameId=[];

    roleNameId.map((item=>{
        let nameid = {
            label: item.roleName,
            value: item.id,
        }
        newRoleNameId.push(nameid);
    }));
    return newRoleNameId;
}

  render() {
    const { getFieldDecorator } = this.props.form;
    const { detail: { user , roleIds , roles} } = this.props;

    const flag = Object.keys(user).length == 0;

    const formItemLayout = {
      labelCol: {
        xs: { span: 4 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 12 },
        sm: { span: 12 },
      },
    };

    return (
      <div>
        <Form layout="horizontal" {...formItemLayout}>
          <FormItem label="头像">
            {getFieldDecorator('avatar',{
              initialValue: user.avatar
            })(
              <UnipicUpload 
                group={"user"}
                onChange={()=>{this.handlerUploadOnChange}}
                image={user.avatar}
              />
            )}
          </FormItem>
          <FormItem label="登录名">
            {getFieldDecorator('loginname', {
              initialValue: user.loginname,
              rules: [
                {
                  required: true,
                  message: '请输入登录名',
                },
              ],
            })(<Input placeholder="将会成为您唯一的登入名" disabled={!flag} />)}
          </FormItem>
          {flag ? (
            <div>
              <FormItem label="密 码">
                {getFieldDecorator('password', {
                  rules: [
                    {
                      required: true,
                      message: '请输入密码',
                    },
                  ],
                })(<Input.Password placeholder="请输入密码" />)}
              </FormItem>
              <FormItem label="确认密码">
                {getFieldDecorator('repassword', {
                  rules: [
                    {
                      required: true,
                      message: '请输入密码',
                    },
                    {
                      validator: this.compareToPassword,
                    },
                  ],
                })(<Input.Password placeholder="确认密码" />)}
              </FormItem>
            </div>
          ) : null}
          <FormItem label="真实姓名">
            {getFieldDecorator('realname', {
              initialValue: user.realname,
            })(<Input placeholder="真实姓名" />)}
          </FormItem>
          <FormItem label="Email">
            {getFieldDecorator('email', {
              initialValue: user.email,
            })(<Input placeholder="email" />)}
          </FormItem>
          <FormItem label="Q  Q">
            {getFieldDecorator('qq', {
              initialValue: user.qq,
            })(<Input placeholder="qq" />)}
          </FormItem>
          <FormItem label="选择角色">
            {
              getFieldDecorator('roleIds',{
                initialValue: flag ? [] : roleIds
              })(
                <Checkbox.Group options={this.roleToNameId(roles)} ></Checkbox.Group>
              )
            }
          </FormItem>
        </Form>
      </div>
    );
  }
}
