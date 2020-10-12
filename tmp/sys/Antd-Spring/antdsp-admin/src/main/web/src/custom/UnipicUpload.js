import { PureComponent } from "react";
import { Upload, Icon, message } from "antd";
import AntdspConfig from "@/AntdspConfig";

export default class UnipicUpload extends PureComponent{

    state = {
        imgUrl: "",
        loading: false,
    }

    componentWillReceiveProps(newProps){
        const { value } = newProps;
        this.setState({
            imgUrl: value
        })
    }

    uploadUrl(group){
        return `${AntdspConfig.AntdspUploadApi}/${group}`;
    }

    handlerBeforeUpload=(file)=>{
        const isJPG = file.type === 'image/jpeg';
        if (!isJPG) {
          message.error('只能上传图片文件');
        }
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isLt2M) {
          message.error('图片大小必须小于2M!');
        }
        return isJPG && isLt2M;
    }

    handlerUploadOnChange=(info)=>{
        const { onChange } = this.props;
        if (info.file.status === 'uploading') {
            this.setState({ loading: true });
            return;
        }

        if (info.file.status === 'done') {
            const response = info.file.response;
            this.setState({loading: false});
            if(response.success){
                onChange(response.message);
            }
        }
    }

    render(){

        const{ group } = this.props;

        const {imgUrl} = this.state;

        return(
            <Upload
                listType="picture-card"
                showUploadList={false}
                action={this.uploadUrl(group)}
                beforeUpload={this.handlerBeforeUpload}
                onChange={this.handlerUploadOnChange}
            >
                {
                    !this.state.loading && imgUrl ? 
                        <img src={`${AntdspConfig.AntdspDownloadApi}/${imgUrl}`} style={{width:'200px'}}/>
                    :
                    <div>
                        <Icon type={this.state.loading ? 'loading' : 'plus'} />
                        <div className="ant-upload-text">Upload</div>
                    </div>
                }
            </Upload>
        )
    }
}