import { routerRedux } from 'dva/router';
import { stringify } from 'qs';
import { fakeAccountLogin, getFakeCaptcha } from '@/services/api';
import { setAuthority } from '@/utils/authority';
import { getPageQuery } from '@/utils/utils';
import { reloadAuthorized } from '@/utils/Authorized';
import { loginAction, logoutAction } from '@/services/login'

export default {
  namespace: 'login',

  state: {
    status: undefined,
  },

  effects: {
    *login({ payload ,callback}, { call, put }) {
      const response = yield call(loginAction, payload);
      
      if(response.success){
        yield put({
          type: 'changeLoginStatus',
          payload: { authority: 'admin', sessionId: response.message },
        });

        reloadAuthorized();
        const urlParams = new URL(window.location.href);
        const params = getPageQuery();
        let { redirect } = params;
        if (redirect) {
          const redirectUrlParams = new URL(redirect);
          if (redirectUrlParams.origin === urlParams.origin) {
            redirect = redirect.substr(urlParams.origin.length);
            if (redirect.match(/^\/.*#/)) {
              redirect = redirect.substr(redirect.indexOf('#') + 1);
            }
            if(redirect.indexOf('/antdsp') == 0){
              redirect = redirect.substr(7);
            }
          } else {
            redirect = null;
          }
        }
        yield put(routerRedux.replace(redirect || '/'));
      }else {
        if(callback) callback(response);  
      }
    },

    *getCaptcha({ payload }, { call }) {
      yield call(getFakeCaptcha, payload);
    },

    *logout(_, { call , put }) {

      yield call(logoutAction);

      yield put({
        type: 'changeLoginStatus',
        payload: { authority: 'guest', sessionId: null},
      });
      reloadAuthorized();
      // redirect
      if (window.location.pathname !== '/antdsp/login') {
        yield put(
          routerRedux.replace({
            pathname: '/login',
            search: stringify({
              redirect: window.location.href,
            }),
          })
        );
      }
    },
  },

  reducers: {
    changeLoginStatus(state, { payload }) {
      setAuthority(payload.authority , payload.sessionId);
      return {
        ...state,
        status: payload.status,
        type: payload.type,
      };
    },
  },
};
