import { PureComponent } from "react";
import PageHeaderWrapper from "@/components/PageHeaderWrapper";
import { Card, Form, Input, Button } from "antd";
import { connect } from "dva";
import ReactQuillWrapper from "@/custom/ReactQuillWrapper";
import router from 'umi/router';


const FormItem = Form.Item;

@connect(({article , loading})=>({
    article,
    loading: loading.models.article
}))
@Form.create()
export default class ArticleEdit extends PureComponent{

    state={
        confirmLoading: false,
    }

    componentDidMount(){
        const { dispatch, match } = this.props;
        let id = match.params.id || 0;
        if(id != 0){
            dispatch({
                type: 'article/fetchById',
                payload: {
                    id: id
                }
            });
        }
    }

    handlerSubmitOnClick=()=>{
        const { dispatch, form } = this.props;

        this.setState({confirmLoading: true});
        form.validateFields((err , fieldsValue)=>{
            if(err) return this.setState({confirmLoading: false}) ;

            let {Article} = this.props.article;

            Article = {
                ...Article,
                ...fieldsValue,
            }

            dispatch({
                type: 'article/save',
                payload: {
                    Article: Article
                },
                callback: (result)=>{
                    if(result.success){
                        router.push('/operation/article')
                    }else {
                        this.setState({
                            confirmLoading: false,
                        })
                    }
                }
            });
        });
    }

    render(){

        const { getFieldDecorator, setFieldsValue }= this.props.form;

        const { Article } = this.props.article;

        const formItemLayout = {
            labelCol:{
                xs: { span : 6},
                sm: { span : 6 },
            },
            wrapperCol:{
                xs: { span : 8},
                sm: { span : 8 },
            }
        }

        return (
            <PageHeaderWrapper title="文章编辑">
                <Card>
                    <Form layout="horizontal" {...formItemLayout}>
                        <FormItem label={"文章标题"}>
                            {getFieldDecorator('title',{
                                initialValue: Article.title,
                                rules: [{
                                    required: true,
                                    message: '请输入标题',
                                },{
                                    max:16,
                                    message: '标题长度不能超过16',
                                }]
                            })(
                                <Input placeholder="请输入文章标题"/>
                            )}
                        </FormItem>
                        <FormItem label={"文章副标题"}>
                            {getFieldDecorator('subTitle',{
                                initialValue: Article.subTitle,
                                rules: [{
                                    required: true,
                                    message: '请输入副标题',
                                },{
                                    max:16,
                                    message: '标题长度不能超过64',
                                }]
                            })(
                                <Input placeholder="请输入文章副标题"/>
                            )}
                        </FormItem>
                        <FormItem label={"文章来源"}>
                            {getFieldDecorator('articleFrom',{
                                initialValue: Article.articleFrom,
                                rules: [{
                                    required: true,
                                    message: '请输入文章来源',
                                }]
                            })(
                                <Input placeholder="请输入文章来源" />
                            )}
                        </FormItem>
                        <FormItem labelCol={{xs:6, sm:6}} wrapperCol={{xs:12, sm:12}} label={"文章内容"}>
                            {getFieldDecorator('content',{
                                initialValue: Article.content,
                            })(
                                <ReactQuillWrapper 
                                    group={"article"}
                                    onChange={(value)=>{
                                        setFieldsValue({
                                            content: value
                                        })
                                    }}
                                />
                            )}
                        </FormItem>
                        <FormItem colon={false} label="  ">
                            <Button type="primary" onClick={this.handlerSubmitOnClick} loading={this.state.confirmLoading}>确认提交</Button>
                        </FormItem>
                    </Form>
                </Card>
            </PageHeaderWrapper>
        )
    }
}