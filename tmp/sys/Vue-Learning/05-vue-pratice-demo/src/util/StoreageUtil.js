import $ from 'jquery';

function getUser(username) {
  const usersStr = sessionStorage.getItem('users');
  let resultUser = {};
  if (usersStr) {
    const users = JSON.parse(usersStr);
    $.each(users, (index, item) => {
      if (username === item.username) {
        resultUser = item;
        return false;
      }
      return true;
    });
  }
  return resultUser;
}

export default {
  getUser,
};
