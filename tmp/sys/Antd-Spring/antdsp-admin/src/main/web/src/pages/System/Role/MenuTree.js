import { PureComponent } from "react";
import { Tree } from "antd";

const { TreeNode } = Tree;

export default class MenuTree extends PureComponent{

    constructor(props){
        super(props);
        this.state= {
            checkedKeys:  props.checkedKeys
        }    
    }

    renderTreeNodes=treeData=>{
        
        return treeData.map(item => {
            if (item.children) {
              return (
                <TreeNode title={item.name} key={item.id} dataRef={item}>
                  {this.renderTreeNodes(item.children)}
                </TreeNode>
              );
            }
            return <TreeNode  title={item.name} key={item.id}/>;
        });
    }

    handlerOnCheck=(checkedKeys , { halfCheckedKeys})=>{
        this.setState({
            checkedKeys: checkedKeys
        });
        this.props.handlerOnCheck(checkedKeys.concat(halfCheckedKeys));
    }

    render(){

        const { menus } = this.props;
        return(
            <Tree 
                checkable
                autoExpandParent={true}
                onCheck={this.handlerOnCheck}
                checkedKeys={this.state.checkedKeys}
            >
                {this.renderTreeNodes(menus)}
            </Tree>
        )
    }
}