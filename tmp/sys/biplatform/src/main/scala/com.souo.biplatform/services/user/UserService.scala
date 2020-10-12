package com.souo.biplatform.services.user

import com.souo.biplatform.model.User

import scala.concurrent.{ExecutionContext, Future}

/**
 * @author souo
 */
class UserService(userRuleDao: UserRuleDao)(implicit ec: ExecutionContext) {

  def isAdminUser(user: User): Future[Boolean] = {
    userRuleDao.isAdmin(user.login)
  }

}
