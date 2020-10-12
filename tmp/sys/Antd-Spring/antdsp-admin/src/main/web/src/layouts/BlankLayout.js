import React from 'react';
import DocumentTitle from 'react-document-title';
import styles from './BlankLayout.less';

export default ({ children }) => {
    return (<DocumentTitle title="登录">
        <div className={styles.container}>{children}</div>
    </DocumentTitle>)
}
