import React, { Fragment } from 'react';
import { Layout, Icon } from 'antd';
import GlobalFooter from '@/components/GlobalFooter';
import styles from './Footer.less'

const { Footer } = Layout;
const FooterView = () => (
  <Footer style={{ padding: 10, textAlign:'center' }}>
    <div >
      <span>Copyright <Icon type="copyright" /> 2019 </span>
      
      <strong><a href="http://www.jt-lee.cn">jt-lee.cn</a> </strong>
      <span> All Rights Reserved. </span>
      <span> 网站备案号: 豫ICP备19023996号-1 </span>
    </div>
  </Footer>
);
export default FooterView;
