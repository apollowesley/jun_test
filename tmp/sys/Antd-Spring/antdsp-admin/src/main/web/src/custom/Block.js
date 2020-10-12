import { PureComponent, Children } from "react";

import styles from "./style.less"

export default class Block extends PureComponent{

    render(){

        return (
            <div className={styles.block}>
                {this.props.children}
            </div>
        )
    }
}