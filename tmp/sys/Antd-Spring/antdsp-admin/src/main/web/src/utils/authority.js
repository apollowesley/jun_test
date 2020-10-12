import { setAuthorization } from './request';

// use localStorage to store the authority info, which might be sent from server in actual project.
export function getAuthority(str) {
  // return localStorage.getItem('antd-pro-authority') || ['admin', 'user'];
  const authorityString =
    typeof str === 'undefined' ? localStorage.getItem('antdsp-authority') : str;
  // authorityString could be admin, "admin", ["admin"]
  let authority;
  try {
    authority = JSON.parse(authorityString);
  } catch (e) {
    authority = authorityString;
  }
  if (typeof authority === 'string') {
    return [authority];
  }
  return authority || ['guest'];
}
export function setAuthority(authority,sessionId) {
  const proAuthority = typeof authority === 'string' ? [authority] : authority;
  if(sessionId) setAuthorization(sessionId);
  return localStorage.setItem('antdsp-authority', JSON.stringify(proAuthority));
}

