import { PureComponent } from "react";
import { Card, Row, Col, Layout } from "antd";
import ReactDOM from 'react-dom';
import PageHeaderWrapper from "@/components/PageHeaderWrapper";

const {Content } = Layout;

export default class SqlMonitor extends PureComponent{

    render(){

        return (

            <div style={{height:'100%',display:'flex',flexDirection:'column',minHeight:'80vh'}}>
                {/* <div style={{flex:'auto'}}>hearder</div>
                <div style={{flex:'auto'}}>Content</div>
                <div style={{flex:'auto'}}>Footer</div> */}
                <iframe style={{width:'100%',border:'0px',flex:1}} 
                    src={`/antdsp-api/druid/index.html`}
                ></iframe>
            </div>
        )
    }
}