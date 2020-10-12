import { PureComponent } from "react";
import { connect } from 'dva';
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import Block from "@/custom/Block";
import { Form, Table, Card, Button, DatePicker } from "antd";
import moment from "moment";
import qs from 'qs';

const FormItem = Form.Item;

const { RangePicker } = DatePicker;
const dateFormat = 'YYYY-MM-DD';

@connect(({ operlog , loading })=>({
    operlog,
    loading: loading.models.operlog
}))
@Form.create()
export default class SystemLog extends PureComponent{

    state={
        formValues:{
            page: 1,
            count: 10,
        }
    }

    componentDidMount(){
        const{ dispatch} = this.props;

        dispatch({
            type: 'operlog/fetchAll',
            payload: {
                ...this.state.formValues
            }
        });
    }

    handlerTableOnChange = (pagination, filters, sorter) => {
        const { dispatch } = this.props;
        let { formValues } = this.state;

        formValues = {
            ...formValues,
            page: pagination.current,
            count: pagination.pageSize,
        };

        this.setState({
            formValues: {
              ...formValues,
            },
        });

        dispatch({
            type: 'operlog/fetchAll',
            payload: {
                ...formValues
            }
        });
    }

    handlerQueryOnClick=()=>{

        const { form, dispatch } = this.props;

        form.validateFields((err, fieldsValue) => {
            if(err) return ;

            let { formValues } = this.state;


            if(fieldsValue.datatime){

                const datatime = fieldsValue.datatime;
                formValues = {
                    ...formValues,
                    startTime: moment(datatime[0].format(dateFormat)).valueOf(),
                    endTime: moment(datatime[1].format(dateFormat)).valueOf(),
                }
            }
            this.setState({formValues: formValues});
            dispatch({
                type: 'operlog/fetchAll',
                payload: {
                    ...formValues
                }
            })
        });
    }

    handlerDownOnclick=()=>{
        const { dispatch } =this.props;
        dispatch({
            type: 'operlog/download',
            payload: {
                ...this.state.formValues
            }
        })
    }

    render(){

        const { operlog:{ LogList} , loading, form: {getFieldDecorator} } = this.props;

        const columns = [{
            title: '用户名',
            dataIndex: 'optorName'
        },{
            title: '用户操作',
            dataIndex: 'optName',
        },{
            title: '请求URI',
            dataIndex: 'optURI',
        },{
            title: '请求方式',
            dataIndex: 'optType',
        },{
            title: '请求参数',
            dataIndex: 'optDetail',
            render: (text, record) => (
                <div style={{ wordWrap: 'break-word', wordBreak: 'break-all', maxWidth: '200px' }}>
                    {text}
                </div>
            ),
        },{
            title: '耗时',
            dataIndex: 'runTime',
        },{
            title: '操作IP',
            dataIndex: 'optorIp',
        },{
            title: '创建时间',
            dataIndex: 'created',
            render: (val)=> <span>{moment(val).format('YYYY-MM-DD HH:mm:ss')}</span>
        }];

        return (
            <PageHeaderWrapper>

                <Card extra={<a onClick={this.handlerDownOnclick}>导出报表</a>}>
                    <Block>
                        <Form layout="inline">
                            <FormItem label="筛选日期">
                                {getFieldDecorator('datatime')(
                                    <RangePicker 

                                    />
                                )}
                            </FormItem>
                            <FormItem>
                                <Button type="primary" onClick={this.handlerQueryOnClick}>查询</Button>
                            </FormItem>
                        </Form>
                    </Block>
                    <Table 
                        columns={columns}
                        rowKey={'id'}
                        dataSource={LogList.data}
                        pagination={LogList.pagination}
                        onChange={this.handlerTableOnChange}
                    />
                </Card>
            </PageHeaderWrapper>
        )
    }
}