<template>
  <div>
    <el-row>
      <el-col
        :xs="22"
        :sm="22"
        :md="22"
        :lg="22"
        :xl="8"
        :offset="1"
      >
        <el-card>
          <div slot="header">
            <el-row>
              <el-col :span="12">
                <span><i class="el-icon-user-solid"></i> 个人信息</span>
              </el-col>
              <el-col
                :span="12"
                align="right"
              >
                <el-button
                  icon="el-icon-edit"
                  style="color:#222"
                  size="mini"
                  plain
                  type="text"
                  v-if="!edit.isEdit"
                  @click="edit.isEdit = !edit.isEdit"
                >编辑</el-button>
                <el-button
                  icon="el-icon-document"
                  style="color:#222"
                  size="mini"
                  plain
                  type="text"
                  v-if="edit.isEdit"
                  @click="update"
                >保存</el-button>
              </el-col>
            </el-row>
          </div>
          <div>
            <el-row>
              <el-col align="center">
                <el-avatar
                  :src="avatar()"
                  :size="100"
                ></el-avatar>
                <div>
                  <el-upload
                    class="avatar-uploader"
                    :action="uploadAva()"
                    :show-file-list="false"
                    :on-success="uploadSuccess"
                  >
                    <el-button
                      class="upload-ava-button"
                      icon="el-icon-edit"
                      circle
                      size="mini"
                      v-if="edit.isEdit"
                    ></el-button>
                  </el-upload>
                </div>
              </el-col>
              <el-col>
                <el-divider> <span style="font-style: italic">Welcome {{edit.detail.username}}</span> </el-divider>
              </el-col>
              <el-col>
                <el-row>
                  <el-col
                    :span="2"
                    style="line-height:28px"
                  >
                    <i class="el-icon-user"></i>
                  </el-col>
                  <el-col :span="10">
                    <span v-if="!edit.isEdit"> {{edit.detail.realName}} </span>
                    <el-input
                      size="mini"
                      clearable
                      v-model="edit.detail.realName"
                      v-if="edit.isEdit"
                      placeholder="请输入姓名"
                    ></el-input>
                  </el-col>
                </el-row>
              </el-col>
              <el-col><br></el-col>
              <el-col>
                <el-row>
                  <el-col
                    :span="2"
                    style="line-height:28px"
                  >
                    <i class="el-icon-star-off"></i>
                  </el-col>
                  <el-col :span="10">
                    <span v-if="!edit.isEdit"> {{edit.detail.nickName}} </span>
                    <el-input
                      size="mini"
                      clearable
                      v-model="edit.detail.nickName"
                      v-if="edit.isEdit"
                      placeholder="请输入昵称"
                    ></el-input>
                  </el-col>
                </el-row>
              </el-col>
              <el-col><br></el-col>
              <el-col>
                <el-row>
                  <el-col
                    :span="2"
                    style="line-height:28px"
                  >
                    <i class="el-icon-mobile-phone"></i>
                  </el-col>
                  <el-col :span="10">
                    <span v-if="!edit.isEdit">{{edit.detail.userPhone}}</span>
                    <el-input
                      size="mini"
                      clearable
                      v-model="edit.detail.userPhone"
                      v-if="edit.isEdit"
                      placeholder="请输入电话号"
                    ></el-input>
                  </el-col>
                </el-row>
              </el-col>
              <el-col><br></el-col>
              <el-col>
                <el-row>
                  <el-col
                    :span="2"
                    style="line-height:28px"
                  >
                    <i class="el-icon-chat-line-square"></i>
                  </el-col>
                  <el-col
                    :span="10"
                    style="font-style: italic"
                  >
                    <span v-if="!edit.isEdit">{{edit.detail.userEmail}}</span>
                    <el-input
                      size="mini"
                      clearable
                      v-model="edit.detail.userEmail"
                      v-if="edit.isEdit"
                      placeholder="请输入邮箱地址"
                    ></el-input>
                  </el-col>
                </el-row>
              </el-col>
              <el-col>
                <br>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col
        :xs="22"
        :sm="22"
        :md="22"
        :lg="22"
        :xl="12"
        :offset="1"
      >
        <el-card>
          <div slot="header">
            <span><i class="el-icon-tickets"></i> 近十次登陆记录</span>
          </div>
          <div>
            <el-table
              :data="records"
              stripe
              border
              size="mini"
            >
              <el-table-column
                prop="loginIp"
                label="登陆IP"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="ipLocation"
                label="归属地"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="osName"
                label="系统名称"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="osVersion"
                label="系统版本"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="borwserName"
                label="浏览器名称"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="borwserVersion"
                label="浏览器版本号"
                show-overflow-tooltip
              >
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="登陆时间"
                show-overflow-tooltip
              >
                <template slot-scope="scope">
                  {{$formatDateTime(scope.row.createTime)}}
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { userDetail, update, loginRecords } from "@/api/portalUser";
export default {
  name: 'center',
  data() {
    return {
      edit: {
        isEdit: false,
        detail: {

        }
      },
      records: []
    }
  },
  methods: {
    uploadAva() {
      return process.env.VUE_APP_BASE_API + '/hors/portal/user/uploadAvatar'
    },
    uploadSuccess(res) {
      if (res.header.code == '200') {
        this.edit.detail.avatarUrl = res.body
      } else {
        this.$message.fail(res.header.message)
      }
    },
    detail() {
      userDetail().then(res => {
        this.edit.detail = res
      })
    },
    avatar() {
      if (this.edit.detail.avatarUrl) {
        return process.env.VUE_APP_BASE_API + '/hors/portal/user/avatar?id=' + this.edit.detail.avatarUrl
      }
    },
    update() {
      update(this.edit.detail).then(res => {
        this.edit.detail = res
        this.edit.isEdit = false
      })
    }
  },
  created() {
    this.detail()
    loginRecords().then(res => {
      this.records = res
    })
  }
}
</script>

<style scoped>
.upload-ava-button {
  display: block;
  position: absolute;
  margin-top: -50px;
  margin-left: 26px;
}
</style>