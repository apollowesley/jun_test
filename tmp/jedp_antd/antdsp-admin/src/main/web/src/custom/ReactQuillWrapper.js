import { PureComponent, Fragment } from "react";
import 'react-quill/dist/quill.snow.css';

import ReactQuill from 'react-quill';
import fetch from 'dva/fetch';
import AntdspConfig from '@/AntdspConfig'
import { Input } from "antd";

let uploadGroup = "";

export default class ReactQuillWrapper extends PureComponent{

    state={
        value: ""
    }

    componentDidMount(){
        uploadGroup = this.props.group;
    }

    componentWillReceiveProps(newProps){
        console.log(newProps);
        if(!newProps.value){
            return ;
        }
        this.setState({
            value: newProps.value
        });
    }

    toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
    
        [{ 'header': 1 }, { 'header': 2 }],               // custom button values
        [{ 'list': 'ordered' }, { 'list': 'bullet' }],
        [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
        [{ 'indent': '-1' }, { 'indent': '+1' }],          // outdent/indent
        [{ 'direction': 'rtl' }],                         // text direction
    
        [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
    
        [{ 'color': [] }, { 'background': [] }],          // dropdown with defaults from theme
        [{ 'font': [] }],
        [{ 'align': [] }],
        ['link', 'image',],
        ['clean']                                         // remove formatting button
    ];

    modules = {
        toolbar: {
            container: this.toolbarOptions, 
            handlers: {
                //重写图片上传方法
                image: function (value) {
                    let _this3 = this;
                    let fileInput = this.container.querySelector('input.ql-image[type=file]');
                    if (fileInput == null) {
                        fileInput = document.createElement('input');
                        fileInput.setAttribute('type', 'file');
                        fileInput.setAttribute('accept', 'image/png, image/gif, image/jpeg, image/bmp, image/x-icon');
                        fileInput.classList.add('ql-image');
                        fileInput.addEventListener('change', function () {
                            if (fileInput.files != null && fileInput.files[0] != null) {
                                if (fileInput.files[0].size > 1024 * 1024 * 2) {
                                    return message.error('图片大小不能超过2M');
                                }
                                let range = _this3.quill.getSelection(true);
    
                                let formData = new FormData();
                                formData.append('file', fileInput.files[0]);
                                fetch(`${AntdspConfig.AntdspUploadApi}/${uploadGroup}`,{
                                    method: 'POST',
                                    body: formData,
                                }).then((response)=>response.json()).then((data)=>{
                                    if(data.success){
                                        _this3.quill.insertEmbed(range.index, 'image', `${AntdspConfig.AntdspDownloadApi}/${ data.message}`);
                                    }
                                })
                            }
                        });
                        this.container.appendChild(fileInput);
                    }
                    fileInput.click();
                }
            }
        }
    }

    render(){
        return(
            <Fragment>
                <ReactQuill 
                    ref={"quillRef"}
                    value={this.state.value}
                    modules={this.modules}
                    onChange={(value)=>{
                        this.setState({value: value});
                        this.props.onChange(value)
                    }}
                    readOnly={this.props.readOnly||false}
                />
            </Fragment>
        )
    }
}