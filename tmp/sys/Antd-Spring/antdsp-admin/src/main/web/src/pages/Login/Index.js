import { PureComponent } from 'react';
import { connect } from 'dva';
import { Card, Form, Input, Icon, Button, message } from 'antd';
import Block from '@/custom/Block';
import styles from './style.less';
import md5 from 'js-md5'
import moment from 'moment';

const FormItem = Form.Item;

@connect(({ login })=>({
    login
}))
@Form.create()
export default class Login extends PureComponent{

    state={
        btnLoading: false,
        captcha: moment().milliseconds()
    }

    componentDidMount(){
        document.body.addEventListener("keypress", (e)=>{
            if(e.keyCode != 13) return ;

            this.handlerLogin();
        });
    }

    handlerLogin=()=>{
        const { form, dispatch } = this.props;

        this.setState({btnLoading: true});

        form.validateFields((err , fieldsValue)=>{
            if(err) return this.setState({btnLoading: false});

            let password = fieldsValue.password;

            dispatch({
                type: 'login/login',
                payload:{
                    ...fieldsValue,
                    password: md5(password)
                },
                callback: ()=>{
                    this.setState({
                        btnLoading: false,
                        captcha: moment().milliseconds()
                    });
                }
            })

        });
    }

    changeCaptcha=()=>{
        this.setState({
            captcha: moment().milliseconds()
        });
    }

    render(){

        const { 
            form: { getFieldDecorator },
        } = this.props;
        
        return(
            <div>
                <div className={styles.main}>
                    <div className={styles.title}>Antd-Spring Login Page</div>
                    <div>
                        <Form layout="horizontal" >
                            <FormItem>
                                {getFieldDecorator('loginname',{
                                    initialValue: 'jt-lee',
                                    rules: [{
                                        required: true,
                                        message: '请输入登录名',
                                    }]
                                })(
                                <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="用户名" />
                                )}
                            </FormItem>
                            <FormItem>
                                {getFieldDecorator('password',{
                                    initialValue: '123456',
                                    rules: [{
                                        required: true,
                                        message: '请输入密码',
                                    }]
                                })(
                                <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="密  码" />
                                )}
                            </FormItem>
                            <FormItem>
                                {getFieldDecorator('code',{
                                    rules: [{
                                        required: true,
                                        message: '请输入验证码',
                                    }]
                                })(
                                <Input placeholder="验证码" />
                                )}
                            </FormItem>
                            <FormItem>
                                <img src={'/antdsp-api/captcha.jpg?'+this.state.captcha} onClick={this.changeCaptcha}/>
                                <a href="javascript:void(0);" className={styles.fresh} onClick={this.changeCaptcha}>点击刷新</a>
                            </FormItem>
                            <FormItem>
                                <Button loading={this.state.btnLoading} style={{width:'100%'}} onClick={this.handlerLogin}>登  录</Button>
                            </FormItem>
                        </Form>
                    </div>
                </div>
            </div>
        )
    }
}